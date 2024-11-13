package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.CustomerDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetConsignmentResponse {

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

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("orderCode")
  public String orderCode;

  @JsonProperty("expectedDeliveryTime")
  public String expectedDeliveryTime;

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("orderDetails")
  public OrderDetailsResponse orderDetails;

  @JsonProperty("deliveries")
  public List<Object> deliveries = null;

  @JsonProperty("paymentAmount")
  public Integer paymentAmount;

  @JsonProperty("loadTime")
  public String loadTime;

  @JsonProperty("nextStatus")
  public List<String> nextStatus = null;

  @JsonProperty("batchedOrderIds")
  public List<String> batchedOrderIds = null;

  @JsonProperty("rider_to_store_eta_seconds")
  private String rider_to_store_eta_seconds;

  @JsonProperty("llm_eta_seconds")
  private String llm_eta_seconds;

  @JsonProperty("orderCategory")
  private List<String> orderCategory;

  @JsonProperty("orderStatus")
  private String orderStatus;
}
