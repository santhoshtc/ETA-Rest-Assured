package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderCashBalanceResponse {

  @JsonProperty("cashBalance")
  public Integer cashBalance;

  @JsonProperty("minDue")
  public Integer minDue;

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("isMultipleSellerPresent")
  public Boolean isMultipleSellerPresent;

  @JsonProperty("cashBalanceAlerts")
  public String cashBalanceAlerts;

  @JsonProperty("cashLimit")
  public Integer cashLimit;
}
