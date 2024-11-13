/***
 * Date: 07/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeadCreationRequest {

  @Builder.Default
  @JsonProperty("fullName")
  public String fullName = "test-lastmile";

  @JsonProperty("vehicleType")
  public String vehicleType;

  @JsonProperty("mobileNumber")
  public String mobileNumber;

  @Builder.Default
  @JsonProperty("alternativeMobile")
  public String alternativeMobile = "";

  @Builder.Default
  @JsonProperty("source")
  public String source = "Zepto rider App";

  @JsonProperty("cityId")
  public String cityId;

  @JsonProperty("storeId")
  public String storeId;
}
