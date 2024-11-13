package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectCashResponse {

  @JsonProperty("status")
  public String status;

  @JsonProperty("failureReason")
  public String failureReason;
}
