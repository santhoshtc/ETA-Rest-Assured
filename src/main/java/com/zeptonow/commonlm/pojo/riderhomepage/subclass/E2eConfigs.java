/***
 * Date: 23/02/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
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
public class E2eConfigs {

  @JsonProperty("DISABLE_AUTO_ARRIVAL_AT_CUSTOMER")
  public String disableAutoArrivalAtCustomer;

  @JsonProperty("ENABLE_LOCAL_NOTIFICATIONS")
  public String enableLocalNotifications;

  @JsonProperty("ENABLE_LOCAL_PACKING_NOTIFICATIONS")
  public String enableLocalPackingNotifications;

  @JsonProperty("MARK_DELIVERED_AFTER_ARRIVAL_TIMER_IN_SEC")
  public String markDeliveredAfterArrivalTimerInSec;

  @JsonProperty("ORDER_PICKUP_DELAY_SECS")
  public String orderPickupDelaySecs;

  @JsonProperty("ORDER_PICKUP_HANDSHAKE_ENABLED")
  public String orderPickupHandshakeEnabled;

  @JsonProperty("POD_UPLOAD_ENABLED")
  public String podUploadEnabled;

  @JsonProperty("RTB_DEADLINE_HOURS")
  public String rtbDeadlineHours;

  @JsonProperty("RTB_MANUAL_QR_ENTRY")
  public String rtbManualQrEntry;

  @JsonProperty("RTB_PENALTY_AMOUNT_IN_PAISE")
  public String rtbPenaltyAmountInPaise;

  @JsonProperty("RTS_NON_BINDING")
  public String rtsNonBinding;

  @JsonProperty("SHOW_FULL_MAP")
  public String showFullMap;

  @JsonProperty("SHOW_MARK_BAG_LOST")
  public String showMarkBagLost;

  @JsonProperty("UPLOAD_DELIVERY_IMAGE_MAX_COUNT")
  public String uploadDeliveryImageMaxCount;

  @JsonProperty("PACKED_NOTIFICATION_ENABLED")
  public String packedNotificationEnabled;

  @JsonProperty("COMPRESSION_QUALITY")
  public String compressionQuantity;

  @JsonProperty("BANNER_TEXT_ON_ARRIVAL_SCREEN")
  public String bannerTextOnArrivalScreen;

  @JsonProperty("SHOW_BANNER_ON_ARRIVAL_SCREEN")
  public String showBannerOnArrivalScreen;

  @JsonProperty("ENABLE_BATCHED_OFFLINE_DELIVERY")
  public String enableBatchedOfflineDelivery;

  @JsonProperty("ENABLE_SINGLE_OFFLINE_DELIVERY")
  public String enableSingleOfflineDelivery;

  @JsonProperty("SHOW_FRONT_CAMERA_ON_POD")
  public String showFrontCameraOnPof;

  @JsonProperty("STORYLY_ID")
  public String storylyId;

  @JsonProperty("MAX_QC_POD_COUNT")
  public String maxQcPodCount;

  @JsonProperty("MIN_QC_POD_COUNT")
  public String minQcPodCount;
}
