/***
 * Date: 06/03/23
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
public class BannerStoreMappingRequest {

  @JsonProperty("bannerId")
  public String bannerId;

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("priority")
  public Integer priority;

  @JsonProperty("isActive")
  public Boolean isActive;
}
