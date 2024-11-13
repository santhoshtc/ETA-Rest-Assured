package com.zeptonow.commonlm.pojo.selfsignup.response;

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
public class GetRiderConfigResponse {

  @JsonProperty("hasNext")
  public Boolean hasNext;

  @JsonProperty("items")
  public List<UpdateRiderConfigResponse> items;
}
