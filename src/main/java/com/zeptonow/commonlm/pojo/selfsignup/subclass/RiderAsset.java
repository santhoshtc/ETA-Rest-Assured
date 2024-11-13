package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
public class RiderAsset {

  @JsonProperty("id")
  public String id;

  @JsonProperty("assetId")
  public String assetId;

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("userId")
  public String userId;

  @JsonProperty("asset")
  public String asset;

  @JsonProperty("riderMobileNumber")
  public String riderMobileNumber;

  @JsonProperty("createdOn")
  public String createdOn;

  @JsonProperty("quantity")
  public String quantity;

  @JsonProperty("recovered")
  public String recovered;

  @JsonProperty("updatedOn")
  public String updatedOn;

  @JsonProperty("createdById")
  public String createdById;

  @JsonProperty("updatedById")
  public String updatedById;

  @JsonProperty("riderName")
  public String riderName;

  @JsonProperty("storeName")
  public String storeName;

  @JsonProperty("city")
  public String city;

  public RiderAsset(
      String id,
      String assetId,
      String riderId,
      String userId,
      String asset,
      String riderMobileNumber,
      String createdOn,
      String quantity,
      String recovered,
      String updatedOn,
      String createdById,
      String updatedById,
      String riderName,
      String storeName,
      String city) {
    this.id = id;
    this.assetId = assetId;
    this.riderId = riderId;
    this.userId = userId;
    this.asset = asset;
    this.riderMobileNumber = riderMobileNumber;
    this.createdOn = createdOn;
    this.quantity = quantity;
    this.recovered = recovered;
    this.updatedOn = updatedOn;
    this.createdById = createdById;
    this.updatedById = updatedById;
    this.riderName = riderName;
    this.storeName = storeName;
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
    RiderAsset that = (RiderAsset) o;
    return Objects.equals(id, that.id)
        && Objects.equals(assetId, that.assetId)
        && Objects.equals(riderId, that.riderId)
        && Objects.equals(userId, that.userId)
        && Objects.equals(asset, that.asset)
        && Objects.equals(riderMobileNumber, that.riderMobileNumber)
        && Objects.equals(quantity, that.quantity)
        && Objects.equals(recovered, that.recovered)
        && Objects.equals(riderName, that.riderName)
        && Objects.equals(storeName, that.storeName)
        && Objects.equals(city, that.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        assetId,
        riderId,
        userId,
        asset,
        riderMobileNumber,
        quantity,
        recovered,
        updatedOn,
        riderName,
        storeName,
        city);
  }
}
