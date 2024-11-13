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
public class NearByRetailer {

  @Builder.Default
  @JsonProperty("retailPartner")
  public String retailPartner = "AIRTEL_MONEY";

  @Builder.Default
  @JsonProperty("shopName")
  public String shopName = "MANGALMURTI MOBILE STORE";

  @Builder.Default
  @JsonProperty("phoneNumber")
  public String phoneNumber = "9004144264";

  @Builder.Default
  @JsonProperty("address")
  public String address = "";

  @Builder.Default
  @JsonProperty("city")
  public String city = "MUMBAI";

  @Builder.Default
  @JsonProperty("pincode")
  public String pincode = "400013";

  @Builder.Default
  @JsonProperty("distance")
  public Object distance = 385.9574838976395;

  @JsonProperty("location")
  public Location location;
}
