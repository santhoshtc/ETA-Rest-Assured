package com.zeptonow.automation.eta;

import com.epam.reportportal.annotations.attribute.Attributes;
import com.epam.reportportal.annotations.attribute.MultiValueAttribute;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.configUtil.TestAttributes;
import com.zepto.common.configUtil.TestPriority;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.constants.Services.Name;
import com.zeptonow.commonlm.constants.TestOwners;
import com.zeptonow.commonlm.constants.TestType;
import com.zeptonow.commonlm.helper.api.IrisEtaApiHelper;
import com.zeptonow.commonlm.pojo.iris_eta.request.DeliveryConfigRequest;
import com.zeptonow.commonlm.pojo.iris_eta.response.GetMainEtaResponse;
import com.zeptonow.controllerlm.servicehelper.IrisEtaHelper;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class GetEtaTest extends TestBase {

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

  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY, TestType.SANITY},
      description =
          "Get eta with cust loc as store with default config values and validate response")
  public void validateEtaForStoreLocationAndDefaultConfig() {

    // doing basic setup and reading expected eta from conf
    int minExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariable.min");
    int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariable.max");

    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndMinVariable");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    Response response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), "73bfbb51-d67b-4eff-9a4f-37c044022f89", lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    logger.info(
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "green",
            "ETA is :: "
                + getMainEtaResponse.getEtaInMinutes()
                + ", Max expected eta is :: "
                + maxExpectedEta));
    Assert.assertTrue(
        maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
            "red",
            "eta is out of defined range, Max expected eta is "
                + maxExpectedEta
                + " but found "
                + getMainEtaResponse.getEtaInMinutes()));
    Assert.assertTrue(
        minExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) <= 0,
        String.format(
            GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "eta is less than expected min value"));
  }

  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY, TestType.SANITY},
      description =
          "Get eta with cust loc as store with default config values and low rain validation")
  public void validateEtaForStoreLocationWithDefaultConfigAndLowRain() {
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
      int minExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariableAndMinRain.min");
      int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariableAndMinRain.max");

      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndMinVariableAndMinRain");

      // call eta api and validate response
      Response response =
          irisEtaApiHelper.getEtaHomePage(
              environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
      logger.info(
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "green",
              "ETA is :: "
                  + getMainEtaResponse.getEtaInMinutes()
                  + ", Max expected eta is :: "
                  + maxExpectedEta));
      Assert.assertTrue(
          maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is out of defined range, Max expected eta is "
                  + maxExpectedEta
                  + " but found "
                  + getMainEtaResponse.getEtaInMinutes()));
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
      irisEtaHelper.setRainIntensity(
          environment.getGlobalTestData().getString("createConsignmentData.storeId"),
          "etaValuesWithStoreLocAndOtherVariablesAndNoRain");
    }
  }

  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY, TestType.SANITY},
      description =
          "Get eta with cust loc as store with default config values and mild rain validation")
  public void validateEtaForStoreLocationWithDefaultConfigAndMildRain() {
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
      int minExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariableAndMildRain.min");
      int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariableAndMildRain.max");

      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndMinVariableAndMildRain");

      // call eta api and validate response
      Response response =
          irisEtaApiHelper.getEtaHomePage(
              environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
      logger.info(
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "green",
              "ETA is :: "
                  + getMainEtaResponse.getEtaInMinutes()
                  + ", Max expected eta is :: "
                  + maxExpectedEta));
      Assert.assertTrue(
          maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              "eta is out of defined range, Max expected eta is "
                  + maxExpectedEta
                  + " but found "
                  + getMainEtaResponse.getEtaInMinutes()));
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
      irisEtaHelper.setRainIntensity(
          environment.getGlobalTestData().getString("createConsignmentData.storeId"),
          "etaValuesWithStoreLocAndOtherVariablesAndNoRain");
    }
  }

  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY, TestType.SANITY},
      description =
          "Get eta with cust loc as store with default config values and high rain validation")
  public void validateEtaForStoreLocationWithDefaultConfigAndHighRain() {
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
      int minExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariableAndHighRain.min");
      int maxExpectedEta = config.getInt("etaValuesWithStoreLocAndMinVariableAndHighRain.max");

      irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndMinVariableAndHighRain");

      // call eta api and validate response
      Response response =
          irisEtaApiHelper.getEtaHomePage(
              environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
      logger.info(
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "green",
              "ETA is :: "
                  + getMainEtaResponse.getEtaInMinutes()
                  + ", Max expected eta is :: "
                  + maxExpectedEta));
      Assert.assertTrue(
          maxExpectedEta - Integer.parseInt(getMainEtaResponse.getEtaInMinutes()) >= 0,
          String.format(
              GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
              "red",
              String.format(
                  GeneralConstants.MAKE_TEXT_HIGHLIGHTED,
                  "red",
                  "eta is out of defined range, Max expected eta is "
                      + maxExpectedEta
                      + " but found "
                      + getMainEtaResponse.getEtaInMinutes())));
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
      irisEtaHelper.setRainIntensity(
          environment.getGlobalTestData().getString("createConsignmentData.storeId"),
          "etaValuesWithStoreLocAndOtherVariablesAndNoRain");
    }
  }

  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY, TestType.SANITY},
      description =
          "Get eta with cust loc as store with other config values and high rain validation for high eta")
  public void validateHighEtaForStoreLocationWithConfigsAndHighRain() {
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
      irisEtaHelper.setBasicConfigForEta(
          storeId, "etaValuesWithStoreLocAndOtherVariablesAndHighRain");

      // call eta api and validate response
      Response response =
          irisEtaApiHelper.getEtaHomePage(
              environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

      GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
      logger.info(
          String.format(GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "Expecting HETA here!!"));
      irisEtaHelper.validateHighEta(true, false, false, getMainEtaResponse);
    } catch (AssertionError assertionError) {
      Response response =
          irisEtaApiHelper.getDeliveryConfig(
              environment.getReqSpecBuilder(), deliveryConfigRequest, null);
      logger.info(
          "Assertion error failure for delivery config response :: " + response.body().asString());
      throw assertionError;
    } finally {
      irisEtaHelper.setRainIntensity(
          environment.getGlobalTestData().getString("createConsignmentData.storeId"),
          "etaValuesWithStoreLocAndOtherVariablesAndNoRain");
    }
  }

  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {TestOwners.OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.IRIS_ETA})
      })
  @Test(
      groups = {TestType.SANITY, TestType.SANITY},
      description =
          "Get eta with cust loc as store with other config values and high rain validation for high eta")
  public void validateHighEtaForStoreLocationWithConfigsAndNoRain() {

    // doing basic setup and reading expected eta from conf
    String storeId = environment.getGlobalTestData().getString("createConsignmentData.storeId");
    irisEtaHelper.setBasicConfigForEta(storeId, "etaValuesWithStoreLocAndOtherVariablesAndNoRain");

    // call eta api and validate response
    double lat =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.lat");
    double longitude =
        environment.getGlobalTestData().getDouble("riderHomePageCheckInForAutomationStore.long");
    Response response =
        irisEtaApiHelper.getEtaHomePage(
            environment.getReqSpecBuilder(), storeId, lat, longitude, null, null);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expecting 200 status code");

    logger.info(
        String.format(GeneralConstants.MAKE_TEXT_HIGHLIGHTED, "red", "Expecting HETA here!!"));
    GetMainEtaResponse getMainEtaResponse = response.as(GetMainEtaResponse.class);
    irisEtaHelper.validateHighEta(false, false, true, getMainEtaResponse);
  }
}
