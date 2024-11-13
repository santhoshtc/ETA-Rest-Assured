package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateConsignmentRequest {
  @JsonProperty("order_id")
  private String orderId;

  @Builder.Default
  @JsonProperty("payment_mode")
  public String paymentMode = "COD";

  @Builder.Default
  @JsonProperty("payment_amount")
  public Integer paymentAmount = 44000;

  @JsonProperty("order_details")
  public OrderDetails orderDetails;
}
