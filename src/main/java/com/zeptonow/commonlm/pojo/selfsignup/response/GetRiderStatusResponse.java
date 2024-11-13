/***
 * Date: 06/12/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRiderStatusResponse {

  @JsonProperty("errors")
  public List<Object> errors = null;

  @JsonProperty("data")
  public Data data;
}
