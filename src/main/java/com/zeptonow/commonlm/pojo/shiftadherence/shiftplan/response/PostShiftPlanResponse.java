package com.zeptonow.commonlm.pojo.shiftadherence.shiftplan.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Abhishek_Singh @Date 18/08/22
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostShiftPlanResponse {

  @JsonProperty("date")
  private String date;

  @JsonProperty("storeId")
  private String storeId;

  @JsonProperty("shiftId")
  private Integer shiftId;

  @JsonProperty("count")
  private Integer count;

  public PostShiftPlanResponse(String date, String storeId, String shiftId, String count) {

    this.date = date.replaceAll("\"", "");
    this.storeId = storeId.replaceAll("\"", "");
    this.shiftId = (Integer.parseInt(shiftId.replaceAll("\"", "")));
    this.count = (Integer.parseInt(count.replaceAll("\"", "")));
  }

  public PostShiftPlanResponse() {}

  @Override
  public String toString() {
    return "PostShiftPlanResponse{"
        + "date='"
        + date
        + '\''
        + ", storeId='"
        + storeId
        + '\''
        + ", shiftId="
        + shiftId
        + ", count="
        + count
        + '}';
  }
}
