package com.zeptonow.controllerlm.servicehelper.riderx;

import com.itextpdf.text.DocumentException;
import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.helper.api.riderx.RiderHomePageApiHelper;
import com.zeptonow.commonlm.helper.api.riderx.SelfSignupApiHelper;
import com.zeptonow.commonlm.helper.mysql.MySqlZeptoBackendServiceHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.riderhomepage.request.RiderCheckInCheckOutRequest;
import com.zeptonow.controllerlm.servicehelper.RiderHelper;
import com.zeptonow.controllerlm.servicehelper.ShiftAdherenceHelper;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zeptonow.commonlm.constants.DBQueries.updateStoreOpeningTime;

public class CheckInCheckOutHelper extends TestBase {

  private final LoggerUtil logger = new LoggerUtil(CheckInCheckOutHelper.class);
  private Environment environment = new Environment();
  private ShiftAdherenceHelper shiftAdherenceHelper = new ShiftAdherenceHelper();
  private SelfSignupApiHelper selfServeApiHelper = new SelfSignupApiHelper();
  private RiderHomePageHelper riderHomePageHelper = new RiderHomePageHelper();
  private RiderHomePageApiHelper riderHomePageApiHelper = new RiderHomePageApiHelper();
  private RiderHelper riderHelper = new RiderHelper();
  private MySqlZeptoBackendServiceHelper mySqlZeptoBackendServiceHelper =
      new MySqlZeptoBackendServiceHelper();

