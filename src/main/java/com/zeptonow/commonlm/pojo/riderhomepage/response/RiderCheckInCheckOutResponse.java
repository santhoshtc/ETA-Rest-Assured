/***
 * Date: 06/01/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.riderhomepage.subclass.CheckInCheckOutData;
import com.zeptonow.commonlm.pojo.riderhomepage.subclass.Errors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiderCheckInCheckOutResponse {

  @JsonProperty("status")
  public String status;

  @JsonProperty("code")
  public Integer code;

  @JsonProperty("errors")
  public List<Errors> errors = null;

  @JsonProperty("data")
  public CheckInCheckOutData data;
}
