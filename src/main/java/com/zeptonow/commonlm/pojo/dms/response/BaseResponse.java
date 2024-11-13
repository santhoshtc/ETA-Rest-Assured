package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

  @JsonProperty("errors")
  private List<Error> errors;

  @JsonProperty("data")
  private T data;

  private String status;
  private int code;
}
