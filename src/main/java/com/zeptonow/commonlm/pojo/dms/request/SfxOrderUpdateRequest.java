package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class SfxOrderUpdateRequest {

  @Builder.Default
  @JsonProperty("rider_name")
  private String riderName = "Amit Kumar";

  @JsonProperty("sfx_order_id")
  private Integer sfxOrderID;

  @JsonProperty("client_order_id")
  private String clientOrderID;

  @JsonProperty("order_status")
  private String orderStatus;

  @JsonProperty("rider_contact")
  private String riderContact = "9898989898";

  @JsonProperty("rider_latitude")
  private Float RiderLatitude = 12.343424f;

  @JsonProperty("rider_longitude")
  private Float RiderLongitude = 77.987987987f;

  @JsonProperty("track_url")
  private String trackUrl = "http://api.shadowfax.in/track/1C3CEC76E35579F1844F346C1D15F603/";

  @JsonProperty("pickup_eta")
  private Integer pickupETA;

  @JsonProperty("drop_eta")
  private Integer dropETA;
}
