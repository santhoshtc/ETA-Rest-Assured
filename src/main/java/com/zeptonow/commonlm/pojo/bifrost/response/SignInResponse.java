package com.zeptonow.commonlm.pojo.bifrost.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInResponse {

  @JsonProperty("email")
  public String email;

  @JsonProperty("jwtToken")
  public String jwtToken;

  @JsonProperty("tokenType")
  public String tokenType;

  @JsonProperty("redirectUrl")
  public String redirectUrl;

  @JsonProperty("passwordCheck")
  public Boolean passwordCheck;

  @JsonProperty("contactVerified")
  public String contactVerified;

  @JsonProperty("accessForApplication")
  public Boolean accessForApplication;

  @JsonProperty("userId")
  public String userId;

  @JsonProperty("fullName")
  public String fullName;

  @JsonProperty("contact")
  public String contact;

  @JsonProperty("tags")
  public String tags;

  @JsonProperty("mfaEnabled")
  public Boolean mfaEnabled;

  @JsonProperty("mfaId")
  public String mfaId;
}
