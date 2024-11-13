package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HandholdingConfigs {

  @JsonProperty("RIDER_HANDHOLDING_ENABLED")
  public Boolean riderHandholdingEnabled;
}
