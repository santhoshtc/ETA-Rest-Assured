package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentSummaryCount {

  @JsonProperty("Active Orders")
  public int activeOrders;

  @JsonProperty("Closed Orders")
  public int closedOrders;

  @JsonProperty("Zepton Unassigned")
  public int zeptonUnassigned;

  @JsonProperty("Zepton Assigned")
  public int zeptonAssigned;

  @JsonProperty("Ongoing")
  public int ongoing;

  @JsonProperty("Completed")
  public int completed;

  @JsonProperty("Cancelled")
  public int cancelled;

  @JsonProperty("Undelivered")
  public int undelivered;

  @JsonProperty("Breached")
  public int breached;
}
