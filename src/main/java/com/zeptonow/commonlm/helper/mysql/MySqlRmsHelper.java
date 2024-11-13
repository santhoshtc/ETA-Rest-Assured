/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.mysql;

import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.Services;
import com.zeptonow.commonlm.helper.utils.GlobalUtil;
import com.zeptonow.controllerlm.common.ConnectionHelper;
import lombok.SneakyThrows;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlRmsHelper {

  private ThreadLocal<Connection> connection =
      ThreadLocal.withInitial(
          () -> {
            return new ConnectionHelper().getPostgresDbInstance(Services.RMS);
          });
  private final LoggerUtil logger = new LoggerUtil(this.getClass());
  private GlobalUtil globalUtil = new GlobalUtil();
  private QueryUtil queryUtil = new QueryUtil();

  /**
   * This wrapper function is used to query rms database
   *
   * @param query
   * @return
   * @throws SQLException
   * @author Abhishek_Singh
   */
  @SneakyThrows
  public JSONArray queryDb(String query) throws SQLException {

    logger.info("Executing query " + query);
    ResultSet resultSet = queryUtil.executeQuery(connection.get(), query);
    return globalUtil.getResponseAsJson(resultSet);
  }

  /**
   * Method returns update result statement
   *
   * @param query
   * @return
   * @throws SQLException
   * @author Ajay_Mishra
   */
  public void executeInsertUpdateQuery(String query) throws SQLException {
    logger.info("Executing query " + query);
    queryUtil.updateDeleteInsert(connection.get(), query);
  }
}
