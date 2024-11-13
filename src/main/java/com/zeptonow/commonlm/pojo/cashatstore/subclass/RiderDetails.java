package com.zeptonow.commonlm.pojo.cashatstore.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDetails {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("name")
  public String name;

  @JsonProperty("mobileNumber")
  public String mobileNumber;

  @JsonProperty("vendorRiderId")
  public String vendorRiderId;
}
