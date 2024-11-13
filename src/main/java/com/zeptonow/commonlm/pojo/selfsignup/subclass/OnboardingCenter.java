/***
 * Date: 20/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingCenter {

  @JsonProperty("id")
  public String id;

  @JsonProperty("type")
  public String type;

  @JsonProperty("isActive")
  public Boolean isActive;

  @JsonProperty("subType")
  public Object subType;

  @JsonProperty("key")
  public String key;

  @JsonProperty("value")
  public String value;
}
