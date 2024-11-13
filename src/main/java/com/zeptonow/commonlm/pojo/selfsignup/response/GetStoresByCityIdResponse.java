/***
 * Date: 24/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.response;

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
public class GetStoresByCityIdResponse {

  @JsonProperty("id")
  public String id;

  @JsonProperty("name")
  public String name;

  @JsonProperty("distance")
  public Integer distance;

  @JsonProperty("distanceInString")
  public String distanceInString;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetStoresByCityIdResponse that = (GetStoresByCityIdResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public String toString() {
    return "GetStoresByCityIdResponse{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
