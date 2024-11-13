/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.CustomerDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.Delivery;
import com.zeptonow.commonlm.pojo.dms.subclass.Feedback;
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
public class CreateConsignmentResponse {

  @JsonProperty("id")
  public String id;

  @JsonProperty("version")
  public Integer version;

  @JsonProperty("orderId")
  public String orderId;

  @JsonProperty("orderCode")
  public String orderCode;

  @JsonProperty("isValid")
  public Boolean isValid;

  @JsonProperty("status")
  public String status;

  @JsonProperty("distanceInMeters")
  public Integer distanceInMeters;

  @JsonProperty("rating")
  public Integer rating;

  @JsonProperty("feedback")
  public Feedback feedback;

  @JsonProperty("customerDetails")
  public CustomerDetails customerDetails;

  @JsonProperty("paymentMode")
  public String paymentMode;

  public String expectedDeliveryTime;

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("instructions")
  public String instructions;

  @JsonProperty("orderDetails")
  public OrderDetailsResponse orderDetails;

  @JsonProperty("deliveries")
  public List<Delivery> deliveries = null;

  @JsonProperty("paymentAmount")
  public Integer paymentAmount;

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("loadTime")
  public String loadTime;

  @JsonProperty("nextStatus")
  public List<String> nextStatus = null;

  @JsonProperty("batchedOrderIds")
  public List<String> batchedOrderIds = null;
}
