package com.zeptonow.controllerlm.servicehelper;

import com.zepto.api.restassured.Environment;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.helper.api.BifrostApiHelper;
import com.zeptonow.commonlm.pojo.bifrost.request.SignInRequest;
import com.zeptonow.commonlm.pojo.bifrost.request.ValidateMfaOtpRequest;
import com.zeptonow.commonlm.pojo.bifrost.response.SignInResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class BifrostHelper {

  Response response;
  private Environment environment = new Environment();
  private BifrostApiHelper bifrostApiHelper = new BifrostApiHelper();

  public BifrostHelper() {
      GeneralConstants.ADMIN_AUTHORIZATION = getJwtToken();
  }

  public String getJwtToken() {
    String bifrostUserEmail =
        environment.getGlobalTestData().getString("createOrderData.bifrostUserEmail");
    String bifrostUserPassword =
        environment.getGlobalTestData().getString("createOrderData.bifrostUserPassword");
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(bifrostUserEmail);
    signInRequest.setPassword(bifrostUserPassword);
    response = bifrostApiHelper.getAuthTokenApiCall(environment.getReqSpecBuilder(), signInRequest);
    SignInResponse signInResponse = response.as(SignInResponse.class);
    return signInResponse.getJwtToken();
  }

  public String validateAuthToken() {
    String mfaId = "";
    ValidateMfaOtpRequest requestObj = ValidateMfaOtpRequest.builder().build();
    requestObj.setMfaId(mfaId);
    response = bifrostApiHelper.validateMFAOtp(environment.getReqSpecBuilder(), requestObj);
    SignInResponse signInResponse = response.as(SignInResponse.class);
    return signInResponse.getJwtToken();
  }

}
