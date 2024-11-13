/***
 * Date: 09/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.mysql;

import com.zepto.api.restassured.Environment;
import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import com.zepto.common.configUtil.Configuration;
import com.zepto.common.fileUtils.PropertiesFileReader;
import com.zepto.common.logger.LoggerUtil;
import com.zepto.common.reporter.ExtentReportUtil;
import com.zeptonow.controllerlm.servicehelper.CommonHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;

import java.util.HashMap;

import static com.zeptonow.commonlm.constants.ConfigConstant.ZEPTOUTILTY_ENDPOINT;
import static com.zeptonow.commonlm.constants.ConfigConstant.ZEPTO_UTIL_BASEURL;

public class DbUtilApi {

  public static String rmsSchemaName;
  public static String referralSchemaName;
  public static String zeptoBackendSchemaName;
  public static String deliveryServiceSchemaName;
  public static String codServiceSchemaName;
  public static String paymentServiceSchemaName;
  public static String kycServiceSchemaName;
  public static String databaseEnvironment;
  public static String surveySchemaName;

  static {
    databaseEnvironment =
        System.getProperty("databaseEnv") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "databaseEnv")
            : System.getProperty("databaseEnv");
    rmsSchemaName =
        System.getProperty("rmsSchema") == null
            ? PropertiesFileReader.getValueFromPropertyFile(Configuration.CONFIG_FILE, "rmsSchema")
            : System.getProperty("rmsSchema");
    zeptoBackendSchemaName =
        System.getProperty("zeptoBackendSchema") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "zeptoBackendSchema")
            : System.getProperty("zeptoBackendSchema");
    deliveryServiceSchemaName =
        System.getProperty("deliveryServiceSchemaName") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "deliveryServiceSchemaName")
            : System.getProperty("deliveryServiceSchemaName");
    surveySchemaName =
        System.getProperty("surveySchemaName") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "surveySchemaName")
            : System.getProperty("surveySchemaName");
    codServiceSchemaName =
        System.getProperty("codServiceSchemaName") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "codServiceSchemaName")
            : System.getProperty("deliveryServiceSchemaName");
    paymentServiceSchemaName =
        System.getProperty("paymentServiceSchemaName") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "paymentServiceSchemaName")
            : System.getProperty("paymentServiceSchemaName");
    kycServiceSchemaName =
        System.getProperty("kycSchemaName") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "kycSchemaName")
            : System.getProperty("kycSchemaName");
    referralSchemaName =
        System.getProperty("referralSchema") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "referralSchema")
            : System.getProperty("referralSchema");
  }

  private final LoggerUtil logger;
  private Environment environment;
  private CommonHelper commonHelper;
  private SchemaValidators schemaValidators;

  public DbUtilApi() {
    logger = new LoggerUtil(DbUtilApi.class);
    environment = new Environment();
    commonHelper = new CommonHelper();
    schemaValidators = new SchemaValidators();
    databaseEnvironment =
        System.getProperty("databaseEnv") == null
            ? PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "databaseEnv")
            : System.getProperty("databaseEnv");
  }

  /**
   * dbQuery this function will hit the DB and gives back query result
   *
   * @param requestSpecBuilder RequestSpecBuilder.
   * @param dbQuery database query
   * @return JSONArray return jsonArray containing all rows data
   * @author Abhishek_Singh
   */
  public JSONArray dbQuery(RequestSpecBuilder requestSpecBuilder, String dbQuery) {

    Response response = null;
    requestSpecBuilder.setBaseUri(ZEPTO_UTIL_BASEURL);

    HashMap<String, Object> queryParam = new HashMap<>();
    queryParam.put("env", databaseEnvironment);
    queryParam.put("query", dbQuery);

    response =
        RestUtils.post(
            requestSpecBuilder, null, ZEPTOUTILTY_ENDPOINT.get("dbQuery"), queryParam, null, "");
    ExtentReportUtil.logStepInfo(response.asString());
    return new JSONArray(response.asString());
  }

  /**
   * getOtp this function will return the otp from DB
   *
   * @param requestSpecBuilder RequestSpecBuilder
   * @param mobileNumber
   * @return Otp
   * @author Abhishek_Singh
   */
  public String getOtp(RequestSpecBuilder requestSpecBuilder, String mobileNumber) {
    Response response = null;
    requestSpecBuilder.setBaseUri(ZEPTO_UTIL_BASEURL);
    String endPoint = ZEPTOUTILTY_ENDPOINT.get("getOtp") + databaseEnvironment + "/" + mobileNumber;
    response = RestUtils.get(requestSpecBuilder, null, endPoint, null, null);
    ExtentReportUtil.logStepInfo(response.asString());
    Assert.assertTrue(
        response.getStatusCode() == 200,
        "Get otp response code from zepto util is not 200 it is " + response.getStatusCode());
    return (response.jsonPath().getString("otp"));
  }
}
