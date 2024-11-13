package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotConfigs {

  @JsonProperty("SLOT_PRIMARY_FLAG_ENABLED")
  public Boolean slotPrimaryFlagEnabled;

  @JsonProperty("SLOT_SECONDARY_FLAG_ENABLED")
  public Boolean slotSecondaryFlagEnabled;
}
