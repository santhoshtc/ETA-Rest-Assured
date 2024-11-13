package com.zeptonow.commonlm.helper.api.riderx;

import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.pojo.codOrderPayment.request.RiderSettlePaymentCallback;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

public class CodOrderApiHelper {

  private final LoggerUtil logger;
  private final SchemaValidators schemaValidators;

  public CodOrderApiHelper() {

    logger = new LoggerUtil(this.getClass());
    schemaValidators = new SchemaValidators();
  }

  /**
   * Method used to make payment both for 'CASH' or 'QR' Mode
   *
   * @param requestSpecBuilder
   * @param modeOfPayment
   * @param amountInPaise
   * @param orderID
   * @param token
   * @param isVerifySchema
   * @return
   * @author Ajay_Mishra
   */
  public Response makePaymentForCodOrder(
      RequestSpecBuilder requestSpecBuilder,
      String modeOfPayment,
      Object amountInPaise,
      String orderID,
      String token,
      boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    String apiPath = String.format(CODSERVICE_ENDPOINT.get("makePayment"), orderID);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("modeOfPayment", modeOfPayment);
    queryParams.put("amountInPaise", amountInPaise);
    requestSpecBuilder.addQueryParams(queryParams);
    response = RestUtils.post(requestSpecBuilder, headers, apiPath);
    if (modeOfPayment.equalsIgnoreCase("CASH")) {
      schemaValidators.validateResponseSchema(response, makePayment_cod_cash, isVerifySchema);
    } else if (modeOfPayment.equalsIgnoreCase("QR")) {
      schemaValidators.validateResponseSchema(response, makePayment_cod_qr, isVerifySchema);
    }
    return response;
  }

