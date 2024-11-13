package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRiderStressV2Details {

  @JsonProperty("endTime")
  private String endTime;

  @JsonProperty("orderRiderStressV2")
  private Integer orderRiderStressV2;

  @JsonProperty("orderRiderStressV2Rat")
  private Integer orderRiderStressV2Rat;

  @JsonProperty("orderRiderStressV2Orders")
  private Integer orderRiderStressV2Orders;
}
