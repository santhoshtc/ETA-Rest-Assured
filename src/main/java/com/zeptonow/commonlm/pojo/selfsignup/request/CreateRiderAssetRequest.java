/***
 * Date: 20/04/23
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
public class CreateRiderAssetRequest {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("assetId")
  public String assetId;

  @JsonProperty("quantity")
  public int quantity;
}
