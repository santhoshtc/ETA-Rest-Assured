package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderDetails {
  private Rider rider;
  private String availableOn;
  private Integer orderPlacedTripsBeforeCurrentOrder;
  private Integer orderInitiatedTripsBeforeCurrentOrder;
  private String onRoadAvailableOn;
}
