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
public class Image {

  @JsonProperty("imageUrl")
  private String imageUrl;

  @JsonProperty("imageRatio")
  private String imageRatio;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image image = (Image) o;
    return Objects.equals(imageUrl, image.imageUrl) && Objects.equals(imageRatio, image.imageRatio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageUrl, imageRatio);
  }
}
