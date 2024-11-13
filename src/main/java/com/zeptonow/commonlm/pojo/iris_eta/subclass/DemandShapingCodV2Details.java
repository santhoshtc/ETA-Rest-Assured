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
public class DemandShapingCodV2Details {

  @JsonProperty("riderStressV2")
  private Integer riderStressV2;

  @JsonProperty("riderStressV2Level")
  private Object riderStressV2Level;

  @JsonProperty("riderStressV2Thresholds")
  private RiderStressV2Thresholds riderStressV2Thresholds;
}
