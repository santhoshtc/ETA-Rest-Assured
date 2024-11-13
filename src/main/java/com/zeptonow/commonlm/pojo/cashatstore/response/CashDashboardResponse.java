package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.cashatstore.subclass.CashBalanceDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashDashboardResponse {

  @JsonProperty("cashBalanceDetails")
  public List<CashBalanceDetail> cashBalanceDetails;

  @JsonProperty("endOfList")
  public Boolean endOfList;
}
