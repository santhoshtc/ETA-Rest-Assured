/***
 * Date: 01/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.mysql;

import com.zepto.common.logger.LoggerUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryUtil {

  private final LoggerUtil LOGGER = new LoggerUtil(com.zepto.common.dbUtils.QueryUtil.class);
  private ResultSet resultSet = null;
  private Statement statement = null;
  private Integer MAX_RETRY_COUNT = 5;

  public ResultSet executeQuery(Connection connection, String query) {
    Integer retry = 1;

    try {
      statement = connection.createStatement();
      if (query.toLowerCase().contains("select")) {
        resultSet = statement.executeQuery(query);
      } else {
        statement.executeUpdate(query);
      }
    } catch (SQLException var5) {
      var5.printStackTrace();
    }
    return resultSet;
  }

  public void updateDeleteInsert(Connection connection, String query) throws SQLException {

    statement = connection.createStatement();
    statement.executeUpdate(query);
  }
}
