package com.zeptonow.commonlm.pojo.cashatstore.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashBalanceDetail {

  @JsonProperty("cashBalanceInPaise")
  public Integer cashBalanceInPaise;

  @JsonProperty("cashLimitInPaise")
  public Integer cashLimitInPaise;

  @JsonProperty("riderDetails")
  public RiderDetails riderDetails;
}
