/***
 * Date: 01/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.shiftadherence.ridercontractshiftplan.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PostRiderContractShiftPlanResponse {

  @JsonProperty("riderContractType")
  private String riderContractType;

  @JsonProperty("shiftId")
  private Integer shiftId;

  public PostRiderContractShiftPlanResponse(String riderContractType, String shiftId) {

    this.riderContractType = riderContractType.replaceAll("\"", "");
    this.shiftId = (Integer.parseInt(shiftId.replaceAll("\"", "")));
  }

  public PostRiderContractShiftPlanResponse() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostRiderContractShiftPlanResponse that = (PostRiderContractShiftPlanResponse) o;
    return riderContractType.equals(that.riderContractType) && shiftId.equals(that.shiftId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(riderContractType, shiftId);
  }

  @Override
  public String toString() {
    return "RiderContractShiftPlanResponse{"
        + "riderContractType='"
        + riderContractType
        + '\''
        + ", shiftId="
        + shiftId
        + '}';
  }
}
