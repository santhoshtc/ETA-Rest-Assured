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

public class AutocompleteApiTest extends TestBase {

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
      description = "verify autocomplete api for valid location",
      dataProvider = "getAutocompleteResponseByPlaceName",
      dataProviderClass = ZeptoMapDataProvider.class)
  public void verifyAutocompleteApiResponseForValidLocation(
      String placeName, String responseExpected) {
    ExtentReportUtil.startTest(
        "verifyAutocompleteApiResponseForValidLocation_placeName: " + placeName);

    logger.info("call and verify autocomplete api");
    Response response =
        zeptoMapApiHelper.getAutocompleteApi(
            environment.getReqSpecBuilder(), placeName, Boolean.TRUE);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Get autocomplete api response status code is not "
            + HttpStatus.SC_OK
            + ", it is "
            + response.getStatusCode());
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
      description = "verify autocomplete api for invalid location",
      dataProvider = "getAutocompleteResponseByInvalidPlaceName",
      dataProviderClass = ZeptoMapDataProvider.class)
  public void verifyAutocompleteApiResponseForInValidLocation(
      String placeName, String responseExpected) {
    ExtentReportUtil.startTest(
        "verifyAutocompleteApiResponseForInValidLocation_placeName: " + placeName);

    logger.info("call and verify autocomplete api");
    Response response =
        zeptoMapApiHelper.getAutocompleteApi(
            environment.getReqSpecBuilder(), placeName, Boolean.FALSE);
    Assert.assertEquals(
        response.getStatusCode(),
        HttpStatus.SC_OK,
        "Get autocomplete api response status code is not "
            + HttpStatus.SC_OK
            + ", it is "
            + response.getStatusCode());
  }
}
