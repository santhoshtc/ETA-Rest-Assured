/***
 * Date: 02/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderCheckInCheckOutRequest {

  @JsonProperty("latitude")
  public Double latitude;

  @JsonProperty("longitude")
  public Double longitude;

  @JsonProperty("type")
  public String type;
}
