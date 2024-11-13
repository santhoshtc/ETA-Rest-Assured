package com.zeptonow.commonlm.pojo.iris_eta.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.iris_eta.subclass.Buffer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPeakBufferResponse {
  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("buffers")
  public List<Buffer> buffers;
}
