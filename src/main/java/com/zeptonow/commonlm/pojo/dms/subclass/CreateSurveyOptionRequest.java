package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateSurveyOptionRequest {

  @JsonAlias("is_root_option")
  private boolean isRootOption;

  @JsonAlias("option_value")
  private String optionValue;

  private Integer priority;

  @JsonAlias("option_type")
  private OptionType optionType;

  @JsonAlias("additional_details")
  private AdditionalDetails additionalDetails;

  @JsonAlias("child_options")
  private List<CreateSurveyOptionRequest> childOptions;

  private Integer id;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateSurveyOptionRequest that = (CreateSurveyOptionRequest) o;
    if (that.childOptions == null || that.childOptions.size() == 0) {
      return Objects.equals(optionValue, that.optionValue)
          && optionType == that.optionType
          && Objects.equals(additionalDetails, that.additionalDetails);
    }
    return Objects.equals(optionValue, that.optionValue)
        && optionType == that.optionType
        && Objects.equals(additionalDetails, that.additionalDetails)
        && Objects.equals(childOptions, that.childOptions);
  }
}
