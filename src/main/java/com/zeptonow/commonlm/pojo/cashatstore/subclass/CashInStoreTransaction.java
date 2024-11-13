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
public class CashInStoreTransaction {

  @JsonProperty("riderDetails")
  public RiderDetails riderDetails;

  @JsonProperty("requestId")
  public String requestId;

  @JsonProperty("transactionTime")
  public String transactionTime;

  @JsonProperty("collectedAmount")
  public Integer collectedAmount;

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("pendingDepositAmount")
  public Integer pendingDepositAmount;

  @JsonProperty("receivedBy")
  public ReceivedBy receivedBy;

  @JsonProperty("lastUpdatedAt")
  public String lastUpdatedAt;

  @JsonProperty("transactionStartTime")
  public String transactionStartTime;
}
