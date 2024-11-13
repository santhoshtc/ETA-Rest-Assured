package com.zeptonow.automation.zeptoMap.test;

import com.epam.reportportal.annotations.attribute.Attributes;
import com.epam.reportportal.annotations.attribute.MultiValueAttribute;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.configUtil.TestAttributes;
import com.zepto.common.configUtil.TestPriority;
import com.zepto.common.logger.LoggerUtil;
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.automation.zeptoMap.dataProvider.ZeptoMapDataProvider;
import com.zeptonow.commonlm.constants.Services.Name;
import com.zeptonow.commonlm.constants.TestOwners.OwnerName;
import com.zeptonow.commonlm.constants.TestType;
import com.zeptonow.commonlm.helper.api.ZeptoMapApiHelper;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LocationDetailsApiTest extends TestBase {

  private final LoggerUtil logger = new LoggerUtil(this.getClass());
  private final ZeptoMapApiHelper zeptoMapApiHelper = new ZeptoMapApiHelper();

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.ZEPTOMAP})
      })
  @Test(
      groups = {TestType.SANITY, TestType.ZEPTOMAP},
      description = "verify location details api for valid placeId",
      dataProvider = "getLocationDetailsResponseByPlaceId",
      dataProviderClass = ZeptoMapDataProvider.class)
  public void verifyLocationDetailsApiResponseForValidPlaceId(
      String placeId, String responseExpected) {
    ExtentReportUtil.startTest("verifyLocationDetailsApiResponseForValidPlaceId");

    logger.info("call and verify location details api");
    Response response =
        zeptoMapApiHelper.getLocationDetailsApi(
            environment.getReqSpecBuilder(), placeId, Boolean.TRUE);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Get location details api response status code is not "
            + HttpStatus.SC_OK
            + ", it is "
            + response.getStatusCode());

    logger.info("Expected Response : " + responseExpected);
    Assert.assertEquals(
        response.asString(),
        responseExpected,
        "location details api response by place Id is not as expected for placeId :" + placeId);
  }

  @SneakyThrows
  @Attributes(
      multiValueAttributes = {
        @MultiValueAttribute(
            key = TestAttributes.OWNER,
            values = {OwnerName.SANTHOSH_TC}),
        @MultiValueAttribute(values = {TestType.SANITY, TestPriority.P0, Name.ZEPTOMAP})
      })
  @Test(
      groups = {TestType.SANITY, TestType.ZEPTOMAP},
      description = "verify location details api for invalid placeId",
      dataProvider = "getLocationDetailsResponseByInvalidPlaceId",
      dataProviderClass = ZeptoMapDataProvider.class)
  public void verifyLocationDetailsApiByInValidPlaceId(String placeId, String responseExpected) {
    ExtentReportUtil.startTest("verifyLocationDetailsApiByInValidPlaceId");

    logger.info("call and verify location details api");
    Response response =
        zeptoMapApiHelper.getLocationDetailsApi(
            environment.getReqSpecBuilder(), placeId, Boolean.FALSE);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_INTERNAL_SERVER_ERROR,
        "Get location details api response status code is not "
            + HttpStatus.SC_OK
            + ", it is "
            + response.getStatusCode());

    logger.info("Expected Response : " + responseExpected);
    Assert.assertEquals(
        response.asString(),
        responseExpected,
        "location details api response by place Id is not as expected for placeId :" + placeId);
  }
}
