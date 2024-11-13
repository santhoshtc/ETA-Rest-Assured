package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.DepositPaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDepositTransactionDetails {

  @JsonProperty("transactionReference")
  public String transactionReference;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;

  @JsonProperty("paymentTime")
  public String paymentTime;

  @JsonProperty("orders")
  public Object orders;

  @JsonProperty("paymentInfo")
  public DepositPaymentInfo paymentInfo;
}
