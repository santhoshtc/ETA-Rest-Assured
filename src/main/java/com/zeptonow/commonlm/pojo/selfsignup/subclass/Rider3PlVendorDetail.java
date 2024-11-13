/***
 * Date: 17/05/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rider3PlVendorDetail {

  @JsonProperty("id")
  public String id;

  @JsonProperty("type")
  public String type;

  @JsonProperty("key")
  public String key;

  @JsonProperty("value")
  public String value;

  @JsonAlias("is_active")
  @JsonProperty("isActive")
  public Boolean isActive;

  @JsonAlias("created_by_id")
  @JsonProperty("createdById")
  public String createdById;
}
