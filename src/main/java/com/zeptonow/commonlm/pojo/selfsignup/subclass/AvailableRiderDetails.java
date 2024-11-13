package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRiderDetails {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("riderName")
  public String riderName;

  @JsonProperty("riderCode")
  public String riderCode;

  @JsonProperty("mobileNumber")
  public String mobileNumber;
}
