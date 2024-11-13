package com.zeptonow.commonlm.pojo.codOrderPayment.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearByRetailers {

  @JsonProperty("nearByRetailers")
  public List<NearByRetailer> nearByRetailers;
}
