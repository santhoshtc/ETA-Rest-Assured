/***
 * Date: 23/02/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
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
public class Config {

  @JsonProperty("codConfigs")
  public CodConfigs codConfigs;

  @JsonProperty("e2eConfigs")
  public E2eConfigs e2eConfigs;

  @JsonProperty("homeConfigs")
  public HomeConfigs homeConfigs;

  @JsonProperty("earningConfigs")
  public EarningConfigs earningConfigs;

  @JsonProperty("rainModeConfigs")
  public RainModeConfigs rainModeConfigs;

  @JsonProperty("referralConfigs")
  public ReferralConfigs referralConfigs;

  @JsonProperty("trainingInfoConfigs")
  public TrainingInfoConfigs trainingInfoConfigs;

  @JsonProperty("loyaltyConfigs")
  public LoyaltyConfigs loyaltyConfigs;

  @JsonProperty("handholdingConfigs")
  public HandholdingConfigs handholdingConfigs;

  @JsonProperty("slotConfigs")
  public SlotConfigs slotConfigs;
}