  /**
   * This method is used to create new rider
   *
   * @param riderType
   * @param vehicleType
   * @param doCheckin
   * @param additionalParams - if length == 1, then lsq else need 4 fields for rider in diff store
   * @return
   * @throws IOException
   * @throws DocumentException
   * @throws InterruptedException
   * @author Deepak_Kumar
   */
  @SneakyThrows
  public Map<String, String> createRiderUploadRiderWorkPlanDoCheckIn(
      String riderType, String vehicleType, boolean doCheckin, String... additionalParams) {

    Response response = null;
    GlobalUtil globalUtil = new GlobalUtil();
    // creating rider, marking rider as active and valid in DB and getting auth token for rider
    String isLsq = additionalParams.length == 1 ? additionalParams[0] : "";
    String storeId =
        additionalParams.length > 0
            ? additionalParams[0]
            : environment.getGlobalTestData().getString("createConsignmentData.storeId");
    HashMap<String, String> riderResponseDataMap =
        riderHelper.createRiderAndGenerateAuthToken(storeId, riderType, vehicleType, isLsq);
    String riderId = riderResponseDataMap.get("riderId");
    String riderToken = riderResponseDataMap.get("token");
    riderHelper.markRiderIsActiveAndIsValid(riderId);

    // insert created rider entry in zeb rider table
    createRiderEntryInZeptoBackend(riderId);

    // uploading and doing validation of post rider work plan api
    int shiftId =
        additionalParams.length > 1
            ? Integer.parseInt(additionalParams[1])
            : environment
                .getGlobalTestData()
                .getInt("riderHomePageCheckInForAutomationStore.checkInShiftId");
    String weekOffDay = LocalDate.now().getDayOfWeek().toString();
    if (weekOffDay.equals("FRIDAY")) {
      weekOffDay = LocalDate.now().plusDays(3).getDayOfWeek().toString();
    } else if (weekOffDay.equals("SATURDAY")) {
      weekOffDay = LocalDate.now().plusDays(2).getDayOfWeek().toString();
    } else {
      weekOffDay = LocalDate.now().plusDays(1).getDayOfWeek().toString();
    }
    shiftAdherenceHelper.uploadRiderWorkPlan(
        shiftId, weekOffDay, riderId, riderToken, 200, null, false);

    // Calling confirm details api to make rider active
    response =
        selfServeApiHelper.confirmDetails(
            environment.getReqSpecBuilder(), Boolean.FALSE, riderToken);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Response status code of confirm details api is not 200, it is "
            + response.getStatusCode());
    if (doCheckin) {
      // doing check-in
      Double lat =
          additionalParams.length > 2
              ? Double.parseDouble(additionalParams[2])
              : environment
                  .getGlobalTestData()
                  .getDouble("riderHomePageCheckInForAutomationStore.lat");
      Double longitude =
          additionalParams.length > 3
              ? Double.parseDouble(additionalParams[3])
              : environment
                  .getGlobalTestData()
                  .getDouble("riderHomePageCheckInForAutomationStore.long");

      // marking the store open and deleting existing redis key
      String storeOpeningTime = globalUtil.getPastTime(-2, -30, "UTC");
      new MySqlZeptoBackendServiceHelper()
          .executeInsertUpdateQuery(
              String.format(updateStoreOpeningTime, storeOpeningTime, storeId));
      globalUtil.deleteRedisKey("store_id:" + storeId);
      globalUtil.deleteRedisKey("rider_store_" + storeId);
      RiderCheckInCheckOutRequest riderCheckInStatusRequest =
          riderHomePageHelper.generateRiderCheckInCheckOutPayload(lat, longitude, "check_in");
      response =
          riderHomePageApiHelper.calRiderCheckInCheckOutApi(
              environment.getReqSpecBuilder(), Boolean.TRUE, riderCheckInStatusRequest, riderToken);
    }
    logger.info("rider id- " + riderId);
    return riderResponseDataMap;
  }

  /**
   * This method is used to create rider entry in zepto backend because sinking to zeb rider is not
   * working
   *
   * @param riderId
   */
  public void createRiderEntryInZeptoBackend(String riderId) throws SQLException {
    JSONObject riderData =
        mySqlZeptoBackendServiceHelper
            .queryDb(String.format(DBQueries.getRiderDetails, riderId))
            .getJSONObject(0);
    System.out.println(riderData);
    mySqlZeptoBackendServiceHelper.queryDb(
        String.format(
            DBQueries.createZebRiderEntry,
            riderId,
            riderData.get("created_at"),
            riderData.get("updated_at"),
            riderData.get("user_id").toString(),
            riderData.getString("vendor_rider_id"),
            riderData.getString("delivery_vendor"),
            riderData.get("store_id").toString(),
            riderData.get("is_valid"),
            riderData.get("created_at"),
            riderData.get("vehicle_type").toString(),
            riderData.get("contract_type").toString(),
            riderData.get("rider_3pl_vendor_id").toString(),
            riderData.get("covid_vaccination_status").toString()));
  }

  /**
   * This method is used to create and update shift plan for store
   *
   * @author Deepak_Kumar
   */
  public void uploadStorePlan() throws IOException {
    // creating shift-plan data map for store
    List<String> keysList =
        Arrays.asList(
            "riderHomePageCheckInForAutomationStore.shift_row0",
            "riderHomePageCheckInForAutomationStore.shift_row1",
            "riderHomePageCheckInForAutomationStore.shift_row2");
    HashMap<String, Object> shiftData = new HashMap<>();
    shiftData.put("keyList", keysList);
    shiftData.put("date", "futureDate");
    shiftData.put("fileName", "uploadStoreShiftPlan.csv");

    // creating week off plan data map for store
    List<String> weekOffKeyList =
        Arrays.asList(
            "riderHomePageCheckInForAutomationStore.week_row0",
            "riderHomePageCheckInForAutomationStore.week_row1");
    HashMap<String, Object> weekOffData = new HashMap<>();
    weekOffData.put("keyList", weekOffKeyList);
    weekOffData.put("fileName", "uploadStoreWeekOffPlan.csv");
    weekOffData.put("date", "");
    // uploading shift plan and week off plan for store
    shiftAdherenceHelper.uploadShiftPlanAndWeekOffPlan(shiftData, weekOffData);
  }
}
