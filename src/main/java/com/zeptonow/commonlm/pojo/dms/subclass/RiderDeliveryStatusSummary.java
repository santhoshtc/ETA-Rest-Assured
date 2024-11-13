package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderDeliveryStatusSummary {

  @JsonProperty("Assigned")
  @JsonAlias("assigned_count")
  private Integer assignedCount;

  @JsonProperty("On Road")
  @JsonAlias("on_road_count")
  private Integer onRoadCount;

  @JsonProperty("Reached Gate")
  @JsonAlias("reached_gate_count")
  private Integer reachedGateCount;

  @JsonProperty("Returning To Store")
  @JsonAlias("returning_to_store_count")
  private Integer returningToStoreCount;

  @JsonProperty("Total Active Riders")
  private Integer totalActiveRiders;

  @JsonProperty("Total Inactive Riders")
  private Integer totalInactiveRiders;

  @JsonProperty("Unassigned")
  @JsonAlias("unassigned_count")
  private Integer unassignedCount;

  @JsonProperty("Returned To Store")
  private Integer returnedToStoreCount;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RiderDeliveryStatusSummary summary = (RiderDeliveryStatusSummary) o;
    return Objects.equals(assignedCount, summary.assignedCount)
        && Objects.equals(onRoadCount, summary.onRoadCount)
        && Objects.equals(reachedGateCount, summary.reachedGateCount)
        && Objects.equals(returningToStoreCount, summary.returningToStoreCount)
        && Objects.equals(unassignedCount, summary.unassignedCount);
  }
}
