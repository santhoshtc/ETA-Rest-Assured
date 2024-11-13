package com.zeptonow.commonlm.pojo.cashatstore.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountBreakUp {

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("sellerName")
  public String sellerName;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;

  private Integer payNearByAmountInPaise;
  private Integer activeDepositAmountInPaise;
  private Integer currentDayUnsettledAmount;
}
