/***
 * Date: 08/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */

package com.zeptonow.commonlm.pojo.createrider.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRiderResponse {

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("id")
  private String id;

  @JsonProperty("vehicleType")
  private String vehicleType;

  @JsonProperty("riderCode")
  private String riderCode;
}
