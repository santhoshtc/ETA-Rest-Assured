/***
 * Date: 06/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderStatus {

  @JsonProperty("userId")
  public String userId;

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("status")
  public String status;

  @JsonProperty("subStatus")
  public String subStatus;

  @JsonProperty("reason")
  public String reason;
}