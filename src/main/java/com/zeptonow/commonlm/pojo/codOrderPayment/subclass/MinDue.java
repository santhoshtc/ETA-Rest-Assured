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
public class MinDue {
  @JsonProperty("amountInPaise")
  public Integer amountInPaise;

  @JsonProperty("reason")
  public String reason;
}
