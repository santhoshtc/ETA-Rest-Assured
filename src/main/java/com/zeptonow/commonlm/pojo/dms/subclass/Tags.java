package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tags {

  @Builder.Default
  @JsonProperty("flagged")
  public Boolean flagged = true;

  @Builder.Default
  @JsonProperty("label")
  public String label = "/rider/images/ic_reusable-logo.png";

  @Builder.Default
  @JsonProperty("message")
  public String message = "Collect the Bag";

  @Builder.Default
  @JsonProperty("name")
  public String name = "Collect the Bag";
}
