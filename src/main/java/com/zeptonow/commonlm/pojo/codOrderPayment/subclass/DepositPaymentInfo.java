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
public class DepositPaymentInfo {

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("paymentMethod")
  public String paymentMethod;

  @JsonProperty("subPaymentMethod")
  public String subPaymentMethod;

  @JsonProperty("paymentCode")
  public String paymentCode;

  @JsonProperty("retailStoreInfo")
  public DepositReatailStoreInfo retailStoreInfo;

  @JsonProperty("cashInStoreInfo")
  public Object cashInStoreInfo;

  @JsonProperty("image")
  public String image;

  @JsonProperty("message")
  public String message;
}
