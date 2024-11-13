package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackingProductConf {
  private Integer pickingTimeBaseInSec;
  private Integer pickingTimeAddonInSec;
  private Integer travelTimeInSec;
}
