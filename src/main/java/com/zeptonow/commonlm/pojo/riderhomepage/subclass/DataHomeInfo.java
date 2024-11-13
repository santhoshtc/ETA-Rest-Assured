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
public class DataHomeInfo {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("store")
  public Store store;

  @JsonProperty("config")
  public Config config;

  @JsonProperty("riderInfo")
  public RiderInfo riderInfo;
}
