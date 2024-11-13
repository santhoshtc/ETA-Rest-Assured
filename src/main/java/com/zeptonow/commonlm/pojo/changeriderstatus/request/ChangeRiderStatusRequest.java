package com.zeptonow.commonlm.pojo.changeriderstatus.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRiderStatusRequest {

  @JsonProperty("rider_id")
  public String riderId;

  @JsonProperty("user_id")
  public String userId;

  @JsonProperty("status")
  public String status;

  @Builder.Default
  @JsonProperty("sub_status")
  public String subStatus = "";
}
