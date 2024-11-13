/***
 * Date: 11/10/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.helper.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.google.common.hash.Hashing;
import com.itextpdf.text.DocumentException;
import com.opencsv.CSVWriter;
import com.zepto.api.restassured.TestBase;
import com.zepto.common.logger.LoggerUtil;
import com.zeptonow.commonlm.constants.GeneralConstants;
import com.zeptonow.commonlm.constants.Services;
import com.zeptonow.controllerlm.common.ConnectionHelper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.ZAddParams;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class GlobalUtil extends TestBase {

  private final LoggerUtil logger = new LoggerUtil(GlobalUtil.class);
  FakeValuesService fakeValuesService =
      new FakeValuesService(new Locale("en-US"), new RandomService());
  private ConnectionHelper connectionHelper = new ConnectionHelper();

  /**
   * set date as per required day and format
   *
   * @param day
   * @param format
   * @return
   * @author binit_anand
   */
  public static String setDateInGivenFormat(int day, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, day);
    Date requiredDate = calendar.getTime();
    DateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
    return dateFormat.format(requiredDate);
  }

  /**
   * set date as per required day and format
   *
   * @param day
   * @param format
   * @return
   * @author Manisha_Kumari
   */
  public static int getHourOfDateInGivenFormat(int day, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, day);
    DateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
    return dateFormat.getCalendar().getTime().getHours();
  }

  /**
   * This function will check if the list<String is sorted> alphabetically
   *
   * @param listOfStrings
   * @return
   * @author ABhishek_Singh
   */
  public static boolean isStringListSorted(List<String> listOfStrings) {
    if (isEmpty(listOfStrings) || listOfStrings.size() == 1) {
      return true;
    }

    Iterator<String> iter = listOfStrings.iterator();
    String current, previous = iter.next();
    while (iter.hasNext()) {
      current = iter.next();
      if (previous.compareTo(current) > 0) {
        return false;
      }
      previous = current;
    }
    return true;
  }

  /***
   * getResponseAsJson method/api will convert ResultSet data into JSONArray having all rows details with proper column name.
   *
   * @param resultSet ResultSet having the query result after execution.
   * @return JSONArray having all rows results.
   * @throws SQLException If resultset data is not correct
   * @author Abhishek_Singh
   */
  public static JSONArray getResponseAsJson(ResultSet resultSet) throws SQLException {
    ResultSetMetaData md = resultSet.getMetaData();
    int numCols = md.getColumnCount();
    List<String> colNames =
        IntStream.range(0, numCols)
            .mapToObj(
                i -> {
                  try {
                    return md.getColumnName(i + 1);
                  } catch (SQLException e) {
                    e.printStackTrace();
                    return "?";
                  }
                })
            .collect(Collectors.toList());

    JSONArray result = new JSONArray();
    while (resultSet.next()) {
      JSONObject row = new JSONObject();
      colNames.forEach(
          cn -> {
            try {
              row.put(cn, resultSet.getObject(cn));
            } catch (JSONException | SQLException e) {
              e.printStackTrace();
            }
          });
      result.put(row);
    }
    return result;
  }

  /**
   * This function will take input date as String in format ("yyyy-MM-dd") and validate
   * day,date,month with expected values
   *
   * @param actualDate Actual date in String format
   * @throws ParseException
   * @author Abhishek_Singh
   */
  public void compareDateWithCurrentDate(String actualDate) throws ParseException {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date dateObj = formatter.parse(actualDate);
    Calendar myCal = Calendar.getInstance();
    myCal.setTime(dateObj);

    // Getting the current date value
    LocalDate currentDate = LocalDate.now();

    Assert.assertTrue(
        currentDate.getDayOfMonth() == myCal.get(Calendar.DAY_OF_MONTH),
        "Expected day of month not matching with actual");
    Assert.assertTrue(
        currentDate.getYear() == myCal.get(Calendar.YEAR),
        "Expected year value not matching with actual");
    Assert.assertTrue(
        currentDate.getMonth().getValue() == myCal.get(Calendar.MONTH) + 1,
        "Expected Month of year not matching with actual");
  }

  /**
   * create and write generic csv file on the fly
   *
   * @param configKeys
   * @param fileName
   * @return
   * @throws IOException
   */
  public File createCsvFile(List<String> configKeys, String fileName) throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();
    for (String key : configKeys) {
      rows.add(environment.getGlobalTestData().getStringList(key).stream().toArray(String[]::new));
    }
    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;

    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();
    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }

    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);
    return new File(tempFilePath);
  }

  /**
   * create and write generic csv file on the fly
   *
   * @param configKeys
   * @param fileName
   * @return
   * @throws IOException
   */
  public File createCsvFile(List<String> configKeys, List<String> configValues, String fileName)
      throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();
    for (String key : configKeys) {
      rows.add(environment.getGlobalTestData().getStringList(key).stream().toArray(String[]::new));
    }

    rows.add(configValues.stream().toArray(String[]::new));

    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;

    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();
    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }

    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);
    return new File(tempFilePath);
  }

  /**
   * This method is to download a file from the given URL
   *
   * @param url
   * @param fileName
   * @return
   * @throws IOException
   */
  public File createFileFromURL(URL url, String fileName) throws IOException {
    File file =
        new File(
            System.getProperty("user.dir")
                + File.separator
                + "tmpTestData"
                + File.separator
                + fileName);
    FileUtils.copyURLToFile(url, file);
    return file;
  }

  /**
   * This method allows us to read contents of the csv file and return key value map
   *
   * @param fileName
   * @return
   * @throws IOException
   */
  public Map<String, List<String>> readCsvFile(File fileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    String str = "";
    Map<String, List<String>> map = new LinkedHashMap<>();
    int line = 0;
    while ((str = br.readLine()) != null) {
      String[] stringArr = str.split(",");
      for (int i = 0; i < stringArr.length; i++) {
        if (line == 0) {
          map.put(stringArr[i], new ArrayList<>());
        } else {
          String[] arrayKeys = map.keySet().toArray(new String[map.size()]);
          List<String> values = map.get(arrayKeys[i]);
          values.add(stringArr[i]);
          map.put(arrayKeys[i], values);
        }
      }
      line++;
    }
    return map;
  }

  /**
   * This function will delete key value from redis
   *
   * @param redisKey
   * @return
   */
  public Long deleteRedisKey(String redisKey) {
    this.logger.info("Clearing Redis To ensure Cache data is not used");
    try {
      return this.connectionHelper
          .getRedisClusterInstance(Services.ZEPTOWALLET)
          .deleteClusterKey(redisKey);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  /**
   * This function will delete key value from redis
   *
   * @param redisKey
   * @return
   */
  public Long deleteRedisKey(String redisKey, String host, String port) {
    this.logger.info("Clearing Redis To ensure Cache data is not used");
    try {

      return this.connectionHelper.getRedisClusterInstance(host, port).deleteClusterKey(redisKey);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  /**
   * Method is used to delete Redis data by pattern
   *
   * @param pattern
   * @author Ajay_Mishra
   */
  public void deleteRedisDataByPattern(String pattern) {
    try {
      this.connectionHelper.getRedisClusterInstance(Services.ZEPTOWALLET).deleteByPattern(pattern);
      this.logger.info("Redis data cleared by pattern :: " + pattern);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  /**
   * This function will set key's value in redis
   *
   * @param redisKey
   * @return
   */
  public void setRedisKey(
      String host,
      String port,
      String redisKey,
      String member,
      long score,
      String... additionalParam) {
    this.logger.info("Set/Update Redis key in zset to new value");
    JedisCluster jedisCluster = new JedisCluster(new HostAndPort(host, Integer.parseInt(port)));
    this.logger.info(
        "Updating " + redisKey + " in cache with score :: " + score + " for member :: " + member);
    if (additionalParam.length > 0) {
      switch (additionalParam[0]) {
        case "xx" -> jedisCluster.zadd(redisKey, score, member, ZAddParams.zAddParams().xx());
        case "nx" -> jedisCluster.zadd(redisKey, score, member, ZAddParams.zAddParams().nx());
        case "ch" -> jedisCluster.zadd(redisKey, score, member, ZAddParams.zAddParams().ch());
      }
    } else {
      jedisCluster.zadd(redisKey, score, member);
    }
    jedisCluster.close();
  }

  /**
   * This function will del key's value in redis zset
   *
   * @param redisKey
   * @return
   */
  public void delRedisKey(String host, String port, String redisKey, String member) {
    this.logger.info("delete Redis key in zset to new value");
    JedisCluster jedisCluster = new JedisCluster(new HostAndPort(host, Integer.parseInt(port)));
    this.logger.info("Removing " + redisKey + " in cache for member :: " + member);
    jedisCluster.zrem(redisKey, member);
    jedisCluster.close();
  }

  /**
   * This function will set key's value for zset in redis
   *
   * @param redisKey
   * @return
   */
  public boolean setRedisKey(String host, String port, String redisKey, String value) {
    this.logger.info("Set Redis key to new value");
    try {
      return this.connectionHelper
          .getRedisClusterInstance(host, port)
          .setClusterKey(redisKey, value);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * This function will create/generate random mobile number
   *
   * @return
   * @author Abhishek_Singh
   */
  public String generateRandomMobileNumber() {
    // The FakeValueService class provides methods for generating random sequences
    String mobileNumber = fakeValuesService.regexify("[1-5]{1}[0-9]{9}");
    return mobileNumber;
  }

  /**
   * This function is used to decode jwt token
   *
   * @param jwtToken
   * @return
   * @author Abhishek_Singh
   */
  public DecodedJWT decodeJwtToken(String jwtToken) {

    DecodedJWT decodedJWT = JWT.decode(jwtToken);
    return decodedJWT;
  }

  /**
   * This function will return dateTime in UTC format
   *
   * @param hour
   * @param minutes
   * @return
   * @author Abhishek_Singh
   */
  public static String returnUTCDateFormat(int hour, int minutes) {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR, hour);
    cal.add(Calendar.MINUTE, minutes);
    Date latestTime = cal.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSZ");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String time = sdf.format(latestTime);
    String s1 = time.split("\\+")[0].substring(0, time.lastIndexOf(':'));
    String s2 = time.split("\\+")[0].substring(time.lastIndexOf(':') + 1, time.indexOf('+'));
    String s3 = time.split("\\+")[1].substring(0, 2);
    time = s1 + ":" + new BigDecimal(s2).stripTrailingZeros().toPlainString() + "+" + s3;
    return time;
  }

  /**
   * This function will return any past or future timestamp with timezone
   *
   * @param hour
   * @param minutes
   * @param zone
   * @return
   * @author Abhishek_Singh
   */
  public String getPastTime(int hour, int minutes, String zone) {

    Date today = new Date();
    Calendar cal = new GregorianCalendar();
    cal.setTime(today);
    cal.add(Calendar.HOUR_OF_DAY, hour);
    cal.add(Calendar.MINUTE, minutes);

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone(zone));
    return sdf.format(cal.getTime());
  }

  /**
   * get passed date in currentFormat to get UTC date in expectedFormat
   *
   * @param date
   * @param currentFormat
   * @param expectedFormat
   * @param expectedZone
   * @return utc date in expected format
   * @author manisha.kumari
   */
  public static String getDateInUTCTimeZone(
      String date, String currentFormat, String expectedFormat, String... expectedZone) {

    DateFormat currentFormatter = new SimpleDateFormat(currentFormat);
    DateFormat expectedFormatter = new SimpleDateFormat(expectedFormat);
    String convertedDate = date;
    String zone = "utc";
    if (expectedZone.length > 0) {
      zone = expectedZone[0];
    }
    if (zone.equalsIgnoreCase("utc")) {
      currentFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
      expectedFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    } else if (zone.equalsIgnoreCase("ist")) {
      currentFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
      expectedFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
    }

    try {
      Date currentDate = currentFormatter.parse(date);
      convertedDate = expectedFormatter.format(currentDate);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return convertedDate;
  }

  /**
   * get passed date in currentFormat to get UTC date in expectedFormat
   *
   * @param date
   * @param currentFormat
   * @param expectedFormat
   * @return utc date in expected format
   * @author manisha.kumari
   */
  public static String getStartOfDayFromGivenDate(
      String date, String currentFormat, String expectedFormat) {
    DateFormat formatterIST = new SimpleDateFormat(currentFormat);
    String convertedDate = date;

    formatterIST.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

    try {
      Date istDate = formatterIST.parse(date);
      Calendar cal = new GregorianCalendar();
      cal.setTime(istDate);
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      formatterIST = new SimpleDateFormat(expectedFormat);
      convertedDate = formatterIST.format(cal.getTime());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return convertedDate;
  }

  /**
   * Method creates random orderCode
   *
   * @return
   * @author Ajay_Mishra
   */
  public String getRandomOrderCode() {
    return fakeValuesService.regexify("[1-9A-N]{14}");
  }

  /**
   * Method creates random uuid
   *
   * @return
   * @author Ajay_Mishra
   */
  public String getRandomUUID() {
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    return uuidAsString;
  }

  /**
   * Method used to return time in UTC format by providing hh and mm where daysOfWeek is plus or
   * minus days
   *
   * @param hour
   * @param minutes
   * @param dayOfWeek
   * @return
   * @author Ajay_Mishra
   */
  public static String returnUTCDateFormatAtSpecificDateAndTime(
      int hour, int minutes, int dayOfWeek) {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_WEEK, dayOfWeek);
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, minutes);
    Date specificTime = cal.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSZ");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String time = sdf.format(specificTime);
    String s1 = time.split("\\+")[0].substring(0, time.lastIndexOf(':'));
    String s2 = time.split("\\+")[0].substring(time.lastIndexOf(':') + 1, time.indexOf('+'));
    String s3 = time.split("\\+")[1].substring(0, 2);
    time = s1 + ":" + new BigDecimal(s2).stripTrailingZeros().toPlainString() + "+" + s3;
    return time;
  }

  /**
   * createDummyImage this method will create a dummy jpg file
   *
   * @param tempFilePath file-path with file name
   * @return File jpeg file
   * @throws IOException
   * @throws DocumentException
   * @author Abhishek_Singh
   */
  public File createDummyImage(String tempFilePath) throws IOException {

    // creating dummy .pdf file
    int width = 250;
    int height = 250;

    // Constructs a BufferedImage of one of the predefined image types.
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Create a graphics which can be used to draw into the buffered image
    Graphics2D g2d = bufferedImage.createGraphics();

    // fill all the image with white
    g2d.setColor(Color.white);
    g2d.fillRect(0, 0, width, height);

    // create a circle with black
    g2d.setColor(Color.black);
    g2d.fillOval(0, 0, width, height);

    // create a string with yellow
    g2d.setColor(Color.yellow);
    g2d.drawString("Rider Image", 50, 120);

    // Disposes of this graphics context and releases any system resources that it is using.
    g2d.dispose();

    // Save as JPEG
    File file = new File(tempFilePath);
    ImageIO.write(bufferedImage, "jpg", file);
    return file;
  }

  /**
   * get current day date and time in required format
   *
   * @param dateTimeFormat
   * @return
   * @author deepak_kumar
   */
  public static String getCurrentDateTime(String dateTimeFormat) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
    return dtf.format(LocalDateTime.now());
  }

  /**
   * Method is used to get specific date of the week
   *
   * @param day required day of current week like monday,tuesday etc.
   * @param format required format
   * @return
   * @author Ajay_Mishra
   */
  public String getSpecificDateOfWeek(String day, String format) {

    Calendar calendar = Calendar.getInstance();
    int dayIndex = dayIndexMapper(day);
    int x = (dayIndex + 1) - calendar.get(Calendar.DAY_OF_WEEK);
    calendar.add(Calendar.DAY_OF_WEEK, x);
    Date requiredDate = calendar.getTime();
    DateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
    return dateFormat.format(requiredDate);
  }

  /**
   * Method is used to map and return the index of the day
   *
   * @param day monday, tuesday, etc.
   * @return index of the day
   * @author Ajay_Mishra
   */
  public int dayIndexMapper(String day) {

    HashMap<String, Integer> dayIndexMapper = new HashMap<String, Integer>();
    String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
    for (int index = 0; index < days.length; index++) {
      dayIndexMapper.put(days[index], index + 1);
    }
    return dayIndexMapper.get(day.toUpperCase());
  }

  /**
   * Compare 2 dates and return factor in same format
   *
   * @param date1
   * @param date2
   * @param format
   * @return
   * @author Manisha.Kumari
   */
  @SneakyThrows
  public int compareTwoDateInSameFormat(String date1, String date2, String format) {
    Date firstDate = new SimpleDateFormat(format).parse(date1);
    Date secondDate = new SimpleDateFormat(format).parse(date2);
    if (firstDate.compareTo(secondDate) > 0) {
      logger.info("first date :: " + firstDate + "is after second date :: " + secondDate);
    } else if (firstDate.compareTo(secondDate) < 0) {
      logger.info("first date :: " + firstDate + "is before second date :: " + secondDate);
    } else {
      System.out.println("first date :: " + firstDate + "is equal to second date :: " + secondDate);
    }
    return firstDate.compareTo(secondDate);
  }

  /**
   * get passed date in currentFormat to get UTC date in expectedFormat
   *
   * @param date
   * @param hour
   * @param minutes
   * @param currentFormat
   * @param expectedFormat
   * @return utc date in expected format
   * @author manisha.kumari
   */
  public static String setSpecificHourForGivenDate(
      String date, int hour, int minutes, String currentFormat, String expectedFormat) {
    DateFormat formatterIST = new SimpleDateFormat(currentFormat);
    String convertedDate = date;

    formatterIST.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

    try {
      Date istDate = formatterIST.parse(date);
      Calendar cal = new GregorianCalendar();
      cal.setTime(istDate);
      cal.set(Calendar.HOUR_OF_DAY, hour);
      cal.set(Calendar.MINUTE, minutes);
      cal.set(Calendar.SECOND, 0);
      formatterIST = new SimpleDateFormat(expectedFormat);
      convertedDate = formatterIST.format(cal.getTime());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return convertedDate;
  }

  /**
   * Generate random string with 8 characters
   *
   * @return
   */
  public String getRandomString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 8) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }

  /**
   * create dynamic csv file
   *
   * @param csvRows
   * @param fileName
   * @return
   * @throws IOException
   */
  public File createDynamicCsvFile(List<List<String>> csvRows, String fileName) throws IOException {

    ArrayList<String[]> rows = new ArrayList<>();
    for (List<String> i : csvRows) {
      rows.add(i.stream().toArray(String[]::new));
    }
    String tempFilePath =
        System.getProperty("user.dir") + File.separator + "tmpTestData" + File.separator + fileName;

    // creating tmpTestData dir if not exists
    File file = new File(tempFilePath);
    file.delete();
    final File parent_directory = file.getParentFile();
    if (null != parent_directory && !parent_directory.exists()) {
      parent_directory.mkdirs();
    }

    // Creating .csv file
    CSVWriter write = new CSVWriter(new FileWriter(tempFilePath));
    write.writeAll(rows);
    write.flush();
    logger.info("Create .csv file at " + tempFilePath);
    return new File(tempFilePath);
  }

  /**
   * This function will return any past or future timestamp with timezone in expected format
   *
   * @param hour
   * @param minutes
   * @param expectedFormat
   * @param zone
   * @return
   * @author Ajay_Mishra
   */
  public String getPastTimeInGivenFormatAndZone(
      int hour, int minutes, String expectedFormat, String zone) {

    Date today = new Date();
    Calendar cal = new GregorianCalendar();
    cal.setTime(today);
    cal.add(Calendar.HOUR_OF_DAY, hour);
    cal.add(Calendar.MINUTE, minutes);

    SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
    sdf.setTimeZone(TimeZone.getTimeZone(zone));
    return sdf.format(cal.getTime());
  }

  /**
   * Returns the ordinal respective to supplied integer(Exception 11, 12 & 13)
   *
   * @param i
   * @return
   * @author Ajay_Mishra
   */
  public String getOrdinal(int i) {
    String[] suffixes = new String[] {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
    switch (i % 100) {
      case 11:
      case 12:
      case 13:
        return i + "th";
      default:
        return i + suffixes[i % 10];
    }
  }

  /**
   * method to remove a node from json
   *
   * @param node
   * @param keyToRemove
   * @return
   */
  public JsonNode removeJsonNode(JsonNode node, String keyToRemove) {
    if (node.has(keyToRemove)) {
      ((ObjectNode) node).remove(keyToRemove);
    }
    return node;
  }

  /**
   * This method will verify date & time b/w api response and DB by formatting them
   *
   * @param apiDate
   * @param dbDate
   * @author Deepak_Kumar
   */
  public void verifyApiAndDBDateTime(String apiDate, String dbDate) {
    String apiTime = apiDate.replace("T", " ").replace("Z", "");
    String dbTime = dbDate.substring(0, dbDate.lastIndexOf("."));
    String istTime =
        GlobalUtil.getDateInUTCTimeZone(
            dbTime, GeneralConstants.DATE_TIME_FORMAT, GeneralConstants.DATE_TIME_FORMAT, "UTC");
    Assert.assertEquals(apiTime, istTime, "Mismatch in api and DB dateTime");
  }

  /**
   * This method will calculate and return time diff in milliseconds between two time
   *
   * @param startDateTime
   * @param endDateTime
   * @author Deepak_Kumar
   */
  public int calculateTimeDifference(String startDateTime, String endDateTime)
      throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date d1 = sdf.parse(startDateTime);
    Date d2 = sdf.parse(endDateTime);
    int difference_In_Time = (int) (d2.getTime() - d1.getTime());
    return difference_In_Time;
  }

  /**
   * This method will return SHA256 hash value of a string
   *
   * @param value
   * @author Deepak_Kumar
   */
  public String getSha256(String value) {
    return Hashing.sha256().hashString(value, StandardCharsets.UTF_8).toString();
  }

  /**
   * This method will convert localdatetime to epoch time
   *
   * @param localdateTime
   * @return
   */
  public long getEpochTime(LocalDateTime localdateTime){
    Date d = Timestamp.valueOf(localdateTime);
    return d.getTime()/1000;
  }
}
