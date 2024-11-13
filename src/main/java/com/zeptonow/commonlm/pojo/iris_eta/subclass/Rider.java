package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rider {
  private String id;
  private String vehicle;
  private String storeId;
  private Object currentLegStatus;
  private Integer age;
  private String createdOn;
}
