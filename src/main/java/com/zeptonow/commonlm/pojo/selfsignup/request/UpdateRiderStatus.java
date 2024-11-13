/***
 * Date: 12/12/22
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
public class UpdateRiderStatus {

  @JsonProperty("riderId")
  public String riderId;

  @Builder.Default
  @JsonProperty("source")
  public String source = "a1213@23";

  @Builder.Default
  @JsonProperty("reason")
  public String reason = "dssdsdds";
}
