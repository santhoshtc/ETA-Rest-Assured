package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderList {

  @JsonProperty("id")
  public String id;

  @JsonProperty("version")
  public Integer version;

  @JsonProperty("orderId")
  public String orderId;

  @JsonProperty("isValid")
  public Boolean isValid;

  @JsonProperty("status")
  public String status;

  @JsonProperty("distanceInMeters")
  public Integer distanceInMeters;

  @JsonProperty("customerDetails")
  public CustomerDetails customerDetails;

  @JsonProperty("paymentMode")
  public String paymentMode;

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("orderDetails")
  public OrderDetailsResponse orderDetails;

  @JsonProperty("orderCode")
  public String orderCode;

  @JsonProperty("paymentAmount")
  public Integer paymentAmount;

  @JsonProperty("etaInSec")
  public Integer etaInSec;

  @JsonProperty("loadTime")
  public String loadTime;

  @JsonProperty("expectedDeliveryTime")
  public String expectedDeliveryTime;

  @JsonProperty("polyline")
  public String polyline;

  @JsonProperty("nextStatus")
  public List<String> nextStatus = null;

  @JsonProperty("orderStatus")
  public String orderStatus;

  @JsonProperty("orderStatusEnum")
  public String orderStatusEnum;

  @JsonProperty("deliveryStatus")
  public String deliveryStatus;

  @JsonProperty("deliveryId")
  public String deliveryId;

  @JsonProperty("tripId")
  public String tripId;

  @JsonProperty("orderStatusPriority")
  public Integer orderStatusPriority;

  @JsonProperty("deliveredBy")
  public String deliveredBy;

  @JsonProperty("isBatched")
  public Boolean isBatched;

  @JsonProperty("statusChangeLog")
  public List<Object> statusChangeLog = null;

  @JsonProperty("deliveryTimeView")
  public Object deliveryTimeView = null;

  @JsonProperty("deliveryNextStatus")
  public List<String> deliveryNextStatus = null;

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("tripStatus")
  public String tripStatus;

  @JsonProperty("riderDetails")
  public Object riderDetails = null;

  @JsonProperty("trackingUrl")
  public String trackingUrl;

  @JsonProperty("partnerName")
  public String partnerName;

  @JsonProperty("IsTplDelivery")
  public Boolean IsTplDelivery;
}
