package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class PackerDetails extends com.zeptonow.common.pojo.dms.subclass.PackerDetails {

  @JsonProperty("availableOn")
  private Object availableOn;
}
