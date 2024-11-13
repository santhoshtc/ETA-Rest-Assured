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
public class Deposit {

  @JsonProperty("transactionId")
  public String transactionId;

  @JsonProperty("transactionReference")
  public String transactionReference;

  @JsonProperty("paymentInfo")
  public PaymentInfo paymentInfo;

  @JsonProperty("numberOfOrders")
  public Integer numberOfOrders;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;
}
