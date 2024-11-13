package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.cashatstore.subclass.ActiveDepositRequest;
import com.zeptonow.commonlm.pojo.cashatstore.subclass.AmountBreakUp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreLedgerBalanceResponse {

  @JsonProperty("totalAmountInPaise")
  public Integer totalAmountInPaise;

  @JsonProperty("sellerNeedToBeSettledFirst")
  public String sellerNeedToBeSettledFirst;

  @JsonProperty("lastHandoverTime")
  public Object lastHandoverTime;

  @JsonProperty("amountBreakUp")
  public List<AmountBreakUp> amountBreakUp;

  @JsonProperty("activeDepositRequests")
  private List<ActiveDepositRequest> activeDepositRequests;
}
