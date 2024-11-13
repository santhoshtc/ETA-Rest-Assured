package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeliveryStatusRequest {
  @Builder.Default
  @JsonProperty("checkOutAfterDelivery")
  public Boolean checkOutAfterDelivery = false;

  @JsonProperty("dataEvent")
  public Object dataEvent;

  @Builder.Default
  @JsonProperty("reasonId")
  public String reasonId = "2406cec3-b6cd-4f47-a2ae-c72f36fa8ba8";

  @JsonProperty("status")
  public String status;

  @Builder.Default public Boolean activeBinScan = false;
}
