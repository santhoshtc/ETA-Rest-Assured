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
public class RainModeConfigs {
  @JsonProperty("RAIN_FLAG_ENABLED")
  private boolean rainFlagEnabled;

  @JsonProperty("RAIN_FLAG_ENABLED_AT")
  private String rainFlagEnabledAt;

  @JsonProperty("RAIN_INTENSITY_TYPE")
  private String rainIntensityType;

  @JsonProperty("RAIN_RATE_VALUE")
  private int rainRateValue;

  @JsonProperty("TIME_LEFT_FOR_DISABLING_RAIN_MODE_IN_MINUTES")
  private int timeLeftForDisablingRainModeInMinutes;
}
