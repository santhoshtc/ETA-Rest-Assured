package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Item {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("riderName")
  public String riderName;

  @JsonProperty("riderMobileNo")
  public String riderMobileNo;

  @JsonProperty("rider3plVendorId")
  public String rider3plVendorId;

  @JsonProperty("rider3plVendorName")
  public String rider3plVendorName;

  @JsonProperty("isValid")
  public Boolean isValid;

  @JsonProperty("createdOn")
  public String createdOn;

  @JsonProperty("updatedOn")
  public String updatedOn;

  @JsonProperty("vehicleType")
  public String vehicleType;

  @JsonProperty("contractType")
  public String contractType;

  @JsonProperty("deliveryVendor")
  public String deliveryVendor;

  @JsonProperty("vendorRiderId")
  public String vendorRiderId;

  @JsonProperty("store")
  public String store;

  @JsonProperty("city")
  public String city;

  public Item(
      String riderId,
      String riderName,
      String riderMobileNo,
      String rider3plVendorId,
      String rider3plVendorName,
      String vehicleType,
      String contractType,
      String store,
      String city) {
    this.riderId = riderId;
    this.riderName = riderName;
    this.riderMobileNo = riderMobileNo;
    this.rider3plVendorId = rider3plVendorId;
    this.rider3plVendorName = rider3plVendorName;
    this.vehicleType = vehicleType;
    this.contractType = contractType;
    this.store = store;
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return Objects.equals(riderId, item.riderId)
        && Objects.equals(riderName, item.riderName)
        && Objects.equals(riderMobileNo, item.riderMobileNo)
        && Objects.equals(rider3plVendorId, item.rider3plVendorId)
        && Objects.equals(rider3plVendorName, item.rider3plVendorName)
        && Objects.equals(vehicleType, item.vehicleType)
        && Objects.equals(contractType, item.contractType)
        && Objects.equals(store, item.store)
        && Objects.equals(city, item.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        riderId,
        riderName,
        riderMobileNo,
        rider3plVendorId,
        rider3plVendorName,
        vehicleType,
        contractType,
        store,
        city);
  }
}
