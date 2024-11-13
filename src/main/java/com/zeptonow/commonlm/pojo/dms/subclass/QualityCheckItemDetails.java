package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QualityCheckItemDetails {

  @Builder.Default
  @JsonProperty("imagesUrl")
  public List<String> imagesUrl = Collections.singletonList("test-image-url-here");

  @JsonProperty("itemId")
  public String itemId;

  @JsonProperty("itemStatus")
  public String itemStatus;

  @JsonProperty("qcDetails")
  public List<QualityCheckQuestion> qcDetails;

  @Builder.Default
  @JsonProperty("scannedEanCode")
  public String scannedEanCode = "1";

  @Builder.Default
  @JsonProperty("itemImageUrl")
  public String itemImageUrl = "test-image-url-here";

  @Builder.Default
  @JsonProperty("itemName")
  public String itemName = "Lemon (Nimbu)";

  @Builder.Default
  @JsonProperty("itemPackSize")
  public String itemPackSize = "2 x 75 g";

  @Builder.Default
  @JsonProperty("itemSkuCode")
  public String itemSkuCode = "553346e5-3002-4ad6-8627-3ada139ee9e1";
}
