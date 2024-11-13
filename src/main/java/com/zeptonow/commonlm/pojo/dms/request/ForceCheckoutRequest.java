/***
 * Date: 29/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForceCheckoutRequest {

  @JsonProperty("riderId")
  public String riderId;

  @Builder.Default
  @JsonProperty("reason")
  public String reason = "order mark arrived by rider";
}
