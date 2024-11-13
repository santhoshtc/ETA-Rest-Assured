package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.QualityCheckItemDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItermQualityCheckRequest {

  @JsonProperty("deliveryId")
  public String deliveryId;

  @Builder.Default
  @JsonProperty("itemDetails")
  public List<QualityCheckItemDetails> itemDetails = new ArrayList<>();

  @Builder.Default
  @JsonProperty("event")
  public String event = "ITEM_SCAN";

  @Builder.Default
  @JsonProperty("itemMarkedSource")
  public String itemMarkedSource = "APP";
}
