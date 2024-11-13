/***
 * Date: 29/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.shiftadherence.weekoffplan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAvailableWeekOffResponse {

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("shiftId")
  public Integer shiftId;

  @JsonProperty("day")
  public String day;

  @JsonProperty("weekOffPercent")
  public Integer weekOffPercent;

  @JsonProperty("status")
  public String status;
}
