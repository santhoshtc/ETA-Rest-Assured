/***
 * Date: 14/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@JsonIgnoreProperties
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOnBoardingCentreResponse {

  @JsonProperty("id")
  public String id;

  @JsonProperty("type")
  public String type;

  @JsonProperty("isActive")
  @JsonAlias("is_active")
  public Boolean isActive;

  @JsonProperty("subType")
  @JsonAlias("sub_type")
  public String subType;

  @JsonProperty("key")
  public String key;

  @JsonProperty("value")
  public String value;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateOnBoardingCentreResponse that = (CreateOnBoardingCentreResponse) o;
    return Objects.equals(id, that.id)
        && Objects.equals(type, that.type)
        && Objects.equals(isActive, that.isActive)
        && Objects.equals(subType, that.subType)
        && Objects.equals(key, that.key)
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, isActive, subType, key, value);
  }
}
