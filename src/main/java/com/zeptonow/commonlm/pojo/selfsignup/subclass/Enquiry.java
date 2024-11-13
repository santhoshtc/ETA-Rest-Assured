package com.zeptonow.commonlm.pojo.selfsignup.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enquiry {

  @JsonProperty("customerCode")
  public String customerCode;

  @JsonProperty("iv")
  public String iv;

  @JsonProperty("kaptureSupportKey")
  public String kaptureSupportKey;

  @JsonProperty("url")
  public String url;

  @JsonProperty("kaptureCustomerId")
  public String kaptureCustomerId;

  @JsonProperty("contactId")
  public String contactId;
}
