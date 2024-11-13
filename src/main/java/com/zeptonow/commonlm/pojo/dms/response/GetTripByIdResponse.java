package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.BagEan;
import com.zeptonow.commonlm.pojo.dms.subclass.BagEansV2;
import com.zeptonow.commonlm.pojo.dms.subclass.Delivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTripByIdResponse {

  @JsonProperty("id")
  private String id;

  @JsonProperty("version")
  private int version;

  @JsonProperty("riderId")
  private String riderId;

  @JsonProperty("isValid")
  private boolean isValid;

  @JsonProperty("status")
  private String status;

  @JsonProperty("nextStatus")
  private String[] nextStatus;

  @JsonProperty("deliveries")
  private List<Delivery> deliveries;

  @JsonProperty("riderScreenStatus")
  private String riderScreenStatus;

  @JsonProperty("extraDetails")
  private Object extraDetails;

  @JsonProperty("currentOrder")
  private Object currentOrder;

  @JsonProperty("isReusableBag")
  private Boolean isReusableBag;

  @JsonProperty("bagEans")
  private List<BagEan> bagEans;

  @JsonProperty("bagEansV2")
  public List<BagEansV2> bagEansV2;

  private Boolean conductSurvey;
  private List<String> questionCategory;
}
