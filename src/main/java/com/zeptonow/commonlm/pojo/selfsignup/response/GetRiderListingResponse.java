package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.Error;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.RiderListingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRiderListingResponse {

  @JsonProperty("errors")
  public List<Error> errors;

  @JsonProperty("data")
  public RiderListingData data;
}
