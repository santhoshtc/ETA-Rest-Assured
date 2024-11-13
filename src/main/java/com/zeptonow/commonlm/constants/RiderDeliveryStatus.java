package com.zeptonow.commonlm.constants;

public enum RiderDeliveryStatus {
  RIDER_DELIVERY_IN_STORE("IN_STORE", "In Store - Unassigned"),
  RIDER_DELIVERY_IN_STORE_ASSIGNED("IN_STORE_ASSIGNED", "In Store - Assigned"),
  RIDER_DELIVERY_OUT_FOR_DELIVERY("OUT_FOR_DELIVERY", "On Road"),
  RIDER_DELIVERY_ARRIVED("ARRIVED", "Reached Gate"),
  RIDER_DELIVERY_RETURNING_TO_STORE("RETURNING_TO_STORE", "Returning to Store"),
  RIDER_DELIVERY_RETURNING_TO_STORE_RTO("RETURNING_TO_STORE_RTO", ""),
  RIDER_DELIVERY_OUT_OF_STORE("OUT_OF_STORE", "Outside Store Geofence"),
  RIDER_DELIVERY_OFFLINE("OFFLINE", "Inactive"),
  RIDER_RETURNED_TO_STORE("RETURNED_TO_STORE", "");

  public final String status;
  public final String message;

  RiderDeliveryStatus(String status, String message) {
    this.status = status;
    this.message = message;
  }
}
