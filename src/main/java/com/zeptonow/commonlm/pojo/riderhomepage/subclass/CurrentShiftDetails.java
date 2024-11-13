/***
 * Date: 05/01/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentShiftDetails {

  @JsonProperty("totalTimeInMins")
  public Integer totalTimeInMins;

  @JsonProperty("completedTimeInMins")
  public Integer completedTimeInMins;

  @JsonProperty("primaryMessage")
  public String primaryMessage;

  @JsonProperty("secondaryMessage")
  public String secondaryMessage;
}
