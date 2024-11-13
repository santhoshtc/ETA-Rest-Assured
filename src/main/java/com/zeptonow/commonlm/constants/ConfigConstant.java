/***
 * Date: 12/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.constants;

import com.zepto.api.restassured.Environment;

import java.util.Map;

/**
 * @author Abhishek_Singh @Date 18/08/22
 */
public class ConfigConstant {

  /** Service path endpoints */
  public static final Map<String, String> CONFIG_ENDPOINT =
      Services.getServiceEndPoint(Services.CONFIG);

  public static final Map<String, String> CART_ENDPOINT =
      Services.getServiceEndPoint(Services.CART);
  public static final Map<String, String> CUSTOMER_ENDPOINT =
      Services.getServiceEndPoint(Services.CUSTOMER);
  public static final Map<String, String> CATALOGUE_ENDPOINT =
      Services.getServiceEndPoint(Services.CATALOGUE);
  public static final Map<String, String> INVENTORY_STORE_ENDPOINT =
      Services.getServiceEndPoint(Services.INVENTORY_STORE);
  public static final Map<String, String> INVENTORY_ENDPOINT =
      Services.getServiceEndPoint(Services.INVENTORY);
  public static final Map<String, String> ORDER_ENDPOINT =
      Services.getServiceEndPoint(Services.ORDER);
  public static final Map<String, String> INSURANCE_ENDPOINT =
      Services.getServiceEndPoint(Services.INSURANCE);
  public static final Map<String, String> LSQ_ENDPOINT = Services.getServiceEndPoint(Services.LSQ);
  public static final Map<String, String> KAFKA_ENDPOINT =
      Services.getServiceEndPoint(Services.KAFKA);
  public static final Map<String, String> LOGIN_ENDPOINT =
      Services.getServiceEndPoint(Services.LOGIN);
  public static final Map<String, String> PAYMENT_ENDPOINT =
      Services.getServiceEndPoint(Services.PAYMENT);
  public static final Map<String, String> GOOGLE_ENDPOINT =
      Services.getServiceEndPoint(Services.GOOGLE);
  public static final Map<String, String> COUPON_ENDPOINT =
      Services.getServiceEndPoint(Services.COUPON);
  public static final Map<String, String> ORDERL_ENDPOINT =
      Services.getServiceEndPoint(Services.ORDERL);
  public static final Map<String, String> WALLET_ENDPOINT =
      Services.getServiceEndPoint(Services.WALLET);
  public static final Map<String, String> ZEPTO_WALLET_ENDPOINT =
      Services.getServiceEndPoint(Services.ZEPTOWALLET);
  public static final Map<String, String> REASON_ENDPOINT =
      Services.getServiceEndPoint(Services.REASON);
  public static final Map<String, String> PRODUCT_ENDPOINT =
      Services.getServiceEndPoint(Services.PRODUCT);
  public static final Map<String, String> CATEGORY_ENDPOINT =
      Services.getServiceEndPoint(Services.CATEGORY);
  public static final Map<String, String> DISCOUNT_ENDPOINT =
      Services.getServiceEndPoint(Services.DISCOUNT);
  public static final Map<String, String> LAYOUTS_ENDPOINT =
      Services.getServiceEndPoint(Services.LAYOUTS);
  public static final Map<String, String> BANNER_ENDPOINT =
      Services.getServiceEndPoint(Services.BANNER);
  public static final Map<String, String> BIFROST_ENDPOINT =
      Services.getServiceEndPoint(Services.BIFROST);
  public static final Map<String, String> SHIFTADHERENCEV1_ENDPOINT =
      Services.getServiceEndPoint(Services.SHIFTADHERENCEV1);
  public static final Map<String, String> RIDER_ENDPOINT =
      Services.getServiceEndPoint(Services.RIDER);
  public static final Map<String, String> ZEPTOUTILTY_ENDPOINT =
      Services.getServiceEndPoint(Services.ZEPTOUTILITY);
  public static final Map<String, String> SHIFTADHERENCEV2 =
      Services.getServiceEndPoint(Services.SHIFTADHERENCEV2);
  public static final Map<String, String> DELIVERY_ENDPOINT =
      Services.getServiceEndPoint(Services.DELIVERY);
  public static final Map<String, String> ORDERREFUND_ENDPOINT =
      Services.getServiceEndPoint(Services.ORDERREFUNDS);
  public static final Map<String, String> CODBLOCK_ENDPOINT =
      Services.getServiceEndPoint(Services.CODBLOCK);
  public static final Map<String, String> EMBARGO_ENDPOINT =
      Services.getServiceEndPoint(Services.INVENTORY_STORE);
  public static final Map<String, String> SELFSIGNUP_ENDPOINT =
      Services.getServiceEndPoint(Services.SELFSIGNUP);
  public static final Map<String, String> DELIVERYSERVICE_ENDPOINT =
      Services.getServiceEndPoint(Services.DELIVERYSERVICE);
  public static final Map<String, String> RIDERHOMEPAGE_ENDPOINT =
      Services.getServiceEndPoint(Services.RIDERHOMEPAGE);
  public static final Map<String, String> PROFILE_ENDPOINT =
      Services.getServiceEndPoint(Services.PROFILE);
  public static final Map<String, String> SESSIONMANAGEMENT_ENDPOINT =
      Services.getServiceEndPoint(Services.SESSIONMANAGEMENT);
  public static final Map<String, String> CONFIGMANAGEMENT_ENDPOINT =
      Services.getServiceEndPoint(Services.CONFIGMANAGEMENT);
  public static final Map<String, String> CODSERVICE_ENDPOINT =
      Services.getServiceEndPoint(Services.COD);
  public static final Map<String, String> CASHATSTORE_ENDPOINT =
      Services.getServiceEndPoint(Services.CASHATSTORE);
  public static final Map<String, String> ADMINPANEL_ENDPOINT =
      Services.getServiceEndPoint(Services.ADMINPANEL);
  public static final Map<String, String> SHADOWFAXSERVICE_ENDPOINT =
      Services.getServiceEndPoint(Services.SHADOWFAX);
  public static final Map<String, String> SSU_ENDPOINT = Services.getServiceEndPoint(Services.SSU);
  public static final Map<String, String> REFERRAL_SERVICE_ENDPOINT =
      Services.getServiceEndPoint(Services.REFERRAL);
  public static final Map<String, String> ADMIN_PANEL_ENDPOINT =
      Services.getServiceEndPoint(Services.ADMINPANEL);
  public static final Map<String, String> PACKER_ENDPOINT =
      Services.getServiceEndPoint(Services.PACKER);
  public static final Map<String, String> ETA_SERVICE_ENDPOINT =
      Services.getServiceEndPoint(Services.IRIS_ETA);

