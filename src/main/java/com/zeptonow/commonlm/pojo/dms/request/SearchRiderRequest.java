package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class SearchRiderRequest {

  @JsonProperty("descendingOrder")
  private Object descendingOrder;

  @JsonProperty("isActive")
  private Object isActive;

  @JsonProperty("mobileNumber")
  private Object mobileNumber;

  @JsonProperty("page")
  private Object page;

  @JsonProperty("riderId")
  private Object riderId;

  @JsonProperty("riderName")
  private Object riderName;

  @JsonProperty("sortBy")
  private Object sortBy;

  @JsonProperty("status")
  private Object status;

  @JsonProperty("storeId")
  private Object storeId;
}
