package com.zeptonow.commonlm.pojo.cashatstore.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HandOverCashToStore {

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;

  @JsonProperty("receiptNumber")
  public String receiptNumber;

  @JsonProperty("pickupCode")
  public String pickupCode;

  @JsonProperty("attachmentId")
  public String attachmentId;

  @JsonProperty("attachmentPath")
  public String attachmentPath;
}
