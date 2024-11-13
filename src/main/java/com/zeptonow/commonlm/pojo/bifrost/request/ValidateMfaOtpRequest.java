package com.zeptonow.commonlm.pojo.bifrost.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateMfaOtpRequest {

  @Builder.Default
  @JsonProperty("otp")
  public String otp = "9271";

  @JsonProperty("mfaId")
  public String mfaId;

  @Builder.Default
  @JsonProperty("applicationId")
  public String applicationId = "00a327e7-d7da-4f18-9b7f-cc1fb5d5aa99";
}
