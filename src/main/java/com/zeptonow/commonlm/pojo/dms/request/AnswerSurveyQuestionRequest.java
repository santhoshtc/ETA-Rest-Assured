package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerSurveyQuestionRequest {

  @JsonAlias("entity_id")
  private String entityId;

  @JsonAlias("entity_type")
  private String entityType;

  @JsonAlias("question_id")
  private Integer questionId;

  @JsonAlias("survey_option_id")
  private List<Integer> surveyOptionId = new ArrayList<Integer>();

  @JsonAlias("free_text")
  private String freeText;
}
