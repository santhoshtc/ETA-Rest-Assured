package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.RiderDeliveryStatusSummary;
import com.zeptonow.commonlm.pojo.dms.subclass.RiderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRidersByFilterResponse {

  @JsonProperty("nextPage")
  private Integer nextPage;

  @JsonProperty("pageSize")
  private Integer pageSize;

  @JsonProperty("riderDeliveryStatusSummary")
  private RiderDeliveryStatusSummary riderDeliveryStatusSummary;

  @JsonProperty("riderDetails")
  private List<RiderDetail> riderDetails = null;
}
