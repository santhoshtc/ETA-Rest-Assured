package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TrainingInfoConfigs {

  @JsonProperty("KNOW_ONBOARD_TRAINING_ID")
  public String knowOnboardTrainingId;

  @JsonProperty("KNOW_TRAINING_ENABLED")
  public Boolean knowTrainingEnabled;

  @JsonProperty("KNOW_REJOINER_TRAINING_ID")
  public String knowRejoinerTrainingId;

  @JsonProperty("KNOW_REACTIVATION_TRAINING_ID")
  public String KnowReactivationTrainingId;

  @JsonProperty("CHECK_IN_LEFT")
  public Integer checkInLeft;

  @JsonProperty("LIFECYCLE_STAGE_TRAINING_ENABLED")
  public Boolean lifecycleStageTrainingEnabled;

  @JsonProperty("TRAINING_BANNER_BTN_TEXT")
  public String trainingBannerBtnText;

  @JsonProperty("TRAINING_BANNER_ENABLED")
  public Boolean trainingBannerEnabled;

  @JsonProperty("TRAINING_BANNER_HEADING")
  public String trainingBannerHeading;

  @JsonProperty("TRAINING_BANNER_WARNING_TEXT")
  public String trainingBannerWarningText;

  @JsonProperty("TRAINING_STATE")
  public String trainingState;

  @JsonProperty("TRAINING_TYPE")
  public String trainingType;
}
