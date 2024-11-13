/***
 * Date: 12/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.controllerlm.servicehelper;

import com.zepto.api.restassured.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonHelper {

  private Environment env;

  public CommonHelper() {
    env = new Environment();
  }

  public Map<String, Object> getQueryParams(Map<String, Object> sTestContext, List<String> query) {
    Map<String, Object> queryParams = new HashMap<>();
    for (String name : query) {
      queryParams.put(name, sTestContext.get(name));
    }
    return queryParams;
  }

  public Map<String, Object> getPathParams(Map<String, Object> sTestContext, List<String> params) {
    Map<String, Object> pathParams = new HashMap<>();
    for (String name : params) {
      pathParams.put(name, sTestContext.get(name).toString());
    }
    return pathParams;
  }

  public Map<String, String> getDefaultAdminHeader() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", new BifrostHelper().getJwtToken());
    return headers;
  }

  public Map<String, String> getPackerRiderHeader(String authorization) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", authorization);
    return headers;
  }

  public Map<String, String> getCustomDeviceUIDHeader(Map<String, Object> sTestContext) {
    Map<String, String> headers = getDefaultHeader();
    headers.put("deviceUID", sTestContext.get("deviceUID").toString());
    return headers;
  }

  public Map<String, String> getDefaultHeader() {
    String defaultAppVersion =
        env.getGlobalTestData().getString("createOrderData.defaultAppVersion");
    String consumerAppAuthorizationProd =
        env.getGlobalTestData().getString("createOrderData.consumerAppAuthorizationProd");
    String consumerAppAuthorizationQA =
        env.getGlobalTestData().getString("createOrderData.consumerAppAuthorizationQA");
    Map<String, String> headers = new HashMap<>();
    headers.put("appVersion", defaultAppVersion);
    headers.put(
        "Authorization",
        env.getEnv().equalsIgnoreCase("prod")
            ? consumerAppAuthorizationProd
            : consumerAppAuthorizationQA);
    return headers;
  }
}
