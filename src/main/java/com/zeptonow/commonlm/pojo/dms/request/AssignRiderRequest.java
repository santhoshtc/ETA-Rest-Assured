package com.zeptonow.commonlm.pojo.dms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignRiderRequest {
  private String riderId;
  private List<String> orderCodes;
  private String storeId;
}
