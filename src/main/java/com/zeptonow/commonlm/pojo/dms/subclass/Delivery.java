/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class Delivery {

  @JsonProperty("id")
  public String id;

  @JsonProperty("version")
  public Integer version;

  @JsonProperty("consignmentId")
  public String consignmentId;

  @JsonProperty("isValid")
  public Boolean isValid;

  @JsonProperty("status")
  public String status;

  @JsonProperty("tripId")
  public Object tripId;

  @JsonProperty("trip")
  public Object trip;

  @JsonProperty("consignment")
  public Object consignment;

  @JsonProperty("isBatched")
  public Boolean isBatched;

  @JsonProperty("extra_details")
  @JsonAlias("extraDetails")
  public ExtraDetails extraDetails;

  @JsonProperty("nextStatus")
  public List<String> nextStatus = null;

  @JsonProperty("batchedOrder")
  private Integer batchedOrder;

  @JsonProperty("isCurrentOrder")
  private Boolean isCurrentOrder;

  @JsonProperty("statusChangeEvent")
  private Object statusChangeEvent;

  @JsonProperty("IsOrderScanPending")
  private Boolean isOrderScanPending;

  @JsonProperty("batchedSequenceNumber")
  @JsonAlias("batched_sequence_number")
  private Integer batchedSequenceNumber;

  @JsonProperty("updatedAt")
  public String updatedAt;

  @JsonProperty("orderType")
  public String orderType;

  @JsonProperty("orderPodEnabled")
  public String orderPodEnabled;
}
