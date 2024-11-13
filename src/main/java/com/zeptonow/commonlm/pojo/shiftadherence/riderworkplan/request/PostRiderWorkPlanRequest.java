/***
 * Date: 04/10/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.subclass.RiderShift;
import com.zeptonow.commonlm.pojo.shiftadherence.riderworkplan.subclass.RiderWeekOff;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRiderWorkPlanRequest {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("riderShift")
  public RiderShift riderShift;

  @JsonProperty("riderWeekOff")
  public RiderWeekOff riderWeekOff;
}
