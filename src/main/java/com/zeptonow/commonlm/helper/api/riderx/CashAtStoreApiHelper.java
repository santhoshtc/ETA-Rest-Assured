package com.zeptonow.commonlm.helper.api.riderx;

import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.SchemaPath;
import com.zeptonow.commonlm.pojo.cashatstore.request.HandOverCashToStore;
import com.zeptonow.commonlm.pojo.cashatstore.request.PayNearByRequest;
import com.zeptonow.commonlm.pojo.cashatstore.request.PayNearBySettlementRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

public class CashAtStoreApiHelper {

  private final SchemaValidators schemaValidators;
  private final LoggerUtil logger;

  public CashAtStoreApiHelper() {

    schemaValidators = new SchemaValidators();
    logger = new LoggerUtil(this.getClass());
  }

  /**
   * Method is used to collect cash by store by providing storeId, requestId and amountInPaise
   *
   * @param reqSpecBuilder
   * @param storeId
   * @param requestId
   * @param amountInPaise
   * @param isVerifySchema
   * @return
   * @author Ajay_Mishra
   */
  public Response collectCash(
      RequestSpecBuilder reqSpecBuilder,
      String adminToken,
      String storeId,
      String requestId,
      Object amountInPaise,
      boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", adminToken);

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("amountInPaise", amountInPaise);
    String apiPath = String.format(CASHATSTORE_ENDPOINT.get("collectCash"), storeId, requestId);
    response = RestUtils.post(reqSpecBuilder, headers, queryParams, null, apiPath, "");
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, postCollectCashByStore, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get transaction(rider payment settlement to Store) details by providing
   * Transaction Code(Payment code or request Id)
   *
   * @param reqSpecBuilder
   * @param paymentCode
   * @param isVerifySchema
   * @return
   * @author Ajay_Mishra
   */
  public Response getTransactionDetailsByCode(
      RequestSpecBuilder reqSpecBuilder, String paymentCode, boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("paymentCode", paymentCode);
    String apiPath = CASHATSTORE_ENDPOINT.get("getTransactionDetails");
    response = RestUtils.get(reqSpecBuilder, null, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, getCashAtStoreTransactionDetails, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get transaction(rider payment settlement to Store) details by providing
   * transaction id
   *
   * @param reqSpecBuilder
   * @param transactionId
   * @param isVerifySchema
   * @return
   * @authpr Ajay_Mishra
   */
  public Response getTransactionDetailsById(
      RequestSpecBuilder reqSpecBuilder, String transactionId, boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("transactionId", transactionId);
    String apiPath = CASHATSTORE_ENDPOINT.get("getTransactionDetails");
    response = RestUtils.get(reqSpecBuilder, null, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, getCashAtStoreTransactionDetails, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get the store ledger balance
   *
   * @param reqSpecBuilder
   * @param storeId
   * @return
   * @author Ajay_Mishra
   */
  public Response getStoreLedgerInfo(
      RequestSpecBuilder reqSpecBuilder, String storeId, Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    String apiPath = CASHATSTORE_ENDPOINT.get("getStoreLedgerInfo").replace("{storeId}", storeId);
    response = RestUtils.get(reqSpecBuilder, null, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, getStoreLedgerBalance, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get unsettled orders for the provided store
   *
   * @param reqSpecBuilder
   * @param storeId
   * @return
   * @author Ajay_Mishra
   */
  public Response getStoreUnsettledTransactions(
      RequestSpecBuilder reqSpecBuilder, String storeId, Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    String apiPath =
        CASHATSTORE_ENDPOINT.get("getStoreUnsettledTransaction").replace("{storeId}", storeId);
    response = RestUtils.get(reqSpecBuilder, null, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, getUnsettledTransactionDetails, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get cash dashboard details by providing store id and rider status
   *
   * @param reqSpecBuilder
   * @param storeId
   * @param riderStatus
   * @return
   * @author Ajay_Mishra
   */
  public Response getCashDashboardByStoreIdAndRiderStatus(
      RequestSpecBuilder reqSpecBuilder,
      String storeId,
      String riderStatus,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("riderStatus", riderStatus);
    String apiPath =
        CASHATSTORE_ENDPOINT
            .get("getCashStoreDashBoardByStoreIdAndRiderStatus")
            .replace("{storeId}", storeId);
    response = RestUtils.get(reqSpecBuilder, null, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, getDashboardByStoreIdAndRiderStatus, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get cash dashboard details(Pagination wise) by providing store id and rider
   * status
   *
   * @param reqSpecBuilder
   * @param storeId
   * @param riderStatus
   * @param pageNo
   * @return
   * @author Ajay_Mishra
   */
  public Response getCashDashboardByStoreIdAndRiderStatusPaginationWise(
      RequestSpecBuilder reqSpecBuilder,
      String storeId,
      String riderStatus,
      int pageNo,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("riderStatus", riderStatus);
    queryParams.put("pageNo", pageNo);
    String apiPath =
        CASHATSTORE_ENDPOINT
            .get("getCashStoreDashBoardByStoreIdAndRiderStatus")
            .replace("{storeId}", storeId);
    response = RestUtils.get(reqSpecBuilder, null, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, getDashboardByStoreIdAndRiderStatus, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get rider details by providing vendor Id
   *
   * @param reqSpecBuilder
   * @param storeId
   * @param vendorRiderId
   * @return
   * @author Ajay_Mishra
   */
  public Response getDetailsByRiderVendorId(
      RequestSpecBuilder reqSpecBuilder,
      String storeId,
      String vendorRiderId,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("vendorRiderId", vendorRiderId);
    String apiPath =
        CASHATSTORE_ENDPOINT
            .get("getDetailsByRiderVendorIdORNumberORName")
            .replace("{storeId}", storeId);
    response = RestUtils.get(reqSpecBuilder, null, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, SchemaPath.getRiderDetails, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get rider details by providing rider mobile number
   *
   * @param reqSpecBuilder
   * @param storeId
   * @param mobileNumber
   * @return
   * @author Ajay_Mishra
   */
  public Response getDetailsByRiderMobileNumber(
      RequestSpecBuilder reqSpecBuilder,
      String storeId,
      String mobileNumber,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("mobileNumber", mobileNumber);
    String apiPath =
        CASHATSTORE_ENDPOINT
            .get("getDetailsByRiderVendorIdORNumberORName")
            .replace("{storeId}", storeId);
    response = RestUtils.get(reqSpecBuilder, null, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, SchemaPath.getRiderDetails, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to hand over the cash to store
   *
   * @param reqSpecBuilder
   * @param token
   * @param storeId
   * @param HandOverCashToStoreRequest
   * @return
   * @author Ajay_Mishra
   */
  public Response handOverTransactionToStore(
      RequestSpecBuilder reqSpecBuilder,
      String token,
      String storeId,
      HandOverCashToStore HandOverCashToStoreRequest,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath =
        CASHATSTORE_ENDPOINT.get("handOverTransactionToStore").replace("{storeId}", storeId);
    response = RestUtils.post(reqSpecBuilder, headers, apiPath, HandOverCashToStoreRequest);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, SchemaPath.postHandOverCashToSeller, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to upload the store hand over receipt
   *
   * @param reqSpecBuilder
   * @param adminToken
   * @param requestObject
   * @return
   * @author Ajay_Mishra
   */
  public Response uploadReceipt(
      RequestSpecBuilder reqSpecBuilder,
      String adminToken,
      Map<Object, Object> requestObject,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(RMS_BASEURL_WITHOUT_API);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", adminToken);
    String apiPath = CASHATSTORE_ENDPOINT.get("uploadReceipt");
    response = RestUtils.post(reqSpecBuilder, headers, apiPath, null, null, requestObject);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, postUploadReceipt, isVerifySchema);
    }
    return response;
  }

  /**
   * Create pay nearby request to handover store cash
   *
   * @param reqSpecBuilder
   * @param token
   * @param storeId
   * @param payNearByRequest
   * @param isVerifySchema
   * @return
   * @author Manisha_Kumari
   */
  public Response createPayNearByRequest(
      RequestSpecBuilder reqSpecBuilder,
      String token,
      String storeId,
      PayNearByRequest payNearByRequest,
      Boolean isVerifySchema) {
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    String apiPath = String.format(CASHATSTORE_ENDPOINT.get("createPayNearByRequest"), storeId);
    Response response =
        RestUtils.post(reqSpecBuilder, headers, apiPath, null, null, payNearByRequest);
    return response;
  }

  /**
   * Settle pay nearby transaction using payment webhook
   *
   * @param reqSpecBuilder
   * @param settlementWebhookRequest
   * @return
   * @author Manisha_Kumari
   */
  public Response settlePayNearByTransaction(
      RequestSpecBuilder reqSpecBuilder, PayNearBySettlementRequest settlementWebhookRequest) {
    reqSpecBuilder.setBaseUri(PNB_PAYMENT_WEBHOOK_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("x-api-key", "x_pay_nearby_key");
    String apiPath = CASHATSTORE_ENDPOINT.get("settlePayNearByTransactionCallback");
    Response response =
        RestUtils.post(reqSpecBuilder, headers, apiPath, null, null, settlementWebhookRequest);
    return response;
  }

  /**
   * Method is used to hand over the cash to store using bulk upload
   *
   * @param reqSpecBuilder
   * @param token
   * @param bulkUploadCsv
   * @return
   * @author Manisha_Kumari
   */
  public Response handOverCashUsingBulkUploadToStore(
      RequestSpecBuilder reqSpecBuilder, String token, File bulkUploadCsv) {

    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", token);
    headers.put("Content-Type", "multipart/form-data;");
    String apiPath = CASHATSTORE_ENDPOINT.get("handoverCashBulkUpload");

    Map<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", bulkUploadCsv);

    Response response = RestUtils.post(reqSpecBuilder, headers, apiPath, null, null, formParam);
    return response;
  }
}
