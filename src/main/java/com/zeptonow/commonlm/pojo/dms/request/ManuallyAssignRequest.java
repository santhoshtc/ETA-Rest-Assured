package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManuallyAssignRequest {

  @JsonProperty("orderCodes")
  public List<String> orderCodes;

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("storeId")
  public String storeId;
}
