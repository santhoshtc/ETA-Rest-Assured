package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmsBulkCashHandoverResponse {
  @JsonProperty("status")
  public String status;

  @JsonProperty("failedCount")
  public Integer failedCount;

  @JsonProperty("failedAttachmentUrl")
  public String failedAttachmentUrl;

  @JsonProperty("failureReason")
  public String failureReason;
}
