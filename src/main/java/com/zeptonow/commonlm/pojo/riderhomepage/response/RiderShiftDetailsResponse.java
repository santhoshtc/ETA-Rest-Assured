/***
 * Date: 05/01/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.riderhomepage.subclass.ShiftDetailsData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderShiftDetailsResponse {

  @JsonProperty("errors")
  public List<Object> errors = null;

  @JsonProperty("data")
  public ShiftDetailsData data;
}
