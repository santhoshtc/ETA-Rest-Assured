package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentFeatureFlags {
  private Boolean isLlmBufferThresholdEnabled;
  private Boolean isUserGppoDemandShapingEnabled;
  private Boolean isDsFlagEnabled;
  private Boolean isRiderRoundRobinEtaEnabled;
  private Boolean isFtbEnhancementEnabled;
  private Boolean isPackingDsFlagEnabled;
  private Boolean ratV2Variant;
  private Boolean reverseLlmComputationEnabled;
  private Boolean isDsV3Enabled;
  private Boolean isArtificialEtaReductionV2Live;
  private Boolean isRoutePreferredMinimumDistanceEnabled;
}
