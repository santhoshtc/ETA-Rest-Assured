package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentConsignmentInfo {

  @JsonProperty("customerDetails")
  private CustomerDetails customerDetails;

  @JsonProperty("deliveredBy")
  private String deliveredBy;

  @JsonProperty("deliveryId")
  private String deliveryId;

  @JsonProperty("deliveryNextStatus")
  private List<String> deliveryNextStatus = null;

  @JsonProperty("deliveryStatus")
  private String deliveryStatus;

  @JsonProperty("deliveryTimeView")
  private DeliveryTimeView deliveryTimeView;

  @JsonProperty("distanceInMeters")
  private Integer distanceInMeters;

  @JsonProperty("etaInSec")
  private Integer etaInSec;

  @JsonProperty("expectedDeliveryTime")
  private String expectedDeliveryTime;

  @JsonProperty("feedback")
  private List<Integer> feedback = null;

  @JsonProperty("id")
  private String id;

  @JsonProperty("instructions")
  private String instructions;

  @JsonProperty("isBatched")
  private Boolean isBatched;

  @JsonProperty("isValid")
  private Boolean isValid;

  @JsonProperty("loadTime")
  private String loadTime;

  @JsonProperty("nextStatus")
  private List<String> nextStatus = null;

  @JsonProperty("orderCode")
  private String orderCode;

  @JsonProperty("orderDetails")
  private OrderDetailsResponse orderDetails;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("orderStatus")
  private String orderStatus;

  @JsonProperty("orderStatusEnum")
  private String orderStatusEnum;

  @JsonProperty("orderStatusPriority")
  private Integer orderStatusPriority;

  @JsonProperty("paymentAmount")
  private Integer paymentAmount;

  @JsonProperty("paymentMode")
  private String paymentMode;

  @JsonProperty("paymentStatus")
  private String paymentStatus;

  @JsonProperty("polyline")
  private String polyline;

  @JsonProperty("rating")
  private Integer rating;

  @JsonProperty("riderDetails")
  private Object riderDetails;

  @JsonProperty("riderId")
  private String riderId;

  @JsonProperty("status")
  private String status;

  @JsonProperty("statusChangeLog")
  private Object statusChangeLog = null;

  @JsonProperty("storeId")
  private String storeId;

  @JsonProperty("tripId")
  private String tripId;

  @JsonProperty("tripStatus")
  private String tripStatus;

  @JsonProperty("version")
  private Integer version;

  private String trackingUrl;
  private String partnerName;

  @JsonProperty("IsTplDelivery")
  private Boolean IsTplDelivery;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrentConsignmentInfo that = (CurrentConsignmentInfo) o;
    return Objects.equals(customerDetails.getCustomerName(), that.customerDetails.getCustomerName())
        && Objects.equals(customerDetails.getLongitude(), that.customerDetails.getLongitude())
        && Objects.equals(customerDetails.getLatitude(), that.customerDetails.getLatitude())
        && Objects.equals(customerDetails.getPhoneNumber(), that.customerDetails.getPhoneNumber())
        && Objects.equals(deliveryId, that.deliveryId)
        && Objects.equals(deliveryStatus, that.deliveryStatus)
        && Objects.equals(distanceInMeters, that.distanceInMeters)
        && Objects.equals(expectedDeliveryTime, that.expectedDeliveryTime)
        && Objects.equals(id, that.id)
        && Objects.equals(isBatched, that.isBatched)
        && Objects.equals(isValid, that.isValid)
        && Objects.equals(orderCode, that.orderCode)
        && Objects.equals(orderDetails, that.orderDetails)
        && Objects.equals(orderId, that.orderId)
        && Objects.equals(riderId, that.riderId)
        && Objects.equals(status, that.status)
        && Objects.equals(tripId, that.tripId)
        && Objects.equals(
            ((List<Object>) statusChangeLog).size(), ((List<Object>) that.statusChangeLog).size());
  }
}
