package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.selfsignup.subclass.Enquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KaptureAPIResponse {

  @JsonProperty("enquiry")
  public Enquiry enquiry;

  @JsonProperty("message")
  public String message;

  @JsonProperty("status")
  public String status;
}
