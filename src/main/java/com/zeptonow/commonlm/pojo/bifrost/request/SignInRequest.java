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
public class SignInRequest {

  @JsonProperty("email")
  public String email;

  @JsonProperty("password")
  public String password;
}
