package com.zeptonow.commonlm.pojo.iris_eta.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.iris_eta.subclass.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetIrisEtaResponse {

  @JsonProperty("cdebId")
  private String cdebId;

  @JsonProperty("totalEtaInSec")
  private Integer totalEtaInSec;

  @JsonProperty("totalEtaInMin")
  private Integer totalEtaInMin;

  @JsonProperty("deliverable")
  private Boolean deliverable;

  @JsonProperty("instantDeliveryAvailable")
  private Boolean instantDeliveryAvailable;

  @JsonProperty("scheduleDeliveryAvailable")
  private Boolean scheduleDeliveryAvailable;

  @JsonProperty("earliestSlot")
  private Object earliestSlot;

  @JsonProperty("nonDeliverableReason")
  private Object nonDeliverableReason;

  @JsonProperty("distance")
  private Integer distance;

  @JsonProperty("etaBreakDown")
  private EtaBreakDown etaBreakDown;

  @JsonProperty("riderDetails")
  private RiderDetails riderDetails;

  @JsonProperty("packerDetails")
  private PackerDetails packerDetails;

  @JsonProperty("slots")
  private Object slots;

  @JsonProperty("embargo")
  private Object embargo;

  @JsonProperty("standStillMode")
  private Boolean standStillMode;

  @JsonProperty("selectedSlot")
  private Object selectedSlot;

  @JsonProperty("scheduleDeliveryM2Enabled")
  private Boolean scheduleDeliveryM2Enabled;

  @JsonProperty("orderPackerStressDetails")
  private OrderPackerStressDetails orderPackerStressDetails;

  @JsonProperty("orderRiderStressDetails")
  private OrderRiderStressDetails orderRiderStressDetails;

  @JsonProperty("demandShapingCodDetails")
  private Object demandShapingCodDetails;

  @JsonProperty("demandShapingBatchingDetails")
  private Object demandShapingBatchingDetails;

  @JsonProperty("threePlDetails")
  private ThreePlDetails threePlDetails;

  @JsonProperty("demandShapingSurgeFeeDetails")
  private Object demandShapingSurgeFeeDetails;

  @JsonProperty("demandShapingEtaDetails")
  private Object demandShapingEtaDetails;

  @JsonProperty("demandShapingAbDetails")
  private Object demandShapingAbDetails;

  @JsonProperty("demandShapingCodV2Details")
  private DemandShapingCodV2Details demandShapingCodV2Details;

  @JsonProperty("priorityBatchingDetails")
  private Object priorityBatchingDetails;

  @JsonProperty("isTotalEtaCapped")
  private Boolean isTotalEtaCapped;

  @JsonProperty("overallEngineeringEta")
  private Integer overallEngineeringEta;

  @JsonProperty("dsBuffer")
  private Object dsBuffer;

  @JsonProperty("experimentFeatureFlags")
  private ExperimentFeatureFlags experimentFeatureFlags;

  @JsonProperty("packerBasedRiderAssignmentDetails")
  private PackerBasedRiderAssignmentDetails packerBasedRiderAssignmentDetails;

  @JsonProperty("packingEtaExperimentDetails")
  private PackingEtaExperimentDetails packingEtaExperimentDetails;

  @JsonProperty("potentialBatchedOrderId")
  private String potentialBatchedOrderId;

  @JsonProperty("orderRiderStressV2Details")
  private OrderRiderStressV2Details orderRiderStressV2Details;

  @JsonProperty("ftbUserDetails")
  private FtbUserDetails ftbUserDetails;

  @JsonProperty("dsPackingEta")
  private Integer dsPackingEta;

  @JsonProperty("isPriorityUser")
  private Boolean isPriorityUser;

  @JsonProperty("calculatedLowestPossibleEta")
  private Boolean calculatedLowestPossibleEta;

  @JsonProperty("hetaNonDeliverableReason")
  private String hetaNonDeliverableReason;

  @JsonProperty("ratEtaDetails")
  private RatEtaDetails ratEtaDetails;

  @JsonProperty("reverseLlmComputationEnabled")
  private Boolean reverseLlmComputationEnabled;
}
