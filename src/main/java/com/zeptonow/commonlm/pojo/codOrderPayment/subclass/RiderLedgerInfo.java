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
public class RiderLedgerInfo {

  @JsonProperty("fullAmount")
  public Integer fullAmount;

  @JsonProperty("minDueAmount")
  public Integer minDueAmount;

  @JsonProperty("verificationPendingAmount")
  public Integer verificationPendingAmount;

  @JsonProperty("cashBalance")
  public Integer cashBalance;
}
