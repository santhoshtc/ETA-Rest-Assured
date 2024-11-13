package com.zeptonow.commonlm.pojo.codOrderPayment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.codOrderPayment.subclass.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnsettledOrdersResponse {

  @JsonProperty("cashBalance")
  public Integer cashBalance;

  @JsonProperty("sellers")
  public List<Seller> sellers;
}
