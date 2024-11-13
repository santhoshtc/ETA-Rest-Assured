package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatEtaDetails {
  private Integer etaSecRatBau;
  private Integer etaSecRatWithRoundRobinTime;
  private Integer etaSecRatWithoutRoundRobinTime;
  private Integer rtsSecRatBau;
  private Integer rtsSecRatWithRoundRobinTime;
  private Integer rtsSecRatWithoutRoundRobinTime;
}
