package com.zeptonow.commonlm.pojo.iris_eta.response;

import com.zeptonow.commonlm.pojo.iris_eta.subclass.EtaBucketMultipliers;
import com.zeptonow.commonlm.pojo.iris_eta.subclass.PackingProductConf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStoreConfigResponse {
  private Boolean isEtaEngineEnabled;
  private Boolean isReverseLlmOptimizationEnabled;
  private EtaBucketMultipliers etaMultiplierBucketsDistance;
  private EtaBucketMultipliers etaMultiplierBucketsTime;
  private Integer riderECycleEtaMaxDistanceInMeters;
  private Integer riderCycleEtaMaxDistanceInMeters;
  private Integer riderWalkerEtaMaxDistanceInMeters;
  private Integer riderExpressFleetEtaMaxDistanceInMeters;
  private Integer riderEvExpressEtaMaxDistanceInMeters;
  private Integer packerAssignmentTime;
  private Integer packingWaitTimeThreshold;
  private Integer packerRiderHandshakeTimeInSec;
  private Integer packingTimeInSec;
  private Integer surgeRiderHandshakeTimeInSec;
  private Integer riderArrivalBufferTimeInSec;
  private Integer demandSurgeThresholdTimeInSec;
  private Integer rainTimeInSec;
  private Integer maximumTotalEtaInSec;
  private Integer userRiderHandshakeCodForwardInSecs;
  private Integer userRiderHandshakeCodReturnInSecs;
  private Integer userRiderHandshakeOnlineForwardInSecs;
  private Integer userRiderHandshakeOnlineReturnInSecs;
  private Double storeLat;
  private Double storeLng;
  private PackingProductConf packingProductConf;
  private Boolean isRiderAvailabilityEnhancedEnabled;
  private Boolean isLlmBufferThresholdEnabled;
  private Boolean packerStressAutoBannerEnabled;
  private Boolean riderStressAutoBannerEnabled;
  private Boolean demandShapingCodBlockEnabled;
  private Boolean demandShapingBatchingEnabled;
  private Boolean isEtaDemandShapingEnabled;
  private Boolean isDsFlagEnabled;
  private Boolean isSurgeFeeDemandShapingEnabled;
  private Boolean isEtaPeakBufferDemandShapingEnabled;
  private Boolean isEtaMicroserviceEnabled;
  private Boolean isEtaMicroserviceShadowModeEnabled;
  private Boolean isEtaEngineV2Enabled;
  private Boolean isEtaPeakBufferV2Enabled;
  private Boolean isMapMyIndiaEtaLoggingEnabled;
  private Boolean isPackerStressThresholdV2Enabled;
}
