/***
 * Date: 29/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.Error;
import com.zeptonow.commonlm.pojo.dms.subclass.ForceCheckOutData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForceCheckoutResponse {

  @JsonProperty("errors")
  public List<Error> errors;

  @JsonProperty("data")
  public ForceCheckOutData data;
}
