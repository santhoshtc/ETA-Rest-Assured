package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HandoverReturnItemRequest {

  @JsonProperty("deliveryId")
  public String deliveryId;

  @Builder.Default
  @JsonProperty("event")
  public String event = "HANDOVER_COMPLETED";

  @Builder.Default
  @JsonProperty("source")
  public String source = "automation";
}
