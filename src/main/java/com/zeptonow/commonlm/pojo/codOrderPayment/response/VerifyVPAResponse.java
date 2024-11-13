package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyVPAResponse {

  @JsonProperty("status")
  public String status;

  @JsonProperty("failureReason")
  public String failureReason;

  @JsonProperty("valid")
  public Boolean valid;
}
