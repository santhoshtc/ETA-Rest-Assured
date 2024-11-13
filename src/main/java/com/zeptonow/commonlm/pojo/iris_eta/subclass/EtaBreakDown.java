package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtaBreakDown {

  private Integer packerAssignmentTimeInSec;
  private Integer packingWaitTimeInSec;
  private Integer packerRiderHandshakeTimeInSec;
  private Integer riderArrivalBufferInSec;
  private Integer surgeRiderHandshakeTimeInSec;
  private Integer packingEtaInSec;
  private Integer preparationEtaInSec;
  private Integer riderToStoreEtaInSec;
  private Integer orderToRiderHandshakeTimeSec;
  private Integer peakTimeEtaInSec;
  private String peakTimeEtaRemarks;
  private Integer lowEtaAdditionInSec;
  private Double latitude;
  private Double longitude;
  private Integer rainEta;
  private Integer orderBatchSurgeTimeInSecs;
  private Integer ordersBatchSurgeEtaBufferMax;
  private Integer ordersBatchLastMileEtaBuffer;
  private Integer batchingEtaInSecs;
  private Integer batchedOrderToRiderHandshakeSecs;
  private String directionPolyline;
  private Integer originalGoogleEtaInSec;
  private Integer calibratedGoogleEtaInSec;
  private Integer googleEtaInSecNoTraffic;
  private Integer totalEtaThreshold;
  private Integer buildingBufferInSec;
  private Boolean surgeInDemand;
  private Boolean highTrafficSurge;
  private EtaEngineComponents etaEngineComponents;
  private Integer llmAnalyticsBufferThreshold;
  private Boolean llmExperimentEnabled;
  private Boolean defaultEta;
  private Integer oldRiderToStoreEtaInSec;
  private String packingCalibrationLogic;
  private Integer packingEtaInSecEquation;
  private Integer artificialEtaReductionInSec;
  private Boolean isArtificialEtaReductionLive;
  private Boolean batchingStressV2Enabled;
  private Integer onRoadRtsInSec;
  private Integer osrmEtaInSec;
  private Boolean buildingEntryPointEnabled;
  private Integer googleDirectionsApiLatencyMs;
  private Integer dynamicAllowedEtaThreshold;
  private Boolean reverseLlmComputationEnabled;
  private Boolean pwtOptimisationEnabled;
}
