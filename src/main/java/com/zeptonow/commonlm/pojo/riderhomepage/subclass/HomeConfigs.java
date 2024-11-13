/***
 * Date: 23/02/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeConfigs {

  @JsonProperty("MAX_RIDER_APP_CHECKIN_GPS_ACCURACY")
  public String maxRiderAppCheckinGpsAccuracy;

  @JsonProperty("RIDER_QUALITY_METRIC_ENABLED")
  public Boolean riderQualityMetricEnabled;

  @JsonProperty("STORYLY_ENABLED")
  public String storylyEnabled;

  @JsonProperty("SELFIE_MATCH_FAILED_MAX_COUNT")
  private Integer selfieMatchFailedMaxCount;

  @JsonProperty("RIDER_REACTIVATION_ENABLED")
  private Boolean riderReactivationEnabled;

  @JsonProperty("ENABLE_BATTERY_OPTIMISATION_POPUP")
  private String ENABLE_BATTERY_OPTIMISATION_POPUP;

  @JsonProperty("INAPP_BATTERY_OPTIMISATION_ENABLED")
  private String inAppBatteryOptimisationEnabled;
}
