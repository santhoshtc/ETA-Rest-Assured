package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTimeView {

  @JsonProperty("deliveryMessage")
  private String deliveryMessage;

  @JsonProperty("deliveryMessagePriority")
  private Integer deliveryMessagePriority;

  @JsonProperty("deliveryTimeMessage")
  private String deliveryTimeMessage;
}
