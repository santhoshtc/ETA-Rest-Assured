package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtaEngineComponents {

  private Boolean isEtaEngineEnabled;
  private Integer arrivalEta;
  private Integer llmEta;
  private Integer varianceGoogleEta;
  private Integer fromGrid;
  private Integer toGrid;
}
