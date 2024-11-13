/***
 * Date: 16/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.controllerlm.servicehelper;

import com.opencsv.CSVWriter;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.logger.LoggerUtil;
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.commonlm.helper.api.ShiftAdherenceApiHelper;
import com.zeptonow.commonlm.helper.mysql.DbUtilApi;
import com.zeptonow.commonlm.pojo.shiftadherence.ridercontractshiftplan.response.PostRiderContractShiftPlanResponse;
import com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.request.PostRiderWorkPlanRequest;
import com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.subclass.RiderShift;
import com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.subclass.RiderWeekOff;
import com.zeptonow.commonlm.pojo.shiftadherence.shiftplan.response.PostShiftPlanResponse;
import com.zeptonow.commonlm.pojo.shiftadherence.weekoffplan.response.GetAvailableWeekOffResponse;
import com.zeptonow.commonlm.pojo.shiftadherence.weekoffplan.response.PostWeekOffPlanResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.testng.Assert;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static com.zeptonow.commonlm.helper.mysql.DbUtilApi.rmsSchemaName;

/**
 * @author Abhishek_Singh @Date 18/08/22
 */
public class ShiftAdherenceHelper extends TestBase {

  private final LoggerUtil logger = new LoggerUtil(ShiftAdherenceHelper.class);
  DbUtilApi dbUtilApi = new DbUtilApi();

  private ShiftAdherenceApiHelper shiftAdherenceApiHelper = new ShiftAdherenceApiHelper();

  private RiderHelper riderHelper = new RiderHelper();

  /**
   * createShiftPlanCsvFile method create a csv by fetching the rows from env.conf file using
   * configKeys.
   *
   * @param configKeys List of keys whoose value needs to be fetched from env.conf file
   * @param fileName CSV fileName
   * @param dateType currentDate/pastDate/FutureDate
   * @throws IOException
   * @author Abhishek_Singh
   */
  public File createShiftPlanCsvFile(List<String> configKeys, String fileName, String dateType)
      throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();

    // calendar instance for future date
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int weekday = cal.get(Calendar.DAY_OF_WEEK);
    int days = Calendar.SUNDAY - weekday;

    if (days < 0) {
      days += 7;
    }

