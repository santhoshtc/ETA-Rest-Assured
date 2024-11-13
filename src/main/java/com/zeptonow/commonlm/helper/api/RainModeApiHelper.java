/***
 * Date: 21/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.api;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.pojo.rainmode.request.UpdateRainBlockToggleRequest;
import com.zeptonow.commonlm.pojo.rainmode.request.UpdateRainModeRequest;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.INVENTORY_STORE_ENDPOINT;
import static com.zeptonow.commonlm.constants.ConfigConstant.ZEPTO_BACKEND_BASEURL;

public class RainModeApiHelper {

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;
  private Response response;

  public RainModeApiHelper() {
    logger = new LoggerUtil(RainModeApiHelper.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
  }

  /**
   * This function will call update-rain block mode Api
   *
   * @param requestSpecBuilder
   * @param updateRainBlockToggleRequest
   * @param authToken
   * @return
   * @author Manisha_Kumari
   */
  public Response updateRainBlockToggleForStore(
      RequestSpecBuilder requestSpecBuilder,
      UpdateRainBlockToggleRequest updateRainBlockToggleRequest,
      String authToken) {

    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", authToken);

    String apiPath = INVENTORY_STORE_ENDPOINT.get("toggle_rain_block");

    response =
        RestUtils.post(
            requestSpecBuilder, headers, apiPath, updateRainBlockToggleRequest);
    return response;
  }

  /**
   * This function will call update rain mode Api
   *
   * @param requestSpecBuilder
   * @param updateRainModeRequest
   * @param authToken
   * @return
   * @author Manisha_Kumari
   */
  public Response updateRainModeStatusForStore(
      RequestSpecBuilder requestSpecBuilder,
      UpdateRainModeRequest updateRainModeRequest,
      String authToken) {

    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", authToken);

    String apiPath = INVENTORY_STORE_ENDPOINT.get("set_store_rain_status");

    response =
        RestUtils.post(requestSpecBuilder, headers, apiPath, null, null, updateRainModeRequest);
    return response;
  }

  /**
   * This function will call get rain status Api
   *
   * @param requestSpecBuilder
   * @param authToken
   * @return
   * @author Manisha_Kumari
   */
  public Response getRainModeStatusForStore(
      RequestSpecBuilder requestSpecBuilder, String storeId, String authToken) {

    requestSpecBuilder.setBaseUri(ZEPTO_BACKEND_BASEURL);

    Map<String, String> headers = new HashMap<>();
    headers.put("accept", "application/json");
    headers.put("Authorization", authToken);

    String apiPath = String.format(INVENTORY_STORE_ENDPOINT.get("get_store_rain_status"), storeId);

    response = RestUtils.get(requestSpecBuilder, headers, apiPath, null, null);
    return response;
  }
}
