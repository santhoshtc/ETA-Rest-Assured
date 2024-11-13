package com.zeptonow.commonlm.pojo.iris_eta.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePackingConfigRequest {
  private String storeId;
  private Integer pickingTimeBaseInSec;
  private Integer pickingTimeAddonInSec;
  private Integer travelTimeInSec;
  @Default private String createdBy = "c1c00ba5-7c1a-4888-ac28-0072b4985840";
  @Default private String updatedBy = "c1c00ba5-7c1a-4888-ac28-0072b4985840";
  private String createdOn;
  private String updatedOn;
}
