package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiderStressV2Thresholds {

  @JsonProperty("high")
  private Integer high;

  @JsonProperty("medium")
  private Integer medium;

  @JsonProperty("low")
  private Integer low;
}
