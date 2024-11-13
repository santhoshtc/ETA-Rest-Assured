package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Buffer {

  @JsonProperty("hourIst")
  public Integer hourIst;

  @JsonProperty("buffer")
  public Integer buffer;

  @JsonProperty("updatedOn")
  public String updatedOn;

  @JsonProperty("updatedBy")
  public String updatedBy;
}
