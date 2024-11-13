package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderDetail {

  @JsonProperty("breakDurationInMins")
  private Integer breakDurationInMins;

  @JsonProperty("contractType")
  private String contractType;

  @JsonProperty("currentConsignmentIndex")
  private Integer currentConsignmentIndex = 0;

  @JsonProperty("currentConsignmentInfo")
  private List<CurrentConsignmentInfo> currentConsignmentInfo = null;

  @JsonProperty("eligibility")
  private Eligibility eligibility;

  @JsonProperty("id")
  private String id;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("onRoadTime")
  private Integer onRoadTime;

  @JsonProperty("ordersDeliveredToday")
  private Integer ordersDeliveredToday = 0;

  @JsonProperty("riderDeliveryStatus")
  private RiderDeliveryStatus riderDeliveryStatus;

  @JsonProperty("riderId")
  private String riderId;

  @JsonProperty("riderName")
  private String riderName;

  @JsonProperty("shift")
  private Shift shift;

  @JsonProperty("vehicleDetails")
  private VehicleDetails vehicleDetails;

  @JsonProperty("vendorRiderId")
  private String vendorRiderId;

  @JsonProperty("firstCheckInTime")
  private String firstCheckInTime;

  @JsonProperty("isFirstCheckIn")
  private Boolean isFirstCheckIn;

  @JsonProperty("loyaltyTierId")
  private String loyaltyTierId;

  @JsonProperty("loyaltyTierLevel")
  private String loyaltyTierLevel;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RiderDetail that = (RiderDetail) o;
    return Objects.equals(contractType, that.contractType)
        && Objects.equals(eligibility, that.eligibility)
        && Objects.equals(id, that.id)
        && Objects.equals(mobileNumber, that.mobileNumber)
        && Objects.equals(riderId, that.riderId)
        && Objects.equals(riderName, that.riderName)
        && Objects.equals(vehicleDetails, that.vehicleDetails)
        && Objects.equals(vendorRiderId, that.vendorRiderId)
        && Objects.equals(riderDeliveryStatus, that.riderDeliveryStatus)
        && (((that.currentConsignmentInfo == null || that.currentConsignmentInfo.size() < 1)
                && (currentConsignmentInfo == null || currentConsignmentInfo.size() < 1))
            || Objects.equals(currentConsignmentInfo, that.currentConsignmentInfo));
  }
}
