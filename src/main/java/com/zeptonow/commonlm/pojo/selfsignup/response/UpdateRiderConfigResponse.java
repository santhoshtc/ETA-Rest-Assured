package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRiderConfigResponse {

  @JsonProperty("id")
  public String id;

  @JsonProperty("type")
  public String type;

  @JsonProperty("subType")
  public String subType;

  @JsonProperty("key")
  public String key;

  @JsonProperty("value")
  public String value;

  @JsonProperty("isActive")
  public Boolean isActive;

  @JsonProperty("createdById")
  public String createdById;

  @JsonProperty("createdOn")
  public String createdOn;

  @JsonProperty("updatedOn")
  public String updatedOn;
}
