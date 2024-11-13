package com.zeptonow.commonlm.pojo.codOrderPayment.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

  @Builder.Default
  @JsonProperty("lat")
  public Double lat = 18.9982665;

  @Builder.Default
  @JsonProperty("lon")
  public Double lon = 72.82816365;

  @Builder.Default
  @JsonProperty("geohash")
  public String geohash = "te7u14xbgjwf";

  @Builder.Default
  @JsonProperty("fragment")
  public Boolean fragment = true;
}
