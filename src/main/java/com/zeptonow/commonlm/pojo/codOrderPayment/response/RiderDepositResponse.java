package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.Deposit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDepositResponse {

  @JsonProperty("deposits")
  public List<Deposit> deposits = null;

  @JsonProperty("endOfList")
  public Boolean endOfList;
}
