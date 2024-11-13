package com.zeptonow.commonlm.pojo.cashatstore.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreSettlementTransaction {

  private String requestId;
  private Integer amountInPaise;
  private String paymentStatus;
  private String transactionId;
}
