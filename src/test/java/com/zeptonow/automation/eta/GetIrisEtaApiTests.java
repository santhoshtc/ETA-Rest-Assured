package com.zeptonow.automation.eta;

import com.epam.reportportal.annotations.attribute.Attributes;
import com.epam.reportportal.annotations.attribute.MultiValueAttribute;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zepto.api.restassured.RequestMapper;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.configUtil.TestAttributes;
import com.zepto.common.configUtil.TestPriority;
import com.zepto.common.fileUtils.MapperUtils;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.constants.Services.Name;
import com.zeptonow.commonlm.constants.TestOwners;
import com.zeptonow.commonlm.constants.TestType;
import com.zeptonow.commonlm.helper.api.IrisEtaApiHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.dms.response.BaseResponse;
import com.zeptonow.commonlm.pojo.iris_eta.request.DeliveryConfigRequest;
import com.zeptonow.commonlm.pojo.iris_eta.request.GetEtaFromIrisRequest;
import com.zeptonow.commonlm.pojo.iris_eta.response.GetIrisEtaResponse;
import com.zeptonow.commonlm.pojo.iris_eta.response.GetMainEtaResponse;
import com.zeptonow.controllerlm.servicehelper.IrisEtaHelper;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class GetIrisEtaApiTests extends TestBase {

  private final LoggerUtil logger = new LoggerUtil(GetEtaTest.class);
  private IrisEtaHelper irisEtaHelper = new IrisEtaHelper();
  private IrisEtaApiHelper irisEtaApiHelper = new IrisEtaApiHelper();
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
  private GlobalUtil globalUtil = new GlobalUtil();

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta with cust loc as store and cart product with default config values and validate response")
  public void validateEtaForStoreLocationCartProductsAndDefaultConfig() {

    // doing basic setup and reading expected eta from conf
    int minExpectedEta = config.getInt("etaValuesWithStoreLocAndCartProducts.min");
    int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndCartProducts.max");
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndCartProducts");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(storeId, "createConsignmentData", lat, longitude, true);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);
    maxExpectedEta += getIrisEtaResponse.getEtaBreakDown().getOriginalGoogleEtaInSec();
    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, expected max eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta with cust loc as store and cart product with peak buffer -ve config values and validate response")
  public void validateEtaForStoreLocationCartProductsAndPeakBufferNegativeValue() {

    // doing basic setup and reading expected eta from conf
    int minExpectedEta = config.getInt("etaValuesWithStoreLocCartProductsAndNegativeBuffer.min");
    int maxExpectedEta = config.getInt("etaValuesWithStoreLocCartProductsAndNegativeBuffer.max");
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    irisEtaHelper.setBasicConfigForEta(
        storeId, "etaValuesWithStoreLocCartProductsAndNegativeBuffer");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(storeId, "createConsignmentData", lat, longitude, true);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta with cust loc as store and cart product with peak buffer +ve config values and validate response")
  public void validateEtaForStoreLocationAndPeakBufferPositiveValue() {

    // doing basic setup and reading expected eta from conf
    int minExpectedEta = config.getInt("etaValuesWithStoreLocAndPositiveBuffer.min");
    int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndPositiveBuffer.max");

    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndPositiveBuffer");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(storeId, "createConsignmentData", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      enabled = false,
      groups = {TestType.SANITY},
      description = "Get eta with cust loc as store with rider stress and validate response")
  public void validateHighEtaForStoreLocationRiderStress() {
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    DeliveryConfigRequest deliveryConfigRequest =
        DeliveryConfigRequest.builder()
            .storeId(storeId)
            .destLatitude(lat)
            .destLongitude(longitude)
            .build();
    try {
      // doing basic setup and reading expected eta from conf
      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndHighRiderStress");

      // call eta api and validate response
      GetEtaFromIrisRequest getEtaFromIrisRequest =
          irisEtaHelper.createIrisEtaRequest(
              storeId, "createConsignmentData", lat, longitude, false);
      Response response =
          irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      BaseResponse getEtaResponse =
          MapperUtils.deserialize(response.asString(), BaseResponse.class);
      ;
      ObjectMapper objectMapper =
          new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      GetIrisEtaResponse getIrisEtaResponse =
          objectMapper.readValue(
              RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
              GetIrisEtaResponse.class);

      Assert.assertNull(
          getIrisEtaResponse.getTotalEtaInMin(),
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "Expecting heta case with eta null"));
      Assert.assertEquals(
          getIrisEtaResponse.getNonDeliverableReason(),
          GeneralConstants.ORDERS_STOPPED_SYSTEM,
          "Expecting order stopped message");
      Assert.assertTrue(
          getIrisEtaResponse.getOrderRiderStressDetails().getRiderStressThresholdBreached(),
          "Expecting threshold breach to be true");
      Assert.assertTrue(
          getIrisEtaResponse.getOrderRiderStressDetails().getRiderStressAutoBannerEnabled(),
          "Expecting stress auto banner to be true");
    } catch (AssertionError assertionError) {
      Response response =
          irisEtaApiHelper.getDeliveryConfig(
              environment.getReqSpecBuilder(), deliveryConfigRequest, null);
      logger.info("Assertion error failure for delivery config response :: " + response.body());
      throw assertionError;
    } finally {
      globalUtil.deleteRedisKey(String.format(GeneralConstants.RIDER_STRESS_STORE_KEY, storeId));
    }
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description = "Get eta with cust loc outside geofence and validate response")
  public void validateUnserviceableErrorForLocationOutsideGeofence() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValueForUnserviceableGeofence");

    // call eta api and validate response
    double lat = config.getDouble("etaValueForUnserviceableGeofence.lat");
    double longitude = config.getDouble("etaValueForUnserviceableGeofence.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(storeId, "createConsignmentData", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    Assert.assertNull(
        getIrisEtaResponse.getTotalEtaInMin(),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "Expecting heta case with eta null"));
    Assert.assertEquals(
        getIrisEtaResponse.getNonDeliverableReason(),
        GeneralConstants.UNSERVICEABLE_AREA,
        "Expecting UNSERVICEABLE_AREA message");

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);

    Assert.assertEquals(
        getMainEtaResponse.getDisplayText(),
        GeneralConstants.storeClosed,
        "Expecting store closed error");
    Assert.assertEquals(
        getMainEtaResponse.getDeliverableType(),
        GeneralConstants.CLOSED,
        "Expecting store closed error");
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta with cust loc as store with packer stress values and validate response")
  public void validateHighEtaForStoreLocationPackerStress() {
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    DeliveryConfigRequest deliveryConfigRequest =
        DeliveryConfigRequest.builder()
            .storeId(storeId)
            .destLatitude(lat)
            .destLongitude(longitude)
            .build();
    try {
      // doing basic setup and reading expected eta from conf
      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndHighPackerStress");

      // call eta api and validate response
      GetEtaFromIrisRequest getEtaFromIrisRequest =
          irisEtaHelper.createIrisEtaRequest(
              storeId, "createConsignmentData", lat, longitude, true);
      Response response =
          irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      BaseResponse getEtaResponse =
          MapperUtils.deserialize(response.asString(), BaseResponse.class);
      ;
      ObjectMapper objectMapper =
          new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      GetIrisEtaResponse getIrisEtaResponse =
          objectMapper.readValue(
              RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
              GetIrisEtaResponse.class);

      Assert.assertNull(
          getIrisEtaResponse.getTotalEtaInMin(),
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "Expecting heta case with eta null"));
      Assert.assertEquals(
          getIrisEtaResponse.getNonDeliverableReason(),
          GeneralConstants.ORDERS_STOPPED_SYSTEM,
          "Expecting order stopped message");
      Assert.assertTrue(
          getIrisEtaResponse.getOrderPackerStressDetails().getPackerStressThresholdBreached(),
          "Expecting threshold breach to be true");
      Assert.assertTrue(
          getIrisEtaResponse.getOrderPackerStressDetails().getPackerStressAutoBannerEnabled(),
          "Expecting stress auto banner to be true");
    } catch (AssertionError assertionError) {
      Response response =
          irisEtaApiHelper.getDeliveryConfig(
              environment.getReqSpecBuilder(), deliveryConfigRequest, null);
      logger.info("Assertion error failure for delivery config response :: " + response.body());
      throw assertionError;
    } finally {
      globalUtil.deleteRedisKey(String.format(GeneralConstants.PACKER_STRESS_STORE_KEY, storeId));
    }
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description = "Get eta with cust loc as store with stand still and validate response")
  public void validateEtaForStoreLocationAndStandStillMode() {
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    String compatibleComp =
        config.getString("etaValuesWithStoreLocAndStandStillMode.compatibleComponents");
    DeliveryConfigRequest deliveryConfigRequest =
        DeliveryConfigRequest.builder()
            .storeId(storeId)
            .destLatitude(lat)
            .destLongitude(longitude)
            .build();
    try {
      // doing basic setup and reading expected eta from conf
      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndStandStillMode");

      // call eta api and validate response
      GetEtaFromIrisRequest getEtaFromIrisRequest =
          irisEtaHelper.createIrisEtaRequest(
              storeId, "createConsignmentData", lat, longitude, false);
      Response response =
          irisEtaApiHelper.getEtaIris(
              environment.getReqSpecBuilder(), getEtaFromIrisRequest, compatibleComp);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      BaseResponse getEtaResponse =
          MapperUtils.deserialize(response.asString(), BaseResponse.class);
      ;
      ObjectMapper objectMapper =
          new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      GetIrisEtaResponse getIrisEtaResponse =
          objectMapper.readValue(
              RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
              GetIrisEtaResponse.class);
      // expecting eta in iris api
      int minExpectedEta = config.getInt("etaValuesWithStoreLocAndStandStillMode.min");
      int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndStandStillMode.max");
      maxExpectedEta += getIrisEtaResponse.getEtaBreakDown().getOriginalGoogleEtaInSec();

      // for standstill batching is enabled by default
      maxExpectedEta += irisEtaHelper.getBatchingTimeForStore(storeId);

      logger.info(
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "green",
              "ETA is :: "
                  + getIrisEtaResponse.getTotalEtaInMin()
                  + ", Max expected eta is :: "
                  + maxExpectedEta));
      Assert.assertTrue(
          maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is out of defined range, Max expected eta is "
                  + maxExpectedEta
                  + " but found "
                  + getIrisEtaResponse.getTotalEtaInMin()));
      Assert.assertTrue(
          minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
          "eta is out of defined range, min expected eta is "
              + minExpectedEta
              + " but found "
              + getIrisEtaResponse.getTotalEtaInMin());

      // get zeb api response for eta
      response =
          irisEtaApiHelper.getEtaHomePage(
              environment.getReqSpecBuilder(), storeId, lat, longitude, null, compatibleComp);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      // validate zeb api response for eta with standstill open delivery
      GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
      irisEtaHelper.validateHighEta(false, true, false, getMainEtaResponse);
    } catch (AssertionError assertionError) {
      Response response =
          irisEtaApiHelper.getDeliveryConfig(
              environment.getReqSpecBuilder(), deliveryConfigRequest, compatibleComp);
      logger.info("Assertion error failure for delivery config response :: " + response.body());
      throw assertionError;
    } finally {
      // disable stand still for store
      irisEtaHelper.enableStandStillForStore(storeId, false);
      irisEtaApiHelper.syncEtaConfigs(environment.getReqSpecBuilder(), storeId);
    }
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description = "Get eta with cust loc as store with batching mode on and validate response")
  public void validateEtaForStoreLocationAndBatchingMode() {

    // doing basic setup and reading expected eta from conf
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    DeliveryConfigRequest deliveryConfigRequest =
        DeliveryConfigRequest.builder()
            .storeId(storeId)
            .destLatitude(lat)
            .destLongitude(longitude)
            .build();
    try {
      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndBatchingEnabled");

      // call eta api and validate response
      GetEtaFromIrisRequest getEtaFromIrisRequest =
          irisEtaHelper.createIrisEtaRequest(
              storeId, "createConsignmentData", lat, longitude, false);
      Response response =
          irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      BaseResponse getEtaResponse =
          MapperUtils.deserialize(response.asString(), BaseResponse.class);
      ;
      ObjectMapper objectMapper =
          new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      GetIrisEtaResponse getIrisEtaResponse =
          objectMapper.readValue(
              RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
              GetIrisEtaResponse.class);
      // calibrated diff for time
      int minExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariable.min");
      int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariable.max");
      maxExpectedEta += getIrisEtaResponse.getEtaBreakDown().getOriginalGoogleEtaInSec();

      // batching is enabled
      maxExpectedEta += irisEtaHelper.getBatchingTimeForStore(storeId);

      logger.info(
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "green",
              "ETA is :: "
                  + getIrisEtaResponse.getTotalEtaInMin()
                  + ", Max expected eta is :: "
                  + maxExpectedEta));
      Assert.assertTrue(
          maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is out of defined range, Max expected eta is "
                  + maxExpectedEta
                  + " but found "
                  + getIrisEtaResponse.getTotalEtaInMin()));
      Assert.assertTrue(
          minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is less than expected min value"));

      // get zeb api response for eta
      response =
          irisEtaApiHelper.getEtaHomePage(
              environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      // validate zeb api response for eta with standstill open delivery
      GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
      Assert.assertEquals(
          getIrisEtaResponse.getTotalEtaInMin(),
          Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "Expecting eta to be same in iris and zeb"));
      Assert.assertTrue(
          maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is out of defined range, Max expected eta is "
                  + maxExpectedEta
                  + " but found "
                  + getIrisEtaResponse.getTotalEtaInMin()));
      Assert.assertTrue(
          minExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) <= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is less than expected min value"));
    } catch (AssertionError assertionError) {
      Response response =
          irisEtaApiHelper.getDeliveryConfig(
              environment.getReqSpecBuilder(), deliveryConfigRequest, null);
      logger.info(
          "Assertion error failure for delivery config response :: " + response.body().asString());
      throw assertionError;
    } finally {
      // reset batching enabled post execution
      globalUtil.deleteRedisKey(
          String.format(
              config.getString("etaValuesWithStoreLocAndBatchingEnabled.stress_cache_key"),
              storeId));
    }
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta with cust loc as store without checked in riders and validate response")
  public void validateHighEtaForStoreLocationWithNoRidersCheckedIn() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithNoRidersAvailableInStore");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    // validate non deliverable reason
    Assert.assertNull(getIrisEtaResponse.getTotalEtaInMin(), "Expecting eta to be null");
    Assert.assertEquals(
        getIrisEtaResponse.getNonDeliverableReason(),
        GeneralConstants.ORDERS_STOPPED_SYSTEM,
        "Expecting high eta message here");

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta with standstill open delivery
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    irisEtaHelper.validateHighEta(false, false, true, getMainEtaResponse);
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta and validate walker is assigned for store location delivery with other vehicles checked-in riders")
  public void validateWalkerIsAssignedForZeroDistanceWithMinETA() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(
        storeId, "etaValuesWithStoreLocWithRidersTypeWalkerAndCycle");

    logger.info("Checked in 1 cyclist and 1 walker");
    logger.info("wait for 100ms to sync data");
    Thread.sleep(100);

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta = config.getInt("etaValuesWithStoreLocWithRidersTypeWalkerAndCycle.min");
    int maxExpectedEta = config.getInt("etaValuesWithStoreLocWithRidersTypeWalkerAndCycle.max");

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertEquals(
        getIrisEtaResponse.getRiderDetails().getRider().getVehicle().toUpperCase(),
        "WALKER",
        "Expecting walker to assigned");
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta with standstill open delivery
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    Assert.assertEquals(
        getIrisEtaResponse.getTotalEtaInMin(),
        Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting eta to be same in iris and zeb"));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
        "eta is out of defined range, Max expected eta is "
            + maxExpectedEta
            + " but found "
            + getMainEtaResponse.getEtaInMinutes());
    Assert.assertTrue(
        minExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta and validate cyclist is assigned for store location delivery with walker assigned")
  public void validateCyclistIsAssignedForZeroDistanceWithMinETAForOrderAssignedToWalker() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(
        storeId, "etaValuesWithStoreLocWithRidersTypeWalkerAssignedAndCycleUnassigned");

    logger.info("Checked in 1 cyclist and 1 walker");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.long");

    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta =
        config.getInt("etaValuesWithStoreLocWithRidersTypeWalkerAssignedAndCycleUnassigned.min");
    int maxExpectedEta =
        config.getInt("etaValuesWithStoreLocWithRidersTypeWalkerAssignedAndCycleUnassigned.max");

    int deltaThresholdInEta = maxExpectedEta - minExpectedEta;

    logger.info("Expecting cyclist to be soft assigned");
    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
    Assert.assertEquals(
        getIrisEtaResponse.getRiderDetails().getRider().getVehicle().toUpperCase(),
        "CYCLE",
        "Expecting cycle to assigned");

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta with standstill open delivery
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    Assert.assertEquals(
        getIrisEtaResponse.getTotalEtaInMin(),
        Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting eta to be same in iris and zeb"));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes())
            <= deltaThresholdInEta,
        "eta is out of defined range expected eta is "
            + minExpectedEta
            + " but found "
            + getIrisEtaResponse.getTotalEtaInMin());
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta and validate no walker is assigned for location > 150m delivery with other vehicles checked-in riders")
  public void validateNoWalkerIsAssignedForDistanceGreaterThanConfiguredDistanceETA() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(
        storeId, "etaValuesWithCustomer230mFromStoreWithRiderTypeWalkerAndCycle");

    logger.info("Checked in 1 cyclist and 1 walker");
    logger.info("wait for 100ms to sync data");
    Thread.sleep(100);

    // call eta api and validate response
    double lat =
        config.getDouble("etaValuesWithCustomer230mFromStoreWithRiderTypeWalkerAndCycle.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.long");
    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta = config.getInt("etaValuesWithStoreLocWithRidersTypeWalkerAndCycle.min");
    int maxExpectedEta = config.getInt("etaValuesWithStoreLocWithRidersTypeWalkerAndCycle.max");
    maxExpectedEta += getIrisEtaResponse.getEtaBreakDown().getOriginalGoogleEtaInSec();

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertNotEquals(
        getIrisEtaResponse.getRiderDetails().getRider().getVehicle().toUpperCase(),
        "WALKER",
        "Expecting other than walker to be assigned");
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta with standstill open delivery
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    Assert.assertEquals(
        getIrisEtaResponse.getTotalEtaInMin(),
        Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting eta to be same in iris and zeb"));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta and validate no walker,cyclist,e-cycle is assigned for location > 2500m delivery with other vehicles checked-in riders")
  public void
      validateBikeIsAssignedForDistanceGreaterThanConfiguredDistanceAndRTSGreaterThanPackingTimeETA() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(
        storeId, "etaValuesForRtsHigherThanPackingTimeAndDistance2500m");

    logger.info("Checked in 1 cyclist and 1 biker");
    logger.info("wait for 100ms to sync data");
    Thread.sleep(100);

    // call eta api and validate response
    double lat = config.getDouble("etaValuesForRtsHigherThanPackingTimeAndDistance2500m.lat");
    double longitude =
        config.getDouble("etaValuesForRtsHigherThanPackingTimeAndDistance2500m.long");

    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta = config.getInt("etaValuesForRtsHigherThanPackingTimeAndDistance2500m.min");
    int maxExpectedEta = config.getInt("etaValuesForRtsHigherThanPackingTimeAndDistance2500m.max");
    maxExpectedEta += getIrisEtaResponse.getEtaBreakDown().getOriginalGoogleEtaInSec();

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertEquals(
        getIrisEtaResponse.getRiderDetails().getRider().getVehicle().toUpperCase(),
        config.getString("etaValuesForRtsHigherThanPackingTimeAndDistance2500m.vehicle"),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting other than walker to be assigned"));
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range,Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));

    // validate rts time is > packing time
    Assert.assertTrue(
        getIrisEtaResponse.getEtaBreakDown().getRiderToStoreEtaInSec() > 210,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting rts to be considered in eta calculation :: and > 210 but found :: "
                + getIrisEtaResponse.getEtaBreakDown().getRiderToStoreEtaInSec()));

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    Assert.assertEquals(
        getIrisEtaResponse.getTotalEtaInMin(),
        Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting eta to be same in iris and zeb"));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta and validate no walker,cyclist,e-cycle is assigned for location > 2500m delivery with other vehicles checked-in riders")
  public void
      validateBikeIsAssignedForDistanceGreaterThanConfiguredDistanceAndRTSIsPackingTimeETA() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(
        storeId, "etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation");

    logger.info("Checked in 1 cyclist and 1 biker");
    logger.info("wait for 100ms to sync data");
    Thread.sleep(100);

    // call eta api and validate response
    double lat = config.getDouble("etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation.lat");
    double longitude =
        config.getDouble("etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation.long");

    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta =
        config.getInt("etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation.min");
    int maxExpectedEta =
        config.getInt("etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation.max");
    maxExpectedEta += getIrisEtaResponse.getEtaBreakDown().getOriginalGoogleEtaInSec();

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertEquals(
        getIrisEtaResponse.getRiderDetails().getRider().getVehicle().toUpperCase(),
        config.getString("etaValuesForRtsHigherThanPackingTimeAndDistance2500m.vehicle"),
        "Expecting other than walker to be assigned");
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));

    // validate rts time is > packing time
    Assert.assertTrue(
        getIrisEtaResponse.getEtaBreakDown().getRiderToStoreEtaInSec() <= 210,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting rts to be considered in eta calculation :: and <= 210 but found :: "
                + getIrisEtaResponse.getEtaBreakDown().getRiderToStoreEtaInSec()));

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    Assert.assertEquals(
        getIrisEtaResponse.getTotalEtaInMin(),
        Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting eta to be same in iris and zeb"));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description =
          "Get eta and validate walker is assigned for delivery with other vehicles checked-in riders")
  public void validateNoEtaChangeForStoreLocationAndRTSIsEqualToPackingTimeETA() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesForRtsEqualToPackingTimeAtStoreLocation");

    logger.info("Checked in 1 walker");
    logger.info("wait for 100ms to sync data");
    Thread.sleep(100);

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.long");

    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta =
        config.getInt("etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation.min");
    int maxExpectedEta =
        config.getInt("etaValuesForRtsEqualToPackingTimeAndAwayFromStoreLocation.max");

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertEquals(
        getIrisEtaResponse.getRiderDetails().getRider().getVehicle().toUpperCase(),
        "WALKER",
        "Expecting walker to be assigned");
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));

    // validate rts time is > packing time
    Assert.assertTrue(
        getIrisEtaResponse.getEtaBreakDown().getRiderToStoreEtaInSec() <= 210,
        "Expecting rts to be considered in eta calculation");

    // get zeb api response for eta
    response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    // validate zeb api response for eta
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    Assert.assertEquals(
        getIrisEtaResponse.getTotalEtaInMin(),
        Integer.parseInt(getMainEtaResponse.getEtaInMinutes()),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting eta to be same in iris and zeb"));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY},
      description = "Get eta and validate eta increased by building buffer value")
  public void validateEtaIncreaseBy3MinForBuildingBuffer() {

    // doing basic setup and reading expected eta from conf
    String storeId =
        environment.getGlobalTestData().getString("riderHomePageCheckInForWhitefieldStore.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesForBuildingIdInStoreGeofence");

    logger.info("Checked in 1 walker");
    logger.info("wait for 100ms to sync data");
    Thread.sleep(100);

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForWhitefieldStore.long");

    GetEtaFromIrisRequest getEtaFromIrisRequest =
        irisEtaHelper.createIrisEtaRequest(
            storeId, "riderHomePageCheckInForWhitefieldStore", lat, longitude, false);
    getEtaFromIrisRequest.setBuildingId(
        config.getString("etaValuesForBuildingIdInStoreGeofence.buildingId"));

    Response response =
        irisEtaApiHelper.getEtaIris(environment.getReqSpecBuilder(), getEtaFromIrisRequest, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    BaseResponse getEtaResponse = MapperUtils.deserialize(response.asString(), BaseResponse.class);
    ;
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GetIrisEtaResponse getIrisEtaResponse =
        objectMapper.readValue(
            RequestMapper.mapPojoObjectAndGetString(getEtaResponse.getData()),
            GetIrisEtaResponse.class);

    int minExpectedEta = config.getInt("etaValuesForBuildingIdInStoreGeofence.min");
    int maxExpectedEta = config.getInt("etaValuesForBuildingIdInStoreGeofence.max");

    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getIrisEtaResponse.getTotalEtaInMin()
                + " Max expected eta is :: "
                + maxExpectedEta));
    // validate eta and building buffer value
    Assert.assertTrue(
        maxExpectedEta - getIrisEtaResponse.getTotalEtaInMin() >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getIrisEtaResponse.getTotalEtaInMin()));
    Assert.assertTrue(
        minExpectedEta - getIrisEtaResponse.getTotalEtaInMin() <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
    Assert.assertEquals(
        getIrisEtaResponse.getEtaBreakDown().getBuildingBufferInSec(),
        config.getInt("etaValuesForBuildingIdInStoreGeofence.buildingBuffer"),
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "Expecting building buffer value to match"));
  }
}
