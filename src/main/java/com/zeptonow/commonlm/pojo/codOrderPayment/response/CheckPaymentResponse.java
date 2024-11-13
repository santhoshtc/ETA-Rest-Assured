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
public class CheckPaymentResponse {

  @JsonProperty("status")
  public String status;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;
}
