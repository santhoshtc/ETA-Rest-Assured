/***
 * Date: 26/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.shiftadherence.weekoffplan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @author Abhishek_Singh @Date 26/08/22
 */
@Data
public class PostWeekOffPlanResponse {

  @JsonProperty("storeId")
  private String storeId;

  @JsonProperty("day")
  private String day;

  @JsonProperty("shiftId")
  private Integer shiftId;

  @JsonProperty("weekOffPercent")
  private Integer weekOffPercent;

  public PostWeekOffPlanResponse(
      String storeId, String day, String shiftId, String weekOffPercent) {

    this.storeId = storeId.replaceAll("\"", "");
    this.day = day.replaceAll("\"", "");
    this.shiftId = (Integer.parseInt(shiftId.replaceAll("\"", "")));
    this.weekOffPercent = (Integer.parseInt(weekOffPercent.replaceAll("\"", "")));
  }

  public PostWeekOffPlanResponse() {}

  @Override
  public String toString() {
    return "UploadWeekOffPlanResponse{"
        + "storeId='"
        + storeId
        + '\''
        + ", day='"
        + day
        + '\''
        + ", shiftId="
        + shiftId
        + ", weekOffPercent="
        + weekOffPercent
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostWeekOffPlanResponse that = (PostWeekOffPlanResponse) o;
    return storeId.equals(that.storeId)
        && day.equals(that.day)
        && shiftId.equals(that.shiftId)
        && weekOffPercent.equals(that.weekOffPercent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storeId, day, shiftId, weekOffPercent);
  }
}
