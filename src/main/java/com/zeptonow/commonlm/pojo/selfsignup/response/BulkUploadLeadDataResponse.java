/***
 * Date: 27/01/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.BulkLeadData;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BulkUploadLeadDataResponse {

  @JsonProperty("errors")
  public List<Error> errors = null;

  @JsonProperty("data")
  public BulkLeadData data;
}