  /**
   * Method used to check the payment status of the payment done by customer
   *
   * @param reqSpecBuilder
   * @param transactionId
   * @param token
   * @param isVerifySchema
   * @return
   * @author Ajay_Mishra
   */
  public Response checkPaymentStatus(
      RequestSpecBuilder reqSpecBuilder,
      String transactionId,
      String token,
      boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    String apiPath = String.format(CODSERVICE_ENDPOINT.get("checkPaymentStatus"), transactionId);
    response = RestUtils.get(reqSpecBuilder, headers, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, checkPaymentStatus, isVerifySchema);
    }
    return response;
  }

  /**
   * Method used to cancel the cod order payment
   *
   * @param reqSpecBuilder
   * @param token
   * @param transactionId
   * @return
   * @author Ajay_Mishra
   */
  public Response cancelPayment(
      RequestSpecBuilder reqSpecBuilder,
      String token,
      String transactionId,
      boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    String apiPath =
        CODSERVICE_ENDPOINT.get("cancelPayment").replace("{transactionId}", transactionId);
    response = RestUtils.post(reqSpecBuilder, headers, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, cancelPayment, isVerifySchema);
    }
    return response;
  }

  /**
   * Method returns the list of near by retailers by supplying lattitude and longitude
   *
   * @param reqSpecBuilder
   * @param token
   * @param longitude
   * @param latitude
   * @param amountInPaise
   * @return
   * @author Ajay_Mishra
   */
  public Response getNearByRetailers(
      RequestSpecBuilder reqSpecBuilder,
      String token,
      String longitude,
      String latitude,
      int amountInPaise) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("longitude", longitude);
    queryParams.put("latitude", latitude);
    queryParams.put("amountInPaise", amountInPaise);
    reqSpecBuilder.addQueryParams(queryParams);
    String apiPath = String.format(CODSERVICE_ENDPOINT.get("getNearByRetailers"));
    response = RestUtils.get(reqSpecBuilder, headers, apiPath);
    return response;
  }

  /**
   * Method used for settlement of the payment for rider to a specific seller
   *
   * @param reqSpecBuilder
   * @param token
   * @return
   * @author Ajay_Mishra
   */
  public Response riderPaymentSettlement(
      RequestSpecBuilder reqSpecBuilder, String token, Object requestBody, boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    String apiPath = CODSERVICE_ENDPOINT.get("riderPaymentSettlement");
    response = RestUtils.post(reqSpecBuilder, headers, apiPath, requestBody);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, riderPaymentSettlement, isVerifySchema);
    }
    return response;
  }

  /**
   * Method used for getting total unsettled orders for a rider against every seller
   *
   * @param reqSpecBuilder
   * @param token
   * @return
   * @author Ajay_Mishra
   */
  public Response getUnsettledOrders(
      RequestSpecBuilder reqSpecBuilder, String token, boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    String apiPath = String.format(CODSERVICE_ENDPOINT.get("getUnsettledOrders"));
    response = RestUtils.get(reqSpecBuilder, headers, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, getUnsettledOrders, isVerifySchema);
    }
    return response;
  }

  /**
   * Method returns rider pending cash for settlement
   *
   * @param reqSpecBuilder
   * @param token
   * @return
   */
  public Response getRiderCashBalance(
      RequestSpecBuilder reqSpecBuilder, String token, boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    String apiPath = CODSERVICE_ENDPOINT.get("riderCashBalance");
    response = RestUtils.get(reqSpecBuilder, headers, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, getRiderCashBalance, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get payment callback for rider payment settlement request
   *
   * @param reqSpecBuilder
   * @param transactionRefNo
   * @param amount
   * @return
   * @author Ajay_Mishra
   */
  public Response riderSettlePaymentCallback(
      RequestSpecBuilder reqSpecBuilder,
      String transactionRefNo,
      String transactionId,
      Integer amount) {

    Response response = null;
    reqSpecBuilder.setBaseUri(PAYMENT_SETTLEMENT_CALLBACK);
    Map<String, String> headers = new HashMap<>();
    headers.put("x-api-key", "x_api_key");
    RiderSettlePaymentCallback riderSettlePaymentCallback =
        RiderSettlePaymentCallback.builder()
            .amount(amount)
            .transactionRefNo(transactionId)
            .transactionId(transactionRefNo)
            .build();
    String apiPath =
        CODSERVICE_ENDPOINT.get("paymentCallback").replace("{transactionRefNo}", transactionRefNo);
    response = RestUtils.post(reqSpecBuilder, headers, apiPath, riderSettlePaymentCallback);
    return response;
  }

  /**
   * Method used for getting settlement amount verify against the seller getUnsettledOrders
   *
   * @param reqSpecBuilder
   * @param amountInPaise
   * @param sellerId
   * @return
   * @author Ajay_Mishra
   */
  public Response verifySettlementAmount(
      RequestSpecBuilder reqSpecBuilder,
      String token,
      String amountInPaise,
      String sellerId,
      boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("amountInPaise", amountInPaise);
    queryParams.put("sellerId", sellerId);
    reqSpecBuilder.addQueryParams(queryParams);
    String apiPath = CODSERVICE_ENDPOINT.get("verifySettlementAmount");
    response = RestUtils.get(reqSpecBuilder, headers, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, getVerifySettlementAmount, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to get the rider deposit
   *
   * @param reqSpecBuilder
   * @param token
   * @param limit
   * @param offset
   * @return
   * @author Ajay_Mishra
   */
  public Response getRiderDeposits(
      RequestSpecBuilder reqSpecBuilder,
      String token,
      Object limit,
      Object offset,
      boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("limit", limit);
    queryParams.put("offset", offset);
    String apiPath = CODSERVICE_ENDPOINT.get("riderDeposit");
    response = RestUtils.get(reqSpecBuilder, headers, apiPath, queryParams, null);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(response, getRiderDeposit, isVerifySchema);
    }
    return response;
  }

  /**
   * Method used to verify vpa
   *
   * @param reqSpecBuilder
   * @param riderToken
   * @param vpa
   * @return
   * @author Ajay_Mishra
   */
  public Response verifyVPA(RequestSpecBuilder reqSpecBuilder, String riderToken, String vpa) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + riderToken);
    JSONObject body = new JSONObject();
    body.put("vpa", vpa);
    String apiPath = CODSERVICE_ENDPOINT.get("verifyVPA");
    response = RestUtils.post(reqSpecBuilder, headers, apiPath, body.toString());
    return response;
  }

  /**
   * Method used to get the deposit transaction details done by rider to the retailer
   *
   * @param reqSpecBuilder
   * @param riderToken
   * @param transactionId
   * @return
   * @author Ajay_Mishra
   */
  public Response getDepositTransactionDetails(
      RequestSpecBuilder reqSpecBuilder,
      String riderToken,
      String transactionId,
      Boolean isVerifySchema) {

    Response response = null;
    reqSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_KONG_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + riderToken);
    String apiPath =
        CODSERVICE_ENDPOINT
            .get("getDepositTransactionDetails")
            .replace("{transactionId}", transactionId);
    response = RestUtils.get(reqSpecBuilder, headers, apiPath);
    boolean flag = false;
    try {
      flag = response.jsonPath().getString("errors[0].error").length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    }
    if (flag) {
      schemaValidators.validateResponseSchema(response, checkErrorPaymentStatus, isVerifySchema);
    } else {
      schemaValidators.validateResponseSchema(
          response, getDepositTransactionDetails, isVerifySchema);
    }
    return response;
  }

  /**
   * Method is used to post rider payout settlement request
   *
   * @param requestSpecBuilder
   * @param authToken
   * @param csvFile
   * @return
   * @author Vinith_Arjun
   */
  public Response riderPayouts(
      RequestSpecBuilder requestSpecBuilder,
      String authToken,
      File csvFile,
      boolean isVerifySchema) {

    Response response = null;
    requestSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authToken);
    HashMap<Object, Object> formParam = new HashMap<>();
    formParam.put("csvFile", csvFile);
    formParam.put("attachmentId", "f6300d05-2f00-422f-9bc1-069cb33aa1e2");
    formParam.put(
        "attachmentPath", "/rider/weekly-payouts/f6300d05-2f00-422f-9bc1-069cb33aa1e2.csv");

    String apiPath = CODSERVICE_ENDPOINT.get("riderPayouts");

    response = RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, formParam);
    schemaValidators.validateResponseSchema(response, postRiderPayouts, isVerifySchema);
    return response;
  }

  /**
   * Method is used to calculate rider payout settlement request executed and this is a work around
   * for qa to bypass cron
   *
   * @param requestSpecBuilder
   * @return
   * @author Vinith_Arjun
   */
  public Response calculateRiderPayouts(RequestSpecBuilder requestSpecBuilder) {

    Response response = null;
    requestSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, String> headers = new HashMap<>();

    String apiPath = CODSERVICE_ENDPOINT.get("calculateRiderPayouts");

    response = RestUtils.post(requestSpecBuilder, headers, apiPath);
    return response;
  }

  /**
   * Reverts the cash collected by rider, used when customer rejects to take delivery
   *
   * @param requestSpecBuilder
   * @param orderId
   * @return
   * @author Ajay_Mishra
   */
  public Response revertCashCollectedByRider(
      RequestSpecBuilder requestSpecBuilder, String orderId) {
    Response response = null;
    requestSpecBuilder.setBaseUri(COD_ORDER_PAYMENT_BASEURL);
    Map<String, String> headers = new HashMap<>();

    String apiPath = String.format(CODSERVICE_ENDPOINT.get("revertCashCollectedByRider"), orderId);

    response = RestUtils.put(requestSpecBuilder, headers, apiPath);
    return response;
  }
}
