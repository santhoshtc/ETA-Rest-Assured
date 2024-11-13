package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HuDetails {
  @JsonProperty("bags")
  @Builder.Default
  private Integer bags = 1;

  @JsonProperty("bin_code")
  @JsonAlias("binCode")
  @Builder.Default
  private String binCode = "GRN_1";

  @JsonProperty("color")
  private String color;

  @JsonProperty("colorCode")
  private String colorCode;

  @JsonProperty("number")
  private String number;

  private String fontColorCode;

  @JsonProperty("is_reusable_bag")
  @JsonAlias("isReusableBag")
  private Boolean isReusableBag;

  @JsonProperty("bag_eans")
  @JsonAlias("bagEans")
  private List<String> bagEans;

  @JsonProperty("bag_eans_v2")
  @JsonAlias("bagEansV2")
  public List<BagEansV2> bagEansV2;

  private String binPlacedTimeStamp;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HuDetails huDetails = (HuDetails) o;
    return Objects.equals(bags, huDetails.bags)
        && Objects.equals(binCode, huDetails.binCode)
        && Objects.equals(isReusableBag, huDetails.isReusableBag)
        && Objects.equals(bagEans, huDetails.bagEans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bags, binCode, number, isReusableBag, bagEans);
  }
}
