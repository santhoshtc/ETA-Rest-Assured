/***
 * Date: 19/01/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.reports;

import com.zepto.common.configUtil.Configuration;
import com.zepto.common.fileUtils.PropertiesFileReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

  private static int maxTry =
      Integer.parseInt(
          System.getProperty("retryCount") == null
              ? PropertiesFileReader.getValueFromPropertyFile(
                  Configuration.CONFIG_FILE, "retryCount")
              : System.getProperty("retryCount"));
  private int count = 0;

  @Override
  public boolean retry(ITestResult iTestResult) {
    if (!iTestResult.isSuccess()) {
      if (count < maxTry) {
        count++;
        iTestResult.setStatus(ITestResult.FAILURE);
        return true;
      } else {
        iTestResult.setStatus(ITestResult.FAILURE);
      }
    } else {
      iTestResult.setStatus(ITestResult.SUCCESS);
    }
    return false;
  }
}
