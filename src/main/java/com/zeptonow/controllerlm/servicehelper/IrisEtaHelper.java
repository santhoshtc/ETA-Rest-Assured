package com.zeptonow.controllerlm.servicehelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RequestMapper;
import com.zepto.common.fileUtils.MapperUtils;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.helper.api.IrisEtaApiHelper;
import com.zeptonow.commonlm.helper.api.RainModeApiHelper;
import com.zeptonow.commonlm.helper.mysql.MySqlZeptoBackendServiceHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.dms.response.BaseResponse;
import com.zeptonow.commonlm.pojo.iris_eta.request.GetEtaFromIrisRequest;
import com.zeptonow.commonlm.pojo.iris_eta.request.UpdatePackingConfigRequest;
import com.zeptonow.commonlm.pojo.iris_eta.response.GetMainEtaResponse;
import com.zeptonow.commonlm.pojo.iris_eta.response.GetPeakBufferResponse;
import com.zeptonow.commonlm.pojo.iris_eta.response.GetStoreConfigResponse;
import com.zeptonow.commonlm.pojo.iris_eta.subclass.*;
import com.zeptonow.commonlm.pojo.rainmode.request.UpdateRainBlockToggleRequest;
import com.zeptonow.commonlm.pojo.rainmode.request.UpdateRainModeRequest;
import com.zeptonow.controllerlm.servicehelper.riderx.CheckInCheckOutHelper;
import com.zeptonow.controllerlm.servicehelper.riderx.dms.DMSHelper;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;

public class IrisEtaHelper {

  LoggerUtil logger;
  private Environment environment;
  private Response response;
  private IrisEtaApiHelper irisEtaApiHelper;
  private GlobalUtil globalUtil;
  private MySqlZeptoBackendServiceHelper mySqlZeptoBackendServiceHelper;
  private String etaFilePath =
      System.getProperty("user.dir")
          + File.separator
          + "src"
          + File.separator
          + "test"
          + File.separator
          + "resources"
          + File.separator
          + "testdata"
          + File.separator
          + "etaRange.conf";
  private Config config = ConfigFactory.parseFile(new File(this.etaFilePath));
  private RainModeApiHelper rainModeApiHelper;
  private DMSHelper dmsHelper;
  private String token = new BifrostHelper().getJwtToken();
  private List<String> vehicleByPriority =
      new ArrayList<>() {
        {
          add("WALKER");
          add("CYCLE");
          add("EXPRESS_FLEET");
          add("EV_EXPRESS");
          add("E_CYCLE");
          add("EV_NEW");
          add("E_SCOOTER");
          add("BIKE");
        }
      };

  public IrisEtaHelper() {
    logger = new LoggerUtil(IrisEtaHelper.class);
    environment = new Environment();
    irisEtaApiHelper = new IrisEtaApiHelper();
    mySqlZeptoBackendServiceHelper = new MySqlZeptoBackendServiceHelper();
    globalUtil = new GlobalUtil();
    rainModeApiHelper = new RainModeApiHelper();
    dmsHelper = new DMSHelper();
  }

