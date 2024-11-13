/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties
public class CustomerDetails {

  @Builder.Default
  @JsonProperty("customer_id")
  @JsonAlias("customerId")
  public String customerId = "f2b252ac-3d0c-4718-83d9-b30265b5db56";

  @Builder.Default
  @JsonProperty("customer_name")
  @JsonAlias({"customerName"})
  public String customerName = "test";

  @Builder.Default
  @JsonProperty("phone_number")
  @JsonAlias("phoneNumber")
  public Long phoneNumber = 9953544615L;

  @Builder.Default
  @JsonProperty("address_line_1")
  @JsonAlias({"addressLine1"})
  public String addressLine1 = "123 321";

  @Builder.Default
  @JsonProperty("address_line_2")
  @JsonAlias({"addressLine2"})
  public String addressLine2 = "123 321";

  @Builder.Default
  @JsonProperty("latitude")
  public Double latitude = 12.925211;

  @Builder.Default
  @JsonProperty("longitude")
  public Double longitude = 77.674356;

  @Builder.Default
  @JsonProperty("old_latitude")
  public Double old_latitude = 0.0;

  @Builder.Default
  @JsonProperty("old_longitude")
  public Double old_longitude = 0.0;

  @JsonProperty("city_name")
  @JsonAlias("cityName")
  @Builder.Default
  public String cityName = "Mumbai";

  @JsonAlias("building_geofence")
  public List<List<String>> buildingGeofence;

  @JsonAlias("building_id")
  public String buildingId;

  @JsonProperty("address_id")
  public String address_id;

  @JsonProperty("address_updated_at")
  public String address_updated_at;

  @JsonAlias("customer_segment")
  @JsonProperty("CustomerSegment")
  public String customerSegment;

  @JsonAlias("customer_pod_exp_variant")
  @JsonProperty("CustomerPODExpVariant")
  public String customerPODExpVariant;

  @JsonAlias("customer_pod_exp_eligibility")
  @JsonProperty("CustomerPODExpEligibility")
  public Boolean customerPODExpEligibility;

  @JsonProperty("landmark")
  public String landmark;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerDetails that = (CustomerDetails) o;
    return Objects.equals(customerId, that.customerId)
        && Objects.equals(customerName, that.customerName)
        && Objects.equals(phoneNumber, that.phoneNumber)
        && Objects.equals(addressLine1, that.addressLine1)
        && Objects.equals(addressLine2, that.addressLine2)
        && Objects.equals(latitude, that.latitude)
        && Objects.equals(longitude, that.longitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        customerId, customerName, phoneNumber, addressLine1, addressLine2, latitude, longitude);
  }
}
