package com.zeptonow.commonlm.helper.api;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.pojo.iris_eta.request.DeliveryConfigRequest;
import com.zeptonow.commonlm.pojo.iris_eta.request.GetEtaFromIrisRequest;
import com.zeptonow.commonlm.pojo.iris_eta.request.UpdatePackingConfigRequest;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;

public class IrisEtaApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;
  private String clientAuth;

  public IrisEtaApiHelper() {
    logger = new LoggerUtil(IrisEtaApiHelper.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
    this.clientAuth = environment.getGlobalTestData().getString("etaClientAuth.clientToken");
  }

  /**
   * This function will call get store eta config
   *
   * @param requestSpecBuilder
   * @param storeId
   * @return
   * @author Manisha_Kumari
   */
  public Response getEtaConfigs(RequestSpecBuilder requestSpecBuilder, String storeId) {
    requestSpecBuilder.setBaseUri(IRIS_ETA_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("x-client-auth", GeneralConstants.CLIENT_KEY_ETA);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("storeId", storeId);

    String apiPath = ETA_SERVICE_ENDPOINT.get("getStoreEtaConfigs");

    Response response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    return response;
  }

  /**
   * This function will call sync store eta config from zeb
   *
   * @param requestSpecBuilder
   * @param storeId
   * @return
   * @author Manisha_Kumari
   */
  public Response syncEtaConfigs(RequestSpecBuilder requestSpecBuilder, String storeId) {
    requestSpecBuilder.setBaseUri(IRIS_ETA_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("x-client-auth", GeneralConstants.CLIENT_KEY_ETA);

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("storeId", storeId);

    String apiPath = ETA_SERVICE_ENDPOINT.get("syncEtaConfigFromZeb");

    Response response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, queryParams, null, null);
    return response;
  }

  /**
   * This function will call get store eta from zeb
   *
   * @param requestSpecBuilder
   * @param storeId
   * @param lat
   * @param longitude
   * @param compatibleComp
   * @param userId
   * @return
   * @author Manisha_Kumari
   */
  public Response getEtaHomePage(
      RequestSpecBuilder requestSpecBuilder,
      String storeId,
      double lat,
      double longitude,
      String userId,
      String compatibleComp) {
    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);
    compatibleComp =
        compatibleComp == null
            ? "SCHEDULED_DELIVERY,SCHEDULED_DELIVERY_M2,SAMPLING_FOR_COUPON_MOV_ENABLED,CONVENIENCE_FEE,RAIN_FEE,EXTERNAL_COUPONS,BUNDLE,MULTI_SELLER_ENABLED,PIP_V1,ROLLUPS,SCHEDULED_DELIVERY,OTOF,SAMPLING_ENABLED,ETA_NORMAL_WITH_149_DELIVERY,ROLLUPS_UOM,SAMPLING_V2,RE_PROMISE_ETA_ORDER_SCREEN_ENABLED,TEST"
            : compatibleComp;
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("compatible_components", compatibleComp);
    headers.put("appVersion", "12");
    headers.put("platform", "ALL");
    headers.put("source", "DIRECT");
    headers.put("bundle_version", "10");

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("store_id", storeId);
    queryParams.put("latitude", lat);
    queryParams.put("longitude", longitude);
    if (userId != null) {
      queryParams.put("user_id", userId);
    }

    String apiPath = INVENTORY_STORE_ENDPOINT.get("get-eta-main");

    Response response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    return response;
  }

  /**
   * This function will call get store eta from iris
   *
   * @param requestSpecBuilder
   * @param getEtaFromIrisRequest
   * @param compatibleComp
   * @return
   * @author Manisha_Kumari
   */
  public Response getEtaIris(
      RequestSpecBuilder requestSpecBuilder,
      GetEtaFromIrisRequest getEtaFromIrisRequest,
      String compatibleComp) {
    requestSpecBuilder.setBaseUri(IRIS_ETA_SERVICE_BASEURL);
    compatibleComp =
        compatibleComp == null
            ? "SCHEDULED_DELIVERY,SCHEDULED_DELIVERY_M2,SAMPLING_FOR_COUPON_MOV_ENABLED,CONVENIENCE_FEE,RAIN_FEE,EXTERNAL_COUPONS,BUNDLE,MULTI_SELLER_ENABLED,PIP_V1,ROLLUPS,SCHEDULED_DELIVERY,OTOF,SAMPLING_ENABLED,ETA_NORMAL_WITH_149_DELIVERY,ROLLUPS_UOM,SAMPLING_V2,RE_PROMISE_ETA_ORDER_SCREEN_ENABLED,TEST"
            : compatibleComp;
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("compatible_components", compatibleComp);

    String apiPath = ETA_SERVICE_ENDPOINT.get("getStoreEta");

    Response response = RestUtils.post(requestSpecBuilder, headers, apiPath, getEtaFromIrisRequest);
    return response;
  }

  /**
   * This function will call update packing config api
   *
   * @param requestSpecBuilder
   * @param updatePackingConfigRequest
   * @param
   * @return
   * @author Manisha_Kumari
   */
  public Response updatePackingConfigIris(
      RequestSpecBuilder requestSpecBuilder,
      UpdatePackingConfigRequest updatePackingConfigRequest) {
    requestSpecBuilder.setBaseUri(IRIS_ETA_SERVICE_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");

    String apiPath = ETA_SERVICE_ENDPOINT.get("updatePackingConfig");

    Response response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, updatePackingConfigRequest);
    return response;
  }

  /**
   * This function will call update peak buffer api
   *
   * @param requestSpecBuilder
   * @param peakBufferCsv
   * @param
   * @return
   * @author Manisha_Kumari
   */
  public Response updatePeakBufferIris(RequestSpecBuilder requestSpecBuilder, File peakBufferCsv) {
    requestSpecBuilder.setBaseUri(IRIS_ETA_SERVICE_BASEURL);
    String apiPath = ETA_SERVICE_ENDPOINT.get("updatePeakBuffer");
    Map<Object, Object> formParams = new HashMap<>();
    formParams.put("csvFile", peakBufferCsv);

    Response response = RestUtils.post(requestSpecBuilder, null, apiPath, null, null, formParams);
    return response;
  }

  /**
   * This function will call get peak buffer api
   *
   * @param requestSpecBuilder
   * @param storeId
   * @param
   * @return
   * @author Manisha_Kumari
   */
  public Response getPeakBufferIris(RequestSpecBuilder requestSpecBuilder, String storeId) {
    requestSpecBuilder.setBaseUri(IRIS_ETA_SERVICE_BASEURL);
    String apiPath = ETA_SERVICE_ENDPOINT.get("getPeakBufferConfig");

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("storeId", storeId);

    Response response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParams, null);
    return response;
  }

  /**
   * This function will call get store config api
   *
   * @param requestSpecBuilder
   * @param storeId
   * @param
   * @return
   * @author Manisha_Kumari
   */
  public Response getStoreConfigFromZeb(RequestSpecBuilder requestSpecBuilder, String storeId) {
    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);
    String apiPath = INVENTORY_STORE_ENDPOINT.get("get-store-eta-configs");

    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("x-client-auth", clientAuth);

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("store_id", storeId);

    Response response = RestUtils.get(requestSpecBuilder, headers, apiPath, queryParams, null);
    return response;
  }

  /**
   * This function will get delivery config by request
   *
   * @param requestSpecBuilder
   * @param deliveryConfigRequest
   * @param compatibleComponents
   * @return
   * @author Manisha_Kumari
   */
  public Response getDeliveryConfig(
      RequestSpecBuilder requestSpecBuilder,
      DeliveryConfigRequest deliveryConfigRequest,
      String compatibleComponents) {
    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);
    String apiPath = INVENTORY_STORE_ENDPOINT.get("get-delivery-configs");

    compatibleComponents =
        compatibleComponents == null
            ? "SCHEDULED_DELIVERY,SCHEDULED_DELIVERY_M2,SAMPLING_FOR_COUPON_MOV_ENABLED,CONVENIENCE_FEE,RAIN_FEE,EXTERNAL_COUPONS,BUNDLE,MULTI_SELLER_ENABLED,PIP_V1,ROLLUPS,SCHEDULED_DELIVERY,OTOF,SAMPLING_ENABLED,ETA_NORMAL_WITH_149_DELIVERY,ROLLUPS_UOM,SAMPLING_V2,RE_PROMISE_ETA_ORDER_SCREEN_ENABLED,TEST"
            : compatibleComponents;

    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("x-client-auth", clientAuth);
    headers.put("compatible_components", compatibleComponents);

    Response response = RestUtils.post(requestSpecBuilder, headers, apiPath, deliveryConfigRequest);
    return response;
  }
}