  /**
   * Doing basic setup based on testcase required for eta calculation
   *
   * @param storeId
   * @param configName
   * @author Manisha_Kumari
   */
  public void setBasicConfigForEta(String storeId, String configName) {
    // set new eta engine enabled cache key to true for store
    globalUtil.setRedisKey(
        ZEPTO_BACKEND_REDIS_HOST,
        ZEPTO_BACKEND_REDIS_PORT,
        String.format(GeneralConstants.REDIS_IS_NEW_ETA_FLOW_KEY, storeId),
        "TRUE");
    switch (configName) {
      case "etaValuesWithCustomer230mFromStoreWithRiderTypeWalkerAndCycle":
        {
          // check in walker and cyclist
        }
      case "etaValuesWithStoreLocWithRidersTypeWalkerAndCycle":
        {
          // clear cache for the first time to remove older riders
          String walkerRiderId =
              checkInRiderAndReturnId(
                  "WALKER", storeId, "riderHomePageCheckInForWhitefieldStore", true);
          String cycleRiderId =
              checkInRiderAndReturnId(
                  "CYCLE", storeId, "riderHomePageCheckInForWhitefieldStore", false);

          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          // this is to handle edge case when packer count is 0
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesForRtsHigherThanPackingTimeAndDistance2500m":
        {
          String cycleRiderId =
              checkInRiderAndReturnId(
                  "CYCLE", storeId, "riderHomePageCheckInForWhitefieldStore", false);
          String bikeRiderId =
              checkInRiderAndReturnId(
                  "BIKE", storeId, "riderHomePageCheckInForWhitefieldStore", false);

          setBasicConfigForEtaInStoreLocation(storeId, configName, true);

          // update bike rts to >150 sec for the particular rider
          long currentMilliSeconds = new Date().toInstant().toEpochMilli();
          // increase rts by 5 mins from current time
          currentMilliSeconds += 330000;
          String bikeCacheKey = config.getString(configName + ".cache_key");
          globalUtil.deleteRedisKey(bikeCacheKey, IRIS_REDIS_HOST, "6379");

          globalUtil.setRedisKey(
              IRIS_REDIS_HOST,
              "6379",
              config.getString(configName + ".cache_key"),
              bikeRiderId,
              currentMilliSeconds,
              "nx");
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation":
        {
          String bikeRiderId =
              checkInRiderAndReturnId(
                  "BIKE", storeId, "riderHomePageCheckInForWhitefieldStore", false);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);

          // update bike rts to >150 sec for the particular rider
          long currentMilliSeconds = new Date().toInstant().toEpochMilli();
          // increase rts by 5 mins from current time
          currentMilliSeconds += 211000;
          String bikeCacheKey = config.getString(configName + ".cache_key");
          globalUtil.deleteRedisKey(bikeCacheKey, IRIS_REDIS_HOST, "6379");

          globalUtil.setRedisKey(
              IRIS_REDIS_HOST,
              "6379",
              config.getString(configName + ".cache_key"),
              bikeRiderId,
              currentMilliSeconds,
              "nx");
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocWithRidersTypeWalkerAssignedAndCycleUnassigned":
        {
          // clear cache for the first time to remove older riders
          String walkerRiderId =
              checkInRiderAndReturnId(
                  "WALKER", storeId, "riderHomePageCheckInForWhitefieldStore", true);
          checkInRiderAndReturnId(
              "CYCLE", storeId, "riderHomePageCheckInForWhitefieldStore", false);

          // create consignment and assign to walker
          Map<String, Object> orderInfo = new HashMap<>();
          orderInfo.put("storeId", storeId);
          orderInfo.put(
              "lat",
              environment
                  .getGlobalTestData()
                  .getString("riderHomePageCheckInForWhitefieldStore.lat"));
          orderInfo.put(
              "long",
              environment
                  .getGlobalTestData()
                  .getString("riderHomePageCheckInForWhitefieldStore.long"));

          // assign order to consignment in order to have only 1 available rider in store
          JSONObject consignmentInfo = dmsHelper.createConsignmentByOrderInfo(orderInfo);
          dmsHelper.assignRiderManuallyToConsignment(consignmentInfo, walkerRiderId);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesForRtsEqualToPackingTimeAtStoreLocation":
        {
          // clear cache for the first time to remove older riders
          String walkerRiderId =
              checkInRiderAndReturnId(
                  "WALKER", storeId, "riderHomePageCheckInForWhitefieldStore", true);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);

          // update walker rts to >150 sec for the particular rider
          long currentMilliSeconds = new Date().toInstant().toEpochMilli();
          // increase rts by 5 mins from current time
          currentMilliSeconds += 211000;
          String bikeCacheKey = config.getString(configName + ".cache_key");
          globalUtil.deleteRedisKey(bikeCacheKey, IRIS_REDIS_HOST, "6379");

          globalUtil.setRedisKey(
              IRIS_REDIS_HOST,
              "6379",
              config.getString(configName + ".cache_key"),
              walkerRiderId,
              currentMilliSeconds,
              "nx");
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocAndStandStillMode":
        {
          enableStandStillForStore(storeId, true);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValueForUnserviceableGeofence":
        {
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocAndCartProducts":
        {
          setPackerConfigForEtaInStoreLocation(storeId, configName);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesForBuildingIdInStoreGeofence":
        {
          checkInRiderAndReturnId(
              "WALKER", storeId, "riderHomePageCheckInForWhitefieldStore", false);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);

          // update building cache
          String cacheKey = config.getString(configName + ".cache_key");
          String cacheValue = config.getString(configName + ".cache_value");

          String currentTime =
              GlobalUtil.getCurrentDateTime(GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);
          String currentUtcTime =
              GlobalUtil.getDateInUTCTimeZone(
                  currentTime,
                  GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS,
                  GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);
          cacheValue =
              String.format(
                  cacheValue,
                  storeId,
                  config.getString(configName + ".buildingId"),
                  config.getInt(configName + ".buildingBuffer"),
                  currentUtcTime);
          logger.info("updating building cache :: " + cacheValue + "  with value :: " + cacheValue);

          globalUtil.setRedisKey(IRIS_REDIS_HOST, "6379", cacheKey, cacheValue);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocAndHighRiderStress":
        {
        }
      case "etaValuesWithStoreLocAndHighPackerStress":
        {
          updateStressBannerAndCache(configName, storeId);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          break;
        }
      case "etaValuesWithStoreLocAndBatchingEnabled":
        {
          setStressCache(configName, storeId);
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithNoRidersAvailableInStore":
        {
          clearRiderCache(storeId);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocAndMinVariableAndMinRain":
        {
        }
      case "etaValuesWithStoreLocAndMinVariableAndMildRain":
        {
        }
      case "etaValuesWithStoreLocAndMinVariableAndHighRain":
        {
          setRainIntensity(storeId, configName);
        }
      case "etaValuesWithStoreLocAndMinVariable":
        {
          // read all props from config file and update in db
          setBasicConfigForEtaInStoreLocation(storeId, configName, true);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocCartProductsAndNegativeBuffer":
        {
          setPackerConfigForEtaInStoreLocation(storeId, configName);
          setBasicConfigForEtaInStoreLocation(storeId, configName, false);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
      case "etaValuesWithStoreLocAndOtherVariablesAndHighRain":
        {
        }
      case "etaValuesWithStoreLocAndOtherVariablesAndNoRain":
        {
          setRainIntensity(storeId, configName);
        }
      case "etaValuesWithStoreLocAndPositiveBuffer":
        {
          setBasicConfigForEtaInStoreLocation(storeId, configName, false);
          globalUtil.deleteRedisKey(
              String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
          break;
        }
    }
  }

  /**
   * internal function to check in rider and clear avail cache if required
   *
   * @param vehicle
   * @param storeId
   * @param configName
   * @param clearCache
   * @return
   */
  private String checkInRiderAndReturnId(
      String vehicle, String storeId, String configName, boolean clearCache) {
    // get info for checking in walker and cycle rider at whitefield store
    String[] storeDetails = new String[4];
    storeDetails[0] = storeId;
    storeDetails[1] = environment.getGlobalTestData().getString(configName + ".checkInShiftId");
    storeDetails[2] = environment.getGlobalTestData().getString(configName + ".lat");
    storeDetails[3] = environment.getGlobalTestData().getString(configName + ".long");

    // build rider cache by checking in rider and clear cache if required
    String riderId = buildRiderCache(vehicle, storeId, clearCache, storeDetails);
    return riderId;
  }

  /**
   * create iris eta request with/without cart product
   *
   * @param storeId
   * @param destLat
   * @param destLong
   * @return
   * @author Manisha_Kumari
   */
  public GetEtaFromIrisRequest createIrisEtaRequest(
      String storeId, String config, double destLat, double destLong, boolean withCart) {

    double sourceLat = environment.getGlobalTestData().getDouble(config + ".lat");
    double sourceLong = environment.getGlobalTestData().getDouble(config + ".long");
    List<CartProducts> cartProducts = new ArrayList();

    if (withCart) {
      CartProducts cartProductA =
          CartProducts.builder()
              .productId("61bbaa42-6b20-4aaa-b82f-d9e40f9f38cc")
              .quantity(3)
              .build();
      CartProducts cartProductB =
          CartProducts.builder()
              .productId("954eb312-cb66-42ee-a175-f1a456b0d54f")
              .quantity(5)
              .build();
      cartProducts.add(cartProductA);
      cartProducts.add(cartProductB);
    }

    GetEtaFromIrisRequest getEtaFromIrisRequest =
        GetEtaFromIrisRequest.builder()
            .storeId(storeId)
            .sourceLat(sourceLat)
            .sourceLong(sourceLong)
            .destLat(destLat)
            .destLong(destLong)
            .cartProducts(cartProducts)
            .cdebId(UUID.randomUUID().toString())
            .build();
    return getEtaFromIrisRequest;
  }

  /**
   * update config in store table for eta with default values
   *
   * @param storeId
   * @author Manisha_Kumari
   */
  private void setBasicConfigForEtaInStoreLocation(
      String storeId, String configName, boolean noPeakBuffer) {
    int order_packing_time_in_secs = config.getInt(configName + ".order_packing_time_in_secs");
    int packer_assignment_time_in_secs =
        config.getInt(configName + ".packer_assignment_time_in_secs");
    int packer_rider_handshake_time_in_secs =
        config.getInt(configName + ".packer_rider_handshake_time_in_secs");
    int rider_arrival_buffer_in_secs = config.getInt(configName + ".rider_arrival_buffer_in_secs");
    int surge_rider_handshake_time_in_secs =
        config.getInt(configName + ".surge_rider_handshake_time_in_secs");
    try {
      // updating store level config
      mySqlZeptoBackendServiceHelper.executeInsertUpdateQuery(
          String.format(
              DBQueries.updateEtaRelatedConfig,
              order_packing_time_in_secs,
              packer_assignment_time_in_secs,
              packer_rider_handshake_time_in_secs,
              rider_arrival_buffer_in_secs,
              surge_rider_handshake_time_in_secs,
              storeId));

      // update peak buffer as 0 if noPeakBuffer else based on config for store for basic cases
      updatePeakBufferConfig(storeId, noPeakBuffer, configName);

      // call sync api of zeb to update config
      Response response = irisEtaApiHelper.syncEtaConfigs(environment.getReqSpecBuilder(), storeId);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Unexpected response code");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * update packer config for eta with pre-set values
   *
   * @param storeId
   * @author Manisha_Kumari
   */
  private void setPackerConfigForEtaInStoreLocation(String storeId, String configName) {
    int pickingTimeInSec = config.getInt(configName + ".pickingTimeBaseInSec");
    int pickingTimeAddonInSec = config.getInt(configName + ".pickingTimeAddonInSec");
    int travelTimeInSec = config.getInt(configName + ".travelTimeInSec");

    // update packing config for store
    String updatedAt =
        GlobalUtil.getCurrentDateTime(GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);
    UpdatePackingConfigRequest updatePackingConfigRequest =
        UpdatePackingConfigRequest.builder()
            .pickingTimeAddonInSec(pickingTimeAddonInSec)
            .pickingTimeBaseInSec(pickingTimeInSec)
            .travelTimeInSec(travelTimeInSec)
            .storeId(storeId)
            .createdOn(updatedAt)
            .updatedOn(updatedAt)
            .build();

    Response response =
        irisEtaApiHelper.updatePackingConfigIris(
            environment.getReqSpecBuilder(), updatePackingConfigRequest);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting success message");
  }

  /**
   * Set Rain Intensity for store according to testcase
   *
   * @param storeId
   * @param configName
   * @author Manisha.Kumari
   */
  public void setRainIntensity(String storeId, String configName) {
    String rainIntensity = config.getString(configName + ".rainIntensity");
    // update rain block to false
    UpdateRainBlockToggleRequest updateRainBlockToggleRequest =
        UpdateRainBlockToggleRequest.builder()
            .blockRainMode(false)
            .storeIds(
                new ArrayList<>() {
                  {
                    add(storeId);
                  }
                })
            .build();
    Response response =
        rainModeApiHelper.updateRainBlockToggleForStore(
            environment.getReqSpecBuilder(), updateRainBlockToggleRequest, token);

    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Mismatch in status code");

    UpdateRainModeRequest updateRainModeRequest =
        UpdateRainModeRequest.builder()
            .rainIntensity(rainIntensity)
            .rainFlag(!rainIntensity.equals(GeneralConstants.NO_RAIN))
            .storeId(storeId)
            .build();
    response =
        rainModeApiHelper.updateRainModeStatusForStore(
            environment.getReqSpecBuilder(), updateRainModeRequest, token);
    Assert.assertEquals(
        response.getStatusCode(), HttpStatus.SC_CREATED, "Expecting status to be 201");
  }

  /**
   * update peak buffer based on testcase
   *
   * @param storeId
   * @param noPeakBuffer
   * @param configName
   * @author Manisha_Kumari
   */
  private void updatePeakBufferConfig(String storeId, boolean noPeakBuffer, String configName) {
    // update peak buffer as 0 for store for basic cases
    int currentHour = GlobalUtil.getHourOfDateInGivenFormat(0, GeneralConstants.DATE_TIME_FORMAT);
    currentHour = currentHour > 1 && currentHour < 25 ? currentHour++ : currentHour;
    int nextHour = currentHour;

    try {
      if (noPeakBuffer) {
        File peakBufferFile = createPeakBufferCsv("etaValuesWithStoreLocAndMinVariable", storeId);
        Response response =
            irisEtaApiHelper.updatePeakBufferIris(environment.getReqSpecBuilder(), peakBufferFile);
        Thread.sleep(100);
        logger.info("waiting for 100 ms before calling get peak buffer");
        Assert.assertNotEquals(
            response.getStatusCode(),
            HttpStatus.SC_INTERNAL_SERVER_ERROR,
            "Not expecting server error");

        // validate peak buffer is updated and is set to 0 for current hour
        response = irisEtaApiHelper.getPeakBufferIris(environment.getReqSpecBuilder(), storeId);
        Assert.assertEquals(
            response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 response code.");
        BaseResponse getPeakBuffer =
            MapperUtils.deserialize(response.asString(), BaseResponse.class);
        GetPeakBufferResponse getPeakBufferResponse =
            new ObjectMapper()
                .readValue(
                    RequestMapper.mapPojoObjectAndGetString(getPeakBuffer.getData()),
                    GetPeakBufferResponse.class);

        Optional<Buffer> getByHour =
            getPeakBufferResponse.getBuffers().stream()
                .filter(b -> b.getHourIst() == nextHour)
                .findFirst();
        Buffer peakBuffer = getByHour.orElse(null);
        logger.info("Peak buffer value for current hour :: " + nextHour + " is :: " + peakBuffer);
        if (peakBuffer != null) {
          Assert.assertEquals(peakBuffer.getBuffer(), 0, "Expecting 0 buffer");
        }
      } else {
        File peakBufferFile = createPeakBufferCsv(configName, storeId);
        Response response =
            irisEtaApiHelper.updatePeakBufferIris(environment.getReqSpecBuilder(), peakBufferFile);
        Thread.sleep(100);
        logger.info("waiting for 100 ms before calling get peak buffer");
        Assert.assertNotEquals(
            response.getStatusCode(),
            HttpStatus.SC_INTERNAL_SERVER_ERROR,
            "Not expecting server error");

        // validate peak buffer is updated and is set to 0 for current hour
        response = irisEtaApiHelper.getPeakBufferIris(environment.getReqSpecBuilder(), storeId);
        Assert.assertEquals(
            response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 response code.");
        BaseResponse getPeakBuffer =
            MapperUtils.deserialize(response.asString(), BaseResponse.class);
        GetPeakBufferResponse getPeakBufferResponse =
            new ObjectMapper()
                .readValue(
                    RequestMapper.mapPojoObjectAndGetString(getPeakBuffer.getData()),
                    GetPeakBufferResponse.class);
        Optional<Buffer> getByHour =
            getPeakBufferResponse.getBuffers().stream()
                .filter(b -> b.getHourIst() == nextHour)
                .findFirst();
        Buffer peakBuffer = getByHour.orElse(null);
        logger.info("Peak buffer value for current hour :: " + nextHour + " is :: " + peakBuffer);
        int value = config.getInt(configName + ".bufferTimeInSec");
        if (peakBuffer != null) {
          Assert.assertEquals(peakBuffer.getBuffer(), value, "Expecting buffer " + value);
        }
      }
    } catch (InterruptedException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Create peak buffer csv
   *
   * @param configName
   * @param storeId
   * @return
   * @author Manisha_Kumari
   */
  private File createPeakBufferCsv(String configName, String storeId) {
    logger.info("creating a csv file with valid data");
    List<String> keysList =
        configName.equals("etaValuesWithStoreLocAndMinVariable")
            ? Arrays.asList("peakBufferConfig.row1")
            : Arrays.asList("peakBufferConfig.row0");
    List<String> valuesList = Arrays.asList(storeId);
    switch (configName) {
      case "etaValuesWithStoreLocAndMinVariable":
      case "etaValuesWithStoreLocAndCartProducts":
        {
          break;
        }
      case "etaValuesWithStoreLocCartProductsAndNegativeBuffer":
        {
        }
      case "etaValuesWithStoreLocAndPositiveBuffer":
        {
        }
      case "etaValuesWithStoreLocAndOtherVariablesAndNoRain":
        {
        }
      case "etaValuesWithStoreLocAndOtherVariablesAndHighRain":
        {
          String value = config.getString(configName + ".bufferTimeInSec");
          valuesList =
              Arrays.asList(
                  storeId, value, value, value, value, value, value, value, value, value, value,
                  value, value, value, value, value, value, value, value, value, value, value,
                  value, value, value);
          break;
        }
    }
    try {
      File uploadPeakBufferPayloadCsvFile =
          globalUtil.createCsvFile(keysList, valuesList, "peakBufferAutomation.csv");
      return uploadPeakBufferPayloadCsvFile;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * validate high eta response
   *
   * @param isRaining
   * @param isStandStill
   * @param stress
   * @param getMainEtaResponse
   * @author Manisha_Kumari
   */
  public void validateHighEta(
      boolean isRaining,
      boolean isStandStill,
      boolean stress,
      GetMainEtaResponse getMainEtaResponse) {
    if (isRaining) {
      Assert.assertEquals(
          getMainEtaResponse.getDisplayText(),
          GeneralConstants.RAIN_MODE_DT,
          "Expecting rain mode message");
      Assert.assertEquals(
          getMainEtaResponse.getSecondaryDisplayText(),
          GeneralConstants.TRY_LATER,
          "Expecting rain mode secondary message");
      Assert.assertEquals(
          getMainEtaResponse.getDeliverableType(),
          GeneralConstants.CLOSED,
          "Expecting delivery closed");
      Assert.assertEquals(
          getMainEtaResponse.getDeliverableSubtype(),
          GeneralConstants.SURGE_IN_DEMAND,
          "Expecting surge in demand subType");
    }
    if (isStandStill) {
      Assert.assertEquals(
          getMainEtaResponse.getDisplayText(),
          GeneralConstants.ST_DT,
          "Expecting stand still mode message");
      Assert.assertEquals(
          getMainEtaResponse.getSecondaryDisplayText(),
          GeneralConstants.ST_SDT,
          "Expecting stand still  mode secondary message");
      Assert.assertEquals(
          getMainEtaResponse.getDeliverableType(),
          GeneralConstants.OPEN,
          "Expecting delivery open");
      Assert.assertEquals(
          getMainEtaResponse.getDeliverableSubtype(),
          GeneralConstants.ST_DELIVERABLE_SUBTYPE,
          "Expecting stand still subType");
    }
    if (stress) {
      Assert.assertEquals(
          getMainEtaResponse.getDisplayText(),
          GeneralConstants.TRY_LATER,
          "Expecting try later message");
      Assert.assertEquals(
          getMainEtaResponse.getSecondaryDisplayText(),
          GeneralConstants.DEMAND_SURGE_ST,
          "Expecting demand surge message");
      Assert.assertEquals(
          getMainEtaResponse.getDeliverableType(),
          GeneralConstants.CLOSED,
          "Expecting delivery closed");
      Assert.assertEquals(
          getMainEtaResponse.getDeliverableSubtype(),
          GeneralConstants.SURGE_IN_DEMAND,
          "Expecting surge in demand subType");
    }
  }

  /**
   * config for rider/packer stress
   *
   * @param configName
   * @param storeId
   * @throws SQLException
   * @author Manisha_Kumari
   */
  public void updateStressBannerAndCache(String configName, String storeId) {
    String key = config.getString(configName + ".stress_db_key");
    // update banner value to true in db
    try {
      mySqlZeptoBackendServiceHelper.executeInsertUpdateQuery(
          String.format(DBQueries.updateEtaFieldsInStoreMeta, Boolean.TRUE, storeId, key));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    // call set stress cache
    setStressCache(configName, storeId);
  }

  /**
   * update stress cache manually to replicate stress scenarios
   *
   * @param configName
   * @param storeId
   * @author Manisha_Kumari
   */
  private void setStressCache(String configName, String storeId) {
    String currentTime =
        GlobalUtil.getCurrentDateTime(GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);
    String currentUtcTime =
        GlobalUtil.getDateInUTCTimeZone(
            currentTime,
            GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS,
            GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);
    String endTime =
        GlobalUtil.setSpecificHourForGivenDate(
            currentTime,
            23,
            15,
            GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS,
            GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);
    String endUtcTime =
        GlobalUtil.getDateInUTCTimeZone(
            endTime,
            GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS,
            GeneralConstants.DATE_TIME_FORMAT_WITH_CONSTANTS);

    String cacheValue =
        configName.equals("etaValuesWithStoreLocAndBatchingEnabled")
            ? String.format(
                config.getString(configName + ".stress_cache_value"),
                storeId,
                currentUtcTime,
                endUtcTime)
            : String.format(
                config.getString(configName + ".stress_cache_value"), currentTime, endTime);

    String cacheKey = String.format(config.getString(configName + ".stress_cache_key"), storeId);

    logger.info("setting iris db cache for key :: " + cacheKey + " with value :: " + cacheValue);
    globalUtil.setRedisKey(
        ZEPTO_BACKEND_REDIS_HOST, ZEPTO_BACKEND_REDIS_PORT, cacheKey, cacheValue);
  }

  /**
   * delete current cache and check in new rider with req vehicle
   *
   * @param vehicleType
   * @param storeId
   * @author Manisha_Kumari
   */
  public String buildRiderCache(
      String vehicleType, String storeId, boolean clearCache, String... storeDetails) {
    if (clearCache) {
      clearRiderCache(storeId);
    }
    Map<String, String> riderInfo =
        new CheckInCheckOutHelper()
            .createRiderUploadRiderWorkPlanDoCheckIn("FULL_TIME", vehicleType, true, storeDetails);
    return riderInfo.get("riderId");
  }

  /**
   * clear rider cache for the store for all vehicles
   *
   * @param storeId
   * @author Manisha_Kumari
   */
  public void clearRiderCache(String storeId) {
    String key = GeneralConstants.getRiderCacheKey;
    //    key = String.format(key, storeId);
    List<String> vehicles =
        new ArrayList<>() {
          {
            add("cycle");
            add("bike");
            add("walker");
            add("e_cycle");
            add("express_fleet");
            add("ev_express");
          }
        };
    for (String vehicle : vehicles) {
      String toDelete = String.format(key, storeId, vehicle);
      logger.info("Delete cache key :: " + toDelete);
      globalUtil.deleteRedisKey(toDelete, IRIS_REDIS_HOST, "6379");
      toDelete = String.format(key, storeId, vehicle.toUpperCase());
      logger.info("Delete cache key :: " + toDelete);
      globalUtil.deleteRedisKey(toDelete, IRIS_REDIS_HOST, "6379");
    }
    logger.info("Deleted cache keys for riders in store :: " + storeId);
  }

  @SneakyThrows
  public void enableStandStillForStore(String storeId, boolean enable) {
    mySqlZeptoBackendServiceHelper.executeInsertUpdateQuery(
        String.format(DBQueries.updateStandStillFlagForStore, enable, storeId));
  }

  /**
   * return batch time as configured at store level else return default
   *
   * @param storeId
   * @return
   * @author Manisha_Kumari
   */
  public int getBatchingTimeForStore(String storeId) {
    int batchingTime = 300;
    try {
      JSONArray result =
          mySqlZeptoBackendServiceHelper.queryDb(
              String.format(
                  DBQueries.getValueFromStoreMeta,
                  storeId,
                  GeneralConstants.ORDERS_BATCH_SURGE_ETA_BUFFER_IN_SECS));
      if (result.length() > 0) {
        JSONObject data = result.getJSONObject(0);
        batchingTime = data.getInt("value");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return batchingTime / 60;
  }

  /**
   * calculate calibrated eta based on current implementation
   *
   * @param googleEta
   * @param rider
   * @param isStandStill
   * @param distance
   * @param storeId
   * @return
   * @author Manisha_Kumari
   */
  public int getCalibratedEta(
      int googleEta, Rider rider, boolean isStandStill, int distance, String storeId) {
    if (isStandStill) {
      googleEta *= 1.5;
    }
    // call store config api
    Response response =
        irisEtaApiHelper.getStoreConfigFromZeb(environment.getReqSpecBuilder(), storeId);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting status to be 200");
    GetStoreConfigResponse getStoreConfigResponse = response.as(GetStoreConfigResponse.class);

    float distanceMultiplier = getMultiplier("distance", rider, distance, getStoreConfigResponse);
    googleEta *= distanceMultiplier;
    googleEta = Math.max(60, googleEta);

    if (getStoreConfigResponse.getEtaMultiplierBucketsDistance() != null) {
      return googleEta;
    }

    int time = (int) (Math.floor(googleEta / 60) + 1);
    float timeMultiplier = getMultiplier("time", rider, time, getStoreConfigResponse);

    if (rider.getVehicle().equals("e_cycle")) {
      googleEta *= 0.8;
    }
    googleEta *= timeMultiplier;

    return googleEta;
  }

  /**
   * get multiplier based on distance/time for storeId within bucket range
   *
   * @param type
   * @param rider
   * @param bucketValue
   * @param getStoreConfigResponse
   * @return
   * @author Manisha_Kumari
   */
  private float getMultiplier(
      String type, Rider rider, int bucketValue, GetStoreConfigResponse getStoreConfigResponse) {
    String vehicle = rider.getVehicle().toLowerCase();
    EtaBucketMultipliers etaBucketMultipliers;
    List<Bucket> selectedBucket;

    // get bucket multiplier from response to be considered based on type
    switch (type) {
      case "distance":
        {
          etaBucketMultipliers = getStoreConfigResponse.getEtaMultiplierBucketsDistance();
          break;
        }
      case "time":
        {
          etaBucketMultipliers = getStoreConfigResponse.getEtaMultiplierBucketsTime();
          break;
        }
      default:
        {
          etaBucketMultipliers = getStoreConfigResponse.getEtaMultiplierBucketsDistance();
        }
    }
    selectedBucket = getBucketBasedOnRider(vehicle, etaBucketMultipliers);

    // logic to select mutliplier
    if (selectedBucket != null) {
      for (int i = selectedBucket.size() - 1; i >= 0; i--) {
        if (type.equals("distance") && bucketValue > selectedBucket.get(i).getMin()) {
          return selectedBucket.get(i).getMultiplier();
        }
        if (type.equals("time") && bucketValue >= selectedBucket.get(i).getMin()) {
          return selectedBucket.get(i).getMultiplier();
        }
      }
      if (type.equals("distance") && bucketValue == 0 && selectedBucket.size() >= 1) {
        return selectedBucket.get(0).getMultiplier();
      }
    }
    if (vehicle.equals("cycle") || vehicle.equals("e_cycle")) {
      if (selectedBucket == null && type.equals("time")) {
        return 2;
      }
    }
    return 1;
  }

  /**
   * get bucket from list of config based on rider vehicle
   *
   * @param vehicle
   * @param etaBucketMultipliers
   * @return
   * @author Manisha_Kumari
   */
  private List<Bucket> getBucketBasedOnRider(
      String vehicle, EtaBucketMultipliers etaBucketMultipliers) {
    if (etaBucketMultipliers == null) {
      return null;
    }
    switch (vehicle) {
      case "bike":
        {
          return etaBucketMultipliers.getBike();
        }
      case "cycle":
        {
          return etaBucketMultipliers.getCycle();
        }
      case "e_cycle":
        {
          return etaBucketMultipliers.getE_cycle();
        }
      case "walker":
        {
          return etaBucketMultipliers.getWalker();
        }
      case "e_scooter":
        {
          return etaBucketMultipliers.getE_scooter();
        }
    }
    return null;
  }
}
