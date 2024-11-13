package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BulkUpdateHuDetailsRequest {

  @JsonProperty("store_id")
  private String storeId;

  @JsonProperty("time_in_min")
  private Integer timeInMin;
}
