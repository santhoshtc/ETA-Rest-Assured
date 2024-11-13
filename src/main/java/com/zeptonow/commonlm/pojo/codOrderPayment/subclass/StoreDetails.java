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
public class StoreDetails {

  @JsonProperty("retailPartner")
  public String retailPartner;

  @JsonProperty("shopName")
  public String shopName;

  @JsonProperty("phoneNumber")
  public String phoneNumber;

  @JsonProperty("address")
  public String address;

  @JsonProperty("city")
  public String city;

  @JsonProperty("pincode")
  public String pincode;

  @JsonProperty("distance")
  public Float distance;

  @JsonProperty("location")
  public Location location;
}
