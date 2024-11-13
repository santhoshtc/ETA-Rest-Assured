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

public class GeocodeApiTest extends TestBase {

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
      description = "verify geocode details api for valid lat longitude",
      dataProvider = "getGeocodeResponseByLatLongitude",
      dataProviderClass = ZeptoMapDataProvider.class)
  public void verifyLocationDetailsApiResponseForValidGeocode(
      Double lat, Double longitude, String responseExpected) {
    ExtentReportUtil.startTest("verifyLocationDetailsApiByInValidGeocode");

    logger.info("call and verify geocode api");
    Response response =
        zeptoMapApiHelper.getGeocodeApi(
            environment.getReqSpecBuilder(), lat, longitude, Boolean.TRUE);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Get geocode api response status code is not "
            + HttpStatus.SC_OK
            + ", it is "
            + response.getStatusCode());

    logger.info("Expected Response : " + responseExpected);
    Assert.assertEquals(
        response.asString(),
        responseExpected,
        "Geocode api response is not as expected for lat : "
            + lat
            + " and longitude : "
            + longitude);
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
      description = "verify location details api by  invalid lat longitude",
      dataProvider = "getGeocodeResponseByInvalidLatLongitude",
      dataProviderClass = ZeptoMapDataProvider.class)
  public void verifyLocationDetailsApiByInValidGeocode(
      Double lat, Double longitude, String responseExpected) {
    ExtentReportUtil.startTest("verifyLocationDetailsApiByInValidGeocode");

    logger.info("call and verify geocode api");
    Response response =
        zeptoMapApiHelper.getGeocodeApi(
            environment.getReqSpecBuilder(), lat, longitude, Boolean.FALSE);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_INTERNAL_SERVER_ERROR,
        "Get geocode api response status code is not "
            + HttpStatus.SC_OK
            + ", it is "
            + response.getStatusCode());

    logger.info("Expected Response : " + responseExpected);
    Assert.assertEquals(
        response.asString(),
        responseExpected,
        "Geocode api response is not as expected for lat : "
            + lat
            + " and longitude : "
            + longitude);
  }
}
