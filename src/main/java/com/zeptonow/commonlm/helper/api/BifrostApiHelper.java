package com.zeptonow.commonlm.helper.api;

import com.zepto.api.restassured.RestUtils;
import com.zeptonow.commonlm.constants.ConfigConstant;
import com.zeptonow.commonlm.pojo.bifrost.request.SignInRequest;
import com.zeptonow.commonlm.pojo.bifrost.request.ValidateMfaOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.BIFROST_BASEURL;
import static com.zeptonow.commonlm.constants.ConfigConstant.BIFROST_ENDPOINT;

public class BifrostApiHelper {

  public Response validateMFAOtp(
      RequestSpecBuilder requestSpecBuilder, ValidateMfaOtpRequest requestObj) {

    requestSpecBuilder.setBaseUri(BIFROST_BASEURL);
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    Response response =
        RestUtils.post(
            requestSpecBuilder, headers, BIFROST_ENDPOINT.get("validateMfaOtp"), requestObj);
    return response;
  }

  public Response getAuthTokenApiCall(
      RequestSpecBuilder requestSpecBuilder, SignInRequest signInRequest) {
    requestSpecBuilder.setBaseUri(ConfigConstant.BIFROST_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("applicationId", "00a327e7-d7da-4f18-9b7f-cc1fb5d5aa99");
    return RestUtils.post(
        requestSpecBuilder,
        null,
        BIFROST_ENDPOINT.get("sign_in"),
        queryParams,
        null,
        signInRequest);
  }
}
