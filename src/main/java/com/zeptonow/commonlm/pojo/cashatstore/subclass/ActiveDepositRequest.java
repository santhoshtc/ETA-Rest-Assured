package com.zeptonow.commonlm.pojo.cashatstore.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveDepositRequest {

  private String requestId;
  private Integer amountInPaise;
  private String sellerId;
  private String sellerName;
  private String transactionStartTime;
  private ReceivedBy raisedBy;
}
