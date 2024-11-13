/***
 * Date: 23/02/23
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
public class Store {

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("name")
  public String name;

  @JsonProperty("openTime")
  public String openTime;

  @JsonProperty("closeTime")
  public String closeTime;

  @JsonProperty("latitude")
  public Double latitude;

  @JsonProperty("longitude")
  public Double longitude;

  @JsonProperty("isStoreOnline")
  public Boolean isStoreOnline;

  @JsonProperty("address")
  public String address;
}
