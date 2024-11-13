/***
 * Date: 06/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerStoreData {

  @JsonProperty("id")
  public String id;

  @JsonProperty("createdAt")
  public String createdAt;

  @JsonProperty("updatedAt")
  public String updatedAt;

  @JsonProperty("createdBy")
  public String createdBy;

  @JsonProperty("version")
  public Integer version;

  @JsonProperty("bannerId")
  public String bannerId;

  @JsonProperty("priority")
  public Integer priority;

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("isActive")
  public Boolean isActive;
}
