package com.zeptonow.commonlm.pojo.codOrderPayment.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnsettledOrder {

  @JsonProperty("orderId")
  public String orderId;

  @JsonProperty("orderCode")
  public String orderCode;

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("orderAmountInPaise")
  public Integer orderAmountInPaise;

  @JsonProperty("settlementStatus")
  public String settlementStatus;

  @JsonProperty("settledAmountInPaise")
  public Integer settledAmountInPaise;

  @JsonProperty("deliveryTime")
  public String deliveryTime;
}
