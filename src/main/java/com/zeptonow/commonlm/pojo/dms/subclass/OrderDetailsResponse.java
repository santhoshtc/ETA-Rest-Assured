/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailsResponse implements Cloneable {

  @JsonProperty("orderCode")
  @JsonAlias("order_code")
  public String orderCode;

  @JsonProperty("orderDate")
  @JsonAlias("order_date")
  public String orderDate;

  @JsonProperty("startTime")
  @JsonAlias("start_time")
  public String startTime;

  @JsonProperty("expectedDeliveryTime")
  @JsonAlias("expected_delivery_time")
  public String expectedDeliveryTime;

  @JsonProperty("expectedReturnToStoreTime")
  @JsonAlias("expected_return_to_store_time")
  public String expectedReturnToStoreTime;

  @JsonProperty("isBatchable")
  @JsonAlias("is_batchable")
  public Boolean isBatchable;

  @JsonProperty("itemDetails")
  @JsonAlias("item_details")
  public Object itemDetails;

  @JsonProperty("merchantName")
  @JsonAlias("merchant_name")
  public String merchantName;

  @JsonProperty("tags")
  public List<Tags> tags = Arrays.asList(new Tags());

  @JsonProperty("instructions")
  public Object instructions;

  @JsonProperty("sellerId")
  @JsonAlias("seller_id")
  public String sellerId;

  @JsonProperty("etaInSec")
  @JsonAlias("eta_in_sec")
  public Integer etaInSec;

  @JsonProperty("polyline")
  public String polyline;

  @JsonProperty("huDetails")
  @JsonAlias("hu_details")
  public Object huDetails;

  @JsonProperty("packerDetails")
  @JsonAlias("packer_details")
  public PackerDetails packerDetails;

  @JsonProperty("storageLocation")
  @JsonAlias("storage_location")
  public String storageLocation;

  @JsonProperty("weightInGms")
  @JsonAlias("weight_in_gms")
  public Integer weightInGms;

  public Integer totalItemsCount;

  @JsonProperty("is_3pl_eligible")
  private Boolean is3plEligible;

  @JsonProperty("demand_shaping_batching_enable")
  private Boolean demandShapingBatchingEnable;

  @JsonProperty("order_type")
  private String orderType;

  @JsonAlias("tips_in_paise")
  private Object tipsInPaise;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderDetailsResponse that = (OrderDetailsResponse) o;
    return Objects.equals(orderDate, that.orderDate)
        && Objects.equals(expectedDeliveryTime, that.expectedDeliveryTime)
        && Objects.equals(isBatchable, that.isBatchable)
        && Objects.equals(merchantName, that.merchantName)
        && Objects.equals(sellerId, that.sellerId)
        && Objects.equals(weightInGms, that.weightInGms)
        && Objects.equals(polyline, that.polyline);
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
