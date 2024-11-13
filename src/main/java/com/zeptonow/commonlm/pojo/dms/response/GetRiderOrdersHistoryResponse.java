package com.zeptonow.commonlm.pojo.dms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRiderOrdersHistoryResponse {

  @JsonProperty("nextToken")
  private int nextToken;

  @JsonProperty("pageSize")
  private int pageSize;

  @JsonProperty("orders")
  private List<OrderHistory> orders;
}
