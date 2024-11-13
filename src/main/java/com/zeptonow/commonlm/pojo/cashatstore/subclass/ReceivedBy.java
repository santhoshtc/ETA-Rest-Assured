package com.zeptonow.commonlm.pojo.cashatstore.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedBy {

  @JsonProperty("id")
  public String id;

  @JsonProperty("emailId")
  public String emailId;
}
