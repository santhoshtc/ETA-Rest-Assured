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
public class FtbUserDetails {

  @JsonProperty("isFtbOrder")
  private Boolean isFtbOrder;

  @JsonProperty("orderCount")
  private Integer orderCount;
}
