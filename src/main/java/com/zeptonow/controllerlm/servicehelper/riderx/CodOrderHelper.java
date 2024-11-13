package com.zeptonow.controllerlm.servicehelper.riderx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RequestMapper;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.constants.Statuses;
import com.zeptonow.commonlm.helper.api.riderx.CodOrderApiHelper;
import com.zeptonow.commonlm.helper.api.riderx.DMSApiHelper;
import com.zeptonow.commonlm.helper.mysql.DbUtilApi;
import com.zeptonow.commonlm.helper.mysql.MySqlZeptoBackendServiceHelper;
import com.zeptonow.commonlm.pojo.codOrderPayment.request.RiderPaymentSettlementRequest;
import com.zeptonow.commonlm.pojo.codOrderPayment.response.MakePaymentResponse;
import com.zeptonow.commonlm.pojo.codOrderPayment.response.PaymentSettlementResponse;
import com.zeptonow.commonlm.pojo.codOrderPayment.response.UnsettledOrdersResponse;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.NearByRetailer;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.NearByRetailers;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.RetailStoreInfo;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.StoreDetails;
import com.zeptonow.commonlm.pojo.dms.response.BaseResponse;
import com.zeptonow.commonlm.pojo.dms.response.GetConsignmentResponse;
import com.zeptonow.controllerlm.servicehelper.riderx.dms.DMSHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CodOrderHelper {

  private final MySqlZeptoBackendServiceHelper mySqlZeptoBackendServiceHelper;
  private final LoggerUtil logger;
  private final CodOrderApiHelper codOrderApiHelper;
  private final DMSApiHelper dmsApiHelper;
  FakeValuesService fakeValuesService;
  DbUtilApi dbUtilApi;
  Environment environment;
  private DMSHelper dmsHelper;

  public CodOrderHelper() {
    fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
    dbUtilApi = new DbUtilApi();
    environment = new Environment();
    mySqlZeptoBackendServiceHelper = new MySqlZeptoBackendServiceHelper();
    logger = new LoggerUtil(this.getClass());
    codOrderApiHelper = new CodOrderApiHelper();
    dmsApiHelper = new DMSApiHelper();
    dmsHelper = new DMSHelper();
  }

  /**
   * Method used to validation transaction details in cod DB under cod_transaction table
   *
   * @param transactionId
   * @param amount
   * @param expectedPaymentStatus
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public void validateTransactionIdInDB(
      String transactionId, int amount, String expectedPaymentStatus) {
    JSONArray resultSet =
        mySqlZeptoBackendServiceHelper.queryDb(
            String.format(DBQueries.getTransactionDetails, transactionId));
    int amountInDB = resultSet.getJSONObject(0).getInt("amount_in_paise");
    String paymentStatusInDB = resultSet.getJSONObject(0).getString("payment_status");
    Assert.assertEquals(amountInDB, amount, "Amount verification failed.");
    Assert.assertEquals(
        paymentStatusInDB, expectedPaymentStatus, "Payment status verification failed.");
  }

  /**
   * Method used to create request pay load of rider payment settlement request
   *
   * @param amount
   * @param paymentMethod
   * @param nearByRetailer
   * @return
   * @aurthor Ajay_Mishra
   */
  public RiderPaymentSettlementRequest createRiderPaymentSettlementPayload(
      Object amount,
      String paymentMethod,
      String subPaymentMethod,
      String sellerId,
      NearByRetailer nearByRetailer) {
    RetailStoreInfo retailStoreInfo = null;
    if (nearByRetailer != null) {
      retailStoreInfo =
          RetailStoreInfo.builder()
              .shopName(nearByRetailer.shopName)
              .phoneNumber(nearByRetailer.phoneNumber)
              .address(nearByRetailer.address)
              .city(nearByRetailer.city)
              .pincode(nearByRetailer.pincode)
              .distance(nearByRetailer.distance)
              .location(nearByRetailer.location)
              .build();
    }
    return RiderPaymentSettlementRequest.builder()
        .amountInPaise(amount)
        .paymentMethod(paymentMethod)
        .subPaymentMethod(subPaymentMethod)
        .sellerId(sellerId)
        .retailStoreInfo(retailStoreInfo)
        .build();
  }

  /**
   * Method used to compare store details
   *
   * @param storeDetails
   * @param nearByRetailer
   * @author Ajay_Mishra
   */
  public void validateStoreInfo(StoreDetails storeDetails, NearByRetailer nearByRetailer) {

    Assert.assertEquals(
        storeDetails.getRetailPartner(),
        nearByRetailer.getRetailPartner(),
        "RetailPartner did not matched.");
    Assert.assertEquals(
        storeDetails.getAddress(), nearByRetailer.getAddress(), "Address did not matched.");
    Assert.assertEquals(
        storeDetails.getShopName(), nearByRetailer.getShopName(), "ShopName did not matched.");
    Assert.assertEquals(storeDetails.getCity(), nearByRetailer.getCity(), "City did not matched.");
    Assert.assertEquals(
        storeDetails.getPincode(), nearByRetailer.getPincode(), "Pincode did not matched.");
    Assert.assertEquals(
        storeDetails.getPhoneNumber(),
        nearByRetailer.getPhoneNumber(),
        "PhoneNumber did not matched.");
  }

  /**
   * Method used to create rider then create deposit request
   *
   * @return
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public Map createDepositRequest() {

    Response response = null;
    Map<String, Object> depositDetails = new HashMap<>();
    logger.info("Create consignment");
    Map<String, String> orderInfo = dmsHelper.createConsignmentAssigned();
    String consignmentId = orderInfo.get("consignmentId");
    String riderToken = orderInfo.get("riderToken");
    String riderId = orderInfo.get("riderId");
    String deliveryId = orderInfo.get("deliveryId");
    dmsHelper.updateConsignmentStatus(
        consignmentId, Statuses.PICKED_UP.consignmentStatus, riderToken);

    logger.info("Getting consignment details");
    response =
        dmsApiHelper.getConsignmentByConsignmentId(
            environment.getReqSpecBuilder(), consignmentId, false);
    Assert.assertEquals(
        response.statusCode(),
        HttpStatus.SC_OK,
        "Response code of api is not " + HttpStatus.SC_OK + ", it is :" + response.statusCode());

    BaseResponse getConsignmentResponse = response.as(BaseResponse.class);
    GetConsignmentResponse actualGetConsignmentResponse =
        new ObjectMapper()
            .readValue(
                RequestMapper.mapPojoObjectAndGetString(getConsignmentResponse.getData()),
                GetConsignmentResponse.class);

    int amount = actualGetConsignmentResponse.getPaymentAmount();
    String orderId = actualGetConsignmentResponse.getOrderId();
    String sellerId = actualGetConsignmentResponse.getOrderDetails().getSellerId();
    String storeId = actualGetConsignmentResponse.getStoreId();

    logger.info("Making cash payment with order amount");
    int count = 0;
    do {
      response =
          codOrderApiHelper.makePaymentForCodOrder(
              environment.getReqSpecBuilder(), "CASH", amount, orderId, riderToken, true);
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        break;
      }
      count++;
    } while (count < 5);
    Assert.assertEquals(
        response.statusCode(),
        HttpStatus.SC_OK,
        "Response code of make payment api is not "
            + HttpStatus.SC_OK
            + ", it is :"
            + response.statusCode());

    BaseResponse makePaymentResponse = response.as(BaseResponse.class);
    MakePaymentResponse actualMakePaymentResponse =
        new ObjectMapper()
            .readValue(
                RequestMapper.mapPojoObjectAndGetString(makePaymentResponse.getData()),
                MakePaymentResponse.class);

    // Assertions
    String actualPaymentStatus = actualMakePaymentResponse.getPaymentStatus();
    Assert.assertEquals(
        actualPaymentStatus,
        "SUCCESS",
        "Payment Status of make payment api is not 'SUCCESS', it is: " + actualPaymentStatus);

    // Get near-by retailers
    response =
        codOrderApiHelper.getNearByRetailers(
            environment.getReqSpecBuilder(),
            riderToken,
            "72.83035939678881",
            "19.00104835264737",
            amount);
    // TODO: 12/04/23
    //    Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response code of api is not
    // " + HttpStatus.SC_OK + ", it is :" + response.statusCode());

    NearByRetailer nearByRetailer;
    if (response.getStatusCode() == 500) {
      nearByRetailer = NearByRetailer.builder().build();
    } else {
      BaseResponse getNearByRetailerResponse = response.as(BaseResponse.class);
      NearByRetailers nearByRetailers =
          new ObjectMapper()
              .readValue(
                  RequestMapper.mapPojoObjectAndGetString(getNearByRetailerResponse.getData()),
                  NearByRetailers.class);
      nearByRetailer = nearByRetailers.getNearByRetailers().get(0);
    }

    // Get request body of rider Payment Settlement Request
    RiderPaymentSettlementRequest riderPaymentSettlementRequest =
        createRiderPaymentSettlementPayload(
            amount, "RETAIL_PAYMENT", "AIRTEL_MONEY", sellerId, nearByRetailer);

    logger.info("Settle unsettled order");
    count = 0;
    do {
      response =
          codOrderApiHelper.riderPaymentSettlement(
              environment.getReqSpecBuilder(), riderToken, riderPaymentSettlementRequest, true);
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        break;
      }
      count++;
    } while (count < 5);
    Assert.assertEquals(
        response.getStatusCode(), HttpStatus.SC_OK, "Response code verification got failed.");

    BaseResponse riderPaymentSettlementResponse = response.as(BaseResponse.class);
    PaymentSettlementResponse actualRiderPaymentSettlementResponse =
        new ObjectMapper()
            .readValue(
                RequestMapper.mapPojoObjectAndGetString(riderPaymentSettlementResponse.getData()),
                PaymentSettlementResponse.class);

    logger.info("Getting transaction ref no");
    String transactionRefNo =
        actualRiderPaymentSettlementResponse.getRetailStore().getStoreTransactionReference();
    String transactionId = actualRiderPaymentSettlementResponse.getTransactionId();

    depositDetails.put("riderId", riderId);
    depositDetails.put("riderToken", riderToken);
    depositDetails.put("amount", amount);
    depositDetails.put("transactionId", transactionId);
    depositDetails.put("transactionRefNo", transactionRefNo);
    depositDetails.put("nearByRetailer", nearByRetailer);
    return depositDetails;
  }

  /**
   * Method used to get payment callback for the provided transaction
   *
   * @param requestSpecBuilder
   * @param transactionRefNo
   * @param amount
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public void depositPaymentCallback(
      RequestSpecBuilder requestSpecBuilder,
      String transactionRefNo,
      String transactionId,
      int amount) {

    Response response = null;
    logger.info("Payment call back");
    response =
        codOrderApiHelper.riderSettlePaymentCallback(
            requestSpecBuilder, transactionRefNo, transactionId, amount / 100);
    Assert.assertEquals(
        response.getStatusCode(), HttpStatus.SC_OK, "Response code verification got failed.");
    logger.info("Waiting for 3 sec for payment settlement");
    Thread.sleep(3000);
  }

  /**
   * Method used to verify the unsettled orders
   *
   * @param actualUnsettledOrdersResponse
   * @param amount
   * @param sellerId
   * @param noOfOrders
   * @param orderStatus
   * @author Ajay_Mishra
   */
  public void verifyUnsettledOrders(
      UnsettledOrdersResponse actualUnsettledOrdersResponse,
      int amount,
      String sellerId,
      int noOfOrders,
      String orderStatus) {

    Assert.assertEquals(
        actualUnsettledOrdersResponse.getCashBalance(),
        amount,
        "Unsettled cash balance verification got failed");
    Assert.assertEquals(
        actualUnsettledOrdersResponse.getSellers().get(0).getSellerId(),
        sellerId,
        "Seller id verification got failed");
    Assert.assertEquals(
        actualUnsettledOrdersResponse.getSellers().get(0).getSettlementAmountLeft(),
        amount,
        "SettlementAmountLeft verification got failed");
    Assert.assertEquals(
        actualUnsettledOrdersResponse.getSellers().get(0).getMinDue().getReason(),
        "last week pending cash amount",
        "Min due reason verification got failed.");
    Assert.assertEquals(
        actualUnsettledOrdersResponse.getSellers().get(0).getUnsettledOrders().size(),
        noOfOrders,
        "No of unsettled orders should be 1.");
    Assert.assertEquals(
        actualUnsettledOrdersResponse
            .getSellers()
            .get(0)
            .getUnsettledOrders()
            .get(0)
            .getOrderAmountInPaise(),
        amount,
        "OrderAmountInPaise verification got failed.");
    Assert.assertEquals(
        actualUnsettledOrdersResponse.getSellers().get(0).getUnsettledOrders().get(0).getSellerId(),
        sellerId,
        "sellerId verification got failed.");
    Assert.assertEquals(
        actualUnsettledOrdersResponse
            .getSellers()
            .get(0)
            .getUnsettledOrders()
            .get(0)
            .getSettlementStatus(),
        orderStatus,
        "SettlementStatus verification got failed.");
  }

  /**
   * Method is used to create order then mark it delivered and get near-by retailers
   *
   * @return
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public Map<String, Object> createDeliveredOrdersGetNearByRetailers() {

    Response response = null;
    Map<String, Object> orderDetails = new HashMap<>();
    logger.info("Create consignment");
    Map<String, String> orderInfo = dmsHelper.createConsignmentAssigned();
    String consignmentId = orderInfo.get("consignmentId");
    String riderToken = orderInfo.get("riderToken");
    String riderId = orderInfo.get("riderId");
    String deliveryId = orderInfo.get("deliveryId");

    logger.info("Getting consignment details");
    response =
        dmsApiHelper.getConsignmentByConsignmentId(
            environment.getReqSpecBuilder(), consignmentId, false);
    Assert.assertEquals(
        response.statusCode(),
        HttpStatus.SC_OK,
        "Response code of api is not " + HttpStatus.SC_OK + ", it is :" + response.statusCode());

    BaseResponse getConsignmentResponse = response.as(BaseResponse.class);
    GetConsignmentResponse actualGetConsignmentResponse =
        new ObjectMapper()
            .readValue(
                RequestMapper.mapPojoObjectAndGetString(getConsignmentResponse.getData()),
                GetConsignmentResponse.class);

    int amount = actualGetConsignmentResponse.getPaymentAmount();
    String orderId = actualGetConsignmentResponse.getOrderId();
    String sellerId = actualGetConsignmentResponse.getOrderDetails().getSellerId();
    String storeId = actualGetConsignmentResponse.getStoreId();

    // Update consignment status
    dmsHelper.updateConsignmentFromAnyStatus(
        Statuses.ASSIGNED.consignmentStatus,
        Statuses.RETURNED_TO_STORE.consignmentStatus,
        consignmentId,
        riderToken);

    // Get near-by retailers
    response =
        codOrderApiHelper.getNearByRetailers(
            environment.getReqSpecBuilder(),
            riderToken,
            "72.83035939678881",
            "19.00104835264737",
            amount);
    // TODO: 12/04/23
    //    Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response code of api is not
    // " + HttpStatus.SC_OK + ", it is :" + response.statusCode());

    NearByRetailer nearByRetailer;
    if (response.getStatusCode() == 500) {
      nearByRetailer = NearByRetailer.builder().build();
    } else {
      BaseResponse getNearByRetailerResponse = response.as(BaseResponse.class);
      NearByRetailers nearByRetailers =
          new ObjectMapper()
              .readValue(
                  RequestMapper.mapPojoObjectAndGetString(getNearByRetailerResponse.getData()),
                  NearByRetailers.class);
      nearByRetailer = nearByRetailers.getNearByRetailers().get(0);
    }

    orderDetails.put("amount", amount);
    orderDetails.put("riderId", riderId);
    orderDetails.put("riderToken", riderToken);
    orderDetails.put("deliveryId", deliveryId);
    orderDetails.put("orderId", orderId);
    orderDetails.put("sellerId", sellerId);
    orderDetails.put("storeId", storeId);
    orderDetails.put("nearByRetailer", nearByRetailer);
    return orderDetails;
  }

  /**
   * Method is used to create cod order and make payment through QR mode of payment
   *
   * @return
   * @author Ajay_Mishra
   */
  @SneakyThrows
  public Map<String, Object> createQROrder() {

    Response response = null;
    Map<String, Object> orderDetailsAfterPayment = new HashMap();
    logger.info("Create consignment");
    Map<String, String> orderInfo = dmsHelper.createConsignmentAssigned();
    String consignmentId = orderInfo.get("consignmentId");
    String riderToken = orderInfo.get("riderToken");
    dmsHelper.updateConsignmentStatus(
        consignmentId, Statuses.PICKED_UP.consignmentStatus, riderToken);

    logger.info("Getting consignment details");
    response =
        dmsApiHelper.getConsignmentByConsignmentId(
            environment.getReqSpecBuilder(), consignmentId, false);
    BaseResponse getConsignmentResponse = response.as(BaseResponse.class);
    GetConsignmentResponse actualGetConsignmentResponse =
        new ObjectMapper()
            .readValue(
                RequestMapper.mapPojoObjectAndGetString(getConsignmentResponse.getData()),
                GetConsignmentResponse.class);

    int amount = actualGetConsignmentResponse.getPaymentAmount();
    String orderId = actualGetConsignmentResponse.getOrderId();
    String sellerId = actualGetConsignmentResponse.getOrderDetails().getSellerId();

    logger.info("Making cash payment with order amount");
    int count = 0;
    do {
      response =
          codOrderApiHelper.makePaymentForCodOrder(
              environment.getReqSpecBuilder(), "QR", amount, orderId, riderToken, false);
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        break;
      }
      count++;
      // Waiting for 1 sec to hit service again
      Thread.sleep(1000);
    } while (count < 5);
    Assert.assertEquals(
        response.statusCode(),
        HttpStatus.SC_OK,
        "Response code of make payment api is not "
            + HttpStatus.SC_OK
            + ", it is :"
            + response.statusCode());

    BaseResponse makePaymentResponse = response.as(BaseResponse.class);
    MakePaymentResponse actualMakePaymentResponse =
        new ObjectMapper()
            .readValue(
                RequestMapper.mapPojoObjectAndGetString(makePaymentResponse.getData()),
                MakePaymentResponse.class);

    // Assertions
    String actualPaymentStatus = actualMakePaymentResponse.getPaymentStatus();
    Assert.assertEquals(
        actualPaymentStatus,
        "INITIATED",
        "Payment Status of make payment api is not 'SUCCESS', it is: " + actualPaymentStatus);

    // Getting Transaction id
    String transactionId = actualMakePaymentResponse.getTransactionId();

    // Adding details to orderDetailsAfterPayment map
    orderDetailsAfterPayment.put("riderToken", riderToken);
    orderDetailsAfterPayment.put("orderId", orderId);
    orderDetailsAfterPayment.put("sellerId", sellerId);
    orderDetailsAfterPayment.put("transactionId", transactionId);
    orderDetailsAfterPayment.put("amount", amount);
    return orderDetailsAfterPayment;
  }
}
