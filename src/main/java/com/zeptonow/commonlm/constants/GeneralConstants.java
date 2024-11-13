package com.zeptonow.commonlm.constants;

public class GeneralConstants {

  public static final String UTC_FORMAT_WITH_ZONE = "yyyy-MM-dd HH:mm:ss.SSSSZ";
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_TIME_FORMAT_WITH_CONSTANTS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String UUID_REGEX =
      "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
  public static final String TOKEN_REGEX = "Bearer\\s([\\w-]*\\.[\\w-]*\\.[\\w-]*$)";
  public static final String TOKEN_REGEX_WITHOUT_BEARER = "([\\w-]*\\.[\\w-]*\\.[\\w-]*$)";
  public static final String CREATE_DELIVERY_EVENT = "dms_create_delivery";
  public static final String UPDATE_DELIVERY_EVENT = "dms_update_delivery";
  public static final String CANCEL_DELIVERY_EVENT = "dms_cancel_delivery";
  public static final String HEALTH_CARD_DOWNLOAD_PATH =
      System.getProperty("user.dir") + "/insurance.pdf";
  public static final String RETAIL_PAYMENT = "RETAIL_PAYMENT";
  public static final String PAY_NEARBY = "PAY_NEARBY";
  public static final String AIRTEL_MONEY = "AIRTEL_MONEY";
  public static final int HIGH_RAIN_TIME = 2700;
  public static final int MILD_RAIN_TIME = 1800;
  public static final int LOW_RAIN_TIME = 900;
  public static final String HIGH_RAIN = "HIGH_RAIN";
  public static final String MILD_RAIN = "MILD_RAIN";
  public static final String LOW_RAIN = "LOW_RAIN";
  public static final String NO_RAIN = "NO_RAIN";
  public static final String CACHE_KEY_QUESTION_CATEGORY = "STORE_CONFIG_%s_QUESTION_CATEGORY";
  public static final int HIGH_RAIN_ETA = 300;
  public static final int MILD_RAIN_ETA = 240;
  public static final int LOW_RAIN_ETA = 180;
  // ETA
  public static final String CLIENT_KEY_ETA = "8e861683-1b26-4906-8277-47439f4627a2";
  public static String REDIS_IS_NEW_ETA_FLOW_KEY = "IS_ETA_MICROSERVICE_ENABLED_%s";
  public static String EtaConfigMemDbKey = "eta.conf.store:{storeId}";
  public static String StorePeakBufferMemDbKey = "peak.buffer.store:{storeId}:hour.ist:{hour}";
  public static String getRiderCacheKey = "rider.avail.store:%s:vehicle:%s";
  public static final String RAIN_MODE_DT = "Rainfall in your area";
  public static final String ST_DT = "Raining Outside | Delay Expected";
  public static final String ST_SDT = "Deliveries will resume once rain stops";
  public static final String TRY_LATER = "Please try again later";
  public static final String CLOSED = "CLOSED";
  public static final String OPEN = "OPEN";
  public static final String SURGE_IN_DEMAND = "CLOSED_SURGE_IN_DEMAND";
  public static final String ST_DELIVERABLE_SUBTYPE = "STAND_STILL_MODE";

  public static final String DEMAND_SURGE_ST = "Currently facing a demand surge";
  public static final String storeClosed = "Store is currently closed";
  public static final String RIDER_STRESS_AUTO_BANNER_ENABLED_CACHE_KEY =
      "RIDER_STRESS_AUTO_BANNER_ENABLED";
  public static final String PACKER_STRESS_AUTO_BANNER_ENABLED_CACHE_KEY =
      "PACKER_STRESS_AUTO_BANNER_ENABLED";
  public static String RIDER_STRESS_STORE_KEY = "ORDER_RIDER_STRESS:%s";
  public static String PACKER_STRESS_STORE_KEY = "ORDER_PACKER_STRESS:%s";

  public static final String ORDERS_STOPPED_SYSTEM = "ORDERS_STOPPED_SYSTEM";
  public static final String ORDERS_BATCH_SURGE_ETA_BUFFER_IN_SECS =
      "ORDERS_BATCH_SURGE_ETA_BUFFER_IN_SECS";
  public static final String UNSERVICEABLE_AREA = "UNSERVICEABLE_AREA";
  public static String MAKE_TEXT_HIGHLIGHTED = "<span style=\"color:%s\">" + "%s" + "</span>.";

  public static String ADMIN_AUTHORIZATION = "null";
  public static final String DEFAULT_DEVICE_UID = "8f583472f7978abe";
}
