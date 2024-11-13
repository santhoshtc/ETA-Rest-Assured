package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdditionalDetails {

  @JsonProperty("text")
  private Text text;

  @JsonProperty("image")
  private Image image;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdditionalDetails that = (AdditionalDetails) o;
    if (text == null) {
      return Objects.equals(image, that.image);
    }
    return Objects.equals(text, that.text) && Objects.equals(image, that.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, image);
  }
}
