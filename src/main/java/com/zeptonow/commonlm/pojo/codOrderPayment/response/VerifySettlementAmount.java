package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.RiderLedgerInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifySettlementAmount {

  @JsonProperty("status")
  public String status;

  @JsonProperty("alert")
  public String alert;

  @JsonProperty("riderLedgerInfo")
  public RiderLedgerInfo riderLedgerInfo;
}
