package com.zeptonow.commonlm.pojo.rainmode.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRainBlockToggleRequest {
  private boolean blockRainMode;
  private List<String> storeIds;
}
