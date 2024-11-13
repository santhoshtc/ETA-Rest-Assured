package com.zeptonow.commonlm.pojo.cashatstore.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayNearByRequest {

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;

  private String paymentMethod;
  private String subPaymentMethod;
}
