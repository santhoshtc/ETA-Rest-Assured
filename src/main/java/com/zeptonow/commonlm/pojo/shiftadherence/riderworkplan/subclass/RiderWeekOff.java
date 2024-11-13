/***
 * Date: 04/10/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RiderWeekOff {

  @JsonProperty("weekOffDay")
  public String weekOffDay;
}
