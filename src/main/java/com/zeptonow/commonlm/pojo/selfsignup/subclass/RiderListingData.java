package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderListingData {

  @JsonProperty("hasNext")
  public Boolean hasNext;

  @JsonProperty("items")
  public List<Item> items;
}
