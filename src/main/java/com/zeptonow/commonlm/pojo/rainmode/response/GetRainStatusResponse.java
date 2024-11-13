package com.zeptonow.commonlm.pojo.rainmode.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRainStatusResponse {
  private Boolean isRaining;
  private String rainIntensity;
  private String rainModeEndTime;
  private Integer rainModeEndsInSec;
}
