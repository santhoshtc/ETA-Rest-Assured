package com.zeptonow.commonlm.pojo.codOrderPayment.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

  public String sellerId;

  @JsonProperty("settlementAmountLeft")
  public Integer settlementAmountLeft;

  @JsonProperty("minDue")
  public MinDue minDue;

  @JsonProperty("unsettledOrders")
  public List<UnsettledOrder> unsettledOrders = null;
}
