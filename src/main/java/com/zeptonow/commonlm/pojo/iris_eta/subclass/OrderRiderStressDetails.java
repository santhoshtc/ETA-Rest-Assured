package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRiderStressDetails {

  private String startTime;
  private String endTime;
  private Integer numberOfOrders;
  private Integer numberOfRiders;
  private Integer orderRiderStress;
  private Integer riderStressThreshold;
  private Boolean riderStressThresholdBreached;
  private Boolean riderStressAutoBannerEnabled;
}
