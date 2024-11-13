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
public class ThreePlDetails {

  @JsonProperty("threePlSwitch")
  private Object threePlSwitch;

  @JsonProperty("threePlEligibility")
  private Boolean threePlEligibility;
}
