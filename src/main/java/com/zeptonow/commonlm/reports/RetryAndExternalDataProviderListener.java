/***
 * Date: 19/01/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.reports;

import com.google.api.client.util.Lists;
import com.zepto.common.configUtil.Configuration;
import com.zepto.common.fileUtils.PropertiesFileReader;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.ClassHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class RetryAndExternalDataProviderListener implements IAnnotationTransformer {
  private List<IAnnotationTransformer> transformers = Lists.newArrayList();

  /**
   * This will read all the transformers passed in config file and add it to the list @Author
   * Manisha.Kumari
   */
  public RetryAndExternalDataProviderListener() {
    // can set to diff listeners
    String listeners =
        System.getProperty(
            "transformers",
            PropertiesFileReader.getValueFromPropertyFile(
                Configuration.CONFIG_FILE, "transformers"));

    Arrays.stream(listeners.split(","))
        .forEach(
            each -> {
              Class<?> clazz = ClassHelper.forName(each.trim());
              IAnnotationTransformer transformer = null;
              try {
                if (!clazz.getName().equals(getClass().getName())) {
                  transformer = (IAnnotationTransformer) clazz.newInstance();
                }
              } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
              }
              if (transformer != null) {
                transformers.add(transformer);
              }
            });
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void transform(
      ITestAnnotation testannotation,
      Class testClass,
      Constructor testConstructor,
      Method testMethod) {
    testannotation.setRetryAnalyzer(Retry.class);
    for (IAnnotationTransformer each : transformers) {
      each.transform(testannotation, testClass, testConstructor, testMethod);
    }
  }
}
