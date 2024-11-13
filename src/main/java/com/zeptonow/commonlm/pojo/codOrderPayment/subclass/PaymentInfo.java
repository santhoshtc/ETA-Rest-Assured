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
public class PaymentInfo {

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("message")
  public String message;

  @JsonProperty("paymentMethod")
  public String paymentMethod;

  @JsonProperty("subPaymentMethod")
  public String subPaymentMethod;

  @JsonProperty("paymentDate")
  public String paymentDate;

  @JsonProperty("externalRetailStoreName")
  public String externalRetailStoreName;
}
