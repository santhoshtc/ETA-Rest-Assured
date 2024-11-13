package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetails {

  @Builder.Default
  @JsonProperty("image_url")
  private String imageUrl = "test-image-url-here";

  @Builder.Default
  @JsonProperty("pack_size")
  private String packSize = "S";

  @Builder.Default
  @JsonProperty("quantity")
  public String quantity = "1";

  @Builder.Default
  @JsonProperty("replacement_qty")
  public Integer replacementQty = 0;

  @Builder.Default
  @JsonProperty("return_qty")
  public Integer returnQty = 0;

  @Builder.Default private String name = "Cauliflower (Phool Gobi)";

  @JsonAlias("reasonCodes")
  @JsonProperty("reason_codes")
  public List<String> reason_codes;

  @Builder.Default
  @JsonAlias("prevEanCodes")
  @JsonProperty("prev_ean_codes")
  public List<String> prev_ean_codes = new ArrayList<>(Arrays.asList("xyz"));

  @JsonProperty("is_discreet")
  private boolean is_discreet;

  @JsonAlias("prevSkuCode")
  @JsonProperty("prev_sku_code")
  private String prevSkuCode;

  @Builder.Default
  @JsonAlias("skuCode")
  @JsonProperty("sku_code")
  private String sku_code = "test-sku-0011";

  @JsonProperty("itemStatus")
  public String itemStatus;

  @JsonProperty("rejectedReason")
  public String rejectedReason;

  @JsonProperty("itemId")
  public String itemId;

  @JsonProperty("itemType")
  public String itemType;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemDetails that = (ItemDetails) o;
    return Objects.equals(imageUrl, that.imageUrl)
        && Objects.equals(packSize, that.packSize)
        && Objects.equals(quantity, that.quantity)
        && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageUrl, packSize, quantity, name);
  }
}
