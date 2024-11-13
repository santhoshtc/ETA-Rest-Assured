/***
 * Date: 03/05/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BagEansV2 {

  @JsonProperty("bag_ean")
  @JsonAlias("bagEan")
  public String bagEan;

  @Builder.Default
  @JsonProperty("type")
  public String type = "INSULATED";

  @Builder.Default
  @JsonProperty("price")
  public String price = "15000";

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BagEansV2 bagEansV2 = (BagEansV2) o;
    return Objects.equals(bagEan, bagEansV2.bagEan)
        && Objects.equals(type, bagEansV2.type)
        && Objects.equals(price, bagEansV2.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bagEan, type, price);
  }
}
