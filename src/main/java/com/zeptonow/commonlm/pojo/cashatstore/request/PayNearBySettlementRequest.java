package com.zeptonow.commonlm.pojo.cashatstore.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayNearBySettlementRequest {

  private int amount;
  private String transactionRefNo;
  private String transactionId;
}
