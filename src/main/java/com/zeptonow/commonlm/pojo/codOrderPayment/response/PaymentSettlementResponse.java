package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.CashAtStore;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.RetailStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettlementResponse {

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("qr")
  public Object qr;

  @JsonProperty("upi")
  public Object upi;

  @JsonProperty("retailStore")
  public RetailStore retailStore;

  @JsonProperty("transactionId")
  public String transactionId;

  @JsonProperty("cashAtStore")
  public CashAtStore cashAtStore;
}
