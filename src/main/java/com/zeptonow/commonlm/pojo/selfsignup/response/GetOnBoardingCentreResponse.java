/***
 * Date: 20/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.OnboardingCenter;
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
public class GetOnBoardingCentreResponse {

  @JsonProperty("onboardingCenters")
  public List<OnboardingCenter> onboardingCenters;
}
