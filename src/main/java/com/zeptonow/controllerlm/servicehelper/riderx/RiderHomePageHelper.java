/***
 * Date: 02/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.controllerlm.servicehelper.riderx;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.DBQueries;
import com.zeptonow.commonlm.helper.api.riderx.RiderHomePageApiHelper;
import com.zeptonow.commonlm.helper.mysql.MySqlRmsHelper;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.commonlm.pojo.riderhomepage.request.RiderCheckInCheckOutRequest;
import com.zeptonow.commonlm.pojo.riderhomepage.response.CreateBannerResponse;
import com.zeptonow.commonlm.pojo.riderhomepage.response.GetBannerResponse;
import com.zeptonow.commonlm.pojo.riderhomepage.response.UpdateBannerResponse;
import com.zeptonow.commonlm.pojo.riderhomepage.subclass.Datum;
import com.zeptonow.controllerlm.servicehelper.RiderHelper;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.testng.Assert;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static com.zeptonow.commonlm.constants.DBQueries.getBannerDetailsById;

public class RiderHomePageHelper extends TestBase {

  private LoggerUtil logger;
  private Environment environment;
  private GlobalUtil globalUtil;
  private MySqlRmsHelper mySqlRmsHelper;
  private RiderHelper riderHelper;
  private RiderHomePageApiHelper riderHomePageApiHelper;

  public RiderHomePageHelper() {
    logger = new LoggerUtil(this.getClass());
    environment = new Environment();
    globalUtil = new GlobalUtil();
    mySqlRmsHelper = new MySqlRmsHelper();
    riderHelper = new RiderHelper();
    riderHomePageApiHelper = new RiderHomePageApiHelper();
  }

  /**
   * @param latitude
   * @param longitude
   * @param type
   * @return
   */
  public RiderCheckInCheckOutRequest generateRiderCheckInCheckOutPayload(
      Double latitude, Double longitude, String type) {

    return RiderCheckInCheckOutRequest.builder()
        .latitude(latitude)
        .longitude(longitude)
        .type(type)
        .build();
  }

  /**
   * Get shift total break time
   *
   * @param shiftId
   * @return
   * @throws SQLException
   * @author Ajay_Mishra
   */
  public int shiftTotalBreakTime(int shiftId) throws SQLException {
    JSONArray result =
        mySqlRmsHelper.queryDb(
            String.format(DBQueries.getShiftDetailsFromShiftSliceTable, shiftId));
    int totalBreakTime = 0;
    for (int i = 0; i < result.length(); i++) {

      String expectedBreakTime = result.getJSONObject(i).get("break").toString();
      logger.info(expectedBreakTime);
      int hours =
          Integer.parseInt(
              expectedBreakTime.substring(
                  expectedBreakTime.indexOf("days") + 5, expectedBreakTime.indexOf("hours") - 1));
      int minutes =
          Integer.parseInt(
              expectedBreakTime.substring(
                  expectedBreakTime.indexOf("hours") + 6, expectedBreakTime.indexOf("mins") - 1));
      int expectedBreakTimeInMinutes = hours * 60 + minutes;
      totalBreakTime = totalBreakTime + expectedBreakTimeInMinutes;
    }
    return totalBreakTime;
  }

  /**
   * Function to get formData for create Banner api
   *
   * @param isActive
   * @param deepLinkType
   * @param type
   * @param subType
   * @param deepLinkUrl
   * @param tag
   * @return
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public HashMap<Object, Object> prepareBannerFormDataForCreateBanner(
      boolean isActive,
      String deepLinkType,
      String type,
      String subType,
      String deepLinkUrl,
      String tag,
      String deepLink,
      String name) {

    HashMap<Object, Object> formParam = new HashMap<>();

    // creating temp folder
    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator;
    File tmpFolder = new File(tempFilePath);
    tmpFolder.mkdir();

    formParam.put("image", globalUtil.createDummyImage(tempFilePath + "BannerImage.jpg"));
    formParam.put("name", name);
    formParam.put("priority", 6);
    formParam.put("isActive", isActive);
    formParam.put("deeplink", deepLink);
    formParam.put("deeplinkType", deepLinkType);
    formParam.put("clickable", true);
    formParam.put("type", type);
    formParam.put("subtype", subType);
    formParam.put("deeplinkUrl", deepLinkUrl);
    formParam.put("tag", tag);

    return formParam;
  }

  /**
   * Function to get formData for update Banner api
   *
   * @param isActive
   * @param deepLinkType
   * @param type
   * @param subType
   * @param deepLinkUrl
   * @param tag
   * @return
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public HashMap<Object, Object> prepareBannerFormDataForUpdateBanner(
      String deepLinkType,
      String type,
      String subType,
      String deepLinkUrl,
      String tag,
      String deepLink,
      String id,
      String name,
      boolean isActive,
      int priority) {

    HashMap<Object, Object> formParam = new HashMap<>();

    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator;
    File tmpFolder = new File(tempFilePath);
    tmpFolder.mkdir();

    formParam.put("image", globalUtil.createDummyImage(tempFilePath + "BannerImage.jpg"));
    formParam.put("name", name);
    formParam.put("priority", priority);
    formParam.put("isActive", isActive);
    formParam.put("deeplink", deepLink);
    formParam.put("deeplinkType", deepLinkType);
    formParam.put("clickable", true);
    formParam.put("type", type);
    formParam.put("subtype", subType);
    formParam.put("deeplinkUrl", deepLinkUrl);
    formParam.put("tag", tag);
    formParam.put("id", id);
    return formParam;
  }

  /**
   * Create banner function
   *
   * @param deepLinkType
   * @param type
   * @param subType
   * @param deepLinkUrl
   * @param tag
   * @param deepLink
   * @param isVerifySchema
   * @param authToken
   * @return
   * @author Abhishek_Singh
   */
  public Response createBanner(
      String deepLinkType,
      String type,
      String subType,
      String deepLinkUrl,
      String tag,
      String deepLink,
      boolean isVerifySchema,
      String name,
      String authToken,
      Boolean isActive) {

    Response response = null;
    HashMap<Object, Object> formParam =
        prepareBannerFormDataForCreateBanner(
            isActive, deepLinkType, type, subType, deepLinkUrl, tag, deepLink, name);

    response =
        riderHomePageApiHelper.createBanner(
            environment.getReqSpecBuilder(), isVerifySchema, authToken, formParam);
    return response;
  }

  /**
   * Function to validate banner response against DB entry
   *
   * @param createBannerResponse
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public void verifyBannerDetailsInDb(CreateBannerResponse createBannerResponse) {

    JSONArray bannerDbResponse =
        mySqlRmsHelper.queryDb(
            String.format(getBannerDetailsById, createBannerResponse.getData().getId()));

    String expectedBannerName =
        (bannerDbResponse.getJSONObject(0).getString("name").equalsIgnoreCase(""))
            ? null
            : bannerDbResponse.getJSONObject(0).getString("name");
    Assert.assertEquals(
        createBannerResponse.getData().getName(),
        expectedBannerName,
        "Banner Name not coming correctly in Db");
    Assert.assertEquals(
        createBannerResponse.getData().getDeeplink(),
        bannerDbResponse.getJSONObject(0).getString("deeplink"),
        "DeepLink not coming correctly in Db");
    Assert.assertEquals(
        createBannerResponse.getData().getDeeplinkType(),
        bannerDbResponse.getJSONObject(0).getString("deeplink_type"),
        "DeepLinkType not coming correctly in Db");
    Assert.assertEquals(
        createBannerResponse.getData().getType(),
        bannerDbResponse.getJSONObject(0).getString("type"),
        "Type not coming correctly in Db");
    Assert.assertEquals(
        createBannerResponse.getData().getSubtype(),
        bannerDbResponse.getJSONObject(0).getString("sub_type"),
        "SubType not coming correctly in Db");
    Assert.assertEquals(
        createBannerResponse.getData().getDeeplinkUrl(),
        bannerDbResponse.getJSONObject(0).getString("deep_link_url"),
        "DeepLinkUrl not coming correctly in Db");
    Assert.assertEquals(
        createBannerResponse.getData().getTag(),
        bannerDbResponse.getJSONObject(0).getString("tag"),
        "Tag not coming correctly in Db");
  }

  /**
   * Function to validate banner response against DB entry
   *
   * @param updateBannerResponse
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public void verifyUpdatedBannerDetailsInDb(UpdateBannerResponse updateBannerResponse) {

    JSONArray bannerDbResponse =
        mySqlRmsHelper.queryDb(
            String.format(getBannerDetailsById, updateBannerResponse.getData().getId()));

    String expectedBannerName =
        (bannerDbResponse.getJSONObject(0).getString("name").equalsIgnoreCase(""))
            ? null
            : bannerDbResponse.getJSONObject(0).getString("name");
    Assert.assertEquals(
        updateBannerResponse.getData().getName(),
        expectedBannerName,
        "Banner Name not coming correctly in Db");
    Assert.assertEquals(
        updateBannerResponse.getData().getDeeplink(),
        bannerDbResponse.getJSONObject(0).getString("deeplink"),
        "DeepLink not coming correctly in Db");
    Assert.assertEquals(
        updateBannerResponse.getData().getDeeplinkType(),
        bannerDbResponse.getJSONObject(0).getString("deeplink_type"),
        "DeepLinkType not coming correctly in Db");
    Assert.assertEquals(
        updateBannerResponse.getData().getType(),
        bannerDbResponse.getJSONObject(0).getString("type"),
        "Type not coming correctly in Db");
    Assert.assertEquals(
        updateBannerResponse.getData().getSubtype(),
        bannerDbResponse.getJSONObject(0).getString("sub_type"),
        "SubType not coming correctly in Db");
    Assert.assertEquals(
        updateBannerResponse.getData().getDeeplinkUrl(),
        bannerDbResponse.getJSONObject(0).getString("deep_link_url"),
        "DeepLinkUrl not coming correctly in Db");
    Assert.assertEquals(
        updateBannerResponse.getData().getTag(),
        bannerDbResponse.getJSONObject(0).getString("tag"),
        "Tag not coming correctly in Db");
  }

  /**
   * Compare get banner response against create banner response
   *
   * @param getBannerResponse
   * @param createBannerResponse
   * @author Abhishek_Singh
   */
  public void verifyGetBannerDetailsResponse(
      GetBannerResponse getBannerResponse, CreateBannerResponse createBannerResponse) {

    List<Datum> bannerData = getBannerResponse.getData();
    for (Datum data : bannerData) {
      if (data.getId().equalsIgnoreCase(createBannerResponse.getData().getId())) {
        Assert.assertEquals(
            data.getName(),
            createBannerResponse.getData().getName(),
            "Name is not coming correctly in getBanner response");
        Assert.assertEquals(
            data.getImageId(),
            createBannerResponse.getData().getImageId(),
            "ImageId is not coming correctly in getBanner response");
        Assert.assertEquals(
            data.getDeeplink(),
            createBannerResponse.getData().getDeeplink(),
            "Deeplink is not coming correctly in getBanner response");
        Assert.assertEquals(
            data.getType(),
            createBannerResponse.getData().getType(),
            "Type is not coming correctly in getBanner response");
        Assert.assertEquals(
            data.getSubtype(),
            createBannerResponse.getData().getSubtype(),
            "SubType is not coming correctly in getBanner response");
        Assert.assertEquals(
            data.getTag(),
            createBannerResponse.getData().getTag(),
            "Tag is not coming correctly in getBanner response");
        Assert.assertEquals(
            data.getPriority(),
            createBannerResponse.getData().getPriority(),
            "Priority is not coming correctly in getBanner response");
      }
    }
  }
}
