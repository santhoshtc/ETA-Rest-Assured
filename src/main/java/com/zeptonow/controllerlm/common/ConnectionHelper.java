/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.controllerlm.common;

import com.zepto.common.dbUtils.DBConnectionUtil;
import com.zepto.common.dbUtils.RedisUtil;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.Services;

import java.sql.Connection;

import static com.zeptonow.commonlm.constants.ConfigConstant.*;

public class ConnectionHelper {

  private final LoggerUtil logger = new LoggerUtil(this.getClass());
  private static Connection postgresZeptoBackendConn = null;
  private static Connection postgresZeptoWalletConn = null;
  private static Connection postgresAllocationEngineConn = null;

  private static Connection postgresSurveyConn = null;
  private static Connection rmsConn = null;
  private static RedisUtil redisClusterConnection = null;

  public Connection getPostgresDbInstance(Services services) {

    try {
      if (services.equals(Services.ZEPTOWALLET)) {
        if (postgresZeptoWalletConn == null) {
          postgresZeptoWalletConn =
              DBConnectionUtil.getConnection(
                  ZEPTO_WALLET_DB_HOST,
                  ZEPTO_WALLET_DB_PORT,
                  ZEPTO_WALLET_DB_NAME,
                  ZEPTO_WALLET_DB_USER,
                  ZEPTO_WALLET_DB_PASSWORD);
        }
        logger.info("Successfully Connected To Postgres DB: " + Services.ZEPTOWALLET);
        return postgresZeptoWalletConn;
      }
      if (services.equals(Services.DELIVERYSERVICE)) {
        if (postgresAllocationEngineConn == null) {
          postgresAllocationEngineConn =
              DBConnectionUtil.getConnection(
                  DELIVERY_SERVICE__DB_HOST,
                  DELIVERY_SERVICE__DB_PORT,
                  DELIVERY_SERVICE__DB_NAME,
                  DELIVERY_SERVICE__DB_USER,
                  DELIVERY_SERVICE__DB_PASSWORD);
        }
        logger.info("Successfully Connected To Postgres DB: " + Services.DELIVERYSERVICE);
        return postgresAllocationEngineConn;
      }
      if (services.equals(Services.DMS_SURVEY)) {
        if (postgresSurveyConn == null) {
          postgresSurveyConn =
              DBConnectionUtil.getConnection(
                  SURVEY_DB_HOST,
                  SURVEY_DB_PORT,
                  SURVEY_DB_NAME,
                  SURVEY_DB_USER,
                  SURVEY_DB_PASSWORD);
        }
        logger.info("Successfully Connected To Postgres DB: " + Services.DMS_SURVEY);
        return postgresSurveyConn;
      }
      if (services.equals(Services.RMS)) {
        if (rmsConn == null) {
          rmsConn =
              DBConnectionUtil.getConnection(
                  RMS_BASEURL_DB_HOST,
                  RMS_BASEURL_DB_PORT,
                  RMS_BASEURL_DB_NAME,
                  RMS_BASEURL_DB_USER,
                  RMS_BASEURL_DB_PASSWORD);
        }
        logger.info("Successfully Connected To Postgres DB: " + Services.RMS);
        return rmsConn;
      } else if (postgresZeptoBackendConn == null) {
        postgresZeptoBackendConn =
            DBConnectionUtil.getConnection(
                ZEPTO_BACKEND_DB_HOST,
                ZEPTO_BACKEND_DB_PORT,
                ZEPTO_BACKEND_DB_NAME,
                ZEPTO_BACKEND_DB_USER,
                ZEPTO_BACKEND_DB_PASSWORD);
      }

      logger.info("Successfully Connected To Postgres DB:  ZEPTO_QA");
      return postgresZeptoBackendConn;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public RedisUtil getRedisClusterInstance(Services services) {
    if (redisClusterConnection == null) {
      try {
        redisClusterConnection =
            new RedisUtil(ZEPTO_BACKEND_REDIS_HOST, Integer.parseInt(ZEPTO_BACKEND_REDIS_PORT));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return redisClusterConnection;
  }

  public static RedisUtil getRedisClusterInstance(String host, String port) {
    return new RedisUtil(host, Integer.parseInt(port));
  }
}
