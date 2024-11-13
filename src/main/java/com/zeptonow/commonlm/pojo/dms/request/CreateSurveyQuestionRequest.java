package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.zeptonow.commonlm.pojo.dms.subclass.AdditionalDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.CreateSurveyOptionRequest;
import com.zeptonow.commonlm.pojo.dms.subclass.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateSurveyQuestionRequest {
  @JsonAlias("question_text")
  private String questionText;

  @JsonAlias("question_type")
  private QuestionType questionType;

  @JsonAlias("question_category")
  private String questionCategory;

  private Object priority;

  @JsonAlias("survey_options")
  private List<CreateSurveyOptionRequest> surveyOptions;

  @JsonAlias("additional_details")
  private AdditionalDetails additionalDetails;
}
