/***
 * Date: 07/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.riderhomepage.subclass.Datum;
import com.zeptonow.commonlm.pojo.riderhomepage.subclass.Errors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBannerResponse {

  @JsonProperty("errors")
  public List<Errors> errors;

  @JsonProperty("data")
  public List<Datum> data;
}
