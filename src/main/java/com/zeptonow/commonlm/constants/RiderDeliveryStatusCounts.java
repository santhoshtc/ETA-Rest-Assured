package com.zeptonow.commonlm.constants;

public enum RiderDeliveryStatusCounts {
  UNASSIGNED_COUNT("Unassigned", "IN_STORE"),
  ASSIGNED_COUNT("Assigned", "IN_STORE_ASSIGNED"),
  ON_ROAD_COUNT("On Road", "OUT_FOR_DELIVERY"),
  REACHED_GATE_COUNT("Reached Gate", "ARRIVED"),
  RETURNING_T0_STORE_COUNT("Returning To Store", "RETURNING_TO_STORE"),
  OUTSIDE_STORE_GEOFENCE_COUNT("Outside Store Geofence", "OUT_OF_STORE"),
  OTHERS("Invalid status", "invalid");

  public final String name;
  public final String deliveryStatus;

  RiderDeliveryStatusCounts(String name, String deliveryStatus) {
    this.name = name;
    this.deliveryStatus = deliveryStatus;
  }
}
