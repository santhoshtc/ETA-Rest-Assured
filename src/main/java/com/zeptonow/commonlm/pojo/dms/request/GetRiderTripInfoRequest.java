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
public class GetRiderTripInfoRequest {

  @JsonProperty("latitude")
  public Double latitude;

  @JsonProperty("longitude")
  public Double longitude;

  private String riderId;
}
