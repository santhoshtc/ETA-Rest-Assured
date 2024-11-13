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
public class Text {
  @JsonProperty("subHeader")
  private String subHeader;

  @JsonProperty("profileText")
  private String profileText;

  @JsonProperty("displayText")
  private String displayText;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Text text = (Text) o;
    return Objects.equals(profileText, text.profileText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profileText);
  }
}
