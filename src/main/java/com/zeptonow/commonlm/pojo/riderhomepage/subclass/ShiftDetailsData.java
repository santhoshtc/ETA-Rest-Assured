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
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDetailsData {

  @JsonProperty("displayText")
  public String displayText;

  @JsonProperty("shiftStatus")
  public String shiftStatus;

  @JsonProperty("statusMessage")
  public String statusMessage;

  @JsonProperty("message")
  public String message;

  @JsonProperty("insideShift")
  public Boolean insideShift;

  @JsonProperty("activeHoursInMins")
  public Integer activeHoursInMins;

  @JsonProperty("currentShiftDetails")
  public CurrentShiftDetails currentShiftDetails;

  @JsonProperty("breakTimeDetails")
  public BreakTimeDetails breakTimeDetails;

  @JsonProperty("shiftSlots")
  public List<ShiftSlot> shiftSlots = null;
}
