/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.controllerlm.servicehelper.riderx.dms;

import static com.zeptonow.commonlm.constants.DBQueries.getConsignmentDetails;
import static com.zeptonow.commonlm.constants.DBQueries.getDeliveryDetails;
import static com.zeptonow.commonlm.constants.Statuses.ARRIVED;
import static com.zeptonow.commonlm.constants.Statuses.ASSIGNED;
import static com.zeptonow.commonlm.constants.Statuses.AUTO_ARRIVED;
import static com.zeptonow.commonlm.constants.Statuses.CANCELLED;
import static com.zeptonow.commonlm.constants.Statuses.CREATED;
import static com.zeptonow.commonlm.constants.Statuses.DELIVERED;
import static com.zeptonow.commonlm.constants.Statuses.PICKED_UP;
import static com.zeptonow.commonlm.constants.Statuses.READY_TO_ASSIGN;
import static com.zeptonow.commonlm.constants.Statuses.RETURNED;
import static com.zeptonow.commonlm.constants.Statuses.RETURNED_TO_STORE;
import static com.zeptonow.commonlm.constants.Statuses.RETURN_TO_ORIGIN;
import static com.zeptonow.commonlm.constants.Statuses.STARTED;
import static com.zeptonow.commonlm.constants.Statuses.THIRD_PARTY_ALLOTMENT_PENDING;
import static com.zeptonow.commonlm.constants.Statuses.UNASSIGNED;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.itextpdf.text.DocumentException;
import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RequestMapper;
import com.zepto.common.fileUtils.MapperUtils;
import com.zepto.common.logger.LoggerUtil;
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.constants.RiderDeliveryStatus;
import com.zeptonow.commonlm.constants.RiderDeliveryStatusCounts;
import com.zeptonow.commonlm.constants.Statuses;
import com.zeptonow.commonlm.helper.api.riderx.CashAtStoreApiHelper;
import com.zeptonow.commonlm.helper.api.riderx.CodOrderApiHelper;
import com.zeptonow.commonlm.helper.api.riderx.DMSApiHelper;
import com.zeptonow.commonlm.helper.api.riderx.RiderHomePageApiHelper;
import com.zeptonow.commonlm.helper.mysql.MySqlDMSHelper;
import com.zeptonow.commonlm.helper.mysql.MySqlRmsHelper;
import com.zeptonow.commonlm.helper.mysql.MySqlZeptoBackendServiceHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.codOrderPayment.request.RiderPaymentSettlementRequest;
import com.zeptonow.commonlm.pojo.codOrderPayment.response.MakePaymentResponse;
import com.zeptonow.commonlm.pojo.codOrderPayment.response.PaymentSettlementResponse;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.NearByRetailer;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.NearByRetailers;
import com.zeptonow.commonlm.pojo.dms.request.AssignRiderRequest;
import com.zeptonow.commonlm.pojo.dms.request.CreateConsignmentRequest;
import com.zeptonow.commonlm.pojo.dms.request.ForceCheckoutRequest;
import com.zeptonow.commonlm.pojo.dms.request.GetConsignmentByStoreIdAndStatusRequest;
import com.zeptonow.commonlm.pojo.dms.request.GetRiderTripInfoRequest;
import com.zeptonow.commonlm.pojo.dms.request.ItermQualityCheckRequest;
import com.zeptonow.commonlm.pojo.dms.request.SearchRiderRequest;
import com.zeptonow.commonlm.pojo.dms.request.UpdateConsignmentRequest;
import com.zeptonow.commonlm.pojo.dms.request.UpdateDeliveryStatusRequest;
import com.zeptonow.commonlm.pojo.dms.request.UpdateRiderDeliveryStatusRequest;
import com.zeptonow.commonlm.pojo.dms.request.UpdateTripStatusRequest;
import com.zeptonow.commonlm.pojo.dms.response.BaseResponse;
import com.zeptonow.commonlm.pojo.dms.response.CreateConsignmentResponse;
import com.zeptonow.commonlm.pojo.dms.response.ForceCheckoutResponse;
import com.zeptonow.commonlm.pojo.dms.response.GetConsignmentResponse;
import com.zeptonow.commonlm.pojo.dms.response.GetRiderOrdersHistoryResponse;
import com.zeptonow.commonlm.pojo.dms.response.GetRiderTripInfoResponse;
import com.zeptonow.commonlm.pojo.dms.response.GetTripByIdResponse;
import com.zeptonow.commonlm.pojo.dms.response.SearchRidersByFilterResponse;
import com.zeptonow.commonlm.pojo.dms.subclass.ConsignmentSummaryCount;
import com.zeptonow.commonlm.pojo.dms.subclass.CurrentConsignmentInfo;
import com.zeptonow.commonlm.pojo.dms.subclass.CustomerDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.Delivery;
import com.zeptonow.commonlm.pojo.dms.subclass.Eligibility;
import com.zeptonow.commonlm.pojo.dms.subclass.Error;
import com.zeptonow.commonlm.pojo.dms.subclass.ExtraDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.HuDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.ItemDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.NewTripDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderDetailsResponse;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderHistory;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderList;
import com.zeptonow.commonlm.pojo.dms.subclass.Orders;
import com.zeptonow.commonlm.pojo.dms.subclass.PackerDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.QualityCheckItemDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.QualityCheckQuestion;
import com.zeptonow.commonlm.pojo.dms.subclass.RiderDeliveryStatusSummary;
import com.zeptonow.commonlm.pojo.dms.subclass.RiderDetail;
import com.zeptonow.commonlm.pojo.dms.subclass.StatusChangeLog;
import com.zeptonow.commonlm.pojo.dms.subclass.VehicleDetails;
import com.zeptonow.commonlm.pojo.riderhomepage.request.RiderCheckInCheckOutRequest;
import com.zeptonow.commonlm.pojo.riderhomepage.response.RiderCheckInOutStatusResponse;
import com.zeptonow.controllerlm.servicehelper.BifrostHelper;
import com.zeptonow.controllerlm.servicehelper.RiderHelper;
import com.zeptonow.controllerlm.servicehelper.riderx.CheckInCheckOutHelper;
import com.zeptonow.controllerlm.servicehelper.riderx.CodOrderHelper;
import com.zeptonow.controllerlm.servicehelper.riderx.RiderHomePageHelper;
import io.restassured.response.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.text.CaseUtils;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class DMSHelper {

  MySqlDMSHelper mySqlDMSHelper;
  HashMap<String, ArrayList<String>> nextConsignmentStatus;
  ObjectMapper mapper;
  private Environment environment;
  private Response response;
  private DMSApiHelper dmsApiHelper;
  private LoggerUtil logger;
  private SoftAssert softAssert;
  private GlobalUtil globalUtil;
  private CheckInCheckOutHelper checkInCheckOutHelper;
  private CodOrderApiHelper codOrderApiHelper;
  private BifrostHelper bifrostHelper;

  public DMSHelper() {
    logger = new LoggerUtil(DMSHelper.class);
    checkInCheckOutHelper = new CheckInCheckOutHelper();
    mySqlDMSHelper = new MySqlDMSHelper();
    environment = new Environment();
    globalUtil = new GlobalUtil();
    softAssert = new SoftAssert();
    nextConsignmentStatus = new HashMap<>();
    dmsApiHelper = new DMSApiHelper();
    response = null;
    mapper = new ObjectMapper();
    codOrderApiHelper = new CodOrderApiHelper();
  }

  /**
   * This function initialize nextStatus Possible for any particular consignment status
   *
   * @author Deepak_Kumar
   */
  public void setNextConsignmentStatusData() {
    nextConsignmentStatus.put(
            Statuses.CREATED.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.ASSIGNED.consignmentStatus,
                            Statuses.CANCELLED.consignmentStatus,
                            Statuses.READY_TO_ASSIGN.consignmentStatus,
                            THIRD_PARTY_ALLOTMENT_PENDING.consignmentStatus,
                            "order status not found",
                            "order status not found")));
    nextConsignmentStatus.put(
            Statuses.ASSIGNED.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.CANCELLED.consignmentStatus,
                            Statuses.UNASSIGNED.consignmentStatus,
                            Statuses.PICKED_UP.consignmentStatus,
                            Statuses.READY_TO_ASSIGN.consignmentStatus,
                            Statuses.THIRD_PARTY_ARRIVED_AT_STORE.consignmentStatus,
                            THIRD_PARTY_ALLOTMENT_PENDING.consignmentStatus,
                            Statuses.DISPATCHED.consignmentStatus,
                            "order status not found",
                            "order status not found")));
    nextConsignmentStatus.put(
            Statuses.PICKED_UP.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.RETURN_TO_ORIGIN.consignmentStatus,
                            Statuses.DISPATCHED.consignmentStatus,
                            Statuses.STARTED.consignmentStatus)));
    nextConsignmentStatus.put(
            Statuses.STARTED.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.AUTO_ARRIVED.consignmentStatus,
                            Statuses.ARRIVED.consignmentStatus,
                            Statuses.RETURN_TO_ORIGIN.consignmentStatus)));
    nextConsignmentStatus.put(
            Statuses.UNASSIGNED.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.ASSIGNED.consignmentStatus,
                            Statuses.CANCELLED.consignmentStatus,
                            Statuses.READY_TO_ASSIGN.consignmentStatus,
                            THIRD_PARTY_ALLOTMENT_PENDING.consignmentStatus,
                            "order status not found",
                            "order status not found")));
    nextConsignmentStatus.put(
            Statuses.AUTO_ARRIVED.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.ARRIVED.consignmentStatus, Statuses.RETURN_TO_ORIGIN.consignmentStatus)));
    nextConsignmentStatus.put(
            Statuses.ARRIVED.consignmentStatus,
            new ArrayList<>(
                    Arrays.asList(
                            Statuses.RETURN_TO_ORIGIN.consignmentStatus,
                            Statuses.DELIVERED.consignmentStatus)));
    nextConsignmentStatus.put(
            Statuses.RETURN_TO_ORIGIN.consignmentStatus,
            new ArrayList<>(Arrays.asList(Statuses.RETURNED.consignmentStatus)));
    nextConsignmentStatus.put(Statuses.CANCELLED.consignmentStatus, new ArrayList<>());
    nextConsignmentStatus.put(Statuses.RETURNED.consignmentStatus, new ArrayList<>());
    nextConsignmentStatus.put(Statuses.DELIVERED.consignmentStatus, new ArrayList<>());
  }

  /**
   * This function will validate response against database i consignment and delivery data is
   * updated correctly
   *
   * @param consignmentId
   * @param createConsignmentRequest
   * @throws SQLException
   * @throws JsonProcessingException
   * @author Abhishek_Singh
   */
  public void verifyConsignmentAndDeliveryTableData(
          String consignmentId, CreateConsignmentRequest createConsignmentRequest)
          throws SQLException, JsonProcessingException, InterruptedException {

    logger.info("Waiting for 3 sec for data to get populated");
    Thread.sleep(3000L);
    String consignmentDataQuery = String.format(getConsignmentDetails, consignmentId);
    MySqlDMSHelper mySqlDMSHelper = new MySqlDMSHelper();
    JSONArray consignmentRowDataList = mySqlDMSHelper.queryDb(consignmentDataQuery);

    Assert.assertTrue(
            consignmentRowDataList.length() > 0,
            "No Data found in consignment table for consignment id -" + consignmentId);
    JSONObject consignmentRowData = consignmentRowDataList.getJSONObject(0);
    Assert.assertTrue(
            consignmentRowData.getString("status").equalsIgnoreCase("CREATED"),
            "Consignment status is " + consignmentRowData.getString("status"));
    Assert.assertTrue(
            consignmentRowData.getString("payment_mode").equalsIgnoreCase("COD"),
            "Payment mode is not COD");

    // verifying order details api response vs database
    String orderDetailsJson = consignmentRowData.get("order_details").toString();
    ObjectMapper objectMapper = new ObjectMapper();
    OrderDetailsResponse orderDetailsResponse =
            objectMapper.readValue(orderDetailsJson, OrderDetailsResponse.class);
    verifyOrderDetailsInDbAgainstRequestDetails(orderDetailsResponse, createConsignmentRequest);

    // verifying customer details api response vs database
    String customerDetailsJson = consignmentRowData.get("customer_details").toString();
    CustomerDetails customerDetails =
            objectMapper.readValue(customerDetailsJson, CustomerDetails.class);
    Assert.assertTrue(
            customerDetails.equals(createConsignmentRequest.getCustomerDetails()),
            "Customer details is not coming correctly in database compare to api response");

    String deliveryDataQuery = String.format(getDeliveryDetails, consignmentId);
    JSONArray deliveryRowDataList = mySqlDMSHelper.queryDb(deliveryDataQuery);
    Assert.assertTrue(
            deliveryRowDataList.length() > 0,
            "No Data found in delivery table for consignment id -" + consignmentId);
    JSONObject deliveryRowData = deliveryRowDataList.getJSONObject(0);
    Assert.assertTrue(
            deliveryRowData.getString("status").equalsIgnoreCase("CREATED"),
            "In delivery table status is " + deliveryRowData.getString("status"));
  }

  /**
   * This function will do detail validation of response and request
   *
   * @param createConsignmentRequest
   * @param createConsignmentResponse
   * @author Abhishek_Singh
   */
  public void verifyCreateConsignmentResponse(
          CreateConsignmentRequest createConsignmentRequest,
          CreateConsignmentResponse createConsignmentResponse) {

    Assert.assertTrue(
            createConsignmentRequest.getStoreId().equals(createConsignmentResponse.getStoreId()),
            "StoreId is not coming correctly in response");
    Assert.assertTrue(
            createConsignmentRequest.getDistanceInMeters().intValue()
                    == createConsignmentResponse.getDistanceInMeters(),
            "Distance in meters value is not coming correctly in response");
    Assert.assertTrue(
            createConsignmentRequest
                    .getPaymentMode()
                    .equalsIgnoreCase(createConsignmentResponse.getPaymentMode()),
            "Payment mode value is not coming correctly in response");
    Assert.assertTrue(
            createConsignmentRequest.getPaymentAmount().intValue()
                    == createConsignmentResponse.getPaymentAmount(),
            "Payment amount is not coming correctly");
    Assert.assertTrue(
            (createConsignmentResponse.getStatus().equalsIgnoreCase("CREATED")
                    || createConsignmentResponse.getStatus().equalsIgnoreCase("ASSIGN")),
            "Order status is not correct in response");
    Assert.assertTrue(
            createConsignmentRequest
                    .getLoadTime()
                    .equalsIgnoreCase(createConsignmentResponse.getLoadTime()),
            "Load Time not coming correctly in response body");

    Assert.assertTrue(
            createConsignmentResponse.getDeliveries().get(0).getIsValid(),
            "Is valid flag is not coming correctly in response");
  }

  /**
   * This function will verify the data in db for Order details against the request sent to api
   *
   * @param orderDetailsResponse
   * @param createConsignmentRequest
   * @author Abhishek_Singh
   */
  public void verifyOrderDetailsInDbAgainstRequestDetails(
          OrderDetailsResponse orderDetailsResponse,
          CreateConsignmentRequest createConsignmentRequest) {

    OrderDetails orderDetails = createConsignmentRequest.getOrderDetails();

    Assert.assertTrue(
            orderDetailsResponse.getOrderCode().equalsIgnoreCase(orderDetails.getOrderCode()),
            "Order code is not same in DB and request body");
    Assert.assertTrue(
            orderDetailsResponse.getMerchantName().equalsIgnoreCase(orderDetails.getMerchantName()),
            "Order merchant name is not same in DB and request body");
    Assert.assertTrue(
            orderDetailsResponse.getSellerId().equalsIgnoreCase(orderDetails.getSellerId()),
            "Seller is not same in DB and request body");
    if (orderDetailsResponse.getPackerDetails() != null) {
      Assert.assertTrue(
              orderDetailsResponse.getPackerDetails().equals(orderDetails.getPackerDetails()),
              "Packers details not coming correctly");
    }
    Assert.assertTrue(
            orderDetailsResponse.getEtaInSec().intValue() == orderDetails.getEtaInSec().intValue(),
            "ETA in sec is not coming correctly in DB and request");
  }

  /**
   * This method will verify response of getConsignmentAPIByConsignmentId against DB data and some
   * backend logic as well
   *
   * @param response
   * @param consignmentId
   * @author Deepak_Kumar
   */
  public void validateGetConsignmentApiResponse(Response response, String consignmentId)
          throws SQLException, JsonProcessingException {
    setNextConsignmentStatusData();
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(baseResponse.getErrors().size(), 0, "Response error size is not zero");
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    GetConsignmentResponse consignmentResponse =
            mapper.readValue(data.toString(), GetConsignmentResponse.class);
    logger.info("Consignment----->" + consignmentResponse);
    List<String> nextStatusResponse = consignmentResponse.getNextStatus();
    ArrayList<String> nextStatusExpected =
            nextConsignmentStatus.get(consignmentResponse.getStatus());
    Assert.assertEquals(
            nextStatusResponse.size(),
            nextStatusExpected.size(),
            "Possible no of next consignment status is not as expected in api response");
    Collections.sort(nextStatusResponse);
    Collections.sort(nextStatusExpected);
    // Verify nextConsignmentStatus
    for (int i = 0; i < nextStatusResponse.size(); i++) {
      softAssert.assertEquals(
              nextStatusResponse.get(i),
              nextStatusExpected.get(i),
              "Next Consignment status is not as expected");
    }
    // verify deliveryStatus and nextDeliveryStatus
    if (!consignmentResponse.getStatus().equals(CREATED.consignmentStatus)) {
      verifyDeliveries(data);
    }
    // Fetch data from DB
    JSONArray consignmentDetails =
            mySqlDMSHelper.queryDb(String.format(DBQueries.getConsignmentDetails, consignmentId));
    logger.info("DB data: " + consignmentDetails);
    JSONObject consignment = consignmentDetails.getJSONObject(0);
    softAssert.assertEquals(
            consignmentResponse.getId(),
            consignment.get("id").toString(),
            "ConsignmentId in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getVersion(),
            consignment.get("version"),
            "version in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getOrderId(),
            consignment.get("order_id").toString(),
            "orderId in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getIsValid(),
            consignment.get("is_valid"),
            "is_valid in API response and DB is not as expected");
    softAssert.assertEquals(
            consignmentResponse.getStatus(),
            consignment.get("status").toString(),
            "status in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getDistanceInMeters(),
            consignment.get("distance_in_meters"),
            "distance_in_meters in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getPaymentMode(),
            consignment.get("payment_mode").toString(),
            "payment_mode in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getStoreId(),
            consignment.get("store_id").toString(),
            "store_id in API response and DB is not as expected");
    softAssert.assertEquals(
            consignmentResponse.getPaymentAmount(),
            consignment.get("payment_amount"),
            "payment_amount in API response and DB is not same");
    // Validating Customer Details
    verifyCustomerDetails(
            consignmentResponse.getCustomerDetails(),
            new JSONObject(consignment.get("customer_details").toString()));
    // Validating Order Details
    verifyOrderDetails(
            data.getJSONObject("orderDetails"),
            new JSONObject(consignment.get("order_details").toString()));
    softAssert.assertAll();
  }

  /**
   * This method will verify customerDetails in response against DB
   *
   * @param customerDetails
   * @param customerDetailsDB
   * @author Deepak_Kumar
   */
  public void verifyCustomerDetails(CustomerDetails customerDetails, JSONObject customerDetailsDB)
          throws JsonProcessingException {
    CustomerDetails expectedCustomerDetails =
            new ObjectMapper().readValue(customerDetailsDB.toString(), CustomerDetails.class);
    softAssert.assertEquals(
            customerDetails, expectedCustomerDetails, "Mismatch in customer details data");
  }

  /**
   * This method will verify orderDetails in response against DB
   *
   * @param orderDetailsResponse
   * @param orderDetailsDB
   * @author Deepak_Kumar
   */
  public void verifyOrderDetails(JSONObject orderDetailsResponse, JSONObject orderDetailsDB) {
    softAssert.assertEquals(
            orderDetailsResponse.get("orderDate").toString(),
            orderDetailsDB.get("order_date").toString(),
            "OrderDetails: orderDate in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("startTime").toString(),
            orderDetailsDB.get("start_time").toString(),
            "OrderDetails: startTime in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("expectedDeliveryTime").toString(),
            orderDetailsDB.get("expected_delivery_time").toString(),
            "OrderDetails: expectedDeliveryTime in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("isBatchable"),
            orderDetailsDB.get("is_batchable"),
            "OrderDetails: isBatchable in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("merchantName"),
            orderDetailsDB.get("merchant_name"),
            "OrderDetails: merchantName in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("sellerId").toString(),
            orderDetailsDB.get("seller_id").toString(),
            "OrderDetails: sellerId in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("etaInSec"),
            orderDetailsDB.get("eta_in_sec"),
            "OrderDetails: etaInSec in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("polyline"),
            orderDetailsDB.get("polyline"),
            "polyline in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("storageLocation"),
            orderDetailsDB.get("storage_location"),
            "OrderDetails: storageLocation in API Response and DB is not as expected");
    softAssert.assertEquals(
            orderDetailsResponse.get("weightInGms"),
            orderDetailsDB.get("weight_in_gms"),
            "OrderDetails: weightInGms in API Response and DB is not as expected");
    JSONObject packerDetailsResponse =
            orderDetailsResponse.has("packerDetails")
                    && (orderDetailsResponse.get("packerDetails") != null
                    && (orderDetailsResponse.get("packerDetails") instanceof JSONObject))
                    ? orderDetailsResponse.getJSONObject("packerDetails")
                    : new JSONObject();
    JSONObject packerDetailsDB =
            orderDetailsDB.has("packer_details")
                    && (orderDetailsDB.get("packer_details") != null
                    && (orderDetailsDB.get("packer_details") instanceof JSONObject))
                    ? orderDetailsDB.getJSONObject("packer_details")
                    : new JSONObject();
    if (!packerDetailsDB.toString().equals("{}")) {
      if (packerDetailsResponse.has("fullName") && packerDetailsResponse.get("fullName") != null) {
        softAssert.assertEquals(
                packerDetailsResponse.get("fullName"),
                packerDetailsDB.get("full_name"),
                "packer details fullName in API Response and DB is not same");
      }
      if (packerDetailsResponse.has("mobileNumber")
              && packerDetailsResponse.get("mobileNumber") != null) {
        softAssert.assertEquals(
                packerDetailsResponse.get("mobileNumber"),
                packerDetailsDB.get("mobile_number"),
                "packer details mobileNumber in API Response and DB is not same");
      }
      if (packerDetailsResponse.has("is_packed")
              && packerDetailsResponse.get("is_packed") != null) {
        softAssert.assertEquals(
                packerDetailsResponse.get("is_packed"),
                packerDetailsDB.get("is_packed"),
                "packer details packingStatus in API Response and DB is not same");
      }
    }
  }

  /**
   * This method will verify deliveries data in api response against DB along with some backend
   * logic ass well
   *
   * @param data
   * @author Deepak_Kumar
   */
  public void verifyDeliveries(JSONObject data) throws SQLException {
    JSONArray deliveries = data.getJSONArray("deliveries");
    JSONObject deliveryResponse = deliveries.getJSONObject(0);
    String deliveryId = deliveryResponse.get("id").toString();
    String deliveryStatus = deliveryResponse.get("status").toString();
    // Fetch delivery data from delivery DB
    JSONArray deliveryDetails =
            mySqlDMSHelper.queryDb(String.format(DBQueries.getDeliveryDetailsByDeliveryId, deliveryId));
    logger.info("Delivery DB data: " + deliveryDetails);
    JSONObject deliveryDB = deliveryDetails.getJSONObject(0);
    softAssert.assertEquals(
            deliveryResponse.get("id").toString(),
            deliveryDB.get("id").toString(),
            "DeliveryId in API response and DB is not same");
    softAssert.assertEquals(
            deliveryResponse.get("status").toString(),
            deliveryDB.get("status").toString(),
            "Delivery status in API response and DB is not same");
    ArrayList<String> nextStatusExpected =
            nextConsignmentStatus.get(deliveryResponse.get("status").toString());
    if (deliveryStatus.equals(Statuses.UNASSIGNED.consignmentStatus)) {
      nextStatusExpected.add("");
    }
    JSONArray nextStatusDelivery = deliveryResponse.getJSONArray("nextStatus");
    ArrayList<String> nextStatusDeliveryResponse = new ArrayList<>();
    if (nextStatusDelivery != null) {
      for (int i = 0; i < nextStatusDelivery.length(); i++) {
        nextStatusDeliveryResponse.add(nextStatusDelivery.get(i).toString());
      }
    }
    Assert.assertEquals(
            nextStatusDeliveryResponse.size(),
            nextStatusExpected.size(),
            "Possible no of next delivery status is not as expected in api response");
    Collections.sort(nextStatusExpected);
    Collections.sort(nextStatusDeliveryResponse);
    for (int i = 0; i < nextStatusDeliveryResponse.size(); i++) {
      softAssert.assertEquals(
              nextStatusDeliveryResponse.get(i),
              nextStatusExpected.get(i),
              "Next Delivery status is not as expected");
    }
  }

  /**
   * This method is used to update consignment status to any status from assigned status
   *
   * @param consignmentId
   * @param expectedConsignmentStatus
   * @author Deepak_Kumar
   */
  public String updateConsignmentStatus(
          String consignmentId, String expectedConsignmentStatus, String riderToken) {
    try {
      UpdateDeliveryStatusRequest requestMessage =
              new UpdateDeliveryStatusRequest().builder().build();

      response =
              dmsApiHelper.getConsignmentByConsignmentId(
                      environment.getReqSpecBuilder(), consignmentId, false);
      ExtentReportUtil.logResponseDetailsInLogAndReport(response);
      Assert.assertTrue(
              response.getStatusCode() == HttpStatus.SC_OK,
              "Response status code is not 200, it is " + response.getStatusCode());

      JSONObject jsonObject = new JSONObject(response.asString());
      JSONObject consignmentDetails = jsonObject.getJSONObject("data");
      JSONArray deliveries = consignmentDetails.getJSONArray("deliveries");
      JSONObject deliveryDetails = deliveries.getJSONObject(0);
      String tripId = deliveryDetails.get("tripId").toString();
      String deliveryId = deliveryDetails.get("id").toString();
      String orderId = consignmentDetails.get("orderId").toString();
      JSONObject orderDetails = consignmentDetails.getJSONObject("orderDetails");
      String sellerId = orderDetails.getString("sellerId");
      String sellerName = orderDetails.getString("merchantName");
      OrderDetails consignmentOrderDetails =
              mapper.readValue(
                      RequestMapper.mapPojoObjectAndGetString(orderDetails), OrderDetails.class);
      int paymentAmount = consignmentDetails.getInt("paymentAmount");
      String paymentMode = consignmentDetails.getString("paymentMode");
      int etaInSec = orderDetails.getInt("etaInSec");

      if (expectedConsignmentStatus.equals(Statuses.CANCELLED.consignmentStatus)) {
        response = dmsApiHelper.deleteConsignment(environment.getReqSpecBuilder(), orderId, false);
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Delete Consignment Response status code is not 200, it is "
                        + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.CANCELLED.consignmentStatus);
        return consignmentId;
      } else if (expectedConsignmentStatus.equals(Statuses.UNASSIGNED.consignmentStatus)) {
        requestMessage.setStatus(Statuses.UNASSIGNED.consignmentStatus);
        response =
                dmsApiHelper.updateDeliveryStatus(
                        environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Update trip Response status code is not 200, it is " + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.UNASSIGNED.consignmentStatus);
        return consignmentId;
      } else {
        OrderDetails updateOrderDetails = (OrderDetails) consignmentOrderDetails.clone();
        updateOrderDetails.setPackerDetails(PackerDetails.builder().build());
        updateOrderDetails.setHuDetails(HuDetails.builder().build());
        updateOrderDetails.setSellerId(sellerId);
        updateOrderDetails.setMerchantName(sellerName);
        updateOrderDetails.setEtaInSec(etaInSec);
        UpdateConsignmentRequest updateConsignmentRequest =
                UpdateConsignmentRequest.builder()
                        .orderId(orderId)
                        .orderDetails(updateOrderDetails)
                        .paymentAmount(paymentAmount)
                        .paymentMode(paymentMode)
                        .build();
        response =
                dmsApiHelper.updateConsignment(
                        environment.getReqSpecBuilder(), false, updateConsignmentRequest);
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Update consignment Response status code is not 200, it is "
                        + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.READY_TO_PICKUP.consignmentStatus);
        requestMessage.setStatus(Statuses.PICKED_UP.consignmentStatus);
        HuDetails huDetails =
                mapper.readValue(
                        RequestMapper.mapPojoObjectAndGetString(
                                new JSONObject(updateOrderDetails.getHuDetails())),
                        HuDetails.class);
        response =
                dmsApiHelper.updateDeliveryStatus(
                        environment.getReqSpecBuilder(),
                        riderToken,
                        requestMessage,
                        deliveryId,
                        false,
                        huDetails.getBinCode());
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Update trip Response status code is not 200, it is " + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.PICKED_UP.consignmentStatus);
        if (expectedConsignmentStatus.equals(Statuses.PICKED_UP.consignmentStatus)) {
          return consignmentId;
        }
        if (expectedConsignmentStatus.equals(Statuses.RETURN_TO_ORIGIN.consignmentStatus)) {
          dmsApiHelper.customerCallMasking(environment.getReqSpecBuilder(), orderId, riderToken);
          JSONObject metadata = new JSONObject();
          metadata.put("rider_calling_count", 2);
          metadata.put("customer_calling_count", 0);
          mySqlDMSHelper.executeInsertUpdateQuery(
                  String.format(DBQueries.updateCallMaskingMetaData, metadata, orderId));
          requestMessage.setStatus(Statuses.RETURN_TO_ORIGIN.consignmentStatus);
          requestMessage.setReasonId(
                  environment.getGlobalTestData().getString("rtoReason.reasonId"));
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.RETURN_TO_ORIGIN.consignmentStatus);
          return consignmentId;
        }
        if (expectedConsignmentStatus.equals(Statuses.RETURNED.consignmentStatus)
                || expectedConsignmentStatus.equals(Statuses.RETURNED_TO_STORE.tripStatus)) {
          dmsApiHelper.customerCallMasking(environment.getReqSpecBuilder(), orderId, riderToken);
          JSONObject metadata = new JSONObject();
          metadata.put("rider_calling_count", 2);
          metadata.put("customer_calling_count", 0);
          mySqlDMSHelper.executeInsertUpdateQuery(
                  String.format(DBQueries.updateCallMaskingMetaData, metadata, orderId));
          requestMessage.setStatus(Statuses.RETURN_TO_ORIGIN.consignmentStatus);
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.RETURN_TO_ORIGIN.consignmentStatus);
          // Update consignment to returned
          UpdateTripStatusRequest updateTripReadyToPickup =
                  UpdateTripStatusRequest.builder().build();
          updateTripReadyToPickup.setStatus(Statuses.RETURNED_TO_STORE.tripStatus);
          response =
                  dmsApiHelper.updateTripStatusV2(
                          environment.getReqSpecBuilder(), riderToken, tripId, updateTripReadyToPickup);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          if (expectedConsignmentStatus.equals(Statuses.RETURNED_TO_STORE.tripStatus)) {
            return consignmentId;
          }
          updateTripReadyToPickup.setStatus(Statuses.RETURNED.tripStatus);
          response =
                  dmsApiHelper.updateTripStatusV2(
                          environment.getReqSpecBuilder(), riderToken, tripId, updateTripReadyToPickup);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.RETURNED.consignmentStatus);
          return consignmentId;
        }
        requestMessage.setStatus(Statuses.STARTED.consignmentStatus);
        response =
                dmsApiHelper.updateDeliveryStatus(
                        environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Update trip Response status code is not 200, it is " + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.STARTED.consignmentStatus);
        if (expectedConsignmentStatus.equals(Statuses.STARTED.consignmentStatus)) {
          return consignmentId;
        }
        requestMessage.setStatus(Statuses.AUTO_ARRIVED.consignmentStatus);
        response =
                dmsApiHelper.updateDeliveryStatus(
                        environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Update trip Response status code is not 200, it is " + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.AUTO_ARRIVED.consignmentStatus);
        if (expectedConsignmentStatus.equals(Statuses.AUTO_ARRIVED.consignmentStatus)) {
          return consignmentId;
        }
        requestMessage.setStatus(Statuses.ARRIVED.consignmentStatus);
        response =
                dmsApiHelper.updateDeliveryStatus(
                        environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
        Assert.assertTrue(
                response.getStatusCode() == HttpStatus.SC_OK,
                "Update trip Response status code is not 200, it is " + response.getStatusCode());
        verifyConsignmentStatus(consignmentId, Statuses.ARRIVED.consignmentStatus);
        if (expectedConsignmentStatus.equals(Statuses.ARRIVED.consignmentStatus)) {
          return consignmentId;
        }
        // Make cod payment mark order delivered
        if (paymentMode.equals("COD")) {
          response =
                  codOrderApiHelper.makePaymentForCodOrder(
                          environment.getReqSpecBuilder(),
                          "CASH",
                          consignmentDetails.get("paymentAmount"),
                          consignmentDetails.getString("orderId"),
                          riderToken,
                          true);
          Assert.assertEquals(
                  response.statusCode(),
                  HttpStatus.SC_OK,
                  "Response code of make payment api is not "
                          + HttpStatus.SC_OK
                          + ", it is :"
                          + response.statusCode());
        }
        requestMessage.setStatus(Statuses.DELIVERED.consignmentStatus);

        Awaitility.await()
                .atMost(60, TimeUnit.SECONDS)
                .pollInterval(2, TimeUnit.SECONDS)
                .until(
                        () -> {
                          response =
                                  dmsApiHelper.updateDeliveryStatus(
                                          environment.getReqSpecBuilder(),
                                          riderToken,
                                          requestMessage,
                                          deliveryId,
                                          false);
                          return response.getStatusCode() == HttpStatus.SC_OK;
                        });
        verifyConsignmentStatus(consignmentId, Statuses.DELIVERED.consignmentStatus);
        if (expectedConsignmentStatus.equals(Statuses.DELIVERED.consignmentStatus)) {
          return consignmentId;
        }
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return "Please provide valid consignment status";
  }

  /**
   * Update consignment from source state to target
   *
   * @param sourceStatus
   * @param targetStatus
   * @param consignmentId
   * @param riderToken
   * @return
   * @author Manisha.Kumari
   */
  @SneakyThrows
  public String updateConsignmentFromAnyStatus(
          String sourceStatus, String targetStatus, String consignmentId, String riderToken) {
    if (sourceStatus.equals(targetStatus)) {
      return consignmentId;
    }
    int sourceRank = Statuses.valueOf(sourceStatus).rank;
    int targetRank = Statuses.valueOf(targetStatus).rank;
    int diff = targetRank - sourceRank;
    if (diff > 0) {
      updateConsignmentStatus(sourceRank, targetRank + 1, consignmentId, riderToken);
    }
    return consignmentId;
  }

  /**
   * Update consignment from source to target status
   *
   * @param sourceRank
   * @param targetRank
   * @param consignmentId
   * @param riderToken
   * @throws Exception
   * @author Manisha.Kumari
   */
  private void updateConsignmentStatus(
          int sourceRank, int targetRank, String consignmentId, String riderToken) throws Exception {
    Response response = null;
    Thread.sleep(100);
    logger.info("Waiting for 100 ms before calling consignment response");
    JSONObject consignmentDeliveryDetails = getConsignmentDeliveryDetailsById(consignmentId);
    String consignmentStatus = consignmentDeliveryDetails.getString("status");
    String tripId = "";
    if (Statuses.getRank(consignmentStatus) >= 2) {
      consignmentDeliveryDetails = getConsignmentTripDeliveryDetailsById(consignmentId);
      tripId =
              consignmentDeliveryDetails.has("trip_id")
                      ? consignmentDeliveryDetails.get("trip_id").toString()
                      : "";
    }
    String orderId = consignmentDeliveryDetails.get("order_id").toString();
    String deliveryId = consignmentDeliveryDetails.get("delivery_id").toString();
    int paymentAmount = consignmentDeliveryDetails.getInt("payment_amount");
    String paymentMode = consignmentDeliveryDetails.getString("payment_mode");
    UpdateDeliveryStatusRequest requestMessage =
            UpdateDeliveryStatusRequest.builder().reasonId(null).build();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    OrderDetails orderDetails =
            mapper.readValue(
                    consignmentDeliveryDetails.get("order_details").toString(), OrderDetails.class);
    boolean isBatched = consignmentDeliveryDetails.getBoolean("is_batched");
    String storeId = consignmentDeliveryDetails.get("store_id").toString();
    String orderCode = consignmentDeliveryDetails.get("order_code").toString();

    switch (sourceRank) {
      case 0:
        if (sourceRank == targetRank) {
          break;
        }
        logger.info("No step required for initial status " + consignmentStatus);
        sourceRank++;
      case 1:
        if (sourceRank == targetRank) {
          break;
        }
        logger.info("No step required for " + consignmentStatus);
        sourceRank++;
      case 2:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(ASSIGNED.consignmentStatus) || targetRank >= 13) {
          sourceRank++;
        } else {
          String riderId =
                  checkInCheckOutHelper
                          .createRiderUploadRiderWorkPlanDoCheckIn("PART_TIME", "BIKE", true)
                          .get("riderId");
          JSONObject consignmentData = new JSONObject();
          consignmentData.put("storeId", storeId);
          consignmentData.put("orderCode", orderCode);
          assignRiderManuallyToConsignment(consignmentData, riderId);
          sourceRank++;
        }
      case 3:
        if (sourceRank == targetRank) {
          break;
        }
        if ((consignmentDeliveryDetails.has("trip_status")
                && consignmentDeliveryDetails.get("trip_status") != null
                && consignmentDeliveryDetails
                .getString("trip_status")
                .equals(Statuses.READY_TO_PICKUP.tripStatus))
                || targetRank >= 13) {
          sourceRank++;
        } else {
          OrderDetails updateOrderDetails = (OrderDetails) orderDetails.clone();
          updateOrderDetails.setHuDetails(HuDetails.builder().build());
          updateOrderDetails.setPackerDetails(PackerDetails.builder().build());
          UpdateConsignmentRequest updateConsignmentRequest =
                  UpdateConsignmentRequest.builder()
                          .orderId(orderId)
                          .orderDetails(updateOrderDetails)
                          .paymentMode(paymentMode)
                          .paymentAmount(paymentAmount)
                          .build();
          response =
                  dmsApiHelper.updateConsignment(
                          environment.getReqSpecBuilder(), false, updateConsignmentRequest);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update consignment Response status code is not 200, it is "
                          + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.READY_TO_PICKUP.consignmentStatus);
          sourceRank++;
        }

      case 4:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(PICKED_UP.consignmentStatus) || targetRank >= 13) {
          sourceRank++;
        } else {
          // get bincode from consignment
          Thread.sleep(100);
          consignmentDeliveryDetails = getConsignmentTripDeliveryDetailsById(consignmentId);
          orderDetails =
                  mapper.readValue(
                          consignmentDeliveryDetails.get("order_details").toString(), OrderDetails.class);
          HuDetails huDetails =
                  mapper.readValue(
                          RequestMapper.mapPojoObjectAndGetString(orderDetails.getHuDetails()),
                          HuDetails.class);

          requestMessage.setStatus(Statuses.PICKED_UP.consignmentStatus);
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(),
                          riderToken,
                          requestMessage,
                          deliveryId,
                          false,
                          huDetails.getBinCode());
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.PICKED_UP.consignmentStatus);
          sourceRank++;
        }

      case 5:
        if (sourceRank == targetRank) {
          break;
        }
        logger.info("Auto updated to Dispatched :: " + consignmentId);
        sourceRank++;
      case 6:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(STARTED.consignmentStatus) || targetRank >= 13) {
          sourceRank++;
        } else {
          requestMessage.setStatus(STARTED.consignmentStatus);
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.STARTED.consignmentStatus);
          sourceRank++;
        }
      case 7:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(AUTO_ARRIVED.consignmentStatus) || targetRank >= 13) {
          sourceRank++;
        } else {
          requestMessage.setStatus(AUTO_ARRIVED.consignmentStatus);
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.AUTO_ARRIVED.consignmentStatus);
          sourceRank++;
        }
      case 8:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(ARRIVED.consignmentStatus) || targetRank >= 13) {
          sourceRank++;
        } else {
          requestMessage.setStatus(ARRIVED.consignmentStatus);
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.ARRIVED.consignmentStatus);
          sourceRank++;
        }
      case 9:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(DELIVERED.consignmentStatus) || targetRank >= 13) {
          sourceRank++;
        } else {
          logger.info("Making cash payment with order amount");
          int count = 0;
          if (paymentMode.equals("COD")) {
            do {
              response =
                      codOrderApiHelper.makePaymentForCodOrder(
                              environment.getReqSpecBuilder(),
                              "CASH",
                              paymentAmount,
                              orderId,
                              riderToken,
                              true);
              if (response.getStatusCode() == HttpStatus.SC_OK) {
                break;
              }
              count++;
              // Waiting for 1 sec to hit service again
              Thread.sleep(1000);
              logger.info("Retrying payment for " + count + " time");
            } while (count < 5);
            if (response.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
              if (response
                      .getBody()
                      .jsonPath()
                      .get("errors[0].error")
                      .equals(environment.getGlobalTestData().getString("paymentError.diffRider"))) {
                // wait for 5 sec for arrived event
                Thread.sleep(5000);
                // retry payment now
                response =
                        codOrderApiHelper.makePaymentForCodOrder(
                                environment.getReqSpecBuilder(),
                                "CASH",
                                paymentAmount,
                                orderId,
                                riderToken,
                                true);
                if (response.getStatusCode() != HttpStatus.SC_OK) {
                  logger.info("Arrived event is delayed :: for deliveryId :: " + deliveryId);
                }
              }
            }
            Assert.assertEquals(
                    response.statusCode(),
                    HttpStatus.SC_OK,
                    "Response code of make payment api is not "
                            + HttpStatus.SC_OK
                            + ", it is :"
                            + response.statusCode());

            BaseResponse makePaymentResponse =
                    MapperUtils.deserialize(response.asString(), BaseResponse.class);
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
                    "Payment Status of make payment api is not 'SUCCESS', it is: "
                            + actualPaymentStatus);
          }
          // wait until payment is confirmed (In COD) to mark delivered
          requestMessage.setStatus(Statuses.DELIVERED.consignmentStatus);
          Awaitility.await()
                  .atMost(60, TimeUnit.SECONDS)
                  .pollInterval(10, TimeUnit.SECONDS)
                  .until(
                          () -> {
                            Response awaitilityResponse =
                                    dmsApiHelper.updateDeliveryStatus(
                                            environment.getReqSpecBuilder(),
                                            riderToken,
                                            requestMessage,
                                            deliveryId,
                                            false);
                            return awaitilityResponse.getStatusCode() == HttpStatus.SC_OK;
                          });
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.DELIVERED.consignmentStatus);
          sourceRank++;
        }
      case 10:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(RETURN_TO_ORIGIN.consignmentStatus)
                || consignmentStatus.equals(RETURNED_TO_STORE.consignmentStatus)
                || targetRank >= 13) {
          sourceRank++;
        } else {
          Thread.sleep(100);
          logger.info("Waiting for 100 ms before calling consignment response");
          response =
                  dmsApiHelper.getConsignmentByConsignmentId(
                          environment.getReqSpecBuilder(), consignmentId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Response status code is not 200, it is " + response.getStatusCode());
          BaseResponse getConsignmentResponse =
                  MapperUtils.deserialize(response.asString(), BaseResponse.class);
          GetConsignmentResponse actualGetConsignmentResponse =
                  new ObjectMapper()
                          .readValue(
                                  RequestMapper.mapPojoObjectAndGetString(getConsignmentResponse.getData()),
                                  GetConsignmentResponse.class);
          JSONObject getDeliveries =
                  actualGetConsignmentResponse.getDeliveries().size() > 0
                          ? new JSONObject(
                          RequestMapper.mapPojoObjectAndGetString(
                                  actualGetConsignmentResponse.getDeliveries().get(0)))
                          : new JSONObject();
          JSONObject getTripDetails =
                  getDeliveries.has("trip") ? getDeliveries.getJSONObject("trip") : new JSONObject();
          JSONArray getTripDelivery =
                  getTripDetails.has("deliveries")
                          ? getTripDetails.getJSONArray("deliveries")
                          : new JSONArray();
          boolean isRto = false;
          for (int i = 0; i < getTripDelivery.length(); i++) {
            JSONObject tripDeliveryDetails = (JSONObject) getTripDelivery.get(i);
            isRto =
                    tripDeliveryDetails.getString("status").equals(RETURN_TO_ORIGIN.consignmentStatus);
            if (isRto) {
              break;
            }
          }
          if (!actualGetConsignmentResponse.getStatus().equals(DELIVERED.consignmentStatus)) {
            requestMessage.setStatus(Statuses.RETURN_TO_ORIGIN.consignmentStatus);
            response =
                    dmsApiHelper.updateDeliveryStatus(
                            environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
            Assert.assertTrue(
                    response.getStatusCode() == HttpStatus.SC_OK,
                    "Update trip Response status code is not 200, it is " + response.getStatusCode());
            verifyConsignmentStatus(consignmentId, Statuses.RETURN_TO_ORIGIN.consignmentStatus);
          }
          UpdateTripStatusRequest updateTrip =
                  UpdateTripStatusRequest.builder()
                          .status(Statuses.RETURNED_TO_STORE.tripStatus)
                          .build();
          response =
                  dmsApiHelper.updateTripStatusV2(
                          environment.getReqSpecBuilder(), riderToken, tripId, updateTrip);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          HuDetails huDetails =
                  mapper.readValue(
                          RequestMapper.mapPojoObjectAndGetString(
                                  actualGetConsignmentResponse.getOrderDetails().getHuDetails()),
                          HuDetails.class);
          String expectedTripStatus =
                  huDetails.getIsReusableBag() || isRto
                          ? Statuses.RETURNED_TO_STORE.tripStatus
                          : Statuses.RETURNED.tripStatus;
          validateTripStatus(consignmentId, expectedTripStatus, riderToken);
          sourceRank++;
        }
      case 11:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(RETURNED.consignmentStatus)
                || consignmentDeliveryDetails.getString("trip_status").equals(RETURNED.tripStatus)
                || targetRank >= 13) {
          sourceRank++;
        } else {
          UpdateTripStatusRequest updateTrip =
                  UpdateTripStatusRequest.builder().status(RETURNED.tripStatus).build();
          response =
                  dmsApiHelper.updateTripStatusV2(
                          environment.getReqSpecBuilder(), riderToken, tripId, updateTrip);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          validateTripStatus(consignmentId, RETURNED.tripStatus, riderToken);
          sourceRank++;
        }
      case 12:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(CANCELLED.consignmentStatus)) {
          sourceRank++;
        } else {
          response =
                  dmsApiHelper.deleteConsignment(environment.getReqSpecBuilder(), orderId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Delete Consignment Response status code is not 200, it is "
                          + response.getStatusCode());
          if ((isBatched && Statuses.getRank(consignmentStatus) > 3)
                  || Statuses.getRank(consignmentStatus) > 3) {
            verifyConsignmentStatus(consignmentId, RETURN_TO_ORIGIN.consignmentStatus);
          } else {
            verifyConsignmentStatus(consignmentId, Statuses.CANCELLED.consignmentStatus);
          }
          sourceRank++;
        }

      case 13:
        if (sourceRank == targetRank) {
          break;
        }
        if (consignmentStatus.equals(UNASSIGNED.consignmentStatus)) {
          break;
        } else {
          requestMessage.setStatus(Statuses.UNASSIGNED.consignmentStatus);
          response =
                  dmsApiHelper.updateDeliveryStatus(
                          environment.getReqSpecBuilder(), riderToken, requestMessage, deliveryId, false);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Update trip Response status code is not 200, it is " + response.getStatusCode());
          verifyConsignmentStatus(consignmentId, Statuses.UNASSIGNED.consignmentStatus);
        }
    }
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    if (sourceRank == targetRank) {
      return;
    }
  }

  /**
   * This method will verify customerStatus in response against expected status
   *
   * @param consignmentId
   * @param status
   * @author Deepak_Kumar
   */
  public JSONObject verifyConsignmentStatus(String consignmentId, String status) {
    Response response = null;
    Awaitility.await()
            .atMost(60, TimeUnit.SECONDS)
            .pollInterval(2, TimeUnit.SECONDS)
            .until(
                    () -> {
                      Response consignmentResponse =
                              dmsApiHelper.getConsignmentByConsignmentId(
                                      environment.getReqSpecBuilder(), consignmentId, false);
                      return consignmentResponse.getStatusCode() == HttpStatus.SC_OK
                              && consignmentResponse.jsonPath().getString("data.status").equals(status);
                    });
    response =
            dmsApiHelper.getConsignmentByConsignmentId(
                    environment.getReqSpecBuilder(), consignmentId, false);
    Assert.assertTrue(
            response.getStatusCode() == HttpStatus.SC_OK,
            "Response status code is not 200, it is " + response.getStatusCode());
    JSONObject responseObject = new JSONObject(response.asString());
    JSONObject data = responseObject.getJSONObject("data");
    Assert.assertEquals(
            data.get("status").toString(), status, "status in API response and DB is not as expected");
    return data;
  }

  /**
   * This method will verify tripId is same as before change rider
   *
   * @param consignmentId
   * @param oldTripId
   * @author Manisha.Kumari
   */
  @SneakyThrows
  public void verifyTripIsSameAsBefore(String consignmentId, String oldTripId) {
    JSONObject consignmentDetails = getConsignmentTripDeliveryDetailsById(consignmentId);
    String currentTripId =
            consignmentDetails.has("trip_id") ? consignmentDetails.get("trip_id").toString() : "";
    logger.info("Current trip id is  :: " + currentTripId);
    Assert.assertEquals(oldTripId, currentTripId, "trip id are not matching after change rider");
  }

  /**
   * This method will verify tripId is same as before change rider
   *
   * @param tripDetails
   * @author Manisha.Kumari
   */
  @SneakyThrows
  public void verifyConsignmentOrder(GetTripByIdResponse tripDetails) {
    List<Delivery> deliveries = tripDetails.getDeliveries();
    JSONObject firstConsignment = new JSONObject();
    JSONObject secondConsignment = new JSONObject();
    for (Delivery delivery : deliveries) {
      int batchedOrder = delivery.getBatchedSequenceNumber();
      Object firstConsignmentData;
      Object secondConsignmentData;
      if (batchedOrder == 1) {
        firstConsignmentData = delivery.getConsignment();
        secondConsignmentData = deliveries.get(1).getConsignment();
      } else {
        firstConsignmentData = deliveries.get(1).getConsignment();
        secondConsignmentData = delivery.getConsignment();
      }
      firstConsignment =
              new JSONObject(RequestMapper.mapPojoObjectAndGetString(firstConsignmentData));
      secondConsignment =
              new JSONObject(RequestMapper.mapPojoObjectAndGetString(secondConsignmentData));
      break;
    }
    String firstConsignmentId = firstConsignment.get("id").toString();
    String secondConsignmentId = secondConsignment.get("id").toString();
    // Fetch data from DB
    JSONArray firstConsignmentDetails =
            mySqlDMSHelper.queryDb(String.format(DBQueries.getConsignmentDetails, firstConsignmentId));
    logger.info("DB data: " + firstConsignmentDetails);
    JSONArray secondConsignmentDetails =
            mySqlDMSHelper.queryDb(String.format(DBQueries.getConsignmentDetails, secondConsignmentId));
    logger.info("DB data: " + secondConsignmentDetails);
    // compare created at time
    String firstConsignmentDate =
            firstConsignmentDetails.length() > 0
                    ? ((JSONObject) firstConsignmentDetails.get(0)).get("created_at").toString()
                    : "";
    String secondConsignmentDate =
            secondConsignmentDetails.length() > 0
                    ? ((JSONObject) secondConsignmentDetails.get(0)).get("created_at").toString()
                    : "";
    firstConsignmentDate = firstConsignmentDate.substring(0, firstConsignmentDate.indexOf(".") + 3);
    secondConsignmentDate =
            secondConsignmentDate.substring(0, secondConsignmentDate.indexOf(".") + 3);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
    logger.info(sdf.parse(firstConsignmentDate).toString());
    logger.info(sdf.parse(secondConsignmentDate).toString());
    boolean isOrderedCorrect =
            sdf.parse(firstConsignmentDate).compareTo(sdf.parse(secondConsignmentDate)) < 0;
    Assert.assertTrue(isOrderedCorrect, "batching order is incorrect for " + firstConsignmentId);
  }

  /**
   * This method will verify rider delivery status in db against expected status
   *
   * @param riderId
   * @param status
   * @author Manisha_Kumari
   */
  @SneakyThrows
  public void validateRiderStatus(String riderId, String status) {
    Thread.sleep(100);
    logger.info("Waited for 100 ms before calling db to get data");
    JSONArray riderDeliveryInfo =
            mySqlDMSHelper.queryDb(
                    String.format(DBQueries.getRiderDeliveryStatusByRiderId, String.join("','", riderId)));
    if (riderDeliveryInfo.length() > 0) {
      String riderStatus = riderDeliveryInfo.getJSONObject(0).getString("status");
      Assert.assertEquals(
              riderStatus,
              status,
              "Expecting rider status to be" + status + "but found " + riderStatus);
    }
  }

  /**
   * validate trip status associated with consignmentId
   *
   * @param consignmentId
   * @return
   * @author Manisha_Kumari
   */
  public boolean validateTripStatus(
          String consignmentId, String status, String token, String... riderScreenStatus) {
    Response response = null;
    try {
      Thread.sleep(100);
      JSONObject consignmentDetails = getConsignmentTripDeliveryDetailsById(consignmentId);
      if (consignmentDetails.has("trip_id") && consignmentDetails.get("trip_id") != null) {
        String tripId = consignmentDetails.get("trip_id").toString();
        String tripStatus = consignmentDetails.getString("trip_status");
        Assert.assertEquals(tripStatus, status, "mismatch in trip status expectations");
        String expectedRiderScreenStatus =
                (riderScreenStatus.length > 0) ? riderScreenStatus[0] : null;
        // calling trip info api
        if (expectedRiderScreenStatus != null) {
          response = dmsApiHelper.getTripInfoById(environment.getReqSpecBuilder(), token, tripId);
          ExtentReportUtil.logResponseDetailsInLogAndReport(response);
          Assert.assertTrue(
                  response.getStatusCode() == HttpStatus.SC_OK,
                  "Response status code is not 200, it is " + response.getStatusCode());
          BaseResponse<GetTripByIdResponse> tripResponse = response.as(BaseResponse.class);
          GetTripByIdResponse tripDetails =
                  mapper.readValue(
                          RequestMapper.mapPojoObjectAndGetString(tripResponse.getData()),
                          GetTripByIdResponse.class);
          Assert.assertEquals(
                  tripDetails.getRiderScreenStatus(),
                  expectedRiderScreenStatus,
                  "Mismatch in rider screen status");
          return tripStatus.equals(status)
                  && tripDetails.getRiderScreenStatus().equals(expectedRiderScreenStatus);
        } else {
          return tripStatus.equals(status);
        }
      }
      return false;
    } catch (JsonProcessingException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * validate trip info associated with clr
   *
   * @param tripId
   * @param token
   * @param categories
   * @return
   * @author Manisha_Kumari
   */
  public void validateTripInfoForSurvey(String tripId, String token, String[] categories) {
    Response response = null;
    try {
      response = dmsApiHelper.getTripInfoById(environment.getReqSpecBuilder(), token, tripId);
      ExtentReportUtil.logResponseDetailsInLogAndReport(response);
      Assert.assertTrue(
              response.getStatusCode() == HttpStatus.SC_OK,
              "Response status code is not 200, it is " + response.getStatusCode());
      BaseResponse<GetTripByIdResponse> tripResponse =
              MapperUtils.deserialize(response.asString(), BaseResponse.class);
      GetTripByIdResponse tripDetails =
              mapper.readValue(
                      RequestMapper.mapPojoObjectAndGetString(tripResponse.getData()),
                      GetTripByIdResponse.class);
      if (tripDetails.getStatus().equals(RETURN_TO_ORIGIN.tripStatus)) {
        Assert.assertTrue(tripDetails.getConductSurvey(), "Expecting conduct survey to be true.");
        for (String category : categories) {
          Assert.assertTrue(
                  tripDetails.getQuestionCategory().contains(category),
                  "Expecting category :: "
                          + category
                          + " to be present in trip response categories :: "
                          + tripDetails.getQuestionCategory());
        }
      } else {
        Assert.assertFalse(tripDetails.getConductSurvey(), "Expecting conduct survey to be false.");
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * get token for a rider assigned to consignment
   *
   * @param consignmentId
   * @param status
   * @return
   */
  public String getRiderTokenFromConsignmentId(String consignmentId, String... status) {
    try {
      JSONObject consignmentData;
      // validate if consignment is in give status
      if (status.length > 0) {
        verifyConsignmentStatus(consignmentId, status[0]);
      }
      consignmentData = getConsignmentTripDeliveryDetailsById(consignmentId);
      // get rider info
      String assignedRiderId = consignmentData.opt("rider_id").toString();
      String mobileNumber =
              new MySqlZeptoBackendServiceHelper()
                      .queryDb(String.format(DBQueries.getRiderMobileNo, assignedRiderId))
                      .getJSONObject(0)
                      .getString("mobile_number");
      // generate token
      return new RiderHelper()
              .generateRiderAuthToken(environment.getReqSpecBuilder(), mobileNumber);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This method will verify consignmentSummaryCount in response against DB data
   *
   * @param response
   * @param storeId
   * @author Deepak_Kumar
   */
  public void verifyConsignmentSummaryCountResponse(Response response, String storeId)
          throws SQLException, JsonProcessingException {
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    softAssert.assertEquals(baseResponse.getErrors().size(), 0, "Response error size is not zero");
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    ConsignmentSummaryCount consignmentCountSummary =
            mapper.readValue(
                    data.get("consignmentSummaryCount").toString(), ConsignmentSummaryCount.class);
    int activeOrders = consignmentCountSummary.getActiveOrders();
    int closedOrders = consignmentCountSummary.getClosedOrders();
    int zeptonUnassigned = consignmentCountSummary.getZeptonUnassigned();
    int zeptonAssigned = consignmentCountSummary.getZeptonAssigned();
    int ongoing = consignmentCountSummary.getOngoing();
    int completed = consignmentCountSummary.getCompleted();
    int cancelled = consignmentCountSummary.getCancelled();
    int undelivered = consignmentCountSummary.getUndelivered();
    int breached = consignmentCountSummary.getBreached();
    // Store DB consignment Count
    LocalDate localDate = LocalDate.now();
    ZonedDateTime startOfDayInZone = localDate.atStartOfDay(ZoneOffset.UTC);
    JSONArray consignmentDetails =
            mySqlDMSHelper.queryDb(
                    String.format(
                            DBQueries.getTodayConsignmentDetailsByStoreId, storeId, startOfDayInZone));
    logger.info("DB data: " + consignmentDetails);
    HashMap<String, Integer> consignmentCountDB = new HashMap<>();
    for (int i = 0; i < consignmentDetails.length(); i++) {
      JSONObject consignment = consignmentDetails.getJSONObject(i);
      String status = consignment.get("status").toString();
      if (consignmentCountDB.containsKey(status)) {
        consignmentCountDB.put(status, consignmentCountDB.get(status) + 1);
      } else {
        consignmentCountDB.put(status, 1);
      }
    }
    for (Map.Entry<String, Integer> e : consignmentCountDB.entrySet()) {
      logger.info(e.getKey() + "------>" + e.getValue());
    }
    // Validation starts
    softAssert.assertEquals(
            activeOrders + closedOrders,
            consignmentDetails.length(),
            "Total today consignment count of store in Response and DB is not same");
    softAssert.assertEquals(
            activeOrders,
            zeptonUnassigned + zeptonAssigned + ongoing,
            "Today active consignment count of store in Response is not as expected");
    softAssert.assertEquals(
            closedOrders,
            completed + cancelled + undelivered,
            "Today closed consignment count of store in Response is not as expected");
    int zeptoAssignedCount =
            consignmentCountDB.getOrDefault(Statuses.ASSIGNED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(
                    Statuses.THIRD_PARTY_ARRIVED_AT_STORE.consignmentStatus, 0);
    softAssert.assertEquals(
            zeptonAssigned,
            zeptoAssignedCount,
            "Today assigned consignment count of store in Response is not as expected");
    int zeptoUnassignedCount =
            consignmentCountDB.getOrDefault(CREATED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.UNASSIGNED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.READY_TO_ASSIGN.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(THIRD_PARTY_ALLOTMENT_PENDING.consignmentStatus, 0);
    softAssert.assertEquals(
            zeptonUnassigned,
            zeptoUnassignedCount,
            "Today unassigned consignment count of store in Response is not as expected");
    int ongoingCount =
            consignmentCountDB.getOrDefault(Statuses.PICKED_UP.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.DISPATCHED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.STARTED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.AUTO_ARRIVED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.ARRIVED.consignmentStatus, 0);
    softAssert.assertEquals(
            ongoing,
            ongoingCount,
            "Today ongoing consignment count of store in Response is not as expected");
    int completedCount = consignmentCountDB.getOrDefault(Statuses.DELIVERED.consignmentStatus, 0);
    softAssert.assertEquals(
            completed,
            completedCount,
            "Today completed consignment count of store in Response is not as expected");
    int cancelledCount = consignmentCountDB.getOrDefault(Statuses.CANCELLED.consignmentStatus, 0);
    softAssert.assertEquals(
            cancelled,
            cancelledCount,
            "Today cancelled consignment count of store in Response is not as expected");
    int undeliveredCount =
            consignmentCountDB.getOrDefault(Statuses.RETURNED.consignmentStatus, 0)
                    + consignmentCountDB.getOrDefault(Statuses.RETURN_TO_ORIGIN.consignmentStatus, 0);
    softAssert.assertEquals(
            undelivered,
            undeliveredCount,
            "Today undelivered consignment count of store in Response is not as expected");
    int breachedCount = 0;
    softAssert.assertEquals(
            breached,
            breachedCount,
            "Today breached consignment count of store in Response is not as expected");
    softAssert.assertAll();
  }

  /**
   * This method will verify response of getConsignmentAPIByStoreId against DB data and some backend
   * logic as well
   *
   * @param response
   * @param storeId
   * @author Deepak_Kumar
   */
  public void validateGetConsignmentByStoreIdApiResponse(Response response, String storeId)
          throws SQLException, JsonProcessingException {
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(baseResponse.getErrors().size(), 0, "Response error size is not zero");
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    JSONArray orderList = data.getJSONArray("orderList");
    for (int i = 0; i < orderList.length(); i++) {
      JSONObject consignment = orderList.getJSONObject(i);
      OrderList consignmentResponse = mapper.readValue(consignment.toString(), OrderList.class);
      JSONArray consignmentDetails =
              mySqlDMSHelper.queryDb(
                      String.format(DBQueries.getConsignmentDetails, consignmentResponse.getId()));
      JSONObject consignmentDB = consignmentDetails.getJSONObject(0);
      logger.info("ConsignmentDetails: " + consignmentDB);
      softAssert.assertEquals(
              consignmentResponse.getStoreId(),
              storeId,
              "store_id in API response and DB is not as expected");
      softAssert.assertEquals(
              consignmentResponse.getId(),
              consignmentDB.get("id").toString(),
              "ConsignmentId in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getVersion(),
              consignmentDB.get("version"),
              "version in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getOrderId().toString(),
              consignmentDB.get("order_id").toString(),
              "orderId in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getIsValid(),
              consignmentDB.get("is_valid"),
              "is_valid in API response and DB is not as expected");
      softAssert.assertEquals(
              consignmentResponse.getStatus().toString(),
              consignmentDB.get("status").toString(),
              "status in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getDistanceInMeters(),
              consignmentDB.get("distance_in_meters"),
              "distance_in_meters in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getPaymentMode().toString(),
              consignmentDB.get("payment_mode").toString(),
              "payment_mode in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getStoreId().toString(),
              consignmentDB.get("store_id").toString(),
              "store_id in API response and DB is not as expected");
      softAssert.assertEquals(
              consignmentResponse.getPaymentAmount(),
              consignmentDB.get("payment_amount"),
              "payment_amount in API response and DB is not same");
      // Validating Customer Details
      verifyCustomerDetails(
              consignmentResponse.getCustomerDetails(),
              new JSONObject(consignmentDB.get("customer_details").toString()));
      // Validating Order Details
      verifyOrderDetails(
              consignment.getJSONObject("orderDetails"),
              new JSONObject(consignmentDB.get("order_details").toString()));
    }
    softAssert.assertEquals(
            data.get("pageSize"), 10, "Page size is not as expected in api response");
    softAssert.assertAll();
  }

  /**
   * This method will verify getConsignmentHistory API response against consignment history in DB
   *
   * @param response
   * @param consignmentId
   * @throws SQLException
   * @author Deepak_Kumar
   */
  public void validateGetConsignmentHistoryApiResponse(Response response, String consignmentId)
          throws SQLException, JsonProcessingException {
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(baseResponse.getErrors().size(), 0, "Response error size is not zero");
    ObjectMapper mapper = new ObjectMapper();
    JSONArray consignmentHistory = new JSONArray(mapper.writeValueAsString(baseResponse.getData()));
    // Below details need to verify for multiple consignment status
    JSONObject data;
    JSONObject consignment;
    JSONArray consignmentDetails =
            mySqlDMSHelper.queryDb(String.format(DBQueries.getConsignmentHistory, consignmentId));
    Assert.assertEquals(
            consignmentHistory.length(),
            consignmentDetails.length(),
            "No of consignment History in API response and DB is not same");
    for (int i = 0; i < consignmentHistory.length(); i++) {
      data = consignmentHistory.getJSONObject(i);
      logger.info("DB data: " + consignmentDetails);
      consignment = consignmentDetails.getJSONObject(i);
      verifyConsignmentDetails(data, consignment);
    }
    softAssert.assertAll();
  }

  /**
   * This method will verify consignment details against DB data
   *
   * @param data
   * @param consignment
   * @author Deepak_Kumar
   */
  public void verifyConsignmentDetails(JSONObject data, JSONObject consignment)
          throws JsonProcessingException {
    GetConsignmentResponse consignmentResponse =
            mapper.readValue(data.toString(), GetConsignmentResponse.class);
    softAssert.assertEquals(
            consignmentResponse.getDeliveries(), null, "deliveries in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getNextStatus(), null, "nextStatus in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getId(),
            consignment.get("id").toString(),
            "ConsignmentId in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getOrderId(),
            consignment.get("order_id").toString(),
            "orderId in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getIsValid(),
            consignment.get("is_valid"),
            "is_valid in API response and DB is not as expected");
    if (!(consignmentResponse.getStatus().equals(Statuses.DISPATCHED.consignmentStatus)
            || consignmentResponse.getStatus().equals(Statuses.STARTED.consignmentStatus))) {
      softAssert.assertEquals(
              consignmentResponse.getStatus(),
              consignment.get("status").toString(),
              "status in API response and DB is not same");
      softAssert.assertEquals(
              consignmentResponse.getVersion(),
              consignment.get("version"),
              "version in API response and DB is not same");
    }
    softAssert.assertEquals(
            consignmentResponse.getDistanceInMeters(),
            consignment.get("distance_in_meters"),
            "distance_in_meters in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getPaymentMode(),
            consignment.get("payment_mode").toString(),
            "payment_mode in API response and DB is not same");
    softAssert.assertEquals(
            consignmentResponse.getStoreId(),
            consignment.get("store_id").toString(),
            "store_id in API response and DB is not as expected");
    softAssert.assertEquals(
            consignmentResponse.getPaymentAmount(),
            consignment.get("payment_amount"),
            "payment_amount in API response and DB is not same");
    // Validating Customer Details
    verifyCustomerDetails(
            consignmentResponse.getCustomerDetails(),
            new JSONObject(consignment.get("customer_details").toString()));
    // Validating Order Details
    verifyOrderDetails(
            data.getJSONObject("orderDetails"),
            new JSONObject(consignment.get("order_details").toString()));
  }

  /**
   * This method will verify getConsignmentActivity API response against consignment activity in DB
   *
   * @param response
   * @param consignmentId
   * @throws SQLException
   * @author Deepak_Kumar
   */
  public void validateGetConsignmentActivityApiResponse(
          Response response, String consignmentId, String status) throws SQLException {
    JSONObject responseObject = new JSONObject(response.asString());
    String workerCodeRegex = "^[A-Z]{3}[0-9]{2}-[A-Z, 0-9]*";
    JSONArray error = responseObject.getJSONArray("errors");
    Assert.assertEquals(error.length(), 0, "Response error size is not zero");
    JSONObject data = responseObject.getJSONObject("data");
    Assert.assertEquals(
            data.get("consignmentId").toString(),
            consignmentId,
            "ConsignmentId is API response is not matching");
    JSONArray activityLog = data.getJSONArray("activityLogs");
    // Below details need to verify consignment activity
    JSONObject activityResponse;
    JSONObject consignment;
    JSONArray consignmentDetails =
            mySqlDMSHelper.queryDb(String.format(DBQueries.getConsignmentHistory, consignmentId));
    logger.info("DB data: " + consignmentDetails);
    int consignmentHistoryDBCount = consignmentDetails.length() - 1;
    for (int i = 0; i < activityLog.length(); i++) {
      activityResponse = activityLog.getJSONObject(i);
      consignment = consignmentDetails.getJSONObject(consignmentHistoryDBCount);
      if (consignment.get("status").toString().equals(ARRIVED.consignmentStatus)
              && consignment.get("is_state_changed").equals(false)) {
        i--;
        consignmentHistoryDBCount--;
        continue;
      }
      if (activityResponse.getString("status").equals("PACKED")) {
        Assert.assertEquals(
                ASSIGNED.consignmentStatus,
                consignment.get("status").toString(),
                "Status in consignment activity response and DB is not same");
      } else if (!(activityResponse
              .getString("status")
              .equals(Statuses.DISPATCHED.consignmentStatus)
              || activityResponse.getString("status").equals(Statuses.STARTED.consignmentStatus))) {
        Assert.assertEquals(
                activityResponse.get("status").toString(),
                consignment.get("status").toString(),
                "Status in consignment activity response and DB is not same");
      }
      consignmentHistoryDBCount--;
    }

    Assert.assertEquals(
            consignmentHistoryDBCount,
            -1,
            "No of consignment history for consignment state change in consignmentHistory table and consignment activity log api is not equal");

    if (status.equals(RETURN_TO_ORIGIN.consignmentStatus)) {
      Assert.assertTrue(
              activityLog.getJSONObject(activityLog.length() - 1).has("reason"),
              "Expecting reason to be present");
      Assert.assertEquals(
              activityLog.getJSONObject(activityLog.length() - 1).getString("reason"),
              environment.getGlobalTestData().getString("rtoReason.reason"),
              "Expecting reason to be correct");
      Assert.assertTrue(
              activityLog
                      .getJSONObject(activityLog.length() - 1)
                      .getString("updatedBy")
                      .matches(workerCodeRegex),
              "Expecting vendor rider id here");
    }
  }

  /**
   * This method is used to validate api response for invalid request message
   *
   * @param response
   * @param errorCode
   * @author Deepak_Kumar
   */
  public void verifyInvalidResponseMessage(Response response, String errorCode, int responseStatus)
          throws JsonProcessingException {
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(
            baseResponse.getData(), null, "Response data is not null for invalidRequestMessage");
    JSONArray error = new JSONArray(mapper.writeValueAsString(baseResponse.getErrors()));
    JSONObject errorResponse = error.getJSONObject(0);
    softAssert.assertEquals(
            errorResponse.get("code"), responseStatus, "Response code is not as expected");
    softAssert.assertEquals(
            errorResponse.get("error"), errorCode, "Response error is not as expected");
    softAssert.assertAll();
  }

  /**
   * This method is used to get OrderId By ConsignmentId
   *
   * @param consignmentId
   * @return orderId
   * @author Deepak_Kumar
   */
  public String getOrderIdByConsignmentId(String consignmentId) {
    Response response = null;
    try {
      logger.info("Waiting for 100 ms before calling consignment");
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    response =
            dmsApiHelper.getConsignmentByConsignmentId(
                    environment.getReqSpecBuilder(), consignmentId, false);
    ExtentReportUtil.logResponseDetailsInLogAndReport(response);
    Assert.assertTrue(
            response.getStatusCode() == HttpStatus.SC_OK,
            "Response status code is not 200, it is " + response.getStatusCode());
    JSONObject responseObject = new JSONObject(response.asString());
    JSONObject data = responseObject.getJSONObject("data");
    return data.get("orderId").toString();
  }

  /**
   * Validate search and filter rider api response against expected set of data
   *
   * @param actualResponse
   * @param request
   * @author manisha.kumari
   */
  public void validateSearchRidersByFilterResponse(
          SearchRidersByFilterResponse actualResponse, SearchRiderRequest request) {
    String source = "SHIPSY";
    int pageSize = 10;
    String startOfDay =
            GlobalUtil.getDateInUTCTimeZone(
                    new SimpleDateFormat(GeneralConstants.DATE_TIME_FORMAT).format(new Date(0)),
                    GeneralConstants.DATE_TIME_FORMAT,
                    GeneralConstants.UTC_FORMAT_WITH_ZONE);
    String inactiveRiderDate =
            globalUtil.getDateInUTCTimeZone(
                    globalUtil.setDateInGivenFormat(-1, "yyyy-MM-dd HH:mm:ss"),
                    GeneralConstants.DATE_TIME_FORMAT,
                    GeneralConstants.UTC_FORMAT_WITH_ZONE);
    SoftAssert softAssert = new SoftAssert();
    SearchRidersByFilterResponse expectedResponse =
            getExpectedSearchRidersByFilterResponse(
                    request.getStoreId().toString(), startOfDay, inactiveRiderDate, source, request);
    List<RiderDetail> expectedRiderDetails = expectedResponse.getRiderDetails();
    if (actualResponse == null && expectedRiderDetails == null) {
      return;
    }
    List<RiderDetail> actualRiderDetails = actualResponse.getRiderDetails();
    if (expectedRiderDetails != null && actualRiderDetails != null) {
      if (request.getSortBy() == null) {
        if (expectedRiderDetails.size() <= pageSize) {
          expectedRiderDetails.sort(Comparator.comparing(RiderDetail::getRiderId));
          actualRiderDetails.sort(Comparator.comparing(RiderDetail::getRiderId));
          softAssert.assertEquals(
                  actualRiderDetails,
                  expectedRiderDetails,
                  "Mismatch in actual and expected rider details");
        } else {
          if ((boolean) request.getIsActive()) {
            softAssert.assertEquals(
                    actualResponse.getRiderDeliveryStatusSummary().getTotalActiveRiders(),
                    expectedResponse.getRiderDeliveryStatusSummary().getTotalActiveRiders(),
                    "Mismatch in active riders number");
          } else {
            softAssert.assertEquals(
                    actualResponse.getRiderDeliveryStatusSummary().getTotalInactiveRiders(),
                    expectedResponse.getRiderDeliveryStatusSummary().getTotalInactiveRiders(),
                    "Mismatch in inactive riders number");
          }
        }
      } else {
        softAssert.assertEquals(
                actualRiderDetails,
                expectedRiderDetails,
                "Mismatch in actual and expected rider details");
      }
    }
    softAssert.assertEquals(
            actualResponse.getNextPage(),
            expectedResponse.getNextPage(),
            "Expected page number mismatch");
    softAssert.assertEquals(
            actualResponse.getRiderDeliveryStatusSummary(),
            expectedResponse.getRiderDeliveryStatusSummary(),
            "Expected rider delivery summary mismatch");
    softAssert.assertAll();
  }

  /**
   * business logic to get riders filtered by status and other params
   *
   * @param storeId
   * @param activeRiderDate
   * @param inactiveRiderDate
   * @param source
   * @param request
   * @return Expected Response
   * @author manisha.kumari
   */
  private SearchRidersByFilterResponse getExpectedSearchRidersByFilterResponse(
          String storeId,
          String activeRiderDate,
          String inactiveRiderDate,
          String source,
          SearchRiderRequest request) {
    try {
      SearchRidersByFilterResponse expectedSearchRidersResponse =
              SearchRidersByFilterResponse.builder().build();
      List<String> riders;
      List<String> activeRiders =
              getRidersForStore(storeId, activeRiderDate, source, DBQueries.getActiveRidersByStoreId);
      List<String> inactiveRiders =
              getRidersForStore(
                      storeId, inactiveRiderDate, source, DBQueries.getInactiveRidersByStoreId);
      JSONArray filteredRiders;

      if ((request.getRiderId() == null || request.getRiderId().toString().isEmpty())
              && (request.getRiderName() == null || request.getRiderName().toString().isEmpty())
              && (request.getMobileNumber() == null
              || request.getMobileNumber().toString().isEmpty())) {
        if ((boolean) request.getIsActive()) {
          riders = activeRiders;
        } else {
          riders = inactiveRiders;
        }
        String statusToFilter =
                (boolean) request.getIsActive()
                        ? Arrays.stream(RiderDeliveryStatusCounts.values())
                        .filter(v -> v.name.equals(request.getStatus()))
                        .findFirst()
                        .get()
                        .deliveryStatus
                        : RiderDeliveryStatus.RIDER_DELIVERY_OFFLINE.status;
        filteredRiders =
                riders.size() > 0
                        ? mySqlDMSHelper.queryDb(
                        String.format(
                                DBQueries.getRiderDeliveryStatusByRider,
                                String.join("','", riders),
                                statusToFilter))
                        : new JSONArray();
      } else {
        riders = searchRiders(request);
        filteredRiders =
                riders.size() > 0
                        ? mySqlDMSHelper.queryDb(
                        String.format(
                                DBQueries.getRiderDeliveryStatusByRiderId, String.join("','", riders)))
                        : new JSONArray();
      }
      Map<String, String> riderDeliveryStatusMap;
      riderDeliveryStatusMap =
              IntStream.range(0, filteredRiders.length())
                      .mapToObj(index -> ((JSONObject) filteredRiders.get(index)).toMap())
                      .collect(
                              Collectors.toMap(
                                      e -> e.get("rider_id").toString(), e -> e.get("status").toString()));
      String startOfDay =
              globalUtil.getDateInUTCTimeZone(
                      globalUtil.setDateInGivenFormat(-1, "yyyy-MM-dd") + " 04:00:00",
                      GeneralConstants.DATE_TIME_FORMAT,
                      GeneralConstants.UTC_FORMAT_WITH_ZONE);
      if (request.getPage() == null || (Integer) request.getPage() <= 500) {
        expectedSearchRidersResponse =
                populateOtherRiderInformation(
                        startOfDay,
                        expectedSearchRidersResponse,
                        riderDeliveryStatusMap,
                        (boolean) request.getIsActive());
      }
      JSONArray ridersCount =
              activeRiders.size() > 0
                      ? mySqlDMSHelper.queryDb(
                      String.format(DBQueries.getCountOfRidersStatus, String.join("','", activeRiders)))
                      : new JSONArray();
      JSONObject riderSummary = ridersCount.opt(0) != null ? (JSONObject) ridersCount.opt(0) : null;
      RiderDeliveryStatusSummary summary =
              riderSummary != null
                      ? new ObjectMapper()
                      .readValue(riderSummary.toString(), RiderDeliveryStatusSummary.class)
                      : RiderDeliveryStatusSummary.builder()
                      .assignedCount(0)
                      .onRoadCount(0)
                      .unassignedCount(0)
                      .reachedGateCount(0)
                      .returningToStoreCount(0)
                      .build();
      if ((boolean) request.getIsActive()) {
        summary.setTotalActiveRiders(
                summary.getAssignedCount()
                        + summary.getOnRoadCount()
                        + summary.getUnassignedCount()
                        + summary.getReachedGateCount()
                        + summary.getReturningToStoreCount());
      } else {
        summary.setTotalInactiveRiders(inactiveRiders.size());
      }
      expectedSearchRidersResponse.setRiderDeliveryStatusSummary(summary);
      if (expectedSearchRidersResponse.getRiderDetails() == null
              || expectedSearchRidersResponse.getRiderDetails().size() <= 10) {
        expectedSearchRidersResponse.setNextPage(0);
        if ((request.getPage() != null)
                && (expectedSearchRidersResponse.getRiderDetails() != null
                && expectedSearchRidersResponse.getRiderDetails().size()
                < (int) request.getPage())) {
          expectedSearchRidersResponse.setRiderDetails(new ArrayList<>());
        }
      } else {
        int nextPage = request.getPage() == null ? 2 : (int) request.getPage() + 1;
        expectedSearchRidersResponse.setNextPage(nextPage);
      }
      logger.info(
              "expected search response : "
                      + RequestMapper.mapPojoObjectAndGetString(expectedSearchRidersResponse));
      return expectedSearchRidersResponse;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get Active and Inactive riders by below params
   *
   * @param storeId
   * @param date
   * @param source
   * @param query
   * @return listOfRiders
   * @author manisha.kumari
   */
  private List<String> getRidersForStore(String storeId, String date, String source, String query) {
    List<String> riders;
    try {
      JSONArray riderDetails =
              new MySqlRmsHelper().queryDb(String.format(query, storeId, date, source));
      riders =
              IntStream.range(0, riderDetails.length())
                      .mapToObj(index -> ((JSONObject) riderDetails.get(index)).optString("rider_id"))
                      .collect(Collectors.toList());
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return riders;
  }

  /**
   * Search riders by additional params
   *
   * @param searchRiderRequest
   * @return riders list
   * @author manisha.kumari
   */
  private List<String> searchRiders(SearchRiderRequest searchRiderRequest) {
    List<String> riders;
    String dbQuery =
            "select r.id from public.rider r, public.user u where r.user_id = u.id and r.store_id = '"
                    + searchRiderRequest.getStoreId()
                    + "' ";
    if (searchRiderRequest.getRiderId() != null) {
      dbQuery = dbQuery + "and r.vendor_rider_id = '" + searchRiderRequest.getRiderId() + "' ";
    }
    if (searchRiderRequest.getRiderName() != null) {
      dbQuery = dbQuery + "and u.full_name like '%" + searchRiderRequest.getRiderName() + "%' ";
    }
    if (searchRiderRequest.getMobileNumber() != null) {
      dbQuery = dbQuery + "and u.mobile_number = '" + searchRiderRequest.getMobileNumber() + "' ";
    }
    try {
      JSONArray queryResults = new MySqlZeptoBackendServiceHelper().queryDb(dbQuery);
      riders =
              IntStream.range(0, queryResults.length())
                      .mapToObj(index -> ((JSONObject) queryResults.get(index)).optString("id"))
                      .collect(Collectors.toList());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return riders;
  }

  /**
   * Get data from consignment, trip and delivery for riders in current date
   *
   * @param date
   * @param riderData
   * @param riderDeliveryStatusMap
   * @param isActive
   * @return ExpectedResponse
   * @author manisha.kumari
   */
  private SearchRidersByFilterResponse populateOtherRiderInformation(
          String date,
          SearchRidersByFilterResponse riderData,
          Map<String, String> riderDeliveryStatusMap,
          boolean isActive) {
    int riderCount = 0;
    for (String riderId : riderDeliveryStatusMap.keySet()) {
      if (riderCount <= 20) {
        try {
          ObjectMapper om = new ObjectMapper();
          om.setSerializationInclusion(Include.NON_NULL);
          String rds = riderDeliveryStatusMap.get(riderId);
          List<CurrentConsignmentInfo> currentConsignmentInfoList = new ArrayList<>();
          if (((isActive && !rds.equals(RiderDeliveryStatus.RIDER_DELIVERY_OFFLINE.status))
                  || (!isActive && rds.equals(RiderDeliveryStatus.RIDER_DELIVERY_OFFLINE.status)))) {
            JSONArray tripInfo =
                    mySqlDMSHelper.queryDb(String.format(DBQueries.getTripsByRiderId, riderId, date));
            JSONArray consignmentDeliveryDetails = new JSONArray();
            JSONObject tripDetails = new JSONObject();
            for (Object trip : tripInfo) {
              int ordersDelivered = 0;
              tripDetails = (JSONObject) trip;
              String tripId = null;
              if (tripDetails != null) {
                tripId = tripDetails.opt("trip_id").toString();
              }
              if ((tripDetails.get("is_trip_valid") != null
                      && !tripDetails.getBoolean("is_trip_valid"))) {
                if (tripDetails
                        .getString("trip_status")
                        .equals(Statuses.DELIVERED.consignmentStatus)) {
                  ordersDelivered++;
                }
              } else {
                consignmentDeliveryDetails =
                        mySqlDMSHelper.queryDb(
                                String.format(DBQueries.getDeliveryConsignmentInfoByRiders, tripId));
              }
            }
            if (consignmentDeliveryDetails.length() > 0) {
              for (Object allDetails : consignmentDeliveryDetails) {
                if (allDetails instanceof JSONObject) {
                  JSONObject details = (JSONObject) allDetails;
                  if (details.get("is_trip_valid") != null && details.getBoolean("is_trip_valid")) {
                    CurrentConsignmentInfo currentConsignmentInfo =
                            CurrentConsignmentInfo.builder().build();

                    String orderDetails = details.get("order_details").toString();
                    OrderDetailsResponse od =
                            om.readValue(orderDetails, OrderDetailsResponse.class);
                    String customerDetails = details.get("customer_details").toString();
                    String statusChangeLogs = details.get("del_extra_details").toString();
                    CustomerDetails cd = om.readValue(customerDetails, CustomerDetails.class);
                    ExtraDetails statusChangeLog =
                            om.readValue(statusChangeLogs, ExtraDetails.class);
                    currentConsignmentInfo.setOrderDetails(od);
                    currentConsignmentInfo.setCustomerDetails(cd);
                    currentConsignmentInfo.setStatusChangeLog(statusChangeLog.getStatusChangeLog());
                    currentConsignmentInfo.setId(details.get("id").toString());
                    currentConsignmentInfo.setStatus(details.getString("status"));
                    currentConsignmentInfo.setDeliveryId(details.get("delivery_id").toString());
                    currentConsignmentInfo.setDistanceInMeters(
                            Integer.parseInt(details.get("distance_in_meters").toString()));
                    currentConsignmentInfo.setDeliveryStatus(details.getString("delivery_status"));
                    currentConsignmentInfo.setTripId(details.get("trip_id").toString());
                    currentConsignmentInfo.setTripStatus(details.getString("trip_status"));
                    currentConsignmentInfo.setIsBatched(details.getBoolean("is_batched"));
                    currentConsignmentInfo.setOrderCode(details.getString("order_code"));
                    currentConsignmentInfo.setOrderId(details.get("order_id").toString());
                    currentConsignmentInfo.setExpectedDeliveryTime(od.getExpectedDeliveryTime());
                    currentConsignmentInfo.setIsValid(details.getBoolean("is_valid"));
                    currentConsignmentInfo.setPolyline(od.getPolyline());
                    currentConsignmentInfoList.add(currentConsignmentInfo);
                  }
                }
              }
            }

            JSONArray riderDetails =
                    new MySqlZeptoBackendServiceHelper()
                            .queryDb(String.format(DBQueries.getAdditionalRiderDetails, riderId));
            JSONObject riderInfo =
                    riderDetails.opt(0) != null ? (JSONObject) riderDetails.opt(0) : new JSONObject();
            String vehicleType = riderInfo.optString("vehicle_type").toLowerCase();
            String vehicleTypeEnum = "";
            String contractType = riderInfo.optString("contract_type");
            boolean codEligibility = !contractType.equals("TEMPORARY_RIDER");
            boolean batchingEligibility = true;
            if (vehicleType.contains("new")) {
              vehicleTypeEnum = "EV-New";
            } else if (vehicleType.contains("scooter")) {
              vehicleTypeEnum = "E-Scooter";
            } else if (vehicleType.equals("cycle")) {
              vehicleTypeEnum = "Cycle";
            } else if (vehicleType.contains("cycle")) {
              vehicleTypeEnum = "E-Cycle";
            } else if (vehicleType.equalsIgnoreCase("Express-fleet")) {
              vehicleTypeEnum = "Express-Fleet";
            } else {
              vehicleTypeEnum = CaseUtils.toCamelCase(vehicleType, true);
            }
            RiderDetail riderDetail =
                    RiderDetail.builder()
                            .id(riderId)
                            .riderId(riderId)
                            .vendorRiderId(riderInfo.getString("vendor_rider_id"))
                            .mobileNumber(riderInfo.getString("mobile_number"))
                            .riderName(riderInfo.getString("full_name"))
                            .vehicleDetails(VehicleDetails.builder().vehicleType(vehicleTypeEnum).build())
                            .contractType(contractType)
                            .eligibility(
                                    Eligibility.builder()
                                            .batchingEligibility(batchingEligibility)
                                            .codEligibility(codEligibility)
                                            .build())
                            .currentConsignmentInfo(currentConsignmentInfoList)
                            .riderDeliveryStatus(
                                    com.zeptonow.commonlm.pojo.dms.subclass.RiderDeliveryStatus.builder()
                                            .deliveryStatus(rds)
                                            .primaryMessage(
                                                    Arrays.stream(RiderDeliveryStatus.values())
                                                            .filter(v -> v.status.equalsIgnoreCase(rds))
                                                            .findFirst()
                                                            .get()
                                                            .message)
                                            .build())
                            .build();
            if (riderData.getRiderDetails() == null) {
              riderData.setRiderDetails(
                      new ArrayList() {
                        {
                          add(riderDetail);
                        }
                      });
            } else {
              riderData.getRiderDetails().add(riderDetail);
            }
          }
          riderCount++;
        } catch (SQLException | JsonProcessingException e) {
          e.printStackTrace();
          throw new RuntimeException(e);
        }
      }
    }
    return riderData;
  }

  /**
   * Validate trip status by getting trip info
   *
   * @param response
   * @param expectedStatus
   * @author Manisha.kumari
   */
  public void validateUpdateTripResponse(
          BaseResponse<GetTripByIdResponse> response, String expectedStatus) {
    GetTripByIdResponse tripDetails = null;
    try {
      tripDetails =
              new ObjectMapper()
                      .readValue(
                              RequestMapper.mapPojoObjectAndGetString(response.getData()),
                              GetTripByIdResponse.class);
      String actualStatus = tripDetails.getStatus();
      Assert.assertEquals(actualStatus, expectedStatus, "Status expectations mismatch");
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Validate delivery rider change
   *
   * @param changeRiderResponse
   * @param deliveryId
   * @param requestStatus
   * @author Manisha.kumari
   */
  public void validateDeliveryRiderChangeResponse(
          String requestStatus,
          Response changeRiderResponse,
          String deliveryId,
          JSONArray getDeliveryDetailsBeforeUpdate,
          String oldRiderId) {
    try {
      JSONArray getDeliveryDetails =
              getDeliveryDetailsBeforeUpdate != null
                      ? getDeliveryDetailsBeforeUpdate
                      : mySqlDMSHelper.queryDb(String.format(DBQueries.getDeliveryDetailsById, deliveryId));
      String currentStatus =
              getDeliveryDetails.iterator().hasNext()
                      ? ((JSONObject) getDeliveryDetails.get(0)).opt("status").toString()
                      : "";
      String tripId = "";
      if (!currentStatus.isEmpty()) {
        tripId = ((JSONObject) getDeliveryDetails.get(0)).opt("trip_id").toString();
        String consignmentId =
                ((JSONObject) getDeliveryDetails.get(0)).opt("consignment_id").toString();

        logger.info("Query trip details by trip id");
        JSONArray tripDbDetails =
                mySqlDMSHelper.queryDb(String.format(DBQueries.getTripDetailsById, tripId));
        JSONObject tripDetails =
                tripDbDetails.length() > 0 ? (JSONObject) tripDbDetails.get(0) : new JSONObject();

        switch (requestStatus) {
          case "READY_TO_ASSIGN", "DELIVERED", "UNASSIGNED", "ARRIVED", "AUTO_ARRIVED" -> {
            Assert.assertEquals(
                    changeRiderResponse.getStatusCode(),
                    HttpStatus.SC_BAD_REQUEST,
                    "Invalid status to change rider");
            getDeliveryDetails =
                    mySqlDMSHelper.queryDb(String.format(DBQueries.getDeliveryDetailsById, deliveryId));
            String deliveryStatusAfterUpdate =
                    getDeliveryDetails.iterator().hasNext()
                            ? ((JSONObject) getDeliveryDetails.get(0)).opt("status").toString()
                            : "";
            Assert.assertEquals(
                    currentStatus,
                    deliveryStatusAfterUpdate,
                    "Mismatch in delivery status expectations ");
          }
          case "ASSIGNED" -> {
            Assert.assertEquals(
                    changeRiderResponse.getStatusCode(),
                    HttpStatus.SC_OK,
                    "Status code assertion failure");
            Assert.assertEquals(
                    tripDetails.getString("status"),
                    Statuses.CANCELLED.tripStatus,
                    "Mismatch in trip status expectations");
            Assert.assertEquals(
                    ASSIGNED.consignmentStatus,
                    currentStatus,
                    "Mismatch in delivery status expectations ");
            // after update
            getDeliveryDetails =
                    mySqlDMSHelper.queryDb(String.format(DBQueries.getDeliveryDetailsById, deliveryId));
            String deliveryStatusAfterUpdate =
                    getDeliveryDetails.iterator().hasNext()
                            ? ((JSONObject) getDeliveryDetails.get(0)).opt("status").toString()
                            : "";
            Assert.assertEquals(
                    READY_TO_ASSIGN.consignmentStatus,
                    deliveryStatusAfterUpdate,
                    "Mismatch in delivery status expectations ");
            verifyConsignmentStatus(consignmentId, Statuses.READY_TO_ASSIGN.consignmentStatus);
            validateRiderStatus(oldRiderId, RiderDeliveryStatus.RIDER_DELIVERY_IN_STORE.status);
          }
          case "ACCEPTED", "PICKED_UP", "STARTED", "DISPATCHED" -> {
            Assert.assertEquals(
                    changeRiderResponse.getStatusCode(),
                    HttpStatus.SC_OK,
                    "Status code assertion failure");
            Assert.assertEquals(
                    tripDetails.getString("status"),
                    READY_TO_ASSIGN.consignmentStatus,
                    "Mismatch in trip status expectations");
            if (!currentStatus.equals(ASSIGNED.consignmentStatus)) {
              Assert.assertEquals(
                      requestStatus, currentStatus, "Mismatch in delivery status expectations ");
            }
            // get delivery details after update
            getDeliveryDetails =
                    mySqlDMSHelper.queryDb(String.format(DBQueries.getDeliveryDetailsById, deliveryId));
            String deliveryStatusAfterUpdate =
                    getDeliveryDetails.iterator().hasNext()
                            ? ((JSONObject) getDeliveryDetails.get(0)).opt("status").toString()
                            : "";
            Assert.assertEquals(
                    READY_TO_ASSIGN.consignmentStatus,
                    deliveryStatusAfterUpdate,
                    "Mismatch in delivery status expectations ");
            verifyConsignmentStatus(consignmentId, Statuses.READY_TO_ASSIGN.consignmentStatus);
            validateRiderStatus(oldRiderId, RiderDeliveryStatus.RIDER_DELIVERY_OFFLINE.status);
          }
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Get expected error code for search riders
   *
   * @param request
   * @return
   * @author Manisha_Kumari
   */
  public Error checkIfValidRequest(SearchRiderRequest request) {
    Error error = Error.builder().build();
    String uuidRegex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    try {
      if (request.getIsActive() == null
              || request.getIsActive().equals("")
              || request
              .getIsActive()
              .toString()
              .matches("^(?!" + request.getIsActive().toString() + ").*$")) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if (request.getStoreId() == null
              || !request.getStoreId().toString().matches(uuidRegex)) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if (((request.getIsActive() != null) && ((boolean) request.getIsActive()))
              && (request.getStatus() == null
              || Arrays.stream(RiderDeliveryStatusCounts.values())
              .filter(s -> s.name.equals(request.getStatus().toString()))
              .findFirst()
              .isEmpty())) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if ((request.getRiderId() != null)
              && (request.getRiderId().toString().length() < 9
              || request.getRiderId().toString().length() > 15)) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if ((request.getMobileNumber() != null)
              && (!request.getMobileNumber().toString().matches("^[1-9][0-9]{9}$"))) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if ((request.getSortBy() != null)
              && !(request.getSortBy().toString().equals("ORDERS_DELIVERED")
              || request.getSortBy().toString().equals("BREAK_TIME"))) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if ((request.getStatus() != null)
              && Arrays.stream(RiderDeliveryStatusCounts.values())
              .filter(s -> s.name.equals(request.getStatus().toString()))
              .findFirst()
              .isEmpty()) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if ((request.getRiderId() != null) && request.getRiderName() != null) {
        error.setCode(HttpStatus.SC_BAD_REQUEST);
      } else if (request.getPage() != null) {
        int page = (int) request.getPage();
        error.setCode(0);
      }
    } catch (Exception e) {
      error.setCode(HttpStatus.SC_BAD_REQUEST);
    }
    return error;
  }

  /**
   * This method is used to create new consignment whose status is Assigned
   *
   * @return riderId
   * @author Deepak_Kumar
   */
  public String createConsignmentWithAssignedStatus()
          throws DocumentException, IOException, InterruptedException {
    SoftAssert softAssert = new SoftAssert();
    String riderId =
            checkInCheckOutHelper
                    .createRiderUploadRiderWorkPlanDoCheckIn("PART_TIME", "BIKE", true)
                    .get("riderId");
    JSONObject createConsignmentResponse = createConsignment();
    assignRiderManuallyToConsignment(createConsignmentResponse, riderId);
    String consignmentId = createConsignmentResponse.get("id").toString();
    createConsignmentResponse =
            verifyConsignmentStatus(consignmentId, Statuses.ASSIGNED.consignmentStatus);
    // log if same rider is not assigned, it does not fail the test
    JSONObject delivery =
            ((JSONObject) createConsignmentResponse.getJSONArray("deliveries").get(0));
    String assignedRiderId = ((JSONObject) delivery.get("trip")).get("riderId").toString();
    softAssert.assertEquals(
            riderId, assignedRiderId, "===== consignment got assigned to another rider =====");
    return consignmentId;
  }

  /**
   * This method is used to verify consignment, delivery and trip status at any particular state and
   * ensure all are in sync
   *
   * @param consignmentId
   * @param expectedConsignmentStatus
   * @param expectedDeliveryStatus
   * @param expectedTripStatus
   * @author Deepak_Kumar
   */
  public void verifyConsignmentDeliveryAndTripStatus(
          String consignmentId,
          String expectedConsignmentStatus,
          String expectedDeliveryStatus,
          String expectedTripStatus) {
    SoftAssert softAssert = new SoftAssert();
    try {
      logger.info("Waiting for 100 ms before calling get consignment.");
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    logger.info("Waited for 100 ms, now calling get consignment");
    JSONObject consignmentDetails =
            expectedTripStatus == null
                    ? getConsignmentDeliveryDetailsById(consignmentId)
                    : getConsignmentTripDeliveryDetailsById(consignmentId);

    softAssert.assertEquals(
            consignmentDetails.getString("status"),
            expectedConsignmentStatus,
            "Consignment status is not as expected");
    softAssert.assertEquals(
            consignmentDetails.getString("delivery_status"),
            expectedDeliveryStatus,
            "Delivery Status is not as expected");
    if (expectedTripStatus != null) {
      softAssert.assertEquals(
              consignmentDetails.getString("trip_status"),
              expectedTripStatus,
              "trip status is not as expected");
    }
    softAssert.assertAll();
  }

  /**
   * Validate rider orders history response
   *
   * @param actualResponse
   * @param riderId
   * @author Manisha.Kumari
   */
  public void validateRiderOrdersHistoryResponse(
          GetRiderOrdersHistoryResponse actualResponse,
          String riderId,
          int pageToken,
          boolean inStore,
          List<OrderHistory> totalOrders)
          throws JsonProcessingException {
    GetRiderOrdersHistoryResponse expectedRiderHistoryDetails =
            buildExpectedOrderHistoryResponse(riderId, pageToken);
    SoftAssert softAssert = new SoftAssert();
    List<OrderHistory> expectedTotalOrders = expectedRiderHistoryDetails.getOrders();
    expectedTotalOrders.sort(Comparator.comparing(o -> o.getDate(), Comparator.reverseOrder()));

    logger.info("Validate pagination data");
    softAssert.assertTrue(
            expectedTotalOrders.size() == totalOrders.size(), "Mismatch in order history count");
    for (OrderHistory expectedOrderHistory : expectedTotalOrders) {
      softAssert.assertEquals(
              totalOrders.get(expectedTotalOrders.indexOf(expectedOrderHistory)).getDate(),
              expectedOrderHistory.getDate(),
              "Mismatch in rider load date");
      softAssert.assertTrue(
              totalOrders.get(expectedTotalOrders.indexOf(expectedOrderHistory)).getOrderList().size()
                      == expectedOrderHistory.getOrderList().size(),
              "Mismatch in order list size for date :: " + expectedOrderHistory.getDate());
    }
    totalOrders = actualResponse.getOrders();
    if (totalOrders != null) {
      for (OrderHistory actualOrderHistoryData : totalOrders) {
        List<Orders> actualOrderInfo = actualOrderHistoryData.getOrderList();
        List<Orders> expectedOrderInfo =
                expectedTotalOrders.get(totalOrders.indexOf(actualOrderHistoryData)).getOrderList();
        for (Orders actualOrder : actualOrderInfo) {
          Orders expectedOrder = expectedOrderInfo.get(actualOrderInfo.indexOf(actualOrder));
          OrderDetailsResponse expectedOrderDetails = expectedOrder.getOrderDetails();
          OrderDetailsResponse actualOrderDetails = actualOrder.getOrderDetails();
          HuDetails actualHu =
                  mapper.readValue(
                          RequestMapper.mapPojoObjectAndGetString(actualOrderDetails.getHuDetails()),
                          HuDetails.class);
          HuDetails expectedHu =
                  mapper.readValue(
                          RequestMapper.mapPojoObjectAndGetString(expectedOrderDetails.getHuDetails()),
                          HuDetails.class);

          softAssert.assertEquals(
                  actualOrder.getOrderCode(),
                  expectedOrder.getOrderCode(),
                  "Mismatch in order code data");
          softAssert.assertEquals(
                  actualOrder.getLoadTime().split("T")[0],
                  expectedOrder.getLoadTime().split(" ")[0],
                  "Mismatch in load time date");
          softAssert.assertEquals(
                  actualOrder.getPaymentMode(),
                  expectedOrder.getPaymentMode(),
                  "Mismatch in payment mode data");
          softAssert.assertEquals(
                  actualOrder.getPaymentStatus(),
                  expectedOrder.getPaymentStatus(),
                  "Mismatch in payment status data");
          softAssert.assertEquals(
                  actualOrder.getStoreId(), expectedOrder.getStoreId(), "Mismatch in store id data");
          softAssert.assertEquals(
                  actualOrder.getStatus(),
                  expectedOrder.getStatus(),
                  "Mismatch in delivery status data");
          softAssert.assertEquals(
                  actualOrder.getCustomerDetails(),
                  expectedOrder.getCustomerDetails(),
                  "Mismatch in customer details data");
          softAssert.assertEquals(
                  actualOrder.getDeliveries().size(),
                  expectedOrder.getDeliveries().size(),
                  "Mismatch in delivery data");
          softAssert.assertEquals(
                  actualOrder.getLoadTime().split("T")[0],
                  actualOrder.getOrderDetails().getOrderDate().split("T")[0],
                  "Mismatch in load time date and order time date");
          Delivery expectedDeliveryDetails = expectedOrder.getDeliveries().get(0);
          Delivery actualDeliveryDetails = actualOrder.getDeliveries().get(0);
          softAssert.assertEquals(
                  actualDeliveryDetails.getConsignmentId(),
                  expectedDeliveryDetails.getConsignmentId(),
                  "Mismatch in delivery consignment id ");
          softAssert.assertEquals(
                  actualDeliveryDetails.getId(),
                  expectedDeliveryDetails.getId(),
                  "Mismatch in delivery id ");
          softAssert.assertEquals(
                  actualDeliveryDetails.getStatus(),
                  expectedDeliveryDetails.getStatus(),
                  "Mismatch in delivery status ");
          JSONObject tripDetails =
                  new JSONObject(mapper.writeValueAsString(actualDeliveryDetails.getTrip()));
          JSONObject extraDetails = new JSONObject(tripDetails.get("extraDetails").toString());
          JSONObject rtsDetails = new JSONObject(extraDetails.get("riderRtsDetails").toString());
          boolean rtsCalculated = rtsDetails.getBoolean("rtsCalculated");
          // instore flag helps in checking if it's directly updated or not, if directly we want to
          // fail the scenario temporarily
          if (!rtsCalculated && !inStore) {
            expectedDeliveryDetails
                    .getExtraDetails()
                    .getStatusChangeLog()
                    .remove(expectedDeliveryDetails.getExtraDetails().getStatusChangeLog().size() - 1);
          }
          softAssert.assertEquals(
                  actualDeliveryDetails.getExtraDetails(),
                  expectedDeliveryDetails.getExtraDetails(),
                  "Mismatch in extra details status log ");
          softAssert.assertEquals(
                  mapper.readValue(
                          mapper.writeValueAsString(actualDeliveryDetails.getStatusChangeEvent()),
                          StatusChangeLog.class),
                  expectedDeliveryDetails.getStatusChangeEvent(),
                  "Mismatch in status change event ");
          softAssert.assertEquals(actualHu, expectedHu, "Mismatch in hu details ");
          softAssert.assertEquals(
                  actualOrder.getCustomerDetails(),
                  expectedOrder.getCustomerDetails(),
                  "Mismatch in customer details data");
          List<ItemDetails> actualItemDetails =
                  List.of(
                          mapper.readValue(
                                  RequestMapper.mapPojoObjectAndGetString(actualOrderDetails.getItemDetails()),
                                  ItemDetails[].class));
          List<ItemDetails> expectedItemDetails =
                  List.of(
                          mapper.readValue(
                                  RequestMapper.mapPojoObjectAndGetString(
                                          expectedOrderDetails.getItemDetails()),
                                  ItemDetails[].class));
          softAssert.assertEquals(
                  actualItemDetails, expectedItemDetails, "Mismatch in Order item details ");
        }
      }
    }
    softAssert.assertEquals(
            actualResponse.getNextToken(),
            expectedRiderHistoryDetails.getNextToken(),
            "Mismatch in page token value");
    softAssert.assertAll();
  }

  /**
   * Validate rider orders history response for negative cases
   *
   * @param getRiderHistoryResponse
   * @param riderId
   * @param auth
   * @param pageToken
   * @author Manisha.Kumari
   */
  public void validateRiderOrdersHistoryInvalidResponse(
          Response getRiderHistoryResponse, String riderId, String auth, int pageToken) {
    BaseResponse<GetRiderOrdersHistoryResponse> getRiderOrdersHistoryResponse = null;
    SoftAssert softAssert = new SoftAssert();
    Error error = Error.builder().build();
    if (riderId == null || (riderId != null && !riderId.matches(GeneralConstants.UUID_REGEX))) {
      error.setCode(HttpStatus.SC_BAD_REQUEST);
      error.setError(
              environment.getGlobalTestData().getString("getOrderHistoryData.rider_parser_error_msg"));
      getRiderOrdersHistoryResponse = getRiderHistoryResponse.as(BaseResponse.class);
    } else if (pageToken < 0) {
      error.setCode(HttpStatus.SC_BAD_REQUEST);
      error.setError(
              environment.getGlobalTestData().getString("getOrderHistoryData.invalid_page_token_msg"));
      getRiderOrdersHistoryResponse = getRiderHistoryResponse.as(BaseResponse.class);
    } else if (auth.isEmpty() || !auth.matches(GeneralConstants.TOKEN_REGEX_WITHOUT_BEARER)) {
      error.setCode(HttpStatus.SC_UNAUTHORIZED);
      error.setError(
              environment
                      .getGlobalTestData()
                      .getString("getOrderHistoryData.access_token_invalid_msg"));
    } else {
      error.setCode(HttpStatus.SC_BAD_REQUEST);
      getRiderOrdersHistoryResponse = getRiderHistoryResponse.as(BaseResponse.class);
    }
    softAssert.assertTrue(
            getRiderHistoryResponse.getStatusCode() == error.getCode(),
            "Mismatch in status code validation");
    if (getRiderOrdersHistoryResponse != null) {
      softAssert.assertTrue(
              getRiderOrdersHistoryResponse.getErrors().size() > 0,
              "Error case expected but found no errors");
      if (error.getError() != null || error.getError().isEmpty()) {
        softAssert.assertEquals(
                getRiderOrdersHistoryResponse.getErrors().get(0).getError(),
                error.getError(),
                "Mismatch in error message expectation");
      }
      softAssert.assertEquals(
              getRiderOrdersHistoryResponse.getErrors().get(0).getCode(),
              error.getCode(),
              "Error code mismatch expectation");
    } else {
      softAssert.assertEquals(
              getRiderHistoryResponse.getBody().jsonPath().getString("message"),
              error.getError(),
              "Mismatch in error message expectation");
      softAssert.assertTrue(
              getRiderHistoryResponse.getBody().jsonPath().getInt("code") == error.getCode(),
              "Mismatch in error code expectation");
    }

    softAssert.assertAll();
  }

  /**
   * build expected response of rider history using db data
   *
   * @param riderId
   * @param pageToken
   * @return
   * @author Manisha.Kumari
   */
  private GetRiderOrdersHistoryResponse buildExpectedOrderHistoryResponse(
          String riderId, int pageToken) {
    try {
      GetRiderOrdersHistoryResponse expectedOrdersHistoryResponse =
              GetRiderOrdersHistoryResponse.builder().build();
      List<OrderHistory> orderHistories = new ArrayList<>();
      int pageSize = 10;
      logger.info("querying from db to get order details for the rider :: " + riderId);
      JSONArray orderDetailsFromDb =
              mySqlDMSHelper.queryDb(
                      String.format(
                              DBQueries.getOrdersHistoryByRiderId,
                              riderId,
                              Statuses.DELIVERED.consignmentStatus,
                              Statuses.RETURNED.consignmentStatus));
      int nextPage = orderDetailsFromDb.length() > pageToken + pageSize ? pageToken + pageSize : 0;

      logger.info("group results by load time ");
      Map<Object, List<JSONObject>> groupByResult =
              IntStream.range(0, orderDetailsFromDb.length())
                      .mapToObj(index -> ((JSONObject) orderDetailsFromDb.get(index)))
                      .collect(
                              Collectors.groupingBy(
                                      o ->
                                              GlobalUtil.getStartOfDayFromGivenDate(
                                                      o.get("load_time").toString(),
                                                      GeneralConstants.DATE_TIME_FORMAT,
                                                      "yyyy-MM-dd'T'HH:mm:ss'Z'")));
      logger.info("Grouped orders by date :: " + groupByResult);
      if (orderDetailsFromDb.length() < pageToken) {
        groupByResult.clear();
      }

      Iterator orderIterator = groupByResult.entrySet().iterator();
      while (orderIterator.hasNext()) {
        List<Orders> ordersList = new ArrayList<>();
        OrderHistory orderHistory = OrderHistory.builder().build();
        Entry<Object, List<JSONObject>> orderEntry =
                (Entry<Object, List<JSONObject>>) orderIterator.next();
        String date = orderEntry.getKey().toString();
        date =
                GlobalUtil.getStartOfDayFromGivenDate(
                        date, "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssXXX");
        orderHistory.setDate(date);
        for (JSONObject orders : orderEntry.getValue()) {
          Orders orderDetails = Orders.builder().build();
          List<Delivery> deliveries = new ArrayList<>();
          String orderInfo = orders.get("order_details").toString();
          OrderDetailsResponse od = mapper.readValue(orderInfo, OrderDetailsResponse.class);
          String statusChangeLogs = orders.get("extra_details").toString();
          ObjectMapper ob =
                  new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
          ExtraDetails statusChangeLog = ob.readValue(statusChangeLogs, ExtraDetails.class);
          for (StatusChangeLog log : statusChangeLog.getStatusChangeLog()) {
            log.setStatus(mapStatusToStatusMessage(log.getStatus()));
            JSONObject location =
                    new JSONObject(RequestMapper.mapPojoObjectAndGetString(log.getLocation()));
            if (!location.has("accuracy")) {
              location.put("accuracy", 0);
              log.setLocation(location.toMap());
            }
          }
          // logic to add Returned to store log if rts details are present
          JSONObject consignmentDetails =
                  getConsignmentTripDeliveryDetailsById(orders.get("consignment_id").toString());
          if (consignmentDetails.has("trip_id") && consignmentDetails.has("trip_extra_details")) {
            JSONObject tripDetails =
                    new JSONObject(consignmentDetails.get("trip_extra_details").toString());
            if (tripDetails.has("rider_rts_details")
                    && tripDetails.get("rider_rts_details") != null) {
              StatusChangeLog returnToStoreLog =
                      StatusChangeLog.builder()
                              .status(mapStatusToStatusMessage(RETURNED_TO_STORE.tripStatus))
                              .build();
              JSONObject location = new JSONObject();
              location.put("latitude", 0);
              location.put("longitude", 0);
              location.put("accuracy", 0);
              returnToStoreLog.setLocation(location.toMap());
              statusChangeLog.getStatusChangeLog().add(returnToStoreLog);
            }
          }
          String statusChangeEvent = orders.get("status_change_event").toString();
          StatusChangeLog statusChange = mapper.readValue(statusChangeEvent, StatusChangeLog.class);
          JSONObject location =
                  new JSONObject(RequestMapper.mapPojoObjectAndGetString(statusChange.getLocation()));
          if (!location.has("accuracy")) {
            location.put("accuracy", 0);
            statusChange.setLocation(location.toMap());
          }
          orderDetails.setCustomerDetails(null);
          orderDetails.setOrderDetails(od);
          orderDetails.setId(orders.get("consignment_id").toString());
          orderDetails.setOrderCode(orders.getString("order_code"));
          orderDetails.setOrderId(orders.get("order_id").toString());
          orderDetails.setDistanceInMeters(orders.getInt("distance_in_meters"));
          orderDetails.setExpectedDeliveryTime(
                  GlobalUtil.getDateInUTCTimeZone(
                          orders.get("expected_delivery_time").toString(),
                          "yyyy-MM-dd HH:mm:ss.S",
                          "yyyy-MM-dd HH:mm:ss.S"));
          orderDetails.setLoadTime(
                  GlobalUtil.getDateInUTCTimeZone(
                          orders.get("load_time").toString(),
                          "yyyy-MM-dd HH:mm:ss.S",
                          "yyyy-MM-dd HH:mm:ss.S"));
          orderDetails.setPaymentStatus(orders.getString("payment_status"));
          orderDetails.setPaymentMode(orders.getString("payment_mode"));
          orderDetails.setStatus(orders.getString("status"));
          orderDetails.setStoreId(orders.get("store_id").toString());
          Delivery delivery = Delivery.builder().build();
          delivery.setId(orders.get("id").toString());
          delivery.setConsignmentId(orderDetails.getId());
          delivery.setTripId(orders.get("trip_id").toString());
          delivery.setIsBatched(orders.getBoolean("is_batched"));
          delivery.setExtraDetails(statusChangeLog);
          delivery.setStatusChangeEvent(statusChange);
          delivery.setStatus(orders.getString("status"));
          deliveries.add(delivery);
          orderDetails.setDeliveries(deliveries);
          ordersList.add(orderDetails);
        }
        orderHistory.setOrderList(ordersList);
        orderHistories.add(orderHistory);
      }
      expectedOrdersHistoryResponse.setOrders(orderHistories);
      expectedOrdersHistoryResponse.setPageSize(pageSize);
      expectedOrdersHistoryResponse.setNextToken(nextPage);
      logger.info("Expected orders history response :: " + expectedOrdersHistoryResponse);
      return expectedOrdersHistoryResponse;
    } catch (SQLException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Create consignment for automation store
   *
   * @author Manisha.Kumari
   */
  public JSONObject createConsignment(String... isBatchable) {
    List<ItemDetails> itemDetails = new ArrayList<>();
    itemDetails.add(ItemDetails.builder().build());

    boolean batchOrder = (isBatchable.length > 0) ? Boolean.valueOf(isBatchable[0]) : Boolean.FALSE;
    OrderDetails orderDetails =
            OrderDetails.builder()
                    .itemDetails(itemDetails)
                    .orderCode(new GlobalUtil().getRandomOrderCode())
                    .expectedDeliveryTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .startTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .orderDate(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .isBatchable(batchOrder)
                    .build();

    CreateConsignmentRequest createConsignmentRequest =
            CreateConsignmentRequest.builder()
                    .orderId(UUID.randomUUID().toString())
                    .orderDetails(orderDetails)
                    .loadTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .build();
    createConsignmentRequest
            .getCustomerDetails()
            .setLatitude(environment.getGlobalTestData().getDouble("createConsignmentData.lat"));
    createConsignmentRequest
            .getCustomerDetails()
            .setLongitude(environment.getGlobalTestData().getDouble("createConsignmentData.long"));
    Response response =
            new DMSApiHelper()
                    .createConsignment(
                            environment.getReqSpecBuilder(), Boolean.FALSE, createConsignmentRequest);
    Assert.assertTrue(response.getStatusCode() == HttpStatus.SC_CREATED);
    JSONObject responseObject = new JSONObject(response.asString());
    JSONObject createConsignmentResponse = responseObject.getJSONObject("data");
    return createConsignmentResponse;
  }

  /**
   * checkIn existing rider and return token or use existing token to check in a rider create
   * consignment, manual assign and deliver finally mark rider in store
   *
   * @param riderId
   * @return
   * @author Manisha_Kumari
   */
  public String checkInAndPlaceOrderForRider(String riderId, String... authToken) {
    String token = "";
    RiderHomePageApiHelper riderHomePageApiHelper = new RiderHomePageApiHelper();
    try {
      if (authToken.length > 0) {
        token += authToken[0];
      } else {
        logger.info("generating auth token for rider :: " + riderId);
        String mobileNumber =
                new MySqlZeptoBackendServiceHelper()
                        .queryDb(String.format(DBQueries.getRiderMobileNo, riderId))
                        .getJSONObject(0)
                        .getString("mobile_number");
        token =
                new RiderHelper().generateRiderAuthToken(environment.getReqSpecBuilder(), mobileNumber);
      }
      logger.info("create consignment and assign it to rider");
      JSONObject consignmentResponse = createConsignment();
      String consignmentId = consignmentResponse.get("id").toString();
      String storeId = consignmentResponse.get("storeId").toString();
      String tripId =
              ((JSONObject) consignmentResponse.optJSONArray("deliveries").opt(0))
                      .opt("tripId")
                      .toString();

      logger.info("check in existing rider");
      checkInRider(riderId, storeId, token);

      assignRiderManuallyToConsignment(consignmentResponse, riderId);
      verifyConsignmentStatus(consignmentId, Statuses.ASSIGNED.consignmentStatus);

      if (!tripId.equals("null")) {
        boolean isSameRiderAssigned =
                mySqlDMSHelper
                        .queryDb(String.format(DBQueries.isRiderAssignedToTrip, tripId, riderId))
                        .getJSONObject(0)
                        .getInt("count")
                        > 0;
        token =
                isSameRiderAssigned
                        ? token
                        : getRiderTokenFromConsignmentId(consignmentId, ASSIGNED.consignmentStatus);
      }
      logger.info("mark consignment delivered and rider in-store");

      updateConsignmentStatus(consignmentId, Statuses.DELIVERED.consignmentStatus, token);
      markRiderInStore(riderId, storeId, Boolean.FALSE);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return token;
  }

  /**
   * checkIn existing rider
   *
   * @param riderId
   * @param storeId
   * @param authToken
   * @author Manisha_Kumari
   */
  @SneakyThrows
  public void checkInRider(String riderId, String storeId, String... authToken) {
    String token = "";
    RiderHomePageApiHelper riderHomePageApiHelper = new RiderHomePageApiHelper();

    if (authToken.length > 0) {
      token += authToken[0];
    } else {
      logger.info("generating auth token for rider :: " + riderId);
      String mobileNumber =
              new MySqlZeptoBackendServiceHelper()
                      .queryDb(String.format(DBQueries.getRiderMobileNo, riderId))
                      .getJSONObject(0)
                      .getString("mobile_number");
      token =
              new RiderHelper().generateRiderAuthToken(environment.getReqSpecBuilder(), mobileNumber);
    }

    logger.info("check in existing rider");
    Response riderStatusResponse =
            riderHomePageApiHelper.getCheckInCheckOutStatusApi(
                    environment.getReqSpecBuilder(), Boolean.FALSE, token);
    Assert.assertTrue(riderStatusResponse.getStatusCode() == HttpStatus.SC_OK);
    RiderCheckInOutStatusResponse riderCheckInOutStatusResponse =
            riderStatusResponse.getBody().as(RiderCheckInOutStatusResponse.class);
    String type = riderCheckInOutStatusResponse.getData().getType();
    RiderCheckInCheckOutRequest riderCheckInStatusRequest =
            new RiderHomePageHelper()
                    .generateRiderCheckInCheckOutPayload(
                            environment
                                    .getGlobalTestData()
                                    .getDouble("riderHomePageCheckInForAutomationStore.lat"),
                            environment
                                    .getGlobalTestData()
                                    .getDouble("riderHomePageCheckInForAutomationStore.long"),
                            type.equals("check_in") ? "check_out" : "check_in");
    Response checkInResponse =
            riderHomePageApiHelper.calRiderCheckInCheckOutApi(
                    environment.getReqSpecBuilder(), Boolean.TRUE, riderCheckInStatusRequest, token);
    Assert.assertTrue(checkInResponse.getStatusCode() == HttpStatus.SC_OK);
    // clear cash for existing rider
    if (checkInResponse
            .getBody()
            .jsonPath()
            .get("data.message")
            .toString()
            .equals("Please clear your cash balance to check-in.")) {
      Response riderBalanceResponse =
              codOrderApiHelper.getRiderCashBalance(environment.getReqSpecBuilder(), token, false);
      int cashBalance = riderBalanceResponse.getBody().jsonPath().get("data.cashBalance");
      Response getNearByRetailersResponse =
              codOrderApiHelper.getNearByRetailers(
                      environment.getReqSpecBuilder(),
                      token,
                      "72.83035939678881",
                      "19.00104835264737",
                      cashBalance);
      // TODO: 12/04/23
      //    Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response code of api is
      // not " + HttpStatus.SC_OK + ", it is :" + response.statusCode());

      NearByRetailer nearByRetailer;
      if (getNearByRetailersResponse.getStatusCode() == 500) {
        nearByRetailer = NearByRetailer.builder().build();
      } else {
        BaseResponse getNearByRetailerResponse =
                MapperUtils.deserialize(getNearByRetailersResponse.asString(), BaseResponse.class);
        NearByRetailers nearByRetailers =
                new ObjectMapper()
                        .readValue(
                                RequestMapper.mapPojoObjectAndGetString(getNearByRetailerResponse.getData()),
                                NearByRetailers.class);
        nearByRetailer = nearByRetailers.getNearByRetailers().get(0);
      }
      String paymentMethod = "RETAIL_PAYMENT";
      // Get request body of rider Payment Settlement Request
      RiderPaymentSettlementRequest riderPaymentSettlementRequest =
              new CodOrderHelper()
                      .createRiderPaymentSettlementPayload(
                              cashBalance,
                              paymentMethod,
                              "AIRTEL_MONEY",
                              environment.getGlobalTestData().getString("defaultSellerInfo.sellerId"),
                              nearByRetailer);

      // Settle unsettled order
      Response response =
              codOrderApiHelper.riderPaymentSettlement(
                      environment.getReqSpecBuilder(), token, riderPaymentSettlementRequest, true);
      Assert.assertEquals(
              response.getStatusCode(), HttpStatus.SC_OK, "Response code verification got failed.");

      BaseResponse riderPaymentSettlementResponse = response.as(BaseResponse.class);
      PaymentSettlementResponse actualRiderPaymentSettlementResponse =
              new ObjectMapper()
                      .readValue(
                              RequestMapper.mapPojoObjectAndGetString(riderPaymentSettlementResponse.getData()),
                              PaymentSettlementResponse.class);

      // Getting transaction ref no
      String transactionRefNo =
              actualRiderPaymentSettlementResponse.getRetailStore().getStoreTransactionReference();
      String transactionId = actualRiderPaymentSettlementResponse.getTransactionId();

      // Payment call back
      response =
              codOrderApiHelper.riderSettlePaymentCallback(
                      environment.getReqSpecBuilder(), transactionRefNo, transactionId, cashBalance / 100);
      Assert.assertEquals(
              response.getStatusCode(), HttpStatus.SC_OK, "Response code verification got failed.");
      type = "check_in";
    }
    if (type.equals("check_in")) {
      riderCheckInStatusRequest =
              new RiderHomePageHelper()
                      .generateRiderCheckInCheckOutPayload(
                              environment
                                      .getGlobalTestData()
                                      .getDouble("riderHomePageCheckInForAutomationStore.lat"),
                              environment
                                      .getGlobalTestData()
                                      .getDouble("riderHomePageCheckInForAutomationStore.long"),
                              "check_in");
      checkInResponse =
              riderHomePageApiHelper.calRiderCheckInCheckOutApi(
                      environment.getReqSpecBuilder(), Boolean.TRUE, riderCheckInStatusRequest, token);
      Assert.assertTrue(checkInResponse.getStatusCode() == HttpStatus.SC_OK);
    }
    logger.info("Waiting for 100ms before marking rider in store");
    Thread.sleep(100);
    markRiderInStore(riderId, storeId, Boolean.FALSE);
  }

  /**
   * Map order status to message
   *
   * @param orderStatus
   * @return
   * @author Manisha_Kumari
   */
  public String mapStatusToStatusMessage(String orderStatus) {
    Statuses status = Statuses.valueOf(orderStatus);
    switch (status) {
      case READY_TO_ASSIGN:
        return "Ready To Assign";
      case CREATED:
        return "Order Created";
      case ASSIGNED:
        return "Order Assigned";
      case INITIATED:
        return "Order Initiated";
      case PICKED_UP:
        return "Order Picked_UP";
      case DISPATCHED:
        return "Order Dispatched";
      case STARTED:
        return "Delivery Started";
      case ARRIVED:
        return "Order Arrived";
      case AUTO_ARRIVED:
        return "Order Auto Arrived";
      case DELIVERED:
        return "Order Delivered";
      case CANCELLED:
        return "Order Cancelled";
      case RETURN_TO_ORIGIN:
        return "Return To Origin";
      case RETURNED:
        return "Order Returned At Store";
      case UNASSIGNED:
        return "Order Unassigned";
      case RETURNED_TO_STORE:
        return "Returned To Store";
    }
    return "";
  }

  /**
   * Mark rider status in store
   *
   * @param riderId
   * @param storeId
   * @throws SQLException
   * @author Manisha.Kumari
   */
  public void markRiderInStore(String riderId, String storeId, boolean schemaValidation)
          throws SQLException {
    JSONArray riderDeliveryInfo =
            mySqlDMSHelper.queryDb(
                    String.format(DBQueries.getRiderDeliveryStatusByRiderId, String.join("','", riderId)));
    if (riderDeliveryInfo.length() > 0) {
      String status = riderDeliveryInfo.getJSONObject(0).getString("status");
      if (!status.equals("IN_STORE")) {
        UpdateRiderDeliveryStatusRequest updateRiderDeliveryStatusRequest =
                UpdateRiderDeliveryStatusRequest.builder()
                        .riderId(riderId)
                        .storeId(storeId)
                        .status("IN_STORE")
                        .build();
        dmsApiHelper.updateRiderDeliveryStatus(
                environment.getReqSpecBuilder(), schemaValidation, updateRiderDeliveryStatusRequest);
      }
    }
  }

  /**
   * Method assigns provide rider to tge consignment
   *
   * @param createConsignmentResponse
   * @param riderId
   * @author Ajay_Mishra
   */
  public void assignRiderManuallyToConsignment(
          JSONObject createConsignmentResponse, String riderId) {
    String storeId = createConsignmentResponse.get("storeId").toString();
    AssignRiderRequest assignRiderRequest =
            AssignRiderRequest.builder()
                    .riderId(riderId)
                    .storeId(storeId)
                    .orderCodes(
                            new ArrayList<>() {
                              {
                                add(createConsignmentResponse.get("orderCode").toString());
                              }
                            })
                    .build();
    logger.info(
            "Polling for every 3 sec till 15 secs before calling manual assign rider api for :: "
                    + assignRiderRequest.getOrderCodes());
    Awaitility.await()
            .atMost(22, TimeUnit.SECONDS)
            .pollInterval(3, TimeUnit.SECONDS)
            .until(
                    () -> {
                      JSONArray getRiderStatus =
                              new MySqlDMSHelper()
                                      .queryDb(String.format(DBQueries.getRiderDeliveryStatusByRiderId, riderId));
                      logger.info("Rider delivery data from db is :: " + getRiderStatus);
                      JSONObject riderStatusFields =
                              getRiderStatus.length() > 0 ? getRiderStatus.getJSONObject(0) : new JSONObject();
                      // Added check for store id as well
                      return riderStatusFields.has("status")
                              && riderStatusFields.getString("status").equals("IN_STORE")
                              && riderStatusFields.has("store_id")
                              && riderStatusFields.get("store_id").toString().equals(storeId);
                    });
    Response response =
            dmsApiHelper.assignRider(environment.getReqSpecBuilder(), assignRiderRequest);

    // retry using polling if bad connection error is received for 1 min
    if (response.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
      logger.info(
              "Polling manual consignment api every 20 sec for 1.5 minute if response code is not 200 for  :: "
                      + assignRiderRequest.getOrderCodes());
      Awaitility.await("Manual assignment api is failed with status code other than 200")
              .atMost(90, TimeUnit.SECONDS)
              .pollInterval(20, TimeUnit.SECONDS)
              .until(
                      () -> {
                        Response assignRiderResponse =
                                dmsApiHelper.assignRider(environment.getReqSpecBuilder(), assignRiderRequest);
                        ExtentReportUtil.logResponseDetailsInLogAndReport(assignRiderResponse);
                        return assignRiderResponse.getStatusCode() == HttpStatus.SC_OK
                                || assignRiderResponse.getStatusCode() != HttpStatus.SC_INTERNAL_SERVER_ERROR;
                      });
    }
  }

  /**
   * validate get active trip info response
   *
   * @param getActiveTripInfoResponse
   * @param getRiderTripInfoRequest
   * @param token
   * @author Manisha.Kumari
   */
  public void validateGetActiveTripInfoByRiderResponse(
          Response getActiveTripInfoResponse,
          GetRiderTripInfoRequest getRiderTripInfoRequest,
          String token,
          Boolean newTripAssigned) {
    SoftAssert softAssert = new SoftAssert();
    GetRiderTripInfoResponse getRiderTripResponse = null;
    try {
      if (token.matches(GeneralConstants.TOKEN_REGEX_WITHOUT_BEARER)) {
        BaseResponse baseResponse =
                MapperUtils.deserialize(getActiveTripInfoResponse.asString(), BaseResponse.class);
        JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
        getRiderTripResponse = mapper.readValue(data.toString(), GetRiderTripInfoResponse.class);
      }
      if (token == null || !token.matches(GeneralConstants.TOKEN_REGEX_WITHOUT_BEARER)) {
        softAssert.assertEquals(
                getActiveTripInfoResponse.getStatusCode(),
                HttpStatus.SC_UNAUTHORIZED,
                "Expected Invalid token error");
      } else {
        softAssert.assertEquals(getActiveTripInfoResponse.getStatusCode(), HttpStatus.SC_OK);
        String riderId = getRiderTripInfoRequest.getRiderId();
        JSONArray activeTrips =
                mySqlDMSHelper.queryDb(String.format(DBQueries.getActiveTripsByRiderId, riderId));
        JSONArray riderDeliveryStatus =
                mySqlDMSHelper.queryDb(
                        String.format(DBQueries.getRiderDeliveryStatusByRiderId, riderId));
        String status =
                riderDeliveryStatus.length() > 0
                        ? riderDeliveryStatus.getJSONObject(0).getString("status")
                        : "";
        boolean isRiderCheckedIn =
                !status.equals(RiderDeliveryStatus.RIDER_DELIVERY_OFFLINE.status);
        GetRiderTripInfoResponse expectedTripInfo = GetRiderTripInfoResponse.builder().build();
        if (activeTrips.length() > 0) {
          String tripId = activeTrips.getJSONObject(activeTrips.length() - 1).get("id").toString();
          expectedTripInfo.setTripId(tripId);
          expectedTripInfo.setOnActiveDelivery(true);
          NewTripDetails newTripDetails =
                  NewTripDetails.builder().newTripAssigned(newTripAssigned).build();
          expectedTripInfo.setNewTripDetails(newTripDetails);
        } else {
          expectedTripInfo.setTripId(null);
        }
        boolean insideGeoFence =
                !(getRiderTripInfoRequest.latitude.equals(0.0)
                        || getRiderTripInfoRequest.longitude.equals(0.0));
        expectedTripInfo.setInsideGeofence(insideGeoFence);
        expectedTripInfo.setRiderCheckedIn(isRiderCheckedIn);
        softAssert.assertEquals(
                getRiderTripResponse, expectedTripInfo, "Mismatch in expected and actual response ");
      }
      softAssert.assertAll();
    } catch (JsonProcessingException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Create diff types of Order State data
   *
   * @param status
   * @return consignmentId
   * @author deepak_kumar
   */
  public String createConsignmentByStatus(String status) {
    JSONObject createConsignmentResponse = createConsignment();
    String consignmentId = createConsignmentResponse.get("id").toString();
    if (status.equals(Statuses.CREATED.consignmentStatus)) {
      return consignmentId;
    }
    Map<String, String> riderInfo =
            checkInCheckOutHelper.createRiderUploadRiderWorkPlanDoCheckIn("PART_TIME", "BIKE", true);
    String newRiderId = riderInfo.get("riderId");
    String newRiderToken = riderInfo.get("token");
    assignRiderManuallyToConsignment(createConsignmentResponse, newRiderId);
    verifyConsignmentStatus(consignmentId, Statuses.ASSIGNED.consignmentStatus);
    if (status.equals(Statuses.ASSIGNED.consignmentStatus)) {
      return consignmentId;
    }
    updateConsignmentStatus(consignmentId, status, newRiderToken);
    return consignmentId;
  }

  /**
   * Method create assigned order and return order details
   *
   * @return orderInfo
   * @author deepak_kumar
   */
  public Map<String, String> createConsignmentAssigned(String... isBatched) {
    String batched = null;
    if (isBatched.length > 0) {
      batched = isBatched[0];
    }
    JSONObject createConsignmentResponse = createConsignment(batched);
    String consignmentId = createConsignmentResponse.get("id").toString();
    String orderCode = createConsignmentResponse.get("orderCode").toString();
    String orderId = createConsignmentResponse.get("orderId").toString();
    Map<String, String> riderInfo =
            checkInCheckOutHelper.createRiderUploadRiderWorkPlanDoCheckIn("PART_TIME", "BIKE", true);
    String newRiderId = riderInfo.get("riderId");
    String newRiderToken = riderInfo.get("token");
    String riderMobile = riderInfo.get("riderMobile");
    assignRiderManuallyToConsignment(createConsignmentResponse, newRiderId);
    JSONObject data = verifyConsignmentStatus(consignmentId, Statuses.ASSIGNED.consignmentStatus);
    JSONObject deliveries = data.getJSONArray("deliveries").getJSONObject(0);
    String deliveryId = deliveries.get("id").toString();
    String tripId = deliveries.get("tripId").toString();
    Map<String, String> orderInfo = new HashMap<>();
    orderInfo.put("consignmentId", consignmentId);
    orderInfo.put("riderToken", newRiderToken);
    orderInfo.put("deliveryId", deliveryId);
    orderInfo.put("tripId", tripId);
    orderInfo.put("riderId", newRiderId);
    orderInfo.put("riderMobile", riderMobile);
    orderInfo.put("orderCode", orderCode);
    orderInfo.put("orderId", orderId);
    orderInfo.put("riderUserName", riderInfo.get("riderUserName"));
    orderInfo.put("riderFullName", riderInfo.get("riderFullName"));
    return orderInfo;
  }

  /**
   * validate store linked with user email
   *
   * @param emailId
   * @param expectedStoreIds
   * @author Manisha.Kumari
   */
  public void validateStoreLinkedWithUser(String emailId, String expectedStoreIds) {
    try {
      JSONArray storeIds =
              new MySqlZeptoBackendServiceHelper()
                      .queryDb(String.format(DBQueries.getStoresByEmailRoleId, emailId));
      String[] expectedStores = expectedStoreIds.split(",");
      logger.info("store ids from db query :: " + storeIds);
      for (String store : expectedStores) {
        Assert.assertTrue(storeIds.toString().contains(store), "Store is not associated properly");
      }
      Assert.assertTrue(storeIds.length() >= 1, "Mismatch in associated storeIds");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Method used to assign rider to multiple consignment
   *
   * @param riderId
   * @param assignRiderRequest
   * @author Deepak_Kumar
   */
  public void assignRiderManually(String riderId, AssignRiderRequest assignRiderRequest) {
    logger.info("Waiting for 5 sec before calling manual assign rider api");
    Awaitility.await()
            .atMost(15, TimeUnit.SECONDS)
            .pollInterval(2, TimeUnit.SECONDS)
            .until(
                    () -> {
                      JSONArray getRiderStatus =
                              new MySqlDMSHelper()
                                      .queryDb(String.format(DBQueries.getRiderDeliveryStatusByRiderId, riderId));
                      JSONObject riderStatusFields =
                              getRiderStatus.length() > 0 ? getRiderStatus.getJSONObject(0) : new JSONObject();

                      // Added check for store id as well
                      return riderStatusFields.has("status")
                              && riderStatusFields.getString("status").equals("IN_STORE")
                              && riderStatusFields.has("store_id")
                              && riderStatusFields
                              .get("store_id")
                              .toString()
                              .equals(assignRiderRequest.getStoreId());
                    });
    Response response =
            dmsApiHelper.assignRider(environment.getReqSpecBuilder(), assignRiderRequest);

    // retry using polling if bad connection error is received for 1 min
    if (response.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
      logger.info(
              "Polling manual consignment api every 20 sec for 1.5 minute if response code is 500");
      Awaitility.await("Manual assignment api is failed with status code other than 200")
              .atMost(90, TimeUnit.SECONDS)
              .pollInterval(20, TimeUnit.SECONDS)
              .until(
                      () -> {
                        Response assignRiderResponse =
                                dmsApiHelper.assignRider(environment.getReqSpecBuilder(), assignRiderRequest);
                        ExtentReportUtil.logResponseDetailsInLogAndReport(assignRiderResponse);
                        return assignRiderResponse.getStatusCode() == HttpStatus.SC_OK
                                || assignRiderResponse.getStatusCode() != HttpStatus.SC_INTERNAL_SERVER_ERROR;
                      });
    }
  }

  /**
   * Method used to create multiple batched orders
   *
   * @return
   * @author Deepak_Kumar
   */
  public HashMap<String, JSONObject> createBatchedOrders()
          throws DocumentException, IOException, InterruptedException {
    Response response = null;
    // Create order1
    JSONObject firstConsignment = createConsignment("true");
    String firstConsignmentId = firstConsignment.get("id").toString();
    String firstOrderCode = firstConsignment.get("orderCode").toString();
    String firstStoreId = firstConsignment.get("storeId").toString();
    String firstDeliveryId =
            firstConsignment.getJSONArray("deliveries").getJSONObject(0).getString("id");

    // Create order2
    JSONObject secondConsignment = createConsignment("true");
    String secondConsignmentId = secondConsignment.get("id").toString();
    String secondOrderCode = secondConsignment.get("orderCode").toString();

    // Assign batch order to rider
    Map<String, String> riderInfo =
            checkInCheckOutHelper.createRiderUploadRiderWorkPlanDoCheckIn("PART_TIME", "BIKE", true);
    String riderId = riderInfo.get("riderId");
    AssignRiderRequest assignRiderRequest =
            AssignRiderRequest.builder()
                    .riderId(riderId)
                    .storeId(firstStoreId)
                    .orderCodes(new ArrayList<>(Arrays.asList(firstOrderCode, secondOrderCode)))
                    .build();
    assignRiderManually(riderId, assignRiderRequest);
    Thread.sleep(100);
    logger.info("Waiting for 100ms before calling get consignment");

    // get trip Id from consignment response
    response =
            dmsApiHelper.getConsignmentByConsignmentId(
                    environment.getReqSpecBuilder(), firstConsignmentId, false);
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    JSONObject getConsignmentResponseFirst =
            new JSONObject(MapperUtils.serialize(baseResponse.getData()));
    String tripId =
            getConsignmentResponseFirst.getJSONArray("deliveries").getJSONObject(0).getString("tripId");

    response =
            dmsApiHelper.getConsignmentByConsignmentId(
                    environment.getReqSpecBuilder(), secondConsignmentId, false);
    baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    JSONObject getConsignmentResponseSecond =
            new JSONObject(MapperUtils.serialize(baseResponse.getData()));

    // Get trip Info details
    response =
            dmsApiHelper.getTripInfoById(
                    environment.getReqSpecBuilder(),
                    getRiderTokenFromConsignmentId(firstConsignmentId),
                    tripId);
    JSONObject tripInfoObject = new JSONObject(response.asString());
    JSONArray deliveries = tripInfoObject.getJSONObject("data").getJSONArray("deliveries");
    String firstDelivery = deliveries.getJSONObject(0).getString("id");

    HashMap<String, JSONObject> orderInfoSequence = new HashMap<>();
    if (firstDelivery.equals(firstDeliveryId)) {
      orderInfoSequence.put("order1", getConsignmentResponseFirst);
      orderInfoSequence.put("order2", getConsignmentResponseSecond);
    } else {
      orderInfoSequence.put("order1", getConsignmentResponseSecond);
      orderInfoSequence.put("order2", getConsignmentResponseFirst);
    }
    return orderInfoSequence;
  }

  /**
   * Update updated at to 5 mins before to change rider
   *
   * @param updatedAt
   * @param deliveryId
   * @param time
   * @author Manisha_Kumari
   */
  @SneakyThrows
  public void getUpdatedAtAndAddMinutes(String updatedAt, String deliveryId, int time) {
    updatedAt = updatedAt.substring(0, updatedAt.indexOf(".") + 3);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
    Date date = sdf.parse(updatedAt);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, time);
    date = calendar.getTime();
    String updatedAtDate = sdf.format(date);
    logger.info("Updating date in delivery  :: " + updatedAtDate);
    mySqlDMSHelper.executeInsertUpdateQuery(
            String.format(
                    DBQueries.updateDeliveryUpdatedAtTo5MinutesBefore, updatedAtDate, deliveryId));
    Thread.sleep(100);
  }

  /**
   * Get trip, consignment and delivery info for given consignment
   *
   * @param consignmentId
   * @return
   * @author Manisha_Kumari
   */
  public JSONObject getConsignmentTripDeliveryDetailsById(String consignmentId) {
    try {
      JSONArray consignmentInfoList =
              mySqlDMSHelper.queryDb(
                      String.format(
                              DBQueries.getDeliveryTripConsignmentInfoByConsignmentId, consignmentId));
      JSONObject consignmentInfo =
              consignmentInfoList.length() > 0
                      ? (JSONObject) consignmentInfoList.getJSONObject(0)
                      : new JSONObject();
      logger.info(
              "Received details from db for consignmentId :: "
                      + consignmentId
                      + " with info :: "
                      + consignmentInfo);
      return consignmentInfo;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get consignment and delivery info for given consignment
   *
   * @param consignmentId
   * @return
   * @author Manisha_Kumari
   */
  public JSONObject getConsignmentDeliveryDetailsById(String consignmentId) {
    try {
      JSONArray consignmentInfoList =
              mySqlDMSHelper.queryDb(
                      String.format(DBQueries.getDeliveryConsignmentInfoByConsignmentId, consignmentId));
      JSONObject consignmentInfo =
              consignmentInfoList.length() > 0
                      ? (JSONObject) consignmentInfoList.get(0)
                      : new JSONObject();
      logger.info(
              "Received details from db for consignmentId :: "
                      + consignmentId
                      + " with info :: "
                      + consignmentInfo);
      return consignmentInfo;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Create consignment with specific order information for automation store
   *
   * @param orderInfo
   * @author Deepak_kumar
   */
  public JSONObject createConsignmentByOrderInfo(Map<String, Object> orderInfo) {
    List<ItemDetails> itemDetails = new ArrayList<>();
    itemDetails.add(ItemDetails.builder().build());
    boolean orderEligibleForShadowFax =
            (boolean) orderInfo.getOrDefault("is_3pl_eligible", Boolean.FALSE);
    String paymentMode = (String) orderInfo.getOrDefault("paymentMode", "COD");
    String sellerId =
            orderInfo.getOrDefault("sellerId", "71e067e6-9a70-4157-8f5a-30cc5c62e49d").toString();
    String merchantName =
            orderInfo.getOrDefault("merchantName", "Commodum Groceries Private Limited").toString();
    double lat =
            Double.parseDouble(
                    orderInfo
                            .getOrDefault(
                                    "lat", environment.getGlobalTestData().getDouble("createConsignmentData.lat"))
                            .toString());
    double longitude =
            Double.parseDouble(
                    orderInfo
                            .getOrDefault(
                                    "long", environment.getGlobalTestData().getDouble("createConsignmentData.long"))
                            .toString());
    boolean isBatchable = (boolean) orderInfo.getOrDefault("isBatched", false);

    OrderDetails orderDetails =
            OrderDetails.builder()
                    .itemDetails(itemDetails)
                    .orderCode(new GlobalUtil().getRandomOrderCode())
                    .sellerId(sellerId)
                    .merchantName(merchantName)
                    .expectedDeliveryTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .startTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .orderDate(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .is3plEligible(orderEligibleForShadowFax)
                    .isBatchable(isBatchable)
                    .build();

    CreateConsignmentRequest createConsignmentRequest =
            CreateConsignmentRequest.builder()
                    .orderId(UUID.randomUUID().toString())
                    .orderDetails(orderDetails)
                    .loadTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .paymentMode(paymentMode)
                    .storeId(
                            (String) orderInfo.getOrDefault("storeId", "d48222ae-a642-4aeb-9cee-e2829ebaa5e7"))
                    .build();
    createConsignmentRequest.getCustomerDetails().setLatitude(lat);
    createConsignmentRequest.getCustomerDetails().setLongitude(longitude);
    Response response =
            new DMSApiHelper()
                    .createConsignment(
                            environment.getReqSpecBuilder(), Boolean.FALSE, createConsignmentRequest);
    Assert.assertTrue(response.getStatusCode() == HttpStatus.SC_CREATED);
    BaseResponse consignmentResponse =
            MapperUtils.deserialize(response.asString(), BaseResponse.class);
    JSONObject createConsignmentResponse =
            new JSONObject(RequestMapper.mapPojoObjectAndGetString(consignmentResponse.getData()));
    return createConsignmentResponse;
  }

  /**
   * get batched orders with specified store
   *
   * @param orderInfo
   * @return
   * @author Manisha_Kumari
   */
  @SneakyThrows
  public List<String> createBatchedOrdersByOrderInfo(Map<String, Object> orderInfo) {
    List<String> consignmentIds = new ArrayList<>();
    String[] additionalInfo = new String[4];

    JSONObject consignmentInfo = createConsignmentByOrderInfo(orderInfo);
    String firstOrderCode = consignmentInfo.getString("orderCode");
    consignmentIds.add(consignmentInfo.get("id").toString());
    JSONObject secondConsignmentInfo = createConsignmentByOrderInfo(orderInfo);
    String secondOrderCode = secondConsignmentInfo.getString("orderCode");
    consignmentIds.add(secondConsignmentInfo.get("id").toString());

    additionalInfo[0] = orderInfo.get("storeId").toString();
    additionalInfo[1] = "1";
    additionalInfo[2] = orderInfo.get("lat").toString();
    additionalInfo[3] = orderInfo.get("long").toString();

    // Assign batch order to rider
    Map<String, String> riderInfo =
            checkInCheckOutHelper.createRiderUploadRiderWorkPlanDoCheckIn(
                    "PART_TIME", "BIKE", true, additionalInfo);
    String riderId = riderInfo.get("riderId");
    AssignRiderRequest assignRiderRequest =
            AssignRiderRequest.builder()
                    .riderId(riderId)
                    .storeId(orderInfo.get("storeId").toString())
                    .orderCodes(new ArrayList<>(Arrays.asList(firstOrderCode, secondOrderCode)))
                    .build();
    assignRiderManually(riderId, assignRiderRequest);

    Thread.sleep(100);
    logger.info("Waiting for 100ms before calling get consignment");
    return consignmentIds;
  }

  /**
   * get consignment from db for store with/without bincode for set of statuses based on params
   *
   * @param storeId
   * @param binCode
   * @param time
   * @param validStatuses
   * @return
   * @author Manisha_Kumari
   */
  @SneakyThrows
  public List<String> getConsignmentsWithHuDetailsAsNullOrProvidedBinCode(
          String storeId, String binCode, String time, String validStatuses) {
    List<String> huList = new ArrayList<>();
    JSONArray dbDetails =
            mySqlDMSHelper.queryDb(
                    String.format(
                            DBQueries.getEligibleConsignmentDetailsForBulkUpdate,
                            storeId,
                            validStatuses,
                            time));
    if (dbDetails.length() > 0) {
      for (int i = 0; i < dbDetails.length(); i++) {
        JSONObject consignmentDetails = dbDetails.getJSONObject(i);
        // getting all consignments with huDetails as null
        String orderDetailsJson = consignmentDetails.get("order_details").toString();
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDetailsResponse orderDetailsResponse =
                objectMapper.readValue(orderDetailsJson, OrderDetailsResponse.class);
        if (orderDetailsResponse.getHuDetails() != null) {
          if (binCode == null) {
            continue;
          }
          HuDetails huDetails =
                  mapper.convertValue(orderDetailsResponse.getHuDetails(), HuDetails.class);
          if (huDetails.getBinCode().equals(binCode)) {
            huList.add(consignmentDetails.get("id").toString());
          }
        } else {
          huList.add(consignmentDetails.get("id").toString());
        }
      }
      return huList;
    } else {
      return huList;
    }
  }

  /**
   * This method is used to verify order delivery message as In Time/Delayed wrt ETA of order
   *
   * @param orderCode
   * @param storeId
   * @throws JsonProcessingException
   * @author Deepak_Kumar
   */
  @SneakyThrows
  public void verifyConsignmentDeliveryMessage(String orderCode, String storeId) {
    // Get order details from dms dashboard
    GetConsignmentByStoreIdAndStatusRequest requestMessage =
            new GetConsignmentByStoreIdAndStatusRequest().builder().build();
    requestMessage.setOrderCode(orderCode);
    response =
            new DMSApiHelper()
                    .getConsignmentByStoreIdAndStatus(
                            new Environment().getReqSpecBuilder(), storeId, requestMessage);
    Assert.assertTrue(
            response.getStatusCode() == HttpStatus.SC_OK,
            "Response status code is not " + HttpStatus.SC_OK + ", it is " + response.getStatusCode());
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper mapper = new ObjectMapper();
    JSONObject dmsDashboardOrderDetails =
            new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    JSONObject orderData = dmsDashboardOrderDetails.getJSONArray("orderList").getJSONObject(0);
    String deliveryMessage =
            orderData.getJSONObject("deliveryTimeView").get("deliveryMessage").toString();
    String loadTime = orderData.get("loadTime").toString();
    int etaInSec = (int) orderData.getJSONObject("orderDetails").get("etaInSec");
    String timeStampArrival = null;
    JSONArray statusChangeLog = orderData.getJSONArray("statusChangeLog");

    // Iterate to get auto-arrival/arrival time stamp
    for (int i = 0; i < statusChangeLog.length(); i++) {
      JSONObject orderStatusDetail = statusChangeLog.getJSONObject(i);
      if (orderStatusDetail.get("status").equals(AUTO_ARRIVED.consignmentStatus)) {
        timeStampArrival = orderStatusDetail.get("timeStamp").toString();
        break;
      }
      if (orderStatusDetail.get("status").equals(ARRIVED.consignmentStatus)) {
        timeStampArrival = orderStatusDetail.get("timeStamp").toString();
      }
    }
    loadTime = loadTime.substring(0, loadTime.indexOf('Z')).replace("T", " ");
    timeStampArrival =
            timeStampArrival.substring(0, timeStampArrival.lastIndexOf('.')).replace("T", " ");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d1 = sdf.parse(loadTime);
    Date d2 = sdf.parse(timeStampArrival);
    Date eta = DateUtils.addSeconds(d1, etaInSec);
    logger.info("LoadTime:" + d1);
    logger.info("ArrivalTime:" + d2);
    logger.info("ETATime:" + eta);
    logger.info("etaInSec:" + etaInSec);

    // Calculate time diff between arrival and start
    long difference_In_Time = (d2.getTime() - d1.getTime()) / 1000;
    logger.info("TimeDiff:" + difference_In_Time);
    long deliveryTime;
    String deliveryMessageExpected;

    // Calculate deliveryTime
    if (difference_In_Time > etaInSec + 59) {
      // order get delayed
      difference_In_Time = (d2.getTime() - eta.getTime()) / 1000;
      logger.info("DelayedTimeDiff:" + difference_In_Time);
      deliveryTime = difference_In_Time / 60;
      deliveryMessageExpected = "Delayed by " + deliveryTime + " min";
    } else {
      // order is on time
      deliveryTime = difference_In_Time / 60;
      deliveryMessageExpected = "In " + deliveryTime + " min";
    }
    logger.info("DeliveryTime:" + deliveryTime);
    Assert.assertEquals(
            deliveryMessage,
            deliveryMessageExpected,
            "Delivery message in api response is not as expected");
  }

  /**
   * Create diff types of Order State data by order info
   *
   * @param status
   * @return consignmentId
   * @author Deepak_kumar
   */
  public Map<String, String> createConsignmentByConsignmentInfoAndStatus(
          Map<String, Object> consignmentInfo, String status) {

    JSONObject createConsignmentResponse = createConsignmentByConsignmentInfo(consignmentInfo);
    String consignmentId = createConsignmentResponse.get("id").toString();
    String orderCode = createConsignmentResponse.get("orderCode").toString();

    Map<String, String> orderInfo = new HashMap<>();
    orderInfo.put("consignmentId", consignmentId);
    orderInfo.put("orderCode", orderCode);

    if (status.equals(Statuses.CREATED.consignmentStatus)) {
      return orderInfo;
    }
    Map<String, String> riderInfo =
            checkInCheckOutHelper.createRiderUploadRiderWorkPlanDoCheckIn("FULL_TIME", "BIKE", true);
    String newRiderId = riderInfo.get("riderId");
    String newRiderToken = riderInfo.get("token");
    assignRiderManuallyToConsignment(createConsignmentResponse, newRiderId);
    verifyConsignmentStatus(consignmentId, Statuses.ASSIGNED.consignmentStatus);
    if (status.equals(Statuses.ASSIGNED.consignmentStatus)) {
      return orderInfo;
    }
    updateConsignmentStatus(consignmentId, status, newRiderToken);
    return orderInfo;
  }

  /**
   * Create consignment by consignment Info for automation store
   *
   * @author Deepak_Kumar
   */
  public JSONObject createConsignmentByConsignmentInfo(Map<String, Object> consignmentInfo) {
    ItemDetails itemDetail = ItemDetails.builder().build();
    int replacementQuantity =
            (consignmentInfo.containsKey("replacement_qty"))
                    ? (int) consignmentInfo.get("replacement_qty")
                    : itemDetail.replacementQty;
    int returnQuantity =
            (consignmentInfo.containsKey("return_qty"))
                    ? (int) consignmentInfo.get("return_qty")
                    : itemDetail.returnQty;
    String quantity =
            (consignmentInfo.containsKey("quantity"))
                    ? (String) consignmentInfo.get("quantity")
                    : itemDetail.quantity;
    String paymentMode =
            (consignmentInfo.containsKey("payment_mode"))
                    ? (String) consignmentInfo.get("payment_mode")
                    : CreateConsignmentRequest.builder().build().getPaymentMode();
    List<String> reason_codes = new ArrayList<>();
    if (consignmentInfo.containsKey("reason_code")) {
      reason_codes.add((String) consignmentInfo.get("reason_code"));
    }
    itemDetail.setReplacementQty(replacementQuantity);
    itemDetail.setReturnQty(returnQuantity);
    itemDetail.setQuantity(quantity);
    itemDetail.setReason_codes(reason_codes);

    List<ItemDetails> itemDetails = new ArrayList<>();
    itemDetails.add(itemDetail);
    OrderDetails orderDetailsDefault = new OrderDetails();
    int etaInSec =
            (consignmentInfo.containsKey("etaInSec"))
                    ? (int) consignmentInfo.get("etaInSec")
                    : orderDetailsDefault.getEtaInSec();
    boolean batchOrder =
            (consignmentInfo.containsKey("isBatchable"))
                    ? (boolean) consignmentInfo.get("isBatchable")
                    : Boolean.FALSE;
    OrderDetails orderDetails =
            OrderDetails.builder()
                    .itemDetails(itemDetails)
                    .orderCode(new GlobalUtil().getRandomOrderCode())
                    .expectedDeliveryTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .startTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .orderDate(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .isBatchable(batchOrder)
                    .etaInSec(etaInSec)
                    .build();

    CreateConsignmentRequest createConsignmentRequest =
            CreateConsignmentRequest.builder()
                    .paymentMode(paymentMode)
                    .orderId(UUID.randomUUID().toString())
                    .orderDetails(orderDetails)
                    .loadTime(
                            GlobalUtil.getDateInUTCTimeZone(
                                    GlobalUtil.setDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT),
                                    GeneralConstants.DATE_TIME_FORMAT,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .build();
    createConsignmentRequest
            .getCustomerDetails()
            .setLatitude(environment.getGlobalTestData().getDouble("createConsignmentData.lat"));
    createConsignmentRequest
            .getCustomerDetails()
            .setLongitude(environment.getGlobalTestData().getDouble("createConsignmentData.long"));
    Response response =
            new DMSApiHelper()
                    .createConsignment(
                            environment.getReqSpecBuilder(), Boolean.FALSE, createConsignmentRequest);
    Assert.assertTrue(response.getStatusCode() == HttpStatus.SC_CREATED);
    JSONObject responseObject = new JSONObject(response.asString());
    JSONObject createConsignmentResponse = responseObject.getJSONObject("data");
    return createConsignmentResponse;
  }

  /**
   * This method is used to force checkout rider after consignment get delivered
   *
   * @param riderId
   * @param authToken
   * @author Deepak_Kumar
   */
  public void forceCheckoutRider(String riderId, String authToken) {
    // Calling force checkout after consignment is delivered
    ForceCheckoutRequest forceCheckoutRequest =
            ForceCheckoutRequest.builder()
                    .riderId(riderId)
                    .reason("Zepton outside geo-fence. Auto checkout did not work")
                    .build();
    response =
            dmsApiHelper.forceCheckout(
                    environment.getReqSpecBuilder(), forceCheckoutRequest, authToken, true);
    Assert.assertEquals(
            response.getStatusCode(),
            HttpStatus.SC_OK,
            "Response status code of force checkout Api is " + response.getStatusCode());
    ForceCheckoutResponse forceCheckoutResponse =
            response.getBody().as(ForceCheckoutResponse.class);
    Assert.assertEquals(
            forceCheckoutResponse.getData().getMessage(),
            "Check-out successful!",
            "Force checkout api response message not correct");
    Assert.assertEquals(
            forceCheckoutResponse.getData().getStatus(),
            "success",
            "Force checkout api response status message not correct");
  }

  /**
   * This method is used to get consignment details by orderCode nad storeId
   *
   * @param storeId
   * @param orderCode
   * @return
   * @author Deepak_Kumar
   */
  public Map<String, String> getConsignmentDataByOrderCode(String orderCode, String storeId)
          throws JsonProcessingException {
    Map<String, String> consignmentInfo = new HashMap<>();
    GetConsignmentByStoreIdAndStatusRequest requestMessage =
            GetConsignmentByStoreIdAndStatusRequest.builder().orderCode(orderCode).build();
    response =
            dmsApiHelper.getDmsDashboardConsignmentByStoreId(
                    environment.getReqSpecBuilder(), storeId, requestMessage, bifrostHelper.getJwtToken());
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper mapper = new ObjectMapper();
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    JSONObject orderList = data.getJSONArray("orderList").getJSONObject(0);
    consignmentInfo.put("deliveryId", orderList.get("deliveryId").toString());
    consignmentInfo.put("consignmentId", orderList.get("id").toString());
    consignmentInfo.put("riderId", orderList.get("riderId").toString());
    return consignmentInfo;
  }

  /**
   * This method is used to initiate rider cash balance settlement and submit to store
   *
   * @param token
   * @author Deepak_Kumar
   */
  public void riderPaymentSettlement(String token) throws JsonProcessingException {

    // clear cash for existing rider
    Response riderBalanceResponse =
            codOrderApiHelper.getRiderCashBalance(environment.getReqSpecBuilder(), token, false);
    int cashBalance = riderBalanceResponse.getBody().jsonPath().get("data.cashBalance");
    String lat = environment.getGlobalTestData().getString("createOrderData.lat");
    String longitude = environment.getGlobalTestData().getString("createOrderData.long");
    Response getNearByRetailersResponse =
            codOrderApiHelper.getNearByRetailers(
                    environment.getReqSpecBuilder(), token, longitude, lat, cashBalance);
    NearByRetailer nearByRetailer;
    if (getNearByRetailersResponse.getStatusCode() == 500) {
      nearByRetailer = NearByRetailer.builder().build();
    } else {
      BaseResponse getNearByRetailerResponse = getNearByRetailersResponse.as(BaseResponse.class);
      NearByRetailers nearByRetailers =
              new ObjectMapper()
                      .readValue(
                              RequestMapper.mapPojoObjectAndGetString(getNearByRetailerResponse.getData()),
                              NearByRetailers.class);
      nearByRetailer = nearByRetailers.getNearByRetailers().get(0);
    }
    String paymentMethod = "CASH_AT_STORE";

    // Get request body of rider Payment Settlement Request
    RiderPaymentSettlementRequest riderPaymentSettlementRequest =
            new CodOrderHelper()
                    .createRiderPaymentSettlementPayload(
                            cashBalance,
                            paymentMethod,
                            "AIRTEL_MONEY",
                            environment.getGlobalTestData().getString("defaultSellerInfo.sellerId"),
                            nearByRetailer);

    // Settle unsettled order
    Response response =
            codOrderApiHelper.riderPaymentSettlement(
                    environment.getReqSpecBuilder(), token, riderPaymentSettlementRequest, false);
    Assert.assertEquals(
            response.getStatusCode(), HttpStatus.SC_OK, "Response code verification got failed.");

    BaseResponse riderPaymentSettlementResponse =
            MapperUtils.deserialize(response.asString(), BaseResponse.class);
    PaymentSettlementResponse actualRiderPaymentSettlementResponse =
            new ObjectMapper()
                    .readValue(
                            RequestMapper.mapPojoObjectAndGetString(riderPaymentSettlementResponse.getData()),
                            PaymentSettlementResponse.class);

    logger.info("Making payment via collect cash api");
    String storeId = environment.getGlobalTestData().getString("createOrderData.storeId");
    logger.info("Fetching request Id");
    String requestId = actualRiderPaymentSettlementResponse.getCashAtStore().getRequestId();
    logger.info("Getting admin token");
    String adminToken = bifrostHelper.getJwtToken();
    response =
            new CashAtStoreApiHelper()
                    .collectCash(
                            environment.getReqSpecBuilder(),
                            adminToken,
                            storeId,
                            requestId,
                            cashBalance,
                            false);
    Assert.assertEquals(
            response.getStatusCode(), HttpStatus.SC_OK, "Response code verification got failed.");
  }

  /**
   * This method is used to update consignment delivery status from DMS dashboard
   *
   * @param consignmentInfo
   * @param updatedDeliveryFromDashboard
   * @param status
   * @author Deepak_Kumar
   */
  public void updateConsignmentStatusFromDMSDashboard(
          Map<String, String> consignmentInfo,
          UpdateDeliveryStatusRequest updatedDeliveryFromDashboard,
          String status) {
    updatedDeliveryFromDashboard.setStatus(status);
    response =
            dmsApiHelper.updateDeliveryStatusFromDashboard(
                    environment.getReqSpecBuilder(),
                    updatedDeliveryFromDashboard,
                    consignmentInfo.get("deliveryId"),
                    bifrostHelper.getJwtToken(),
                    false);
    Assert.assertEquals(
            response.getStatusCode(),
            HttpStatus.SC_OK,
            "Response status code is not " + HttpStatus.SC_OK + ", it is " + response.getStatusCode());
    verifyConsignmentStatus(consignmentInfo.get("consignmentId"), status);
  }

  /**
   * This method is used to quality check for scanned item
   *
   * @param deliveryId
   * @param riderToken
   * @param scanItemResponse
   * @param itemStatus
   * @author Deepak_Kumar
   */
  public void verifyScannedItemQualityCheck(
          String deliveryId, String riderToken, Response scanItemResponse, String itemStatus)
          throws JsonProcessingException {
    BaseResponse baseResponse =
            MapperUtils.deserialize(scanItemResponse.asString(), BaseResponse.class);
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    JSONObject itemDetails = new JSONObject(data.get("itemDetails").toString());

    // Call scanned item quality check api
    QualityCheckQuestion question1 =
            QualityCheckQuestion.builder()
                    .questionId("3043489f-d723-4e41-8a91-7a3c8cf1d4a1")
                    .optionId("28c0771e-a1a8-48da-a0dc-671525021bc6")
                    .build();
    QualityCheckQuestion question2 =
            QualityCheckQuestion.builder()
                    .questionId("b083b922-ea50-402e-94af-a7f0257bd336")
                    .optionId("857acdb8-9afc-4aee-b896-f9107c3191a3")
                    .build();
    List<QualityCheckQuestion> questionList = new ArrayList<>();
    questionList.add(question1);
    questionList.add(question2);

    QualityCheckItemDetails qualityCheckItemDetails =
            QualityCheckItemDetails.builder()
                    .itemId(itemDetails.get("itemId").toString())
                    .itemStatus(itemStatus)
                    .qcDetails(questionList)
                    .build();
    List<QualityCheckItemDetails> itemDetailsList = new ArrayList<>();
    itemDetailsList.add(qualityCheckItemDetails);

    ItermQualityCheckRequest qualityCheckRequest =
            ItermQualityCheckRequest.builder()
                    .deliveryId(deliveryId)
                    .itemDetails(itemDetailsList)
                    .build();

    response =
            dmsApiHelper.itemQualityCheck(
                    environment.getReqSpecBuilder(), riderToken, qualityCheckRequest);
    Assert.assertEquals(
            response.getStatusCode(),
            HttpStatus.SC_OK,
            "Quality check for scanned item response status code is not "
                    + HttpStatus.SC_OK
                    + ", it is "
                    + response.getStatusCode());
  }

  /**
   * This method is used to scan return or replacement item at customer location
   *
   * @param consignmentId
   * @param itemStatus
   * @author Deepak_Kumar
   */
  public void verifyItemStatus(String consignmentId, List<String> itemStatus)
          throws JsonProcessingException {
    Response response =
            dmsApiHelper.getConsignmentByConsignmentId(
                    environment.getReqSpecBuilder(), consignmentId, false);
    Assert.assertTrue(
            response.getStatusCode() == HttpStatus.SC_OK,
            "Response status code is not " + HttpStatus.SC_OK + ", it is " + response.getStatusCode());
    BaseResponse baseResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    JSONObject data = new JSONObject(mapper.writeValueAsString(baseResponse.getData()));
    JSONObject deliveries = data.getJSONArray("deliveries").getJSONObject(0);
    JSONObject itemCollectedDetails =
            deliveries.getJSONObject("extraDetails").getJSONObject("items_collected_details");
    JSONArray scannedItemDetails = itemCollectedDetails.getJSONArray("scanned_item_details");
    for (int i = 0; i < itemStatus.size(); i++) {
      JSONObject scannedItem = scannedItemDetails.getJSONObject(i);
      String scannedItemStatus = scannedItem.get("item_status").toString();
      Assert.assertEquals(
              scannedItemStatus,
              itemStatus.get(0),
              "Item scan status of " + i + 1 + "th item is not as expected");
    }
  }

  /**
   * This method is used to update trip status from RETURNING_TO_STORE to DONE
   *
   * @param consignmentId
   * @param riderToken
   */
  public void updateTripFromRTOToReturned(String consignmentId, String riderToken) {
    JSONObject consignmentDeliveryDetails = getConsignmentTripDeliveryDetailsById(consignmentId);
    String tripId = consignmentDeliveryDetails.get("trip_id").toString();

    // Update consignment to returned
    UpdateTripStatusRequest updateTripReadyToPickup = UpdateTripStatusRequest.builder().build();
    updateTripReadyToPickup.setStatus(Statuses.RETURNED_TO_STORE.tripStatus);
    response =
            dmsApiHelper.updateTripStatusV2(
                    environment.getReqSpecBuilder(), riderToken, tripId, updateTripReadyToPickup);
    Assert.assertTrue(
            response.getStatusCode() == HttpStatus.SC_OK,
            "Update trip Response status code is not 200, it is " + response.getStatusCode());

    updateTripReadyToPickup.setStatus(Statuses.RETURNED.tripStatus);
    response =
            dmsApiHelper.updateTripStatusV2(
                    environment.getReqSpecBuilder(), riderToken, tripId, updateTripReadyToPickup);
    Assert.assertTrue(
            response.getStatusCode() == HttpStatus.SC_OK,
            "Update trip Response status code is not 200, it is " + response.getStatusCode());
    verifyConsignmentDeliveryAndTripStatus(
            consignmentId,
            Statuses.RETURNED.consignmentStatus,
            Statuses.RETURNED.deliveryStatus,
            Statuses.RETURNED.tripStatus);
  }

  /**
   * This method is used to create loadShare order and allocate rider
   *
   * @param orderCode
   */
  public void createLoadShareOrderAndAllocateRider(String orderCode) {
    Response response =
            dmsApiHelper.createLoadShareOrder(environment.getReqSpecBuilder(), orderCode);
    Assert.assertEquals(
            response.getStatusCode(),
            HttpStatus.SC_OK,
            "Create loadShare order api response is not "
                    + HttpStatus.SC_OK
                    + " it is: "
                    + response.getStatusCode());

    response = dmsApiHelper.getLoadShareOrderStatus(environment.getReqSpecBuilder(), orderCode);
    Object loadShareId = response.jsonPath().getList("id").get(0);

    response =
            dmsApiHelper.allocateLoadShareRider(environment.getReqSpecBuilder(), loadShareId, 23884);
    Assert.assertEquals(
            response.getStatusCode(),
            HttpStatus.SC_OK,
            "Allocate loadShare rider api response is not "
                    + HttpStatus.SC_OK
                    + " it is: "
                    + response.getStatusCode());
    Assert.assertEquals(
            response.jsonPath().getBoolean("data.success"),
            Boolean.TRUE,
            "LoadShare Rider allocation failed");
  }
}
