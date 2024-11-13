package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackerBasedRiderAssignmentDetails {
  @JsonProperty("featureFlag")
  private Boolean featureFlag;

  @JsonProperty("orderRiderStressCutoff")
  private Object orderRiderStressCutoff;

  @JsonProperty("orderPackerStressCutoff")
  private Object orderPackerStressCutoff;

  @JsonProperty("orderRiderStressEligibility")
  private Object orderRiderStressEligibility;

  @JsonProperty("orderPackerStressEligibility")
  private Object orderPackerStressEligibility;

  @JsonProperty("orderType")
  private String orderType;

  @JsonProperty("packStage")
  private String packStage;
}
