/***
 * Date: 17/05/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiderConfigData3pl {

  @JsonProperty("rider3PlVendorDetails")
  @JsonAlias({"riderCohortDetails", "onboardingCenter"})
  public List<Rider3PlVendorDetail> rider3PlVendorDetails;
}
