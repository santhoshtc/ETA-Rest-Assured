/***
 * Date: 02/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.api.riderx;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.pojo.riderhomepage.request.BannerStoreMappingRequest;
import com.zeptonow.commonlm.pojo.riderhomepage.request.RiderCheckInCheckOutRequest;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

public class RiderHomePageApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;

  public RiderHomePageApiHelper() {
    logger = new LoggerUtil(this.getClass());
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
  }

  /**
   * This function will call rider check-in-out status api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param riderCheckInCheckOutRequest
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public Response calRiderCheckInCheckOutApi(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      RiderCheckInCheckOutRequest riderCheckInCheckOutRequest,
      String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("check-in-checkout");
    logger.info("Waiting for 500 ms before calling checkIn-checkOut Api");
    Thread.sleep(500);

    response =
        RestUtils.put(
            requestSpecBuilder, headers, apiPath, null, null, riderCheckInCheckOutRequest);
    schemaValidators.validateResponseSchema(response, riderCheckInCheckOut, isVerifySchema);
    return response;
  }

  /**
   * This function will call check-in-check-out status api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response getCheckInCheckOutStatusApi(
      RequestSpecBuilder requestSpecBuilder, boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("check-in-check-out-status");

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, null, null);
    schemaValidators.validateResponseSchema(
        response, getRiderCheckInCheckOutStatus, isVerifySchema);
    return response;
  }

  /**
   * This function will call getShiftDetails Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response getShiftDetails(
      RequestSpecBuilder requestSpecBuilder, boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("shiftDetails");

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, null, null);
    schemaValidators.validateResponseSchema(response, getShiftDetails, isVerifySchema);
    return response;
  }

  /**
   * Get home/info api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response getHomeInfo(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      String authToken,
      String... riderId) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    if (riderId.length > 0) {
      headers.put("x-rider-id", riderId[0]);
      headers.put("x-passed-by", "passed-by-kong");
    } else {
      headers.put("Authorization", "Bearer " + authToken);
    }
    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("homeInfo");

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, null, null);
    schemaValidators.validateResponseSchema(response, getRiderHomeInfo, isVerifySchema);
    return response;
  }

  /**
   * Create Banner Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param formParam
   * @return
   * @author Abhishek_Singh
   */
  public Response createBanner(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      String authToken,
      Map<Object, Object> formParam) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("createBanner");

    response = RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, formParam);
    schemaValidators.validateResponseSchema(response, postCreateBanner, isVerifySchema);
    return response;
  }

  /**
   * Update Banner Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param formParam
   * @return
   * @author Abhishek_Singh
   */
  public Response updateBanner(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      String authToken,
      Map<Object, Object> formParam) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("createBanner");

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, formParam);
    schemaValidators.validateResponseSchema(response, updateBanner, isVerifySchema);
    return response;
  }

  /**
   * Banner Store Mapping Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param bannerStoreMappingRequest
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response bannerStoreMapping(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      BannerStoreMappingRequest bannerStoreMappingRequest,
      String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("bannerStoreMapping");

    response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, bannerStoreMappingRequest);
    schemaValidators.validateResponseSchema(response, postBannerStoreMapping, isVerifySchema);
    return response;
  }

  /**
   * Get Banner Details
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param tag
   * @param storeId
   * @return
   * @author Abhishek_Singh
   */
  public Response getBannerDetails(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      String authToken,
      String tag,
      String storeId) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    HashMap<String, Object> queryParam = new HashMap<>();
    queryParam.put("tag", tag);
    queryParam.put("StoreId", storeId);

    String apiPath = RIDERHOMEPAGE_ENDPOINT.get("getBanner");

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParam, null);
    schemaValidators.validateResponseSchema(response, getBanner, isVerifySchema);
    return response;
  }
}
