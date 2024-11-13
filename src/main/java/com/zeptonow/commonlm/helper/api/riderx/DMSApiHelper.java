/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.api.riderx;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RequestMapper;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.constants.Statuses;
import com.zeptonow.commonlm.helper.mysql.MySqlZeptoBackendServiceHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.dms.request.*;
import com.zeptonow.controllerlm.servicehelper.BifrostHelper;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.DBQueries.getUserIdForRiderId;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

public class DMSApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;
  private MySqlZeptoBackendServiceHelper mySqlZeptoBackendServiceHelper;

  public DMSApiHelper() {
    logger = new LoggerUtil(DMSApiHelper.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
    mySqlZeptoBackendServiceHelper = new MySqlZeptoBackendServiceHelper();
  }

  /**
   * This function will call create Consignment api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param createConsignmentRequest
   * @return
   * @author Abhishek_Singh
   */
  public Response createConsignment(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      CreateConsignmentRequest createConsignmentRequest) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");

    String apiPath = DELIVERYSERVICE_ENDPOINT.get("createConsignment");

    response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, createConsignmentRequest);
    schemaValidators.validateResponseSchema(response, createConsignment, isVerifySchema);
    return response;
  }

  /**
   * This function will call update Consignment api
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param updateConsignmentRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response updateConsignment(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      UpdateConsignmentRequest updateConsignmentRequest) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");

    String apiPath = DELIVERYSERVICE_ENDPOINT.get("createConsignment");

    response =
        RestUtils.put(requestSpecBuilder, headers, apiPath, null, null, updateConsignmentRequest);
    schemaValidators.validateResponseSchema(response, updateConsignment, isVerifySchema);
    return response;
  }

  /**
   * This function is used to update riderDeliveryStatus
   *
   * @param requestSpecBuilder
   * @param isVerifySchema
   * @param updateRiderDeliveryStatus
   * @return
   * @author Abhishek_Singh
   */
  public Response updateRiderDeliveryStatus(
      RequestSpecBuilder requestSpecBuilder,
      boolean isVerifySchema,
      UpdateRiderDeliveryStatusRequest updateRiderDeliveryStatus) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("update-rider-delivery-status");
    response =
        RestUtils.post(requestSpecBuilder, null, apiPath, null, null, updateRiderDeliveryStatus);
    schemaValidators.validateResponseSchema(response, postRiderDeliveryStatus, isVerifySchema);
    return response;
  }

  /**
   * This function is used to get ConsignmentDetails By consignmentId
   *
   * @param requestSpecBuilder
   * @param consignmentId
   * @return response
   * @author Deepak_kumar
   */
  public Response getConsignmentByConsignmentId(
      RequestSpecBuilder requestSpecBuilder, String consignmentId, Boolean isVerifySchema) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("getConsignment"), consignmentId);
    response = RestUtils.get(requestSpecBuilder, apiPath);
    schemaValidators.validateResponseSchema(
        response, getConsignmentByConsignmentId, isVerifySchema);
    return response;
  }

  /**
   * This function is used to get ConsignmentDetails By orderId
   *
   * @param requestSpecBuilder
   * @param orderId
   * @return response
   * @author Deepak_kumar
   */
  public Response getConsignmentByOrderId(
      RequestSpecBuilder requestSpecBuilder, String orderId, Boolean isVerifySchema) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("createConsignment");
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("orderId", orderId);
    response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(
        response, getConsignmentByConsignmentId, isVerifySchema);
    return response;
  }

  /**
   * This function is used to get ConsignmentDetails By storeId
   *
   * @param requestSpecBuilder
   * @param storeId
   * @param requestMessage
   * @return response
   * @author Deepak_kumar
   */
  public Response getConsignmentByStoreIdAndStatus(
      RequestSpecBuilder requestSpecBuilder,
      String storeId,
      GetConsignmentByStoreIdAndStatusRequest requestMessage) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath =
        String.format(DELIVERYSERVICE_ENDPOINT.get("getConsignmentByStoreId"), storeId);
    response = RestUtils.post(requestSpecBuilder, null, apiPath, requestMessage);
    return response;
  }

  /**
   * This function is used to update delivery status
   *
   * @param requestSpecBuilder
   * @param riderToken
   * @param requestBody
   * @param deliveryId
   * @param isVerifySchema
   * @return response
   * @author Manisha_Kumari
   */
  public Response updateDeliveryStatus(
      RequestSpecBuilder requestSpecBuilder,
      String riderToken,
      UpdateDeliveryStatusRequest requestBody,
      String deliveryId,
      Boolean isVerifySchema,
      String... bincode) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    String apiPath =
        String.format(DELIVERYSERVICE_ENDPOINT.get("updateDeliveryStatus"), deliveryId);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + riderToken);
    if (requestBody.getStatus() != null
            && requestBody.getStatus().equals(Statuses.DELIVERED.consignmentStatus)
        || requestBody.getStatus().equals(Statuses.RETURN_TO_ORIGIN.consignmentStatus)) {
      headers.put(
          "latitude", environment.getGlobalTestData().getString("createConsignmentData.lat"));
      headers.put(
          "longitude", environment.getGlobalTestData().getString("createConsignmentData.long"));
    }
    if (requestBody.getStatus() != null
        && requestBody.getStatus().equals(Statuses.PICKED_UP.consignmentStatus)) {
      String binCode = (bincode.length > 0) ? bincode[0] : "GRN_1";
      JSONObject dataEvent = new JSONObject();
      dataEvent.put("binCode", binCode);
      if (bincode.length > 0) {
        requestBody.setDataEvent(dataEvent.toMap());
        requestBody.setActiveBinScan(true);
      }
    }
    response = RestUtils.put(requestSpecBuilder, headers, apiPath, requestBody);
    schemaValidators.validateResponseSchema(response, updateDeliveryStatus, isVerifySchema);
    return response;
  }

  /**
   * This function is used to delete/cancel the order/consignment
   *
   * @param requestSpecBuilder
   * @param orderId
   * @return response
   * @author Deepak_kumar
   */
  public Response deleteConsignment(
      RequestSpecBuilder requestSpecBuilder, String orderId, Boolean isVerifySchema) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("deleteConsignment"));
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("orderId", orderId);
    response = RestUtils.delete(requestSpecBuilder, null, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, deleteConsignment, isVerifySchema);
    return response;
  }

  /**
   * This function is used to get ConsignmentHistory By consignmentId
   *
   * @param requestSpecBuilder
   * @param consignmentId
   * @return response
   * @author Deepak_kumar
   */
  public Response getConsignmentHistoryByConsignmentId(
      RequestSpecBuilder requestSpecBuilder, String consignmentId, Boolean isVerifySchema) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath =
        String.format(DELIVERYSERVICE_ENDPOINT.get("getConsignmentHistory"), consignmentId);
    response = RestUtils.get(requestSpecBuilder, apiPath);
    schemaValidators.validateResponseSchema(
        response, getConsignmentHistoryByConsignmentId, isVerifySchema);
    return response;
  }

  /**
   * This function is used to get ConsignmentHistory By consignmentId
   *
   * @param requestSpecBuilder
   * @param consignmentId
   * @return response
   * @author Deepak_kumar
   */
  public Response getConsignmentActivityLogConsignmentId(
      RequestSpecBuilder requestSpecBuilder, String consignmentId, Boolean isVerifySchema) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath =
        String.format(DELIVERYSERVICE_ENDPOINT.get("getConsignmentActivity"), consignmentId);
    response = RestUtils.get(requestSpecBuilder, apiPath);
    schemaValidators.validateResponseSchema(
        response, getConsignmentActivityLogByConsignmentId, isVerifySchema);
    return response;
  }

  /**
   * Calling search riders inactive/active with filters
   *
   * @param requestSpecBuilder
   * @param searchRiderRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response searchRidersByFilters(
      RequestSpecBuilder requestSpecBuilder, SearchRiderRequest searchRiderRequest) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("searchRiderDetailsByFilter"));
    return RestUtils.post(requestSpecBuilder, null, apiPath, searchRiderRequest);
  }

  /**
   * Update trip status
   *
   * @param requestSpecBuilder
   * @param updateTripStatusRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response updateTripStatus(
      RequestSpecBuilder requestSpecBuilder,
      Map<String, String> headers,
      String tripId,
      UpdateTripStatusRequest updateTripStatusRequest) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("updateTripStatus"), tripId);
    return RestUtils.put(requestSpecBuilder, headers, apiPath, updateTripStatusRequest);
  }

  /**
   * Update trip status
   *
   * @param requestSpecBuilder
   * @param updateTripStatusRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response updateTripStatusV2(
      RequestSpecBuilder requestSpecBuilder,
      String token,
      String tripId,
      UpdateTripStatusRequest updateTripStatusRequest) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    if (updateTripStatusRequest.getStatus() != null
        && (updateTripStatusRequest.getStatus().equals(Statuses.RETURNED.tripStatus)
            || updateTripStatusRequest.getStatus().equals(Statuses.RETURNED_TO_STORE.tripStatus))) {
      headers.put(
          "Latitude", environment.getGlobalTestData().getString("createConsignmentData.lat"));
      headers.put(
          "Longitude", environment.getGlobalTestData().getString("createConsignmentData.long"));
    }
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("updateTripStatus"), tripId);
    return RestUtils.put(requestSpecBuilder, headers, apiPath, updateTripStatusRequest);
  }

  /**
   * Get trip information by tripId
   *
   * @param requestSpecBuilder
   * @param tripId
   * @return
   * @author Manisha_Kumari
   */
  public Response getTripInfoById(
      RequestSpecBuilder requestSpecBuilder, String token, String tripId) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);

    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("getTripById"), tripId);
    return RestUtils.get(requestSpecBuilder, headers, apiPath);
  }

  /**
   * Change delivery rider
   *
   * @param requestSpecBuilder
   * @param deliveryId
   * @param headers
   * @return
   * @author Manisha_Kumari
   */
  public Response changeDeliveryRider(
      RequestSpecBuilder requestSpecBuilder, String deliveryId, Map<String, String> headers) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("changeDeliveryRider"), deliveryId);
    return RestUtils.put(requestSpecBuilder, headers, apiPath);
  }

  /**
   * Get orders history of a rider by riderId and page token
   *
   * @param requestSpecBuilder
   * @param riderId
   * @param auth
   * @param pageToken
   * @param verifySchema
   * @return
   * @author Manisha.Kumari
   */
  public Response getRiderOrdersHistory(
      RequestSpecBuilder requestSpecBuilder,
      String riderId,
      String auth,
      int pageToken,
      boolean verifySchema) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);

    Map<String, String> headers = new HashMap<>();
    headers.put("x-rider-id", riderId);
    headers.put("Authorization", "Bearer " + auth);
    Map<String, Object> queryParams =
        new HashMap<>() {
          {
            put("token", pageToken);
          }
        };

    String apiPath = DELIVERYSERVICE_ENDPOINT.get("getOrdersHistoryByRiderId");
    Response getRiderOrderHistoryResponse =
        RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(
        getRiderOrderHistoryResponse, getRiderOrdersHistory, verifySchema);
    return getRiderOrderHistoryResponse;
  }

  /**
   * Method is used to allocate the order to the rider
   *
   * @param requestSpecBuilder
   * @param assignRiderRequest
   * @return
   * @author Manisha.Kumari
   */
  public Response assignRider(
      RequestSpecBuilder requestSpecBuilder, AssignRiderRequest assignRiderRequest) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("assign_rider"));
    return RestUtils.post(requestSpecBuilder, null, apiPath, assignRiderRequest);
  }

  /**
   * Method is used to get active trip info by rider Id
   *
   * @param requestSpecBuilder
   * @param getRiderTripInfoRequest
   * @param token
   * @return
   * @author Manisha.Kumari
   */
  public Response getActiveTripInfoByRider(
      RequestSpecBuilder requestSpecBuilder,
      GetRiderTripInfoRequest getRiderTripInfoRequest,
      String token,
      boolean verifySchema) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    Response getActiveRiderTripInfoResponse =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            DELIVERYSERVICE_ENDPOINT.get("getActiveRiderTripInfo"),
            getRiderTripInfoRequest);
    schemaValidators.validateResponseSchema(
        getActiveRiderTripInfoResponse, getActiveTripInfoByRider, verifySchema);
    return getActiveRiderTripInfoResponse;
  }

  /**
   * Get userStore v1/v2 based on param
   *
   * @param requestSpecBuilder
   * @param auth
   * @param endpoint
   * @return
   * @author Manisha.Kumari
   */
  public Response getUserStore(
      RequestSpecBuilder requestSpecBuilder, String auth, String endpoint) {
    requestSpecBuilder.setBaseUri(ZEPTO_ADMIN_BACKEND_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", auth);
    Response getUserStore = RestUtils.get(requestSpecBuilder, headers, endpoint);
    return getUserStore;
  }

  /**
   * create link between user and store
   *
   * @param requestSpecBuilder
   * @param auth
   * @param email
   * @param storeIds
   * @return
   * @author Manisha.Kumari
   */
  public Response linkUserStore(
      RequestSpecBuilder requestSpecBuilder, String auth, String email, String storeIds) {
    requestSpecBuilder.setBaseUri(ZEPTO_ADMIN_BACKEND_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", auth);
    Map<String, Object> params = new HashMap<>();
    params.put("email", email);
    params.put("storeIds", storeIds);
    Response linkUserStore =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            params,
            null,
            DELIVERYSERVICE_ENDPOINT.get("createLinkUserStore"),
            "");
    return linkUserStore;
  }

  /**
   * This function is used to get rider Login Hours details By riderId
   *
   * @param requestSpecBuilder
   * @param riderId
   * @return response
   * @author Deepak_kumar
   */
  public Response getRiderLoginHoursApi(
      RequestSpecBuilder requestSpecBuilder, String riderId, String riderToken) {
    Response response = null;
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", riderToken);
    requestSpecBuilder.setBaseUri(ZEPTO_ADMIN_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("getLoginHours"), riderId);
    response = RestUtils.get(requestSpecBuilder, headers, apiPath);
    return response;
  }

  /**
   * This function is used to update delivery status from dashboard
   *
   * @param requestSpecBuilder
   * @param requestBody
   * @param deliveryId
   * @param isVerifySchema
   * @return response
   * @author Deepak_Kumar
   */
  public Response updateDeliveryStatusFromDashboard(
      RequestSpecBuilder requestSpecBuilder,
      UpdateDeliveryStatusRequest requestBody,
      String deliveryId,
      String riderToken,
      Boolean isVerifySchema) {
    Response response = null;
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", riderToken);
    if (requestBody.getStatus() != null
        && requestBody.getStatus().equals(Statuses.DELIVERED.consignmentStatus)) {
      headers.put(
          "latitude", environment.getGlobalTestData().getString("createConsignmentData.lat"));
      headers.put(
          "longitude", environment.getGlobalTestData().getString("createConsignmentData.long"));
    }
    requestSpecBuilder.setBaseUri(ZEPTO_ADMIN_DASHBOARD_BASEURL);
    String apiPath =
        String.format(DELIVERYSERVICE_ENDPOINT.get("updateDeliveryFromDashboard"), deliveryId);
    response = RestUtils.put(requestSpecBuilder, headers, apiPath, requestBody);
    return response;
  }

  /**
   * Force checkout Api
   *
   * @param requestSpecBuilder
   * @param forceCheckoutRequest
   * @param authToken
   * @param isVerifySchema
   * @return
   * @author Abhishek_Singh
   */
  public Response forceCheckout(
      RequestSpecBuilder requestSpecBuilder,
      ForceCheckoutRequest forceCheckoutRequest,
      String authToken,
      Boolean isVerifySchema) {
    Response response = null;
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);
    requestSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("forceCheckout");
    response = RestUtils.put(requestSpecBuilder, headers, apiPath, forceCheckoutRequest);
    schemaValidators.validateResponseSchema(response, putForceCheckout, isVerifySchema);
    return response;
  }

  /**
   * call handle sfx api to update statuses
   *
   * @param requestSpecBuilder
   * @param updateRequest
   * @return
   * @author Deepak_Kumar
   */
  public Response updateSfxOrderCallback(
      RequestSpecBuilder requestSpecBuilder, SfxOrderUpdateRequest updateRequest) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("handleAndUpdateSfxCallback");
    Response response = RestUtils.post(requestSpecBuilder, null, apiPath, updateRequest);
    return response;
  }

  /**
   * call create question api to create survey questions
   *
   * @param requestSpecBuilder
   * @param createSurveyQuestionRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response createSurveyQuestions(
      RequestSpecBuilder requestSpecBuilder,
      CreateSurveyQuestionRequest createSurveyQuestionRequest) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("createSurveyQuestion");
    Response response =
        RestUtils.post(requestSpecBuilder, null, apiPath, createSurveyQuestionRequest);
    return response;
  }

  /**
   * call get question api to fetch survey questions
   *
   * @param requestSpecBuilder
   * @param getSurveyQuestionsRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response getSurveyQuestions(
      RequestSpecBuilder requestSpecBuilder,
      GetSurveyQuestionsRequest getSurveyQuestionsRequest,
      String token,
      Boolean isVerifySchema,
      String... riderId) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    if (riderId.length > 0) {
      headers.put("x-rider-id", riderId[0]);
      headers.put("x-passed-by", "passed-by-kong");
    } else {
      headers.put("Authorization", token);
    }
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("getSurveyQuestions");
    Response response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, getSurveyQuestionsRequest);
    schemaValidators.validateResponseSchema(response, getSurveyQuestions, isVerifySchema);
    return response;
  }

  /**
   * call create answer api to record survey response
   *
   * @param requestSpecBuilder
   * @param answerSurveyQuestionRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response answerSurveyQuestions(
      RequestSpecBuilder requestSpecBuilder,
      List<AnswerSurveyQuestionRequest> answerSurveyQuestionRequest) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("answerSurveyQuestion");
    Response response =
        RestUtils.post(requestSpecBuilder, null, apiPath, answerSurveyQuestionRequest);
    return response;
  }

  /**
   * bulk update hu details to unpacked consignments
   *
   * @param requestSpecBuilder
   * @param bulkUpdateHuDetailsRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response bulkUpdateHuDetails(
      RequestSpecBuilder requestSpecBuilder,
      BulkUpdateHuDetailsRequest bulkUpdateHuDetailsRequest) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("bulkUpdateHuDetails");
    Response response =
        RestUtils.put(requestSpecBuilder, null, apiPath, bulkUpdateHuDetailsRequest);
    return response;
  }

  /**
   * This function is used to get ConsignmentDetails By storeId from dms dashboard
   *
   * @param requestSpecBuilder
   * @param storeId
   * @param requestMessage
   * @param adminPanelToken
   * @return response
   * @author Deepak_kumar
   */
  public Response getDmsDashboardConsignmentByStoreId(
      RequestSpecBuilder requestSpecBuilder,
      String storeId,
      GetConsignmentByStoreIdAndStatusRequest requestMessage,
      String adminPanelToken) {
    Response response = null;
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", adminPanelToken);
    requestSpecBuilder.setBaseUri(ZEPTO_ADMIN_DASHBOARD_BASEURL);
    String apiPath =
        String.format(DELIVERYSERVICE_ENDPOINT.get("getConsignmentByStoreId"), storeId);
    response = RestUtils.post(requestSpecBuilder, headers, apiPath, requestMessage);
    return response;
  }

  /**
   * Api to getInStoreRiders
   *
   * @param requestSpecBuilder
   * @param storeId
   * @author Abhishek_Singh
   * @return
   */
  public Response getInStoreRider(RequestSpecBuilder requestSpecBuilder, String storeId) {

    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("getInStoreRider");

    Map<String, Object> queryParam = new HashMap<>();
    queryParam.put("storeId", storeId);
    response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParam, null);
    return response;
  }

  /**
   * Manuall Assign api
   *
   * @param requestSpecBuilder
   * @param manuallyAssignRequest
   * @return
   * @author Abhishek_Singh
   */
  public Response manualAssign(
      RequestSpecBuilder requestSpecBuilder,
      ManuallyAssignRequest manuallyAssignRequest,
      int statusCode) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("manualAssign"));
    Response response = RestUtils.post(requestSpecBuilder, null, apiPath, manuallyAssignRequest);
    Assert.assertEquals(
        response.getStatusCode(),
        statusCode,
        "Response status code of manual assign api is not " + statusCode);
    return response;
  }

  /**
   * This method is used to create call_masking by orderId
   *
   * @param requestSpecBuilder
   * @param riderToken
   * @param orderId
   * @return
   * @author Deepak_Kumar
   */
  public Response customerCallMasking(
      RequestSpecBuilder requestSpecBuilder, String orderId, String riderToken) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    Map<String, String> headers = new HashMap<>();
    headers.put("authorization", "Bearer " + riderToken);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("order_id", orderId);
    queryParams.put("user_type", "RIDER");
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("callMasking"));
    Response response = RestUtils.post(requestSpecBuilder, headers, queryParams, null, apiPath, "");
    Assert.assertTrue(
        response.getStatusCode() == HttpStatus.SC_OK,
        "Customer call masking Response status code is not 200, it is " + response.getStatusCode());
    return response;
  }

  /**
   * This method is used to handover return item at store by riderId
   *
   * @param requestSpecBuilder
   * @param returnItemRequest
   * @param riderId
   * @return
   * @author Deepak_Kumar
   */
  public Response handoverReturnItemAtStoreAPI(
      RequestSpecBuilder requestSpecBuilder,
      HandoverReturnItemRequest returnItemRequest,
      String riderId)
      throws SQLException {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    String userId =
        mySqlZeptoBackendServiceHelper
            .queryDb(String.format(getUserIdForRiderId, riderId))
            .getJSONObject(0)
            .get("user_id")
            .toString();
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("x-rider-id", riderId);
    headers.put("x-passed-by", "passed-by-kong");
    headers.put("x-user-id", userId);
    headers.put("accept", "application/json");
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("returnItemHandover");
    Response response = RestUtils.post(requestSpecBuilder, headers, apiPath, returnItemRequest);
    return response;
  }

  /**
   * This method is used to scanItem for return/replacement at customer location
   *
   * @param requestSpecBuilder
   * @param riderToken
   * @param deliveryId
   * @return
   * @author Deepak_Kumar
   */
  public Response scanItem(
      RequestSpecBuilder requestSpecBuilder, String riderToken, String deliveryId, String EANCode) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    Map<String, String> headers = new HashMap<>();
    headers.put("authorization", "Bearer " + riderToken);
    headers.put("accept", "application/json");

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("ean_code", EANCode);
    queryParams.put("delivery_id", deliveryId);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("scanItem");
    Response response = RestUtils.post(requestSpecBuilder, headers, queryParams, null, apiPath, "");
    return response;
  }

  /**
   * This method is used to scanItem for return/replacement at customer location
   *
   * @param requestSpecBuilder
   * @param riderToken
   * @param qualityCheckRequest
   * @return
   * @author Deepak_Kumar
   */
  public Response itemQualityCheck(
      RequestSpecBuilder requestSpecBuilder,
      String riderToken,
      ItermQualityCheckRequest qualityCheckRequest) {
    requestSpecBuilder.setBaseUri(DMS_KONG_URL);
    Map<String, String> headers = new HashMap<>();
    headers.put("authorization", "Bearer " + riderToken);
    headers.put("accept", "application/json");

    String apiPath = DELIVERYSERVICE_ENDPOINT.get("itemQualityCheck");
    Response response = RestUtils.post(requestSpecBuilder, headers, apiPath, qualityCheckRequest);
    return response;
  }

  /**
   * This method is used to update huDetails of consignment from DMS dashboard
   *
   * @param requestSpecBuilder
   * @param consignmentId
   * @param huDetailsRequest
   * @return
   * @author Deepak_Kumar
   */
  public Response updateHuDetails(
      RequestSpecBuilder requestSpecBuilder,
      String consignmentId,
      UpdateHuDetailsRequest huDetailsRequest) {
    requestSpecBuilder.setBaseUri(ZEPTO_ADMIN_DASHBOARD_BASEURL);
    new BifrostHelper();
    Map<String, String> headers = new HashMap<>();
    headers.put("authorization", GeneralConstants.ADMIN_AUTHORIZATION);
    headers.put("accept", "application/json");
    headers.put("x-smart-action", "addOrderDetails");
    String apiPath = String.format(DELIVERYSERVICE_ENDPOINT.get("updateHuDetails"), consignmentId);
    Response response = RestUtils.put(requestSpecBuilder, headers, apiPath, huDetailsRequest);
    return response;
  }

  /**
   * This function is used to create loadShare order from zepto created order
   *
   * @param requestSpecBuilder
   * @param orderCode
   * @return response
   * @author Deepak_kumar
   */
  public Response createLoadShareOrder(RequestSpecBuilder requestSpecBuilder, String orderCode) {
    Response response = null;
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("orderCode", orderCode);
    String apiPath = DELIVERYSERVICE_ENDPOINT.get("createLoadShareOrder");
    response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParams, null, null);
    return response;
  }

  /**
   * This function is used to assign loadShare rider
   *
   * @param requestSpecBuilder
   * @param loadShareId
   * @param loadShareRiderId
   * @return response
   * @author Deepak_Kumar
   */
  public Response allocateLoadShareRider(
      RequestSpecBuilder requestSpecBuilder, Object loadShareId, Object loadShareRiderId) {
    requestSpecBuilder.setBaseUri(LOADSHARE_BASEURL);
    String apiPath = String.format(LOADSHARE_ENDPOINT.get("allocateLoadShareRider"));
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("queryName", "manual_alllocation");

    JSONObject innerJsonData = new JSONObject();
    innerJsonData.put("0", loadShareId);
    innerJsonData.put("1", loadShareRiderId);

    JSONObject innerJson = new JSONObject();
    innerJson.put("bodyParams", innerJsonData);

    JSONObject requestBody = new JSONObject();
    requestBody.put("userParams", innerJson);

    Response response =
        RestUtils.post(
            requestSpecBuilder,
            null,
            queryParams,
            null,
            apiPath,
            RequestMapper.mapPojoObjectAndGetString(requestBody.toMap()));
    return response;
  }

  /**
   * This function is used to get loadShare order Status
   *
   * @param requestSpecBuilder
   * @param orderCode
   * @return response
   * @author Deepak_Kumar
   */
  public Response getLoadShareOrderStatus(RequestSpecBuilder requestSpecBuilder, String orderCode) {
    requestSpecBuilder.setBaseUri(LOADSHARE_BASEURL);
    String apiPath = String.format(LOADSHARE_ENDPOINT.get("getOrderStatus"));
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("queryName", "get_order_status_update");

    JSONObject innerJsonData = new JSONObject();
    innerJsonData.put("0", orderCode);

    JSONObject innerJson = new JSONObject();
    innerJson.put("queryParams", innerJsonData);

    JSONObject requestBody = new JSONObject();
    requestBody.put("userParams", innerJson);

    Response response =
        RestUtils.post(
            requestSpecBuilder,
            null,
            queryParams,
            null,
            apiPath,
            RequestMapper.mapPojoObjectAndGetString(requestBody.toMap()));
    return response;
  }

  /**
   * This function is used to update loadShare order status
   *
   * @param requestSpecBuilder
   * @param orderCode
   * @return response
   * @author Deepak_kumar
   */
  public Response updateLoadShareOrderStatus(
      RequestSpecBuilder requestSpecBuilder, String orderCode, String shipmentStatus) {
    requestSpecBuilder.setBaseUri(DELIVERY_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("X-Consumer-Username", "loadshare");

    JSONObject requestBody = new JSONObject();
    requestBody.put("orderId", orderCode);
    requestBody.put("shipmentStatus", shipmentStatus);

    String apiPath = DELIVERYSERVICE_ENDPOINT.get("updateLoadShareOrderStatus");
    Response response =
        RestUtils.post(
            requestSpecBuilder,
            headers,
            apiPath,
            RequestMapper.mapPojoObjectAndGetString(requestBody.toMap()));
    return response;
  }

  /**
   * This function is used to cancel loadShare order
   *
   * @param requestSpecBuilder
   * @param orderCode
   * @return response
   * @author Deepak_Kumar
   */
  public Response cancelLoadShareOrder(RequestSpecBuilder requestSpecBuilder, String orderCode) {
    requestSpecBuilder.setBaseUri(LOADSHARE_BASEURL);
    String apiPath = String.format(LOADSHARE_ENDPOINT.get("cancelLoadShareOrder"));
    Map<String, Object> queryParameter = new HashMap<>();
    queryParameter.put("queryName", "cancel_order_status_Api");

    JSONObject queryParamsData = new JSONObject();
    queryParamsData.put("0", orderCode);

    String data =
        "a3458004e5fed07e3bd515eec043d5df3bc415df1a537ec06f4d838620af0412" + "|ZEPTO|" + orderCode;
    JSONObject headersParamsData = new JSONObject();
    headersParamsData.put("0", new GlobalUtil().getSha256(data));
    headersParamsData.put("1", "ZEPTO");

    JSONObject requestObject = new JSONObject();
    requestObject.put("headersParams", headersParamsData);
    requestObject.put("queryParams", queryParamsData);

    JSONObject requestBody = new JSONObject();
    requestBody.put("userParams", requestObject);

    Response response =
        RestUtils.post(
            requestSpecBuilder,
            null,
            queryParameter,
            null,
            apiPath,
            RequestMapper.mapPojoObjectAndGetString(requestBody.toMap()));
    return response;
  }
}
