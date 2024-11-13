package com.zeptonow.commonlm.pojo.rainmode.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRainModeRequest {
  private boolean rainFlag;
  private String storeId;
  private String rainIntensity;
}
