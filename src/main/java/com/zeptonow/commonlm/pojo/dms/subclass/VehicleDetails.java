package com.zeptonow.commonlm.pojo.dms.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleDetails {

  private Integer priority;
  private String vehicleType;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleDetails that = (VehicleDetails) o;
    return Objects.equals(vehicleType, that.vehicleType);
  }
}
