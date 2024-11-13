package com.zeptonow.commonlm.constants;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Statuses {
  UNASSIGNED("UNASSIGNED", null, "UNASSIGNED", 0),
  ASSIGNED("ASSIGNED", "CREATED", "ASSIGNED", 2),
  READY_TO_PICKUP("ASSIGNED", "READY_TO_PICKUP", "ASSIGNED", 3),
  READY_TO_ASSIGN("READY_TO_ASSIGN", "CREATED", "READY_TO_ASSIGN", 1),
  PICKED_UP("PICKED_UP", "READY_TO_START", "PICKED_UP", 4),
  STARTED("STARTED", "STARTED", "STARTED", 6),
  DISPATCHED("DISPATCHED", "READY_TO_START", "DISPATCHED", 5),
  AUTO_ARRIVED("AUTO_ARRIVED", "STARTED", "AUTO_ARRIVED", 7),
  ARRIVED("ARRIVED", "STARTED", "ARRIVED", 8),
  DELIVERED("DELIVERED", "RETURNING_TO_STORE", "DELIVERED", 9),
  RETURN_TO_ORIGIN("RETURN_TO_ORIGIN", "RETURNING_TO_STORE", "RETURN_TO_ORIGIN", 10),
  RETURNED("RETURNED", "DONE", "RETURNED", 11),
  CANCELLED("CANCELLED", "CANCELLED", "CANCELLED", 12),
  CREATED("CREATED", "CREATED", "CREATED", 0),
  RETURNED_TO_STORE("RETURN_TO_ORIGIN", "RETURNED_TO_STORE", "RETURN_TO_ORIGIN", 10),
  INITIATED("INITIATED", "INITIATED", "INITIATED", 14),
  ASSIGNED_TO_DELIVERY_CANCELLED("CANCELLED", "CANCELLED", "ASSIGNED", 2),
  UNASSIGNED_TO_DELIVERY_CANCELLED("CANCELLED", "CANCELLED", "UNASSIGNED", 0),
  THIRD_PARTY_ALLOTMENT_PENDING(
      "THIRD_PARTY_ALLOTMENT_PENDING", "CREATED", "THIRD_PARTY_ALLOTMENT_PENDING", 0),
  THIRD_PARTY_ARRIVED_AT_STORE(
      "THIRD_PARTY_ARRIVED_AT_STORE", "CREATED", "THIRD_PARTY_ARRIVED_AT_STORE", 0),
  REACHED("ARRIVED", "STARTED", "REACHED", 8),
  RTO_INITIATED("RETURN_TO_ORIGIN", "RETURNING_TO_STORE", "RTO_INITIATED", 10),
  RTO_DELIVERED("RETURNED", "DONE", "RTO_DELIVERED", 11),
  TPL_ALLOTMENT_INITIATED("TPL_ALLOTMENT_INITIATED", null, "TPL_ALLOTMENT_INITIATED", 0);
  public final String deliveryStatus;
  public final String tripStatus;
  public final String consignmentStatus;
  public final int rank;

  Statuses(String deliveryStatus, String tripStatus, String consignmentStatus, int rank) {
    this.deliveryStatus = deliveryStatus;
    this.tripStatus = tripStatus;
    this.consignmentStatus = consignmentStatus;
    this.rank = rank;
  }

  /**
   * get rank from consignment status
   *
   * @param status
   * @return
   * @author Manisha.Kumari
   */
  public static int getRank(String status) {
    return Arrays.stream(Statuses.values())
        .filter(v -> v.consignmentStatus.equals(status))
        .collect(Collectors.toList())
        .get(0)
        .rank;
  }
}
