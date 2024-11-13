package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.ScannedBagsDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTripStatusRequest {

  @JsonProperty("status")
  public String status;

  @Builder.Default
  @JsonProperty("reasonId")
  public String reasonId = "1413c408-5019-48db-aad8-156d816d9cf6";

  @JsonProperty("dataEvent")
  public Integer[] dataEvent;

  @JsonProperty("scannedBagsDetails")
  public List<ScannedBagsDetail> scannedBagsDetails;

  @JsonProperty("bagValidationString")
  public String bagValidationString;
}
