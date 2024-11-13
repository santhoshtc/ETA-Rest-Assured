/***
 * Date: 13/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtraDetails {

  @JsonProperty("status_change_log")
  @JsonAlias("statusChangeLog")
  public List<StatusChangeLog> statusChangeLog;

  @JsonProperty("pod_attachment_ids")
  @JsonAlias({"podAttachmentIds", "podAttachmentUrls", "pod_attachment_urls"})
  private List<Object> podAttachmentIds;

  @JsonProperty("partnerOrderID")
  @JsonAlias({"partner_order_id", "PartnerOrderID"})
  private String partnerOrderId;

  @JsonProperty("partnerType")
  @JsonAlias({"partner_type", "PartnerType"})
  private String partnerType;

  @JsonProperty("partnerName")
  @JsonAlias({"partner_name", "PartnerName"})
  private String partnerName;

  @JsonProperty("riderContact")
  @JsonAlias({"rider_contact", "RiderContact"})
  private String riderContact;

  @JsonProperty("riderName")
  @JsonAlias({"rider_name", "RiderName"})
  private String riderName;

  @JsonProperty("trackingUrl")
  @JsonAlias({"tracking_url", "TrackingUrl"})
  private String trackingUrl;

  @JsonProperty("thirdPartyAllocationTime")
  @JsonAlias("ThirdPartyAllocationTime")
  private String thirdPartyAllocationTime;

  @JsonProperty("thirdPartyAcceptanceTime")
  @JsonAlias("ThirdPartyAcceptanceTime")
  private String thirdPartyAcceptanceTime;

  @JsonProperty("thirdPartyOrderCreationTime")
  @JsonAlias("ThirdPartyOrderCreationTime")
  private String thirdPartyOrderCreationTime;

  @JsonProperty("is_3pl_eligible")
  private Boolean is_3pl_eligible;

  @JsonProperty("pod_timestamp")
  private String podTimestamp;

  @JsonProperty("pbra_details")
  private Object pbraDetails;

  @JsonProperty("reverse_llm_metadata")
  private String reverseLlmMetadata;

  @JsonProperty("otpDetails")
  private String otpDetails;

  @JsonProperty("isOfflineDelivery")
  private Boolean isOfflineDelivery;

  @JsonProperty("offlineDeliveryTimestamp")
  private String offlineDeliveryTimestamp;

  @JsonProperty("mygate_feature_flag")
  private Boolean myGateFeatureFlag;

  @JsonProperty("mygate_ab_experiment")
  private String myGateAbExperiment;

  @JsonProperty("items_collected_details")
  private String itemsCollectedDetails;

  @JsonProperty("items_handover_details")
  private String itemsHandoverDetails;

  @JsonProperty("mygate_society_id")
  private String myGateSocietyId;

  @JsonProperty("is_manually_dispatch")
  private Boolean isManuallyDispatch;

  @JsonProperty("manually_dispatch_timestamp")
  private String manuallyDispatchTimestamp;

  @JsonProperty("wrong_location_details")
  private Object wrong_location_details;

  @JsonProperty("IsTplDelivery")
  private Boolean IsTplDelivery;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtraDetails that = (ExtraDetails) o;
    return Objects.equals(statusChangeLog, that.statusChangeLog);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusChangeLog);
  }
}
