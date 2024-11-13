/***
 * Date: 08/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.controllerlm.servicehelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.zepto.api.restassured.Environment;
import com.zepto.common.fileUtils.MapperUtils;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.helper.api.RiderApiHelper;
import com.zeptonow.commonlm.helper.api.riderx.SelfSignupApiHelper;
import com.zeptonow.commonlm.helper.mysql.DbUtilApi;
import com.zeptonow.commonlm.helper.mysql.MySqlRmsHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.createrider.response.CreateRiderResponse;
import com.zeptonow.commonlm.pojo.dms.response.BaseResponse;
import com.zeptonow.commonlm.pojo.riderlogin.SendOtpRequest;
import com.zeptonow.commonlm.pojo.riderlogin.VerifyOtpRequest;
import com.zeptonow.commonlm.pojo.selfsignup.response.GetRiderDetailsByProfileIdResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.zeptonow.commonlm.constants.DBQueries.*;
import static com.zeptonow.commonlm.helper.mysql.DbUtilApi.rmsSchemaName;

public class RiderHelper {

  private static HashMap<Object, Object> formData = new HashMap<Object, Object>();
  private final LoggerUtil logger = new LoggerUtil(this.getClass());
  RiderApiHelper riderApiHelper = new RiderApiHelper();
  DbUtilApi dbUtilApi = new DbUtilApi();
  ObjectMapper mapper = new ObjectMapper();
  MySqlRmsHelper mySqlRmsHelper = new MySqlRmsHelper();
  private Environment environment = new Environment();
  private GlobalUtil globalUtil = new GlobalUtil();
  private SelfSignupApiHelper selfServeApiHelper = new SelfSignupApiHelper();

  /**
   * createDummyPdf this method will create a dummy pdf file
   *
   * @param tempFilePath file-path with file name
   * @return File pdf file
   * @throws FileNotFoundException
   * @throws DocumentException
   * @author Abhishek_Singh
   */
  public static File createDummyPdf(String tempFilePath)
      throws FileNotFoundException, DocumentException {

    // creating dummy .pdf file
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(tempFilePath));

    document.open();
    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    Chunk chunk = new Chunk("Hello World", font);

    document.add(chunk);
    document.close();

    return new File(tempFilePath);
  }

  /**
   * @param storeId store Id for which rider to be created
   * @param vehicleType BIKE/CYCLE
   * @param contractType
   * @param isLsq optional
   * @return HashMap<Object, Object> with all form data
   * @throws DocumentException
   * @throws FileNotFoundException
   * @author Deepak_Kumar
   */
  public HashMap<Object, Object> generateCreateRiderPayload(
      String storeId, String vehicleType, String contractType, String... isLsq)
      throws DocumentException, IOException {

    HashMap<Object, Object> requestFormData = new HashMap<>();

    // The FakeValueService class provides methods for generating random sequences
    FakeValuesService fakeValuesService =
        new FakeValuesService(new Locale("en-US"), new RandomService());

    // The Faker class allows us to use JavaFaker's fake data classes.
    Faker faker = new Faker(new Random(24));

    // creating temp folder
    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator;
    File tmpFolder = new File(tempFilePath);
    tmpFolder.mkdir();

    // creating dummy .pdf file for rider document proof
    File docFile = new File(tempFilePath + "riderCreate.pdf");
    if (docFile.exists()) docFile.delete();

    docFile = createDummyPdf(tempFilePath + "riderCreate.pdf");

    File riderImage = new File(tempFilePath + "riderImage.jpeg");
    // creating jpeg for rider image
    if (riderImage.exists()) riderImage.delete();

    riderImage = globalUtil.createDummyImage(tempFilePath + "riderImage.jpeg");

    if (!vehicleType.equalsIgnoreCase("CYCLE")) {
      // initialising variable if vehicle type is bike
      String vehicleNumber = fakeValuesService.bothify("??##??####").toUpperCase();
      String license = fakeValuesService.bothify("?###?#?###").toUpperCase();

      requestFormData.put("vehicleNumber", vehicleNumber);
      requestFormData.put("license", license);
      requestFormData.put("licensePdf", docFile);
      requestFormData.put("licenseExpiryDate", "2024-09-04");
    }
    if (contractType.equalsIgnoreCase("RIDER_3PL")) {
      requestFormData.put("rider3PlVendorId", "57dab9b7-4c42-471f-83e3-5ba6114e2135");
    } else if (isLsq.length == 0 || isLsq[0].equals("")) {
      requestFormData.put("rider3PlVendorId", "8bf6e8d8-3f4c-492d-b6ee-332ac3cb0b16");
    }

    requestFormData.put("riderImage", riderImage);
    requestFormData.put("aadhaarPdf", docFile);
    requestFormData.put("panPdf", docFile);
    requestFormData.put("bankDetailsPdf", docFile);
    requestFormData.put("firstName", "testLastmile");
    requestFormData.put("lastName", "automation");
    requestFormData.put("dob", "1992-09-28");
    requestFormData.put("storeId", storeId);
    requestFormData.put("contractType", contractType);
    requestFormData.put("covidVaccinationStatus", "NOT_VACCINATED");
    requestFormData.put("riderCohortId", "33af3945-58f2-472b-8291-235bdb4fa0f0");
    requestFormData.put("permanentAddress", faker.address().streetAddress());
    requestFormData.put("permanentAddressLandmark", faker.address().streetName());
    requestFormData.put("currentAddress", faker.address().streetAddress());
    requestFormData.put("aadhaarNumber", fakeValuesService.regexify("[1-9]{1}[0-9]{11}"));
    requestFormData.put("panNumber", fakeValuesService.bothify("?????####?").toUpperCase());
    requestFormData.put("pincode", "222007");
    requestFormData.put("bankName", "HDFC");
    requestFormData.put("bankAccountNumber", fakeValuesService.regexify("[0-9]{11}"));
    requestFormData.put("beneficiaryName", "testAutomation");
    requestFormData.put("ifscCode", fakeValuesService.bothify("??????#####").toUpperCase());
    requestFormData.put("fatherName", "zepto");
    requestFormData.put("nomineeFullName", "zepto");
    requestFormData.put("nomineeSex", "MALE");
    requestFormData.put("nomineeDob", "1982-09-06");
    requestFormData.put("nomineeRelationshipWithRider", "parent");
    requestFormData.put("vehicleType", vehicleType);
    requestFormData.put("mobileNumber", fakeValuesService.regexify("[1-5]{1}[0-9]{9}"));
    requestFormData.put("onboardingTag", "Virtual");
    requestFormData.put("onboardingCentre", "ebf969c1-1f01-40cc-86e4-b475ed8e3377");
    requestFormData.put("riderGender", "MALE");
    requestFormData.put("maritalStatus", "SINGLE");
    setRiderDetails(requestFormData);
    return requestFormData;
  }

  /**
   * @param storeId store Id for which rider to be created
   * @param vehicleType BIKE/CYCLE
   * @param contractType
   * @param mobileNumber
   * @param isLsq optional
   * @return HashMap<Object, Object> with all form data
   * @throws DocumentException
   * @throws FileNotFoundException
   */
  public HashMap<Object, Object> generateCreateRiderPayloadWithMobileNumber(
      String storeId, String vehicleType, String contractType, String mobileNumber, String... isLsq)
      throws DocumentException, IOException {

    HashMap<Object, Object> requestFormData = new HashMap<>();

    // The FakeValueService class provides methods for generating random sequences
    FakeValuesService fakeValuesService =
        new FakeValuesService(new Locale("en-US"), new RandomService());

    // The Faker class allows us to use JavaFaker's fake data classes.
    Faker faker = new Faker(new Random(24));

    // creating temp folder
    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator;
    File tmpFolder = new File(tempFilePath);
    tmpFolder.mkdir();

    // creating dummy .pdf file for rider document proof
    File docFile = createDummyPdf(tempFilePath + "riderCreate.pdf");

    // creating jpeg for rider image
    File riderImage = globalUtil.createDummyImage(tempFilePath + "riderImage.jpg");

    if (!vehicleType.equalsIgnoreCase("CYCLE")) {
      // initialising variable if vehicle type is bike
      String vehicleNumber = fakeValuesService.bothify("??##??####").toUpperCase();
      String license = fakeValuesService.bothify("?###?#?###").toUpperCase();

      requestFormData.put("vehicleNumber", vehicleNumber);
      requestFormData.put("license", license);
      requestFormData.put("licensePdf", docFile);
      requestFormData.put("licenseExpiryDate", "2024-09-04");
    }
    if (contractType.equalsIgnoreCase("RIDER_3PL")) {
      requestFormData.put("rider3PlVendorId", "57dab9b7-4c42-471f-83e3-5ba6114e2135");
    } else if (isLsq.length == 0) {
      requestFormData.put("rider3PlVendorId", "8bf6e8d8-3f4c-492d-b6ee-332ac3cb0b16");
    }

    requestFormData.put("riderImage", riderImage);
    requestFormData.put("aadhaarPdf", docFile);
    requestFormData.put("panPdf", docFile);
    requestFormData.put("bankDetailsPdf", docFile);
    requestFormData.put("firstName", "test-lastmile");
    requestFormData.put("lastName", "automation");
    requestFormData.put("dob", "1992-09-28");
    requestFormData.put("storeId", storeId);
    requestFormData.put("contractType", contractType);
    requestFormData.put("covidVaccinationStatus", "NOT_VACCINATED");
    requestFormData.put("riderCohortId", "33af3945-58f2-472b-8291-235bdb4fa0f0");
    requestFormData.put("permanentAddress", faker.address().streetAddress());
    requestFormData.put("permanentAddressLandmark", faker.address().streetName());
    requestFormData.put("currentAddress", faker.address().streetAddress());
    requestFormData.put("aadhaarNumber", fakeValuesService.regexify("[1-9]{1}[0-9]{11}"));
    requestFormData.put("panNumber", fakeValuesService.bothify("?????####?").toUpperCase());
    requestFormData.put("pincode", "222007");
    requestFormData.put("bankName", "HDFC");
    requestFormData.put("bankAccountNumber", fakeValuesService.regexify("[0-9]{11}"));
    requestFormData.put("beneficiaryName", "testAutomation");
    requestFormData.put("ifscCode", fakeValuesService.bothify("??????#####").toUpperCase());
    requestFormData.put("fatherName", "zepto");
    requestFormData.put("nomineeFullName", "zepto");
    requestFormData.put("nomineeSex", "MALE");
    requestFormData.put("nomineeDob", "1982-09-06");
    requestFormData.put("nomineeRelationshipWithRider", "parent");
    requestFormData.put("vehicleType", vehicleType);
    requestFormData.put("mobileNumber", mobileNumber);
    requestFormData.put("onboardingTag", "Virtual");
    requestFormData.put("onboardingCentre", "ebf969c1-1f01-40cc-86e4-b475ed8e3377");
    requestFormData.put("riderGender", "MALE");
    requestFormData.put("maritalStatus", "SINGLE");
    setRiderDetails(requestFormData);
    return requestFormData;
  }

  /**
   * createRiderAndCompleteTraining this function create a new rider and complete his training.
   *
   * @param storeId storeId to which rider will be mapped
   * @param vehicleType BIKE/CYCLE
   * @param contractType
   * @param isLsq optional
   * @return CreateRiderResponse
   * @throws DocumentException
   * @throws IOException
   * @author Abhishek_Singh
   */
  public CreateRiderResponse createRiderAndCompleteTraining(
      String storeId, String vehicleType, String contractType, String... isLsq)
      throws DocumentException, IOException, InterruptedException {

    Response res = null;
    // creating rider and asserting response
    HashMap<Object, Object> formData =
        generateCreateRiderPayload(storeId, vehicleType, contractType, isLsq);
    setRiderDetails(formData);
    res = riderApiHelper.createRider(environment.getReqSpecBuilder(), Boolean.FALSE, formData);
    if (res.getStatusCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
      logger.info("Retrying rider creation due to " + HttpStatus.SC_UNPROCESSABLE_ENTITY);
      res = riderApiHelper.createRider(environment.getReqSpecBuilder(), Boolean.FALSE, formData);
    }
    Assert.assertTrue(
        res.getStatusCode() == 200,
        "Response of create rider api is not 200 , it is -" + res.getStatusCode());

    BaseResponse baseResponse = MapperUtils.deserialize(res.asString(), BaseResponse.class);
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    CreateRiderResponse createRiderResponse =
        mapper.readValue(data.toString(), CreateRiderResponse.class);

    // complete mandatory training for rider
    res =
        riderApiHelper.completeTraining(
            environment.getReqSpecBuilder(),
            createRiderResponse.getRiderCode(),
            createRiderResponse.getId());
    Assert.assertTrue(
        res.getStatusCode() == 200,
        "Response code for complete training is not 200 it is " + res.getStatusCode());
    Awaitility.await()
        .atMost(30, TimeUnit.SECONDS)
        .pollInterval(2, TimeUnit.SECONDS)
        .until(
            () -> {
              JSONArray getRiderStatus =
                  new MySqlRmsHelper()
                      .queryDb(
                          String.format(
                              DBQueries.getRiderStatusFromRiderId, createRiderResponse.getId()));
              return getRiderStatus.length() > 0
                  && getRiderStatus
                      .getJSONObject(0)
                      .getString("status")
                      .equals("TRAINING_COMPLETED");
            });
    return createRiderResponse;
  }

  /**
   * createRiderWithoutTraining this function create a new rider without starting his training.
   *
   * @param storeId storeId to which rider will be mapped
   * @param vehicleType BIKE/CYCLE
   * @param contractType
   * @param isLsq optional
   * @return CreateRiderResponse
   * @throws DocumentException
   * @throws IOException
   * @author Abhishek_Singh
   */
  public CreateRiderResponse createRiderAndMakeTrainingInProgress(
      String storeId, String vehicleType, String contractType, String... isLsq)
      throws DocumentException, IOException, InterruptedException {

    Response response = null;
    // creating rider and asserting response
    HashMap<Object, Object> formData =
        generateCreateRiderPayload(storeId, vehicleType, contractType, isLsq);
    response = riderApiHelper.createRider(environment.getReqSpecBuilder(), Boolean.FALSE, formData);
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    CreateRiderResponse createRiderResponse =
        mapper.readValue(data.toString(), CreateRiderResponse.class);

    // start mandatory training for rider and make it is progress
    logger.info("Change rider status to TRAINING_IN_PROGRESS");
    String riderId = createRiderResponse.getId();
    response =
        riderApiHelper.changeRiderStatus(
            environment.getReqSpecBuilder(), riderId, "TRAINING_IN_PROGRESS");

    Assert.assertTrue(
        response.getStatusCode() == 200,
        "Response code for start training is not 200 it is " + response.getStatusCode());
    Awaitility.await()
        .atMost(30, TimeUnit.SECONDS)
        .pollInterval(2, TimeUnit.SECONDS)
        .until(
            () -> {
              JSONArray getRiderStatus =
                  new MySqlRmsHelper()
                      .queryDb(
                          String.format(
                              DBQueries.getRiderStatusFromRiderId, createRiderResponse.getId()));
              return getRiderStatus.length() > 0
                  && getRiderStatus
                      .getJSONObject(0)
                      .getString("status")
                      .equals("TRAINING_IN_PROGRESS");
            });
    return createRiderResponse;
  }

  /**
   * This function will create rider with given mobile number
   *
   * @param storeId
   * @param vehicleType
   * @param contractType
   * @param mobileNumber
   * @param isLsq optional
   * @return
   * @throws DocumentException
   * @throws IOException
   * @author Abhishek_Singh
   */
  public CreateRiderResponse createRiderWithGivenMobileNumber(
      String storeId, String vehicleType, String contractType, String mobileNumber, String... isLsq)
      throws DocumentException, IOException, InterruptedException {

    Response response = null;
    // creating rider and asserting response
    HashMap<Object, Object> formData =
        generateCreateRiderPayloadWithMobileNumber(
            storeId, vehicleType, contractType, mobileNumber, isLsq);
    response = riderApiHelper.createRider(environment.getReqSpecBuilder(), Boolean.FALSE, formData);
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    CreateRiderResponse createRiderResponse =
        mapper.readValue(data.toString(), CreateRiderResponse.class);

    Thread.sleep(3000L);
    return createRiderResponse;
  }

  /**
   * generateRiderAuthToken will return the bearer token for rider
   *
   * @param requestSpecBuilder RequestSpecBuilder
   * @param mobileNumber mobileNumber of Rider
   * @return auth token for rider
   */
  @SneakyThrows
  public String generateRiderAuthToken(
      RequestSpecBuilder requestSpecBuilder, String mobileNumber, String... riderOtp) {

    Response response = null;
    // Sending otp and verifying response
    SendOtpRequest sendOtpRequestPayload =
        SendOtpRequest.builder().mobileNumber(mobileNumber).build();
    response = riderApiHelper.sendOtp(requestSpecBuilder, sendOtpRequestPayload);
    Assert.assertTrue(
        response.getStatusCode() == 200,
        "Response code for send otp is not 200 it is " + response.getStatusCode());
    Assert.assertTrue(
        response.jsonPath().getString("data.msg").equalsIgnoreCase("OTP sent successfully"),
        "Otp message is not correct");

    // Fetching the otp from Database and verifying with verify otp api
    String otp =
        new MySqlRmsHelper()
            .queryDb(String.format(DBQueries.GET_OTP_TOKEN, mobileNumber))
            .getJSONObject(0)
            .get("token")
            .toString();
    VerifyOtpRequest verifyOtpRequest =
        VerifyOtpRequest.builder().otpToken(otp).mobileNumber(mobileNumber).build();
    response = riderApiHelper.verifyOtp(requestSpecBuilder, verifyOtpRequest);
    Assert.assertTrue(
        response.getStatusCode() == 200,
        "Response code for verify otp is not 200 it is " + response.getStatusCode());
    return response.jsonPath().getString("data.token");
  }

  /**
   * This function will create a new rider, comolete training , generate, verify otp and return
   * token
   *
   * @param storeId
   * @param riderType
   * @param vehicleType
   * @param isLsq (optional)
   * @return riderdata HashMap containing riderId and auth token
   * @throws DocumentException
   * @throws IOException
   * @author Abhishek_Singh
   */
  public HashMap<String, String> createRiderAndGenerateAuthToken(
      String storeId, String riderType, String vehicleType, String... isLsq)
      throws DocumentException, IOException, InterruptedException {

    Environment environment = new Environment();
    CreateRiderResponse responseObj =
        createRiderAndCompleteTraining(storeId, vehicleType, riderType, isLsq);
    String token =
        generateRiderAuthToken(environment.getReqSpecBuilder(), responseObj.getMobileNumber());
    String riderId = responseObj.getId();
    String riderUserName = responseObj.getRiderCode();
    String riderFullName = responseObj.getFullName();
    String riderMobile = responseObj.getMobileNumber();
    String riderCode = responseObj.getRiderCode();
    logger.info("Part Time Rider auth token - " + token);
    HashMap<String, String> riderData = new HashMap<>();
    riderData.put("riderId", riderId);
    riderData.put("token", token);
    riderData.put("riderUserName", riderUserName);
    riderData.put("riderFullName", riderFullName);
    riderData.put("riderMobile", riderMobile);
    riderData.put("riderCode", riderCode);
    return riderData;
  }

  /**
   * This function will updated the created_on date in user table for given new riderID to make
   * rider inactive
   *
   * @param riderId Rider ID which needs to make inactive
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public void markNewRiderAsInActive(String riderId) throws SQLException {

    JSONArray queryResult =
        dbUtilApi.dbQuery(
            environment.getReqSpecBuilder(),
            "select user_id  from "
                + rmsSchemaName
                + ".rider_profile ri  where ri.id  = '"
                + riderId
                + "'");
    String userId = queryResult.getJSONObject(0).getString("user_id");
    mySqlRmsHelper.executeInsertUpdateQuery(String.format(updateUserCreatedOn, userId));
    // dbUtilApi.dbQuery(environment.getReqSpecBuilder(), "update " + zeptoBackendSchemaName +
    // ".user set created_on = '2021-01-01 18:37:28.822 +0530' where id = '" + userId + "'");
    mySqlRmsHelper.executeInsertUpdateQuery(String.format(updateRiderToInvalid, riderId));
    logger.info("Rider with userId - " + userId + " marked as inactive");
    globalUtil.deleteRedisKey("rider_profile_id:" + riderId);
  }

  /**
   * This function will set column is_active and is_valid in rider table to true
   *
   * @param riderId
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public void markRiderIsActiveAndIsValid(String riderId) {

    mySqlRmsHelper.executeInsertUpdateQuery(String.format(updateRiderToValid, riderId));
    globalUtil.deleteRedisKey("rider_profile_id:" + riderId);
    // dbUtilApi.dbQuery(environment.getReqSpecBuilder(), "update " + rmsSchemaName +
    // ".rider_profile set is_active = true , is_valid = true where id = '" + riderId + "'");

  }

  /**
   * This function will assert the expected shift id,week off with database
   *
   * @param shiftID
   * @param weekOff
   * @param riderId
   * @author Abhishek_Singh
   */
  public void verifyRiderShiftAndWeekOff(String shiftID, String weekOff, String riderId)
      throws SQLException {

    String actualShiftId =
        mySqlRmsHelper
            .queryDb(String.format(DBQueries.getShiftByRiderId, riderId))
            .getJSONObject(0)
            .get("shift_id")
            .toString();
    String actualWeekOff =
        mySqlRmsHelper
            .queryDb(String.format(DBQueries.getWeekOffRiderId, riderId))
            .getJSONObject(0)
            .get("week_off_day")
            .toString();
    Assert.assertTrue(
        actualShiftId.equalsIgnoreCase(shiftID), "Shift id for rider is not matching in database");
    Assert.assertTrue(
        actualWeekOff.equalsIgnoreCase(weekOff), "Week off is not matching in database");
  }

  /**
   * This method will return the rider details form data in HashMap<Object, Object> format
   *
   * @return
   */
  public HashMap<Object, Object> getRiderDetails() {
    return this.formData;
  }

  /**
   * This method will set the form data of rider details in HashMap<Object, Object> format
   *
   * @return
   */
  public void setRiderDetails(HashMap<Object, Object> formData) {
    this.formData = formData;
  }

  /**
   * Verifies the rider vehicle type
   *
   * @param riderId
   * @param expectedVehicleType
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public void verifyRiderDetails(String riderId, String expectedVehicleType) {

    new BifrostHelper();
    Response response =
        selfServeApiHelper.getRiderDetailsByProfileId(
            environment.getReqSpecBuilder(),
            riderId,
            GeneralConstants.ADMIN_AUTHORIZATION,
            Boolean.TRUE);
    Assert.assertTrue(
        response.getStatusCode() == HttpStatus.SC_OK,
        "Response status code of get rider details by profile Id api is not "
            + HttpStatus.SC_OK
            + " it is "
            + response.getStatusCode());

    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    GetRiderDetailsByProfileIdResponse getRiderDetails =
        mapper.readValue(data.toString(), GetRiderDetailsByProfileIdResponse.class);

    Assert.assertEquals(
        getRiderDetails.getVehicleType(), expectedVehicleType, "Vehicle type does not match.");
  }
}
