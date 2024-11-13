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
public class GetConsignmentByStoreIdAndStatusRequest {

  @Builder.Default
  @JsonProperty("isActive")
  public Boolean isActive = true;

  @Builder.Default
  @JsonProperty("orderCode")
  public String orderCode = "";

  @Builder.Default
  @JsonProperty("status")
  public String status = "";

  @Builder.Default
  @JsonProperty("token")
  public Integer token = 0;

  @Builder.Default
  @JsonProperty("tripId")
  public String tripId = "";
}
