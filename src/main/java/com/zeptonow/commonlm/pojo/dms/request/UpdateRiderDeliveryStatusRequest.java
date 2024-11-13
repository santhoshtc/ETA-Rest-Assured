/***
 * Date: 19/12/22
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
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRiderDeliveryStatusRequest {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("status")
  public String status;
}
