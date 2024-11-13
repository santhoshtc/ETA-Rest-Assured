/***
 * Date: 23/11/22
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
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.commonlm.pojo.selfsignup.request.*;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

public class SelfSignupApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;

  public SelfSignupApiHelper() {
    logger = new LoggerUtil(SelfSignupApiHelper.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
  }

  /**
   * This function will call getCities api from self serve module
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response getCities(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = SELFSIGNUP_ENDPOINT.get("getCities");
    response = RestUtils.get(requestSpecBuilder, headers, apiPath, null, null);
    schemaValidators.validateResponseSchema(response, selfSignupGetCities, isVerifySchema);
    return response;
  }

  /**
   * This function will call getStoresByCityId api from self serve module
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param cityId
   * @param authToken
   * @param queryParam
   * @return
   * @author Abhishek_Singh
   */
  public Response getStoresByCityId(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String cityId,
      String authToken,
      HashMap<String, Object> queryParam) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getStoresByCityId"), cityId);
    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParam, null);
    schemaValidators.validateResponseSchema(response, selfSignupGetStoresByCityId, isVerifySchema);
    return response;
  }

  /**
   * This function will call getRiderStatus Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderStatus(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = SELFSIGNUP_ENDPOINT.get("getRiderStatus");
    response = RestUtils.get(requestSpecBuilder, headers, apiPath, null, null);
    schemaValidators.validateResponseSchema(response, getRiderStatus, isVerifySchema);
    return response;
  }

  /**
   * This function will call the update-rider-lead-data
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param leadCreationRequest
   * @param authToken
   * @return
   */
  public Response updateRiderLeadData(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      LeadCreationRequest leadCreationRequest,
      String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + authToken);

    String apiPath = SELFSIGNUP_ENDPOINT.get("updateLeadData");

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, leadCreationRequest);
    schemaValidators.validateResponseSchema(response, updateRiderLead, isVerifySchema);
    return response;
  }

  /**
   * This function will call confirmDetails api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response confirmDetails(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + authToken);

    Map<String, Object> queryParam = new HashMap<>();
    queryParam.put("intent", "confirm");

    String apiPath = SELFSIGNUP_ENDPOINT.get("confirmDetails");

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, queryParam, null, null);
    schemaValidators.validateResponseSchema(response, updateRiderLead, isVerifySchema);
    return response;
  }

  /**
   * This function will call shipsy api to update rider Status
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param updateRiderStatus
   * @return
   * @author Abhishek_Singh
   */
  public Response updateRiderStatus(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      UpdateRiderStatus updateRiderStatus,
      String status,
      String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("updateRiderStatus"), status);

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);
    headers.put("Content-Type", "application/json");

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, updateRiderStatus);
    schemaValidators.validateResponseSchema(response, postUpdateRiderStatus, isVerifySchema);
    return response;
  }

  /**
   * Call bulkUpdateLeadData admin api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param csvFile
   * @return
   * @author ABhishek_Singh
   */
  public Response bulkUpdateLeadData(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String token, File csvFile) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "multipart/form-data;");
    headers.put("Authorization", token);

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", csvFile);

    String apiPath = SELFSIGNUP_ENDPOINT.get("bulkUpdateLead");

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, formParam);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, bulkUpdateRiderStatus, isVerifySchema);
    return response;
  }

  /**
   * Call bulkUpdateRiderStatus admin api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param status
   * @param token
   * @param csvFile
   * @return
   * @author Abhishek_Singh
   */
  public Response bulkUpdateRiderStatusData(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String status,
      String token,
      File csvFile) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "multipart/form-data;");
    headers.put("Authorization", token);

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", csvFile);

    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("bulkUpdateRiderStatus"), status);

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, formParam);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, bulkUpdateRiderStatus, isVerifySchema);
    return response;
  }

  /**
   * Get rider status using admin api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param riderId
   * @param token
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderStatusAdminApi(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String riderId, String token) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);

    Map<String, Object> queryParam = new HashMap<>();
    queryParam.put("rider_id", riderId);

    String apiPath = SELFSIGNUP_ENDPOINT.get("getRiderStatusAdmin");

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParam, null);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, getRiderStatus, isVerifySchema);
    return response;
  }

  /**
   * Get rider status reason using admin api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderStatusReason(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    String apiPath = SELFSIGNUP_ENDPOINT.get("getRiderReasonStatus");

    response = RestUtils.get(requestSpecBuilder, apiPath);
    ExtentReportUtil.logStepInfo(response.asString());
    schemaValidators.validateResponseSchema(response, getRiderStatusReason, isVerifySchema);
    return response;
  }

  /**
   * Create OnBoarding centre api utility
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param createOnBoardingCentreRequest
   * @return
   * @author Abhishek_Singh
   */
  public Response createOnBoardingCentre(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String authToken,
      CreateOnBoardingCentreRequest createOnBoardingCentreRequest) {

    Response response = null;
    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);
    String apiPath = SELFSIGNUP_ENDPOINT.get("createOnBoardingCentre");

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response =
        RestUtils.post(
            requestSpecBuilder, headers, apiPath, null, null, createOnBoardingCentreRequest);
    schemaValidators.validateResponseSchema(response, postCreateOnBoardingCentre, isVerifySchema);
    return response;
  }

  /**
   * Get OnBoarding Centres
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response getOnBoardingCentre(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);
    String apiPath = SELFSIGNUP_ENDPOINT.get("getOnBoardingCentre");

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response = RestUtils.get(requestSpecBuilder, headers, apiPath);
    schemaValidators.validateResponseSchema(response, getOnBoardingCentre, isVerifySchema);
    return response;
  }

  /**
   * Create rider config using rider onboarding api's
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param createRiderConfig
   * @return
   * @author Abhishek_Singh
   */
  public Response createRiderConfig(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String authToken,
      CreateRiderConfig createRiderConfig) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    String apiPath = SELFSIGNUP_ENDPOINT.get("createRiderConfig");

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response = RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, createRiderConfig);
    schemaValidators.validateResponseSchema(response, postCreateRiderConfig, isVerifySchema);
    return response;
  }

  /**
   * Create/update rider Asset
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param createRiderAssetRequest
   * @return
   * @author Abhishek_Singh
   */
  public Response createRiderAsset(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String authToken,
      CreateRiderAssetRequest createRiderAssetRequest) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    String apiPath = SELFSIGNUP_ENDPOINT.get("createAsset");

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, createRiderAssetRequest);
    schemaValidators.validateResponseSchema(response, postCreateRiderAsset, isVerifySchema);
    return response;
  }

  /**
   * Update Rider Asset api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param updateRiderAssetRequest
   * @param assetId
   * @return
   * @author Abhishek_Singh
   */
  public Response updateRiderAsset(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String authToken,
      UpdateRiderAssetRequest updateRiderAssetRequest,
      String assetId) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("updateAsset"), assetId);

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response =
        RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, updateRiderAssetRequest);
    schemaValidators.validateResponseSchema(response, postCreateRiderConfig, isVerifySchema);
    return response;
  }

  /**
   * Kapture api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response partnerKaptureApi(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String authToken) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_KONG_URL);
    String apiPath = SELFSIGNUP_ENDPOINT.get("kaptureApi");

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + authToken);

    response = RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, null);
    schemaValidators.validateResponseSchema(response, postKaptureApiResponse, isVerifySchema);
    return response;
  }

  /**
   * Update rider storeId
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param profileId
   * @param updateRiderStoreIdRequest
   * @return
   * @author Abhishek_Singh
   */
  public Response updateRiderStoreId(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String authToken,
      String profileId,
      UpdateRiderStoreIdRequest updateRiderStoreIdRequest) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("updateStoreId"), profileId);

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response =
        RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, updateRiderStoreIdRequest);
    schemaValidators.validateResponseSchema(response, putRiderStoreIdUpdate, isVerifySchema);
    return response;
  }

  /**
   * Api to getRiderConfig by Type
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param configType
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderTypeConfig(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String authToken,
      String configType) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getRiderConfig"), configType);

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    String schemaPath = null;
    if (configType.equalsIgnoreCase("3PL-VENDORS")) {
      schemaPath = getRider3plVendorConfig;
    } else if (configType.equalsIgnoreCase("COHORT")) {
      schemaPath = getRiderCohortConfig;
    } else {
      schemaPath = getRiderOnBoardingCentreConfig;
    }
    response = RestUtils.get(requestSpecBuilder, headers, apiPath);
    schemaValidators.validateResponseSchema(response, schemaPath, isVerifySchema);
    return response;
  }

  /**
   * Call bulkUpdateRiderDetails rms api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param csvFile
   * @return
   * @author Abhishek_Singh
   */
  public Response bulkUpdateRiderDetails(
      RequestSpecBuilder requestSpecBuilder, Boolean isVerifySchema, String token, File csvFile) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "multipart/form-data;");
    headers.put("Authorization", token);

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", csvFile);

    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("bulkUpdateRiderDetails"));

    response = RestUtils.patch(requestSpecBuilder, headers, apiPath, null, null, formParam);

    String schemaPath =
        (response.jsonPath().getList("errors").size() > 0)
            ? patchBulkUpdateRiderDetailsError
            : patchBulkUpdateRiderDetails;
    schemaValidators.validateResponseSchema(response, schemaPath, isVerifySchema);
    return response;
  }

  /**
   * Get rider details by store Id
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param storeId
   * @return
   * @author Deepak_Kumar
   */
  public Response getRiderDetailsByStoreId(
      RequestSpecBuilder requestSpecBuilder, String storeId, String token, Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getRiderDetailsByStoreId"), storeId);
    response = RestUtils.get(requestSpecBuilder, headers, apiPath);
    schemaValidators.validateResponseSchema(response, getRiderDetailsByStoreId, isVerifySchema);
    return response;
  }

  /**
   * Get rider details by profile Id
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param profileId
   * @return
   * @author Deepak_Kumar
   */
  public Response getRiderDetailsByProfileId(
      RequestSpecBuilder requestSpecBuilder,
      String profileId,
      String token,
      Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath =
        String.format(SELFSIGNUP_ENDPOINT.get("getRiderDetailsByProfileId"), profileId);
    response = RestUtils.get(requestSpecBuilder, headers, apiPath);
    schemaValidators.validateResponseSchema(response, getRiderDetailsByProfileId, isVerifySchema);
    return response;
  }

  /**
   * Get rider asset info
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param queryParams
   * @return
   * @author Deepak_Kumar
   */
  public Response getRiderAssetInfo(
      RequestSpecBuilder requestSpecBuilder,
      Map<String, Object> queryParams,
      String token,
      Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getRiderAssetInfo"));
    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, getRiderAssetInfo, isVerifySchema);
    return response;
  }

  /**
   * GetRiderAssetExport Api
   *
   * @param requestSpecBuilder
   * @param queryParams
   * @param token
   * @param isVerifySchema
   * @author Abhishek_Singh
   * @return
   */
  public Response getRiderAssetExport(
      RequestSpecBuilder requestSpecBuilder,
      Map<String, Object> queryParams,
      String token,
      Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getRiderAssetExport"));
    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, getRiderProfileExport, isVerifySchema);
    return response;
  }

  /**
   * GetRiderListing Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param pageNo
   * @param pageSize
   * @param city
   * @param store
   * @param searchQuery
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderListing(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String token,
      Optional<Integer> pageNo,
      Optional<Integer> pageSize,
      Optional<String> city,
      Optional<String> store,
      Optional<String> searchQuery) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getRiderListing"));

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);

    Map<String, Object> queryParam = new HashMap<>();
    if (pageNo.isPresent()) {
      queryParam.put("pageNo", pageNo.get());
    }
    if (pageSize.isPresent()) {
      queryParam.put("pageSize", pageSize.get());
    }
    if (city.isPresent()) {
      queryParam.put("city", city.get());
    }
    if (store.isPresent()) {
      queryParam.put("store", store.get());
    }
    if (searchQuery.isPresent()) {
      queryParam.put("searchQuery", searchQuery.get());
    }

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParam, null);
    schemaValidators.validateResponseSchema(response, getRiderListing, isVerifySchema);
    return response;
  }

  /**
   * GetRiderProfileExport Api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param pageNo
   * @param pageSize
   * @param city
   * @param store
   * @return
   * @author Abhishek_Singh
   */
  public Response getRiderProfileExport(
      RequestSpecBuilder requestSpecBuilder,
      Boolean isVerifySchema,
      String token,
      Optional<Integer> pageNo,
      Optional<Integer> pageSize,
      Optional<String> city,
      Optional<String> store) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL);

    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getRiderProfileExport"));

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);

    Map<String, Object> queryParam = new HashMap<>();
    if (pageNo.isPresent()) {
      queryParam.put("pageNo", pageNo.get());
    }
    if (pageSize.isPresent()) {
      queryParam.put("pageSize", pageSize.get());
    }
    if (city.isPresent()) {
      queryParam.put("city", city.get());
    }
    if (store.isPresent()) {
      queryParam.put("store", store.get());
    }

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParam, null);
    schemaValidators.validateResponseSchema(response, getRiderProfileExport, isVerifySchema);
    return response;
  }

  /**
   * Update rider config
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param authToken
   * @param configId
   * @param updateRiderConfigRequest
   * @return
   * @author Deepak_Kumar
   */
  public Response updateRiderConfig(
      RequestSpecBuilder requestSpecBuilder,
      String authToken,
      String configId,
      UpdateRiderConfigRequest updateRiderConfigRequest,
      Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("updateRiderConfig"), configId);

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);

    response = RestUtils.put(requestSpecBuilder, headers, apiPath, updateRiderConfigRequest);
    schemaValidators.validateResponseSchema(response, updateRiderConfig, isVerifySchema);
    return response;
  }

  /**
   * Get rider config
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param token
   * @param queryParams
   * @return
   * @author Deepak_Kumar
   */
  public Response getRiderConfig(
      RequestSpecBuilder requestSpecBuilder,
      Map<String, Object> queryParams,
      String token,
      Boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath = String.format(SELFSIGNUP_ENDPOINT.get("getMasterConfig"));
    response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, getRiderConfig, isVerifySchema);
    return response;
  }
}
