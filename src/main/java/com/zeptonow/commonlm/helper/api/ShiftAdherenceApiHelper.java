/***
 * Date: 12/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.api;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.request.PostRiderWorkPlanRequest;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

/**
 * @author Abhishek_Singh @Date 18/08/22
 */
public class ShiftAdherenceApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;
  private Response response;

  public ShiftAdherenceApiHelper() {
    logger = new LoggerUtil(ShiftAdherenceApiHelper.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder.
   * @param isVerifySchema Boolean value if schema validation is needed.
   * @param file csv file needed for shiftPlan api.
   * @return RestAssured response for createShiftPlan api.
   * @author Abhishek_Singh
   */
  public Response createShiftPlan(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, File file) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "multipart/form-data;");
    headers.put("accept", "application/json");

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", file);

    response =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            SHIFTADHERENCEV1_ENDPOINT.get("shift_plan"),
            null,
            null,
            formParam);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, postShiftWorkPlan, isVerifySchema);
    return response;
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder.
   * @param isVerifySchema Boolean value if schema validation is needed.
   * @param file csv file needed for week-off-plan api.
   * @return RestAssured response for uploadWeekOffPlan api.
   * @author Abhishek_Singh
   */
  public Response uploadWeekOffPlan(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, File file) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "multipart/form-data;");
    headers.put("accept", "application/json");

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", file);

    response =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            SHIFTADHERENCEV1_ENDPOINT.get("week-off-plan"),
            null,
            null,
            formParam);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, postWeekOffPlan, isVerifySchema);
    return response;
  }

  /**
   * @param requestSpecBuilder RequestSpecBuilder.
   * @param isVerifySchema Boolean value if schema validation is needed.
   * @param file csv file needed for rider contract shift plan api.
   * @return RestAssured response for rider-contract-shift-plan api.
   * @author Abhishek_Singh
   */
  public Response uploadRiderContractShiftPlan(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, File file) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "multipart/form-data;");
    headers.put("accept", "application/json");

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", file);

    response =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            SHIFTADHERENCEV1_ENDPOINT.get("rider-contract-shift-plan"),
            null,
            null,
            formParam);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, postRiderContractShiftPlan, isVerifySchema);
    return response;
  }

  /**
   * This function will return the get available shifts for a rider.
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token Authorisation token
   * @return
   */
  public Response getAvailableShifts(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String token) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + token);

    response =
        RestUtils.get(
            requestSpecBuilder,
            headers,
            SHIFTADHERENCEV1_ENDPOINT.get("get-available-shifts"),
            null,
            null);
    return response;
  }

  /**
   * This function will return available week off for rider
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param shiftId
   * @param riderStatus
   * @return Response
   * @author Abhishek_Singh
   */
  public Response getAvailableWeekOffPlan(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String token,
      String shiftId,
      String riderStatus) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + token);

    Map<String, Object> formParam = new HashMap<>();
    formParam.put("shift_id", shiftId);
    formParam.put("rider_status", riderStatus);

    response =
        RestUtils.get(
            requestSpecBuilder,
            headers,
            SHIFTADHERENCEV1_ENDPOINT.get("get-available-week-off"),
            formParam,
            null);
    return response;
  }

  /**
   * This function will call api to post rider work plan
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param postRiderWorkPlanRequest Request Pojo
   * @return
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public Response postRiderWorkPlan(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String token,
      PostRiderWorkPlanRequest postRiderWorkPlanRequest) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + token);
    logger.info("Waiting for 5 sec before calling rider post workplan api");
    Thread.sleep(5000);
    response =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            SHIFTADHERENCEV1_ENDPOINT.get("post-rider-work-plan"),
            null,
            null,
            postRiderWorkPlanRequest);
    return response;
  }

  /**
   * This function will call get-rider-work=plan api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token rider auth token
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderWorkPlan(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String token) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + token);

    response =
        response =
            RestUtils.get(
                requestSpecBuilder,
                headers,
                SHIFTADHERENCEV1_ENDPOINT.get("getRiderWorkPlan"),
                null,
                null);
    schemaValidators.validateResponseSchema(response, getRiderWorkPlan, isVerifySchema);
    return response;
  }
}
