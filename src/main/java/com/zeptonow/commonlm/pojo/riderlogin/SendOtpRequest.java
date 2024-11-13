/***
 * Date: 09/09/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderlogin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendOtpRequest {

  @JsonProperty("mobile_number")
  public String mobileNumber;
}
