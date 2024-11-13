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
public class MakePaymentResponse {

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("transactionId")
  public String transactionId;

  @JsonProperty("qr")
  public Object qr;
}
