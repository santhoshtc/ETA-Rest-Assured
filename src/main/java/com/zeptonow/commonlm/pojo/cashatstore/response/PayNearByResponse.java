package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.zeptonow.commonlm.pojo.cashatstore.subclass.StoreSettlementTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayNearByResponse {

  private String status;
  private List<StoreSettlementTransaction> storeSettlementTransactions;
}
