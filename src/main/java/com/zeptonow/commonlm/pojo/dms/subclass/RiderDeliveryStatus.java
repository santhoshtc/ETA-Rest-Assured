package com.zeptonow.commonlm.pojo.dms.subclass;

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
public class RiderDeliveryStatus {

  @JsonProperty("deliveryStatus")
  private String deliveryStatus;

  @JsonProperty("primaryMessage")
  private String primaryMessage;

  @JsonProperty("priority")
  private Integer priority;

  @JsonProperty("secondaryMessage")
  private String secondaryMessage;

  @JsonProperty("updatedOn")
  private String updatedOn;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RiderDeliveryStatus that = (RiderDeliveryStatus) o;
    return Objects.equals(deliveryStatus, that.deliveryStatus)
        && Objects.equals(primaryMessage, that.primaryMessage);
  }
}