  public static final Map<String, String> SLOTS_ENDPOINT =
      Services.getServiceEndPoint(Services.SLOTS);

  public static final Map<String, String> LOADSHARE_ENDPOINT =
      Services.getServiceEndPoint(Services.LOADSHARE);
  public static final Map<String, String> ZEPTOMAP_ENDPOINT =
      Services.getServiceEndPoint(Services.ZEPTOMAP);

  public static final Map<String, String> JOINING_BONUS_ENDPOINT =
      Services.getServiceEndPoint(Services.JOININGBONUS);

  private static Environment environment = new Environment();

  /** Environment constant Config details */
  public static final String KONG_BASE_URL = environment.getEnvEntityValue("kong", "baseUrl", null);

  public static final String RMS_KONG_URL =
      KONG_BASE_URL + environment.getEnvEntityValue("rms", "kongNamespace", null);
  public static final String RMS_KONG_URL_WITHOUT_API =
      KONG_BASE_URL + environment.getEnvEntityValue("rms", "kongNamespaceWithoutApi", null);
  public static final String ZEPTO_BACKEND_BASEURL =
      environment.getEnvEntityValue("zepto-backend", "baseUrl", null);
  public static final String ZEPTO_BACKEND_DB_HOST =
      environment.getEnvEntityValue("zepto-backend", "db", "host");
  public static final String ZEPTO_BACKEND_DB_PORT =
      environment.getEnvEntityValue("zepto-backend", "db", "port");
  public static final String ZEPTO_BACKEND_DB_NAME =
      environment.getEnvEntityValue("zepto-backend", "db", "name");
  public static final String ZEPTO_BACKEND_DB_USER =
      environment.getEnvEntityValue("zepto-backend", "db", "user");
  public static final String ZEPTO_BACKEND_DB_PASSWORD =
      environment.getEnvEntityValue("zepto-backend", "db", "password");
  public static final String ZEPTO_BACKEND_REDIS_HOST =
      environment.getEnvEntityValue("zepto-backend", "redis", "host");
  public static final String ZEPTO_BACKEND_REDIS_PORT =
      environment.getEnvEntityValue("zepto-backend", "redis", "port");
  public static final String RMS_BASEURL = environment.getEnvEntityValue("rms", "baseUrl", null);
  public static final String RMS_BASEURL_WITHOUT_API =
      environment.getEnvEntityValue("rms", "baseUrlWithoutApi", null);
  public static final String KAFKA_BASEURL =
      environment.getEnvEntityValue("kafkaendpoint", "baseUrl", null);
  public static final String RMS_BASEURL_DB_HOST =
      environment.getEnvEntityValue("rms", "db", "host");
  public static final String RMS_BASEURL_DB_PORT =
      environment.getEnvEntityValue("rms", "db", "port");
  public static final String RMS_BASEURL_DB_NAME =
      environment.getEnvEntityValue("rms", "db", "name");
  public static final String RMS_BASEURL_DB_USER =
      environment.getEnvEntityValue("rms", "db", "user");
  public static final String RMS_BASEURL_DB_PASSWORD =
      environment.getEnvEntityValue("rms", "db", "password");
  public static final String RMS_BASEURL_REDIS_HOST =
      environment.getEnvEntityValue("rms", "redis", "host");
  public static final String RMS_BASEURL_REDIS_PORT =
      environment.getEnvEntityValue("rms", "redis", "port");
  public static final String ZEPTO_WALLET_BASEURL =
      environment.getEnvEntityValue("zepto-wallet", "baseUrl", null);
  public static final String INSURANCE_BASEURL =
      environment.getEnvEntityValue("zepto-admin", "baseUrl", null);
  public static final String ZEPTO_WALLET_DB_HOST =
      environment.getEnvEntityValue("zepto-wallet", "db", "host");
  public static final String ZEPTO_WALLET_DB_PORT =
      environment.getEnvEntityValue("zepto-wallet", "db", "port");
  public static final String ZEPTO_WALLET_DB_NAME =
      environment.getEnvEntityValue("zepto-wallet", "db", "name");
  public static final String ZEPTO_WALLET_DB_USER =
      environment.getEnvEntityValue("zepto-wallet", "db", "user");
  public static final String ZEPTO_WALLET_DB_PASSWORD =
      environment.getEnvEntityValue("zepto-wallet", "db", "password");
  public static final String DELIVERY_SERVICE_BASEURL =
      environment.getEnvEntityValue("delivery-service", "baseUrl", null);
  public static final String DMS_KONG_URL =
      KONG_BASE_URL + environment.getEnvEntityValue("delivery-service", "kongNamespace", null);
  public static final String DMS_KONG_URL_WITHOUT_API =
      KONG_BASE_URL
          + environment.getEnvEntityValue("delivery-service", "kongNamespaceWithoutApi", null);
  public static final String DELIVERY_SERVICE__DB_HOST =
      environment.getEnvEntityValue("delivery-service", "db", "host");
  public static final String DELIVERY_SERVICE__DB_PORT =
      environment.getEnvEntityValue("delivery-service", "db", "port");
  public static final String DELIVERY_SERVICE__DB_NAME =
      environment.getEnvEntityValue("delivery-service", "db", "name");
  public static final String DELIVERY_SERVICE__DB_USER =
      environment.getEnvEntityValue("delivery-service", "db", "user");
  public static final String DELIVERY_SERVICE__DB_PASSWORD =
      environment.getEnvEntityValue("delivery-service", "db", "password");
  public static final String Session_Management_SERVICE_BASEURL =
      environment.getEnvEntityValue("session-management", "baseUrl", null);
  public static final String ZEPTO_UTIL_BASEURL =
      environment.getEnvEntityValue("zeptoUtil", "baseUrl", null);
  public static final String ZEPTO_ADMIN_BACKEND_BASEURL =
      environment.getEnvEntityValue("zepto-admin-backend", "baseUrl", null);
  public static final String COD_ORDER_PAYMENT_KONG_BASEURL =
      KONG_BASE_URL + environment.getEnvEntityValue("cod", "kongNamespaceWithoutApi", null);
  public static final String PAYMENT_SETTLEMENT_CALLBACK =
      environment.getEnvEntityValue("payment", "baseUrl", null);
  ;
  public static final String ZEPTO_ADMIN_BASEURL =
      environment.getEnvEntityValue("zepto-admin", "baseUrl", null);
  public static final String COD_ORDER_PAYMENT_BASEURL =
      environment.getEnvEntityValue("cod", "baseUrl", null);
  public static final String ZEPTO_ADMIN_DASHBOARD_BASEURL =
      environment.getEnvEntityValue("zepto-admin-frontend", "baseUrl", null);
  public static final String DMS_KAFKA_BROKERS =
      environment.getEnvEntityValue("delivery-service", "kafka", "brokers");
  public static final String CONSIGNMENT_KAFKA_TOPIC =
      environment.getEnvEntityValue("delivery-service", "kafka", "consignment-topic");
  public static final String SHADOWFAX_SERVICE_BASEURL =
      environment.getEnvEntityValue("shadowfax-service", "baseUrl", null);
  public static final String EARNINGS_KAFKA_BROKERS =
      environment.getEnvEntityValue("earningsAndPayout", "kafka", "brokers");
  public static final String EARNINGS_ORDER_KAFKA_TOPIC =
      environment.getEnvEntityValue("earningsAndPayout", "kafka", "order-topic");
  public static final String EARNINGSANDPAYOUTS_BASEURL =
      environment.getEnvEntityValue("earningsAndPayout", "baseUrl", null);
  public static final String PNB_PAYMENT_WEBHOOK_BASEURL =
      environment.getEnvEntityValue("payment-pnb-webhook", "baseUrl", null);
  public static final String SURVEY_DB_HOST =
      environment.getEnvEntityValue("survey_db", "db", "host");
  public static final String SURVEY_DB_PORT =
      environment.getEnvEntityValue("survey_db", "db", "port");
  public static final String SURVEY_DB_NAME =
      environment.getEnvEntityValue("survey_db", "db", "name");
  public static final String SURVEY_DB_USER =
      environment.getEnvEntityValue("survey_db", "db", "user");
  public static final String SURVEY_DB_PASSWORD =
      environment.getEnvEntityValue("survey_db", "db", "password");
  public static final String REFERRAL_SERVICE_BASEURL =
      environment.getEnvEntityValue("referral-service", "baseUrl", null);
  public static final String RIDER_REFERRAL_KAFKA_TOPIC =
      environment.getEnvEntityValue("referral-service", "kafka", "rider-referral-topic");
  public static final String RIDER_ORDER_MILESTONE_KAFKA_TOPIC =
      environment.getEnvEntityValue("referral-service", "kafka", "order-milestone-topic");
  public static final String REFERRAL_KAFKA_BROKERS =
      environment.getEnvEntityValue("referral-service", "kafka", "brokers");
  public static final String ADMIN_PANEL_BASEURL =
      environment.getEnvEntityValue("adminPanel", "baseUrl");
  public static final String IRIS_ETA_SERVICE_BASEURL =
      environment.getEnvEntityValue("iris-eta", "baseUrl", null);
  public static final String IRIS_REDIS_HOST =
      environment.getEnvEntityValue("iris-eta", "redis", "host");
  public static final String LOADSHARE_BASEURL =
      environment.getEnvEntityValue("loadshare-service", "baseUrl", null);
  public static final String ZEPTOMAP_BASEURL =
      environment.getEnvEntityValue("zeptomap", "baseUrl", null);

  public static final String BIFROST_BASEURL =
      environment.getEnvEntityValue("bifrost", "baseUrl", null);
}
