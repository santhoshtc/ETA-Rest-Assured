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
public class ShiftSlot {

  @JsonProperty("displayText")
  public String displayText;

  @JsonProperty("slotStatus")
  public String slotStatus;

  @JsonProperty("slotMessage")
  public String slotMessage;
}
