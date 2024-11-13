package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

  @JsonProperty("batchedConsignmentsIds")
  private List<String> batchedConsignmentsIds;

  @JsonProperty("batchedOrderIds")
  private List<String> batchedOrderIds;

  @JsonProperty("customerDetails")
  private CustomerDetails customerDetails;

  @JsonProperty("deliveries")
  private List<Delivery> deliveries;

  @JsonProperty("distanceInMeters")
  private int distanceInMeters;

  @JsonProperty("expectedDeliveryTime")
  private String expectedDeliveryTime;

  @JsonProperty("id")
  private String id;

  @JsonProperty("isValid")
  private boolean isValid;

  @JsonProperty("loadTime")
  private String loadTime;

  @JsonProperty("nextStatus")
  private ArrayList<String> nextStatus;

  @JsonProperty("orderCode")
  private String orderCode;

  @JsonProperty("orderDetails")
  private OrderDetailsResponse orderDetails;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("paymentAmount")
  private int paymentAmount;

  @JsonProperty("paymentMode")
  private String paymentMode;

  @JsonProperty("paymentStatus")
  private String paymentStatus;

  @JsonProperty("status")
  private String status;

  @JsonProperty("storeId")
  private String storeId;

  @JsonProperty("version")
  private int version;
}
