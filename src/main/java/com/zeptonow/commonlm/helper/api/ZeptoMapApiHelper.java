package com.zeptonow.commonlm.helper.api;

import com.zepto.api.restassured.RestUtils;
import com.zepto.api.restassured.SchemaValidators;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.zeptonow.commonlm.constants.ConfigConstant.ZEPTOMAP_BASEURL;
import static com.zeptonow.commonlm.constants.ConfigConstant.ZEPTOMAP_ENDPOINT;
import static com.zeptonow.commonlm.constants.SchemaPath.*;

public class ZeptoMapApiHelper {

  private SchemaValidators schemaValidators;

  public ZeptoMapApiHelper() {
    schemaValidators = new SchemaValidators();
  }

  /**
   * This function will call get autocomplete api by place name
   *
   * @param requestSpecBuilder
   * @param placeName
   * @param isVerifySchema
   * @return
   * @author santhosh_tc
   */
  public Response getAutocompleteApi(
      RequestSpecBuilder requestSpecBuilder, String placeName, boolean isVerifySchema) {
    requestSpecBuilder.setBaseUri(ZEPTOMAP_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("place_name", placeName);

    String apiPath = ZEPTOMAP_ENDPOINT.get("autoComplete");

    Response response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, getAutocompleteMap, isVerifySchema);
    return response;
  }

  /**
   * This function will call get location details api by placeId
   *
   * @param requestSpecBuilder
   * @param placeId
   * @param isVerifySchema
   * @return
   * @author santhosh_tc
   */
  public Response getLocationDetailsApi(
      RequestSpecBuilder requestSpecBuilder, String placeId, boolean isVerifySchema) {
    requestSpecBuilder.setBaseUri(ZEPTOMAP_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("place_id", placeId);

    String apiPath = ZEPTOMAP_ENDPOINT.get("locationDetails");

    Response response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, getLocationDetails, isVerifySchema);
    return response;
  }

  /**
   * This function will call get geocode api by lat longitude
   *
   * @param requestSpecBuilder
   * @param lat
   * @param longitude
   * @param isVerifySchema
   * @return
   * @author santhosh_tc
   */
  public Response getGeocodeApi(
      RequestSpecBuilder requestSpecBuilder, Double lat, Double longitude, boolean isVerifySchema) {
    requestSpecBuilder.setBaseUri(ZEPTOMAP_BASEURL);
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("latitude", lat);
    queryParams.put("longitude", longitude);

    String apiPath = ZEPTOMAP_ENDPOINT.get("geocode");

    Response response = RestUtils.get(requestSpecBuilder, null, apiPath, queryParams, null);
    schemaValidators.validateResponseSchema(response, getGeocodeDetails, isVerifySchema);
    return response;
  }
}
