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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Cloneable {

  @JsonProperty("order_code")
  public String orderCode;

  @JsonProperty("order_date")
  public String orderDate;

  @JsonProperty("start_time")
  public String startTime;

  @JsonProperty("expected_delivery_time")
  public String expectedDeliveryTime;

  @JsonProperty("expectedReturnToStoreTime")
  @JsonAlias("expected_return_to_store_time")
  public String expectedReturnToStoreTime;

  @Builder.Default
  @JsonProperty("is_batchable")
  @JsonAlias("isBatchable")
  public Boolean isBatchable = Boolean.FALSE;

  @Builder.Default
  @JsonProperty("item_Details")
  @JsonAlias("item_details")
  public Object itemDetails = Arrays.asList();

  @Builder.Default
  @JsonProperty("merchant_name")
  public String merchantName = "Commodum Groceries Private Limited";

  @Builder.Default
  @JsonProperty("tags")
  public List<Tags> tags = Arrays.asList(new Tags());

  @Builder.Default
  @JsonProperty("instructions")
  public List<Instructions> instructions = Arrays.asList(new Instructions());

  @Builder.Default
  @JsonProperty("seller_id")
  public String sellerId = "71e067e6-9a70-4157-8f5a-30cc5c62e49d";

  @Builder.Default
  @JsonProperty("eta_in_sec")
  public Integer etaInSec = 906;

  @Builder.Default
  @JsonProperty("polyline")
  public String polyline = "{}|mA{qcyM@qAPCtEM?m@?_C@g@";

  @Builder.Default
  @JsonProperty("storage_location")
  public Object storageLocation = null;

  @JsonProperty("hu_details")
  public Object huDetails;

  @JsonProperty("packer_details")
  public PackerDetails packerDetails;

  @Builder.Default
  @JsonProperty("weight_in_gms")
  public Integer weight_in_gms = 5000;

  @JsonProperty("is_3pl_eligible")
  private Boolean is3plEligible;

  @JsonProperty("demand_shaping_batching_enable")
  private Boolean demandShapingBatchingEnable;

  @JsonProperty("order_type")
  private String orderType;

  @JsonAlias("tips_in_paise")
  private Integer tipsInPaise;

  @JsonAlias("rider_to_store_eta_seconds")
  private Integer riderToStoreEtaSeconds;

  @JsonAlias("llm_eta_seconds")
  private Integer llmEtaSeconds;

  @JsonAlias("order_category")
  private List<String> orderCategory;

  @JsonAlias("order_status")
  private String orderStatus;

  @JsonAlias("pbra_details")
  private Object pbraDetails;

  @JsonProperty("is_manually_marked_online")
  private Boolean is_manually_marked_online;

  @JsonProperty("is_ftb_enhancement_enabled")
  private Boolean is_ftb_enhancement_enabled;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderDetails that = (OrderDetails) o;
    return Objects.equals(orderCode, that.orderCode)
        && Objects.equals(orderDate, that.orderDate)
        && Objects.equals(startTime, that.startTime)
        && Objects.equals(expectedDeliveryTime, that.expectedDeliveryTime)
        && Objects.equals(isBatchable, that.isBatchable)
        && Objects.equals(itemDetails, that.itemDetails)
        && Objects.equals(merchantName, that.merchantName)
        && Objects.equals(tags, that.tags)
        && Objects.equals(instructions, that.instructions)
        && Objects.equals(sellerId, that.sellerId)
        && Objects.equals(etaInSec, that.etaInSec)
        && Objects.equals(polyline, that.polyline)
        && Objects.equals(storageLocation, that.storageLocation)
        && Objects.equals(huDetails, that.huDetails)
        && Objects.equals(packerDetails, that.packerDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        orderCode,
        orderDate,
        startTime,
        expectedDeliveryTime,
        isBatchable,
        itemDetails,
        merchantName,
        tags,
        instructions,
        sellerId,
        etaInSec,
        polyline,
        storageLocation,
        huDetails,
        packerDetails);
  }
}
