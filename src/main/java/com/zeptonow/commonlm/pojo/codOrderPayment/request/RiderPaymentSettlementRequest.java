package com.zeptonow.commonlm.pojo.codOrderPayment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.RetailStoreInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderPaymentSettlementRequest {
  @JsonProperty("amountInPaise")
  public Object amountInPaise;

  @JsonProperty("paymentMethod")
  public String paymentMethod;

  @JsonProperty("subPaymentMethod")
  public String subPaymentMethod;

  @JsonProperty("sellerId")
  public String sellerId;

  @Builder.Default
  @JsonProperty("vpa")
  public String vpa = "abc@paytm";

  @JsonProperty("retailStoreInfo")
  public RetailStoreInfo retailStoreInfo;
}
