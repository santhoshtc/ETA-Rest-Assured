package com.zeptonow.commonlm.pojo.codOrderPayment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderPayouts {

  @JsonProperty("amount")
  public Integer amount;

  @JsonProperty("transactionRefNo")
  public String transactionRefNo;

  @JsonProperty("transactionId")
  public String transactionId;
}
