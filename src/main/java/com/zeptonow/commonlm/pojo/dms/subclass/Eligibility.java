package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Eligibility {

  @JsonProperty("batchingEligibility")
  private Boolean batchingEligibility;

  @JsonProperty("codEligibility")
  private Boolean codEligibility;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Eligibility that = (Eligibility) o;
    return Objects.equals(batchingEligibility, that.batchingEligibility);
  }
}
