/***
 * Date: 07/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.fileUtils.MapperUtils;
import com.zepto.common.logger.LoggerUtil;
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.helper.mysql.MySqlZeptoBackendServiceHelper;
import com.zeptonow.commonlm.pojo.changeriderstatus.request.ChangeRiderStatusRequest;
import com.zeptonow.commonlm.pojo.createrider.response.CreateRiderResponse;
import com.zeptonow.commonlm.pojo.dms.response.BaseResponse;
import com.zeptonow.commonlm.pojo.riderlogin.SendOtpRequest;
import com.zeptonow.commonlm.pojo.riderlogin.VerifyOtpRequest;
import com.zeptonow.controllerlm.servicehelper.BifrostHelper;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.SchemaPath.postShiftWorkPlan;

public class RiderApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;
  private MySqlZeptoBackendServiceHelper mySqlZeptoBackendServiceHelper;
  private FakeValuesService fakeValuesService;

  public RiderApiHelper() {
    logger = new LoggerUtil(RiderApiHelper.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
    mySqlZeptoBackendServiceHelper = new MySqlZeptoBackendServiceHelper();
    fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder.
   * @param isVerifySchema Boolean value if schema validation is needed.
   * @param formParam HashMap containing all formParameters
   * @return RestAssured response for createRider api.
   * @author Deepak_Kumar
   */
  @SneakyThrows
  public Response createRider(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      HashMap<Object, Object> formParam) {

    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    new BifrostHelper();
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", GeneralConstants.ADMIN_AUTHORIZATION);

    Response response =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            RIDER_ENDPOINT.get("rider_profile"),
            null,
            null,
            formParam);
    Assert.assertTrue(
        response.getStatusCode() == 200,
        "Response of create rider api is not 200 , it is -" + response.getStatusCode());

    schemaValidators.validateResponseSchema(response, postShiftWorkPlan, isVerifySchema);

    logger.info("Change rider status to TRAINING_PENDING");
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    CreateRiderResponse createRiderResponse =
        mapper.readValue(data.toString(), CreateRiderResponse.class);
    String riderId = createRiderResponse.getId();
    changeRiderStatus(environment.getReqSpecBuilder(), riderId, "TRAINING_PENDING");

    return response;
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder
   * @param userId Rider username
   * @param userId
   * @return
   * @author Abhishek_Singh
   */
  public Response completeTraining(
      RequestSpecBuilder requestSpecBuilder, String userId, String riderId)
      throws InterruptedException {

    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    logger.info("Change rider status to TRAINING_IN_PROGRESS");
    Response response =
        changeRiderStatus(environment.getReqSpecBuilder(), riderId, "TRAINING_IN_PROGRESS");
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Rider status change api to make training in progress is giving"
            + response.getStatusCode());

    logger.info("Change rider status to TRAINING_COMPLETED");
    response = changeRiderStatus(environment.getReqSpecBuilder(), riderId, "TRAINING_COMPLETED");
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Rider status change api to make training completed is giving" + response.getStatusCode());

    return response;
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder
   * @param requestObj SendOtpRequest request pojo
   * @return send-otp response
   */
  public Response sendOtp(RequestSpecBuilder requestSpecBuilder, SendOtpRequest requestObj) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    response =
        RestUtils.post(requestSpecBuilder, headers, RIDER_ENDPOINT.get("send_otp"), requestObj);
    ExtentReportUtil.logStepInfo(response.asString());

    return response;
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder
   * @param requestObj VerifyOtpRequest request pojo
   * @return verify-otp response
   */
  public Response verifyOtp(RequestSpecBuilder requestSpecBuilder, VerifyOtpRequest requestObj) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("deviceUID", Faker.instance().name().lastName());

    response =
        RestUtils.post(requestSpecBuilder, headers, RIDER_ENDPOINT.get("verifyOtp"), requestObj);
    ExtentReportUtil.logStepInfo(response.asString());

    return response;
  }

  /**
   * delete rider
   *
   * @param requestSpecBuilder
   * @param riderId
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response deleteRider(
      RequestSpecBuilder requestSpecBuilder, String riderId, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    String apiPath = RIDER_ENDPOINT.get("deleteRider") + riderId;
    response = RestUtils.delete(requestSpecBuilder, headers, apiPath, null);
    ExtentReportUtil.logStepInfo(response.asString());
    return response;
  }

  /**
   * Method is used to get the change rider status request object
   *
   * @param riderId
   * @param status
   * @return
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public ChangeRiderStatusRequest createChangeRiderStatusRequest(String riderId, String status) {

    String userId =
        new MySqlZeptoBackendServiceHelper()
            .queryDb(String.format(DBQueries.getUserIdForRiderId, riderId))
            .getJSONObject(0)
            .get("user_id")
            .toString();
    return ChangeRiderStatusRequest.builder()
        .userId(userId)
        .riderId(riderId)
        .status(status)
        .build();
  }

  /**
   * Method is used to change the rider status
   *
   * @param requestSpecBuilder
   * @param riderId
   * @param status
   * @return
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public Response changeRiderStatus(
      RequestSpecBuilder requestSpecBuilder, String riderId, String status) {

    ChangeRiderStatusRequest changeRiderStatusRequest =
        createChangeRiderStatusRequest(riderId, status);
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Response response =
        RestUtils.put(
            requestSpecBuilder,
            null,
            RIDER_ENDPOINT.get("changeRiderStatus"),
            changeRiderStatusRequest);
    logger.info("Waiting for 1 sec to status get updated in DB");
    Thread.sleep(1000);
    return response;
  }

  /**
   * Updates the rider vehicle type from BIKE to required type
   *
   * @param requestSpecBuilder
   * @param riderId
   * @param vehicleType
   * @return
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public Response updateRiderVehicleType(
      RequestSpecBuilder requestSpecBuilder, String riderId, String vehicleType) {

    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", new BifrostHelper().getJwtToken());

    HashMap<String, Object> formData = new HashMap<>();
    formData.put("vehicleType", vehicleType);

    String apiPath = RIDER_ENDPOINT.get("rider_profile") + riderId;
    Response response = RestUtils.put(requestSpecBuilder, headers, apiPath, null, formData);
    return response;
  }

  /**
   * Deletes the rider from RMS
   *
   * @param reqSpecBuilder
   * @param riderId
   * @return @Ajay_Mishra
   */
  public Response deleteRiderFromRMS(RequestSpecBuilder reqSpecBuilder, String riderId) {

    Response response = null;
    reqSpecBuilder.setBaseUri(RMS_BASEURL);

    String apiPath = RIDER_ENDPOINT.get("deleteRiderFromRMS") + riderId;
    response = RestUtils.delete(reqSpecBuilder, null, apiPath, null);
    ExtentReportUtil.logStepInfo(response.asString());
    return response;
  }
}
