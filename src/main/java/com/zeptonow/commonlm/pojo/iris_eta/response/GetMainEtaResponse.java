package com.zeptonow.commonlm.pojo.iris_eta.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zeptonow.commonlm.pojo.iris_eta.subclass.UnlimitedFreeDeliveryBannerData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMainEtaResponse {

  private String displayText;
  private String secondaryDisplayText;
  private String descriptionText;
  private String storeOpenTime;
  private String storeCloseTime;
  private String etaInMinutes;
  private String deliverableType;
  private String deliverableSubtype;
  private String isOtofDeliverable;
  private Object supportiveThinLineResponse;
  private UnlimitedFreeDeliveryBannerData unlimitedFreeDeliveryBannerData;
}
