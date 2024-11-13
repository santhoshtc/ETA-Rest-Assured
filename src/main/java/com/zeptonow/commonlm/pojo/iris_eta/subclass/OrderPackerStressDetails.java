package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPackerStressDetails {

  private String startTime;
  private String endTime;
  private Integer numberOfOrders;
  private Integer numberOfPackers;
  private Integer orderPackerStress;
  private Integer packerStressThreshold;
  private Boolean packerStressThresholdBreached;
  private Boolean packerStressAutoBannerEnabled;
  private Boolean packerStressV2Enabled;
}
