package com.zeptonow.commonlm.pojo.selfsignup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRiderConfigRequest {

  @JsonProperty("active")
  public Boolean active;

  @JsonProperty("value")
  public String value;
}