    cal.add(Calendar.DAY_OF_YEAR, days);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("IST"));
    String currentTimeStamp = sdf.format(new Date()) + "T00:00:00Z";
    String futureDateTimeStamp = sdf.format(cal.getTime()) + "T00:00:00Z";

    switch (dateType) {
      case "currentDate":
        for (String key : configKeys) {
          List<String> row = environment.getGlobalTestData().getStringList(key);

          if (!row.contains("date")) {
            row.set(0, currentTimeStamp);
          }

          rows.add(row.stream().toArray(String[]::new));
        }

        break;
      case "pastDate":
        for (String key : configKeys) {
          rows.add(
              environment.getGlobalTestData().getStringList(key).stream().toArray(String[]::new));
        }

        break;

      case "futureDate":
        for (String key : configKeys) {
          List<String> row = environment.getGlobalTestData().getStringList(key);

          if (!row.contains("date")) {
            row.set(0, futureDateTimeStamp);
          }

          rows.add(row.stream().toArray(String[]::new));
        }

        break;
      default:
        for (String key : configKeys) {
          rows.add(
              environment.getGlobalTestData().getStringList(key).stream().toArray(String[]::new));
        }

        break;
    }

    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;
    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();

    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }

    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);

    return new File(tempFilePath);
  }

  /**
   * createShiftPlanCsvFile method create a csv by fetching the rows from env.conf file using
   * configKeys and make index value as null.
   *
   * @param configKeys List of keys whoose value needs to be fetched from env.conf file
   * @param fileName CSV fileName
   * @param index index value whose key values needs to be null
   * @throws IOException
   * @author Abhishek_Singh
   */
  public File createShiftPlanWIthNullValuesCsvFile(
      List<String> configKeys, int index, String fileName) throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();

    for (String key : configKeys) {
      List<String> row = environment.getGlobalTestData().getStringList(key);

      if (!row.contains("date")) {
        row.set(index, "");
      }

      rows.add(row.stream().toArray(String[]::new));
    }

    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;
    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();

    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }

    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);

    return new File(tempFilePath);
  }

  /**
   * This method compare expected csv file data with the actual shift-plan api response
   *
   * @param expectedCsvFile CSV file which was passed as an input to shift plan api.
   * @param actualResponse Response of shift plan api
   * @throws IOException
   * @author Abhishek_Singh
   */
  public void verifyExpectedAndActualResponseShiftPlan(
      File expectedCsvFile, Response actualResponse) throws IOException {

    PostShiftPlanResponse[] postShiftPlanActualResponse =
        actualResponse.getBody().as(PostShiftPlanResponse[].class);

    ArrayList<PostShiftPlanResponse> postShiftPlanExpectedResponse = new ArrayList<>();

    // Reading the csv file and adding the row wise details to ArrayList
    BufferedReader reader = new BufferedReader(new FileReader(expectedCsvFile));
    String line = "";
    int iteration = 0;

    line = reader.readLine();

    while (!StringUtils.isEmpty(line)) {
      if (iteration == 0) {
        iteration++;
      } else {
        line.replaceAll("\"", "");
        String csvData[] = line.split(",");
        postShiftPlanExpectedResponse.add(
            new PostShiftPlanResponse(csvData[0], csvData[1], csvData[2], csvData[3]));
      }

      line = reader.readLine();
    }

    // Comparing expectedResponse details and actualResponse details
    iteration = 0;

    for (PostShiftPlanResponse obj : postShiftPlanExpectedResponse) {

      ExtentReportUtil.logStepInfo(
          "Response data validation " + obj.equals(postShiftPlanActualResponse[iteration]) + "");
      Assert.assertTrue(obj.equals(postShiftPlanActualResponse[iteration]));
      iteration++;
    }
  }

  /**
   * This method compare expected csv file data with the actual get available shift api response
   *
   * @param expectedCsvFile CSV file which was passed as an input to shift plan api.
   * @param actualResponse Response of get available shift api
   * @throws IOException
   * @author Abhishek_Singh
   */
  public void verifyExpectedAndActualGetAvailableShiftResponse(
      File expectedCsvFile, Response actualResponse) throws IOException {

    PostShiftPlanResponse[] postShiftPlanActualResponse =
        actualResponse.getBody().as(PostShiftPlanResponse[].class);

    ArrayList<PostShiftPlanResponse> postShiftPlanExpectedResponse = new ArrayList<>();

    // Reading the csv file and adding the row wise details to ArrayList
    BufferedReader reader = new BufferedReader(new FileReader(expectedCsvFile));
    String line = "";
    int iteration = 0;

    line = reader.readLine();

    while (!StringUtils.isEmpty(line)) {
      if (iteration == 0) {
        iteration++;
      } else {
        line.replaceAll("\"", "");
        String csvData[] = line.split(",");
        postShiftPlanExpectedResponse.add(
            new PostShiftPlanResponse(csvData[0], csvData[1], csvData[2], csvData[3]));
      }

      line = reader.readLine();
    }

    // Comparing expectedResponse details and actualResponse details
    // Actual response may contain more shift than expected response that's why we are adding nested
    // loop to verify only expected shift id details
    for (PostShiftPlanResponse obj : postShiftPlanExpectedResponse) {
      boolean isFound = Boolean.FALSE;
      // Iterating actual response array to find the json object with same shift id as expected
      // shift id
      actualResponseLoop:
      for (int i = 0; i <= postShiftPlanActualResponse.length; i++) {
        if (obj.getShiftId() == postShiftPlanActualResponse[i].getShiftId()) {
          ExtentReportUtil.logStepInfo(
              "Response data validation " + obj.equals(postShiftPlanActualResponse[i]) + "");
          Assert.assertTrue(
              obj.getCount() == postShiftPlanActualResponse[i].getCount(),
              "Shift Count is not matching");
          Assert.assertTrue(
              obj.getStoreId().equalsIgnoreCase(postShiftPlanActualResponse[i].getStoreId()),
              "Store id is not matching");
          isFound = Boolean.TRUE;
          break actualResponseLoop;
        }
      }

      Assert.assertTrue(
          isFound,
          "Shift details for shift id " + obj.getShiftId() + " not found in actual response");
    }
  }

  /***
   * deleteExistingRiderFromStoreAndShift this function will delete all the tagged rider for given storeId, shiftId
   * @author Abhishek_Singh
   * @param storeId
   * @param shiftId
   */

  public void deleteExistingRiderFromStoreAndShift(String storeId, String shiftId) {

    // fetch the list of rider for the given storeId and shiftID
    JSONArray queryResult =
        dbUtilApi.dbQuery(
            environment.getReqSpecBuilder(),
            "select rs.rider_id from "
                + rmsSchemaName
                + ".rider_shift rs join rider r on rs.rider_id =r.id where r.store_id ='"
                + storeId
                + "'"
                + " and shift_id ="
                + shiftId);

    for (int i = 0; i < queryResult.length(); i++) {

      String riderId = queryResult.getJSONObject(i).getString("rider_id");
      JSONArray riderShiftId =
          dbUtilApi.dbQuery(
              environment.getReqSpecBuilder(),
              "select id from " + rmsSchemaName + ".rider_shift where rider_id='" + riderId + "'");

      for (int j = 0; j < riderShiftId.length(); j++) {
        String shiftIdForRider = riderShiftId.getJSONObject(j).getInt("id") + "";
        logger.info(
            "deleting entry from"
                + rmsSchemaName
                + " rider shift table - "
                + "delete from payouts.rider_shift where id='"
                + shiftIdForRider
                + "'");
        dbUtilApi.dbQuery(
            environment.getReqSpecBuilder(),
            "delete from " + rmsSchemaName + ".rider_shift where id='" + shiftIdForRider + "'");
        logger.info(
            "Selecting id from "
                + rmsSchemaName
                + " rider week off table "
                + "select id from "
                + rmsSchemaName
                + ".rider_week_off where rider_id='"
                + riderId
                + "'");
        JSONArray queryRes =
            dbUtilApi.dbQuery(
                environment.getReqSpecBuilder(),
                "select id from "
                    + rmsSchemaName
                    + ".rider_week_off where rider_id='"
                    + riderId
                    + "'");

        if (queryRes.length() != 0) {
          logger.info(
              "Deleting entry from "
                  + rmsSchemaName
                  + " rider week off table "
                  + "delete from "
                  + rmsSchemaName
                  + ".rider_week_off where id='"
                  + queryRes.getJSONObject(0).getInt("id")
                  + "'");
          dbUtilApi.dbQuery(
              environment.getReqSpecBuilder(),
              "delete from "
                  + rmsSchemaName
                  + ".rider_week_off where id='"
                  + queryRes.getJSONObject(0).getInt("id")
                  + "'");
        }
      }
    }
  }

  /**
   * This method compare expected csv file data with the actual week-off-plan api response
   *
   * @param expectedCsvFile CSV file which was passed as an input to week-off plan api.
   * @param actualResponse Response of week-off plan api
   * @throws IOException
   * @author Abhishek_Singh
   */
  public void verifyExpectedAndActualResponseWeekOffPlan(
      File expectedCsvFile, Response actualResponse) throws IOException {

    PostWeekOffPlanResponse[] uploadWeekOffPlanActualResponse =
        actualResponse.getBody().as(PostWeekOffPlanResponse[].class);

    ArrayList<PostWeekOffPlanResponse> uploadWeekOffPlanActualExpectedResponse = new ArrayList<>();

    // Reading the csv file and adding the row wise details to ArrayList
    BufferedReader reader = new BufferedReader(new FileReader(expectedCsvFile));
    String line = "";
    int iteration = 0;

    line = reader.readLine();

    while (!StringUtils.isEmpty(line)) {
      if (iteration == 0) {
        iteration++;
      } else {
        line.replaceAll("\"", "");
        String csvData[] = line.split(",");
        uploadWeekOffPlanActualExpectedResponse.add(
            new PostWeekOffPlanResponse(csvData[0], csvData[1], csvData[2], csvData[3]));
      }

      line = reader.readLine();
    }

    // Comparing expectedResponse details and actualResponse details
    iteration = 0;

    for (PostWeekOffPlanResponse obj : uploadWeekOffPlanActualExpectedResponse) {

      ExtentReportUtil.logStepInfo(
          "Response data validation "
              + obj.equals(uploadWeekOffPlanActualResponse[iteration])
              + "");
      Assert.assertTrue(obj.equals(uploadWeekOffPlanActualResponse[iteration]));
      iteration++;
    }
  }

  /**
   * This method compare expected csv file data with the actual rider-contract-shift-plan api
   * response
   *
   * @param expectedCsvFile CSV file which was passed as an input to rider-contract-shift-plan api.
   * @param actualResponse Response of rider-contract-shift-plan api
   * @throws IOException
   * @author Abhishek_Singh
   */
  public void verifyExpectedAndActualResponseRiderContractShiftPlan(
      File expectedCsvFile, Response actualResponse) throws IOException {

    PostRiderContractShiftPlanResponse[] uploadWeekOffPlanActualResponse =
        actualResponse.getBody().as(PostRiderContractShiftPlanResponse[].class);

    ArrayList<PostRiderContractShiftPlanResponse> uploadWeekOffPlanExpectedResponse =
        new ArrayList<>();

    // Reading the csv file and adding the row wise details to ArrayList
    BufferedReader reader = new BufferedReader(new FileReader(expectedCsvFile));
    String line = "";
    int iteration = 0;

    line = reader.readLine();

    while (!StringUtils.isEmpty(line)) {
      if (iteration == 0) {
        iteration++;
      } else {
        line.replaceAll("\"", "");
        String csvData[] = line.split(",");
        uploadWeekOffPlanExpectedResponse.add(
            new PostRiderContractShiftPlanResponse(csvData[0], csvData[1]));
      }

      line = reader.readLine();
    }

    // Comparing expectedResponse details and actualResponse details
    iteration = 0;

    for (PostRiderContractShiftPlanResponse obj : uploadWeekOffPlanExpectedResponse) {

      ExtentReportUtil.logStepInfo(
          "Response data validation "
              + obj.equals(uploadWeekOffPlanActualResponse[iteration])
              + "");
      Assert.assertTrue(obj.equals(uploadWeekOffPlanActualResponse[iteration]));
      iteration++;
    }
  }

  /**
   * This function will verify getAvailableWeekOff response
   *
   * @param shiftId
   * @param getAvailableWeekOffActualResponses
   * @author Abhishek_Singh
   */
  public void verifyGetAvailableWeekOffResponse(
      String shiftId, GetAvailableWeekOffResponse[] getAvailableWeekOffActualResponses) {

    List<String> dayList =
        new ArrayList<>(
            Arrays.asList(
                "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));

    for (GetAvailableWeekOffResponse obj : getAvailableWeekOffActualResponses) {
      Assert.assertTrue(
          obj.getShiftId() == Integer.parseInt(shiftId), "ShiftId is not matching in response");
      Assert.assertTrue(
          dayList.contains(obj.getDay()), obj.getDay() + " is not expected in response");
      dayList.remove(obj.getDay());
    }

    Assert.assertTrue(dayList.isEmpty(), "All days entry are present in response");
  }

  /**
   * createWeekOffPlanWIthNullValuesCsvFile method create a csv by fetching the rows from env.conf
   * file using configKeys and make index value as null.
   *
   * @param configKeys List of keys whoose value needs to be fetched from env.conf file
   * @param fileName CSV fileName
   * @param index index value whose key values needs to be null
   * @throws IOException
   * @author Abhishek_Singh
   */
  public File createWeekOffPlanWIthNullValuesCsvFile(
      List<String> configKeys, int index, String fileName) throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();

    for (String key : configKeys) {
      List<String> row = environment.getGlobalTestData().getStringList(key);

      if (!row.contains("store_id")) {
        row.set(index, "");
      }

      rows.add(row.stream().toArray(String[]::new));
    }

    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;
    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();

    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }

    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);

    return new File(tempFilePath);
  }

  /**
   * createWeekOffPlanWithCurrentDayCsvFile method create a csv by including today's day as week off
   * day in plan.
   *
   * @param configKeys List of keys whoose value needs to be fetched from env.conf file
   * @param fileName CSV fileName
   * @throws IOException
   * @author Abhishek_Singh
   */
  public File createWeekOffPlanWithCurrentDayCsvFile(List<String> configKeys, String fileName)
      throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();

    LocalDate date = LocalDate.now();
    DayOfWeek dow = date.getDayOfWeek();

    for (String key : configKeys) {
      List<String> row = environment.getGlobalTestData().getStringList(key);

      if (!row.contains("day")) {
        row.set(1, dow.toString());
      }

      rows.add(row.stream().toArray(String[]::new));
    }

    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;

    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();

    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }
    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);

    return new File(tempFilePath);
  }

  /**
   * This function will upload shiftPlan and weekoff plan for a given storeID
   *
   * @param shiftDataMap
   * @param weekOffDataMap
   * @throws IOException
   * @author Abhishek_Singh
   */
  public void uploadShiftPlanAndWeekOffPlan(
      HashMap<String, Object> shiftDataMap, HashMap<String, Object> weekOffDataMap)
      throws IOException {

    // uploading shift plan
    File workPlanFile =
        createShiftPlanCsvFile(
            (List<String>) shiftDataMap.get("keyList"),
            (String) shiftDataMap.get("fileName"),
            (String) shiftDataMap.get("date"));
    ExtentReportUtil.logStepInfo("File created " + (String) shiftDataMap.get("fileName"));
    response =
        shiftAdherenceApiHelper.createShiftPlan(
            environment.getReqSpecBuilder(), Boolean.TRUE, workPlanFile);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == HttpStatus.SC_OK,
        "Response code is -" + response.getStatusCode());
    verifyExpectedAndActualResponseShiftPlan(workPlanFile, response);

    // uploading week off plan
    File weekOffPlanFile =
        createShiftPlanCsvFile(
            (List<String>) weekOffDataMap.get("keyList"),
            (String) weekOffDataMap.get("fileName"),
            (String) weekOffDataMap.get("date"));
    ExtentReportUtil.logStepInfo("File created " + (String) weekOffDataMap.get("fileName"));
    response =
        shiftAdherenceApiHelper.uploadWeekOffPlan(
            environment.getReqSpecBuilder(), Boolean.TRUE, weekOffPlanFile);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == HttpStatus.SC_OK,
        "Response code is -" + response.getStatusCode());
    verifyExpectedAndActualResponseWeekOffPlan(weekOffPlanFile, response);
  }

  /**
   * This function will create payload , call, and validate response for post rider work plan api
   *
   * @param shiftId
   * @param weekOffDay
   * @param riderId
   * @param token
   * @param statusCode
   * @param errorMessage
   * @author Abhishek_Singh
   */
  public void uploadRiderWorkPlan(
      Integer shiftId,
      String weekOffDay,
      String riderId,
      String token,
      int statusCode,
      String errorMessage,
      boolean skipDataValidation)
      throws SQLException {

    // creating post rider work plan payload
    RiderShift riderShift = RiderShift.builder().shiftId(shiftId).build();
    RiderWeekOff riderWeekOff = RiderWeekOff.builder().weekOffDay(weekOffDay).build();
    PostRiderWorkPlanRequest postRiderWorkPlanRequest =
        PostRiderWorkPlanRequest.builder()
            .riderId(riderId)
            .riderShift(riderShift)
            .riderWeekOff(riderWeekOff)
            .build();

    // Calling post rider work-plan and asserting response with Db
    response =
        shiftAdherenceApiHelper.postRiderWorkPlan(
            environment.getReqSpecBuilder(), Boolean.FALSE, token, postRiderWorkPlanRequest);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == statusCode,
        "Response code of post rider work plan api is not"
            + statusCode
            + ", it is "
            + response.getStatusCode());

    if (errorMessage != null && !skipDataValidation) {
      Assert.assertTrue(
          response.asString().equalsIgnoreCase(errorMessage),
          "Response body not matching the error message");
    } else {
      if (!skipDataValidation) {
        riderHelper.verifyRiderShiftAndWeekOff(shiftId + "", weekOffDay, riderId);
      }
    }
  }

  /**
   * This function will create payload , call, and validate response for post rider work plan api
   * handling weekend day for weekoff
   *
   * @param shiftId
   * @param riderId
   * @param token
   * @param statusCode
   * @param errorMessage
   * @author Abhishek_Singh
   */
  public void uploadRiderWorkPlanExcludingWeekends(
      Integer shiftId,
      String riderId,
      String token,
      int statusCode,
      String errorMessage,
      boolean skipDataValidation)
      throws SQLException {

    // creating post rider work plan payload
    String weekOffDay;
    if (LocalDate.now().getDayOfWeek().toString().equals("FRIDAY")) {
      weekOffDay = LocalDate.now().plusDays(3).getDayOfWeek().toString();
    } else if (LocalDate.now().getDayOfWeek().toString().equals("SATURDAY")) {
      weekOffDay = LocalDate.now().plusDays(2).getDayOfWeek().toString();
    } else if (LocalDate.now().getDayOfWeek().toString().equals("SUNDAY")) {
      weekOffDay = LocalDate.now().plusDays(1).getDayOfWeek().toString();
    } else {
      weekOffDay = LocalDate.now().plusDays(1).getDayOfWeek().toString();
    }

    RiderShift riderShift = RiderShift.builder().shiftId(shiftId).build();
    RiderWeekOff riderWeekOff = RiderWeekOff.builder().weekOffDay(weekOffDay).build();
    PostRiderWorkPlanRequest postRiderWorkPlanRequest =
        PostRiderWorkPlanRequest.builder()
            .riderId(riderId)
            .riderShift(riderShift)
            .riderWeekOff(riderWeekOff)
            .build();

    // Calling post rider work-plan and asserting response with Db
    response =
        shiftAdherenceApiHelper.postRiderWorkPlan(
            environment.getReqSpecBuilder(), Boolean.FALSE, token, postRiderWorkPlanRequest);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == statusCode,
        "Response code of post rider work plan api is not"
            + statusCode
            + ", it is "
            + response.getStatusCode());

    if (errorMessage != null && !skipDataValidation) {
      Assert.assertTrue(
          response.asString().equalsIgnoreCase(errorMessage),
          "Response body not matching the error message");
    } else {
      if (!skipDataValidation) {
        riderHelper.verifyRiderShiftAndWeekOff(shiftId + "", weekOffDay, riderId);
      }
    }
  }

  /**
   * This function will create payload and upload rider work-plan but only week off for rider
   *
   * @param weekOffDay
   * @param riderId
   * @param token
   * @param statusCode
   * @author Abhishek_Singh
   */
  public void uploadRiderWeekOffPlan(
      String weekOffDay, String riderId, String token, int statusCode) {

    // creating post rider work plan payload
    RiderWeekOff riderWeekOff = RiderWeekOff.builder().weekOffDay(weekOffDay).build();
    PostRiderWorkPlanRequest postRiderWorkPlanRequest =
        PostRiderWorkPlanRequest.builder().riderId(riderId).riderWeekOff(riderWeekOff).build();

    // Calling post rider work-plan and asserting response with Db
    response =
        shiftAdherenceApiHelper.postRiderWorkPlan(
            environment.getReqSpecBuilder(), Boolean.FALSE, token, postRiderWorkPlanRequest);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == statusCode,
        "Response code of post rider work plan api is not"
            + statusCode
            + ", it is "
            + response.getStatusCode());
  }

  /**
   * This function will create payload and upload rider work-plan but only week off for rider,
   * excluding weekends
   *
   * @param riderId
   * @param token
   * @param statusCode
   * @author Abhishek_Singh
   */
  public void uploadRiderWeekOffPlanExcludingWeekend(String riderId, String token, int statusCode) {

    // creating post rider work plan payload
    String weekOffDay = LocalDate.now().getDayOfWeek().toString();
    if (LocalDate.now().getDayOfWeek().toString().equals("FRIDAY")) {
      weekOffDay = LocalDate.now().plusDays(3).getDayOfWeek().toString();
    } else if (LocalDate.now().getDayOfWeek().toString().equals("SATURDAY")) {
      weekOffDay = LocalDate.now().plusDays(2).getDayOfWeek().toString();
    } else if (LocalDate.now().getDayOfWeek().toString().equals("SUNDAY")) {
      weekOffDay = LocalDate.now().plusDays(1).getDayOfWeek().toString();
    } else {
      weekOffDay = LocalDate.now().plusDays(1).getDayOfWeek().toString();
    }
    RiderWeekOff riderWeekOff = RiderWeekOff.builder().weekOffDay(weekOffDay).build();
    PostRiderWorkPlanRequest postRiderWorkPlanRequest =
        PostRiderWorkPlanRequest.builder().riderId(riderId).riderWeekOff(riderWeekOff).build();

    // Calling post rider work-plan and asserting response with Db
    response =
        shiftAdherenceApiHelper.postRiderWorkPlan(
            environment.getReqSpecBuilder(), Boolean.FALSE, token, postRiderWorkPlanRequest);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == statusCode,
        "Response code of post rider work plan api is not"
            + statusCode
            + ", it is "
            + response.getStatusCode());
  }

  /**
   * This function will create payload and upload rider workplan but only shiftPlan for rider
   *
   * @param shiftId
   * @param riderId
   * @param token
   * @param statusCode
   * @author Abhishek_Singh
   */
  public void uploadRiderShiftPlan(Integer shiftId, String riderId, String token, int statusCode) {

    // creating post rider work plan payload
    RiderShift riderShift = RiderShift.builder().shiftId(shiftId).build();
    RiderWeekOff riderWeekOff = RiderWeekOff.builder().build();
    PostRiderWorkPlanRequest postRiderWorkPlanRequest =
        PostRiderWorkPlanRequest.builder()
            .riderId(riderId)
            .riderShift(riderShift)
            .riderWeekOff(riderWeekOff)
            .build();

    // Calling post rider work-plan and asserting response with Db
    response =
        shiftAdherenceApiHelper.postRiderWorkPlan(
            environment.getReqSpecBuilder(), Boolean.FALSE, token, postRiderWorkPlanRequest);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
        response.getStatusCode() == statusCode,
        "Response code of post rider work plan api is not"
            + statusCode
            + ", it is "
            + response.getStatusCode());
  }

  /**
   * Used to upload store plan
   *
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public void uploadStorePlan() {

    // creating shift-plan data map for store
    List<String> keysList =
        Arrays.asList(
            "riderHomePageCheckInCheckOutValidData.shift_row0",
            "riderHomePageCheckInCheckOutValidData.shift_row1",
            "riderHomePageCheckInCheckOutValidData.shift_row2",
            "riderHomePageCheckInCheckOutValidData.shift_row3");
    HashMap<String, Object> shiftData = new HashMap<>();
    shiftData.put("keyList", keysList);
    shiftData.put("date", "futureDate");
    shiftData.put("fileName", "uploadStoreShiftPlan.csv");

    // creating week off plan data map for store
    List<String> weekOffKeyList =
        Arrays.asList(
            "riderHomePageCheckInCheckOutValidData.week_row0",
            "riderHomePageCheckInCheckOutValidData.week_row1",
            "riderHomePageCheckInCheckOutValidData.week_row2",
            "riderHomePageCheckInCheckOutValidData.week_row3",
            "riderHomePageCheckInCheckOutValidData.week_row4",
            "riderHomePageCheckInCheckOutValidData.week_row5");
    HashMap<String, Object> weekOffData = new HashMap<>();
    weekOffData.put("keyList", weekOffKeyList);
    weekOffData.put("fileName", "uploadStoreWeekOffPlan.csv");
    weekOffData.put("date", "");

    // uploading shift plan and week off plan for store
    uploadShiftPlanAndWeekOffPlan(shiftData, weekOffData);
  }
}
