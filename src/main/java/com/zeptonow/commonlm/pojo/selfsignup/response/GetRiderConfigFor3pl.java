/***
 * Date: 17/05/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.RiderConfigData3pl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRiderConfigFor3pl {

  @JsonProperty("errors")
  public List<Object> errors;

  @JsonProperty("data")
  public RiderConfigData3pl data;
}
