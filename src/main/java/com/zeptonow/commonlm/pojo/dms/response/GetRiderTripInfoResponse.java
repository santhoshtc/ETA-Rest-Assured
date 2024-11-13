package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.NewTripDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRiderTripInfoResponse {

  @Builder.Default private boolean onActiveDelivery = false;
  private boolean insideGeofence;
  @Builder.Default private String tripId = "00000000-0000-0000-0000-000000000000";

  @JsonProperty("isRiderCheckedIn")
  private boolean isRiderCheckedIn;

  @JsonProperty("newTripDetails")
  public NewTripDetails newTripDetails;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetRiderTripInfoResponse that = (GetRiderTripInfoResponse) o;
    return onActiveDelivery == that.onActiveDelivery
        && insideGeofence == that.insideGeofence
        && Objects.equals(tripId, that.tripId)
        && isRiderCheckedIn == that.isRiderCheckedIn;
  }

  @Override
  public int hashCode() {
    return Objects.hash(onActiveDelivery, insideGeofence, tripId);
  }
}
