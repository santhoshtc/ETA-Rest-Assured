/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackerDetails {

  @Builder.Default
  @JsonProperty("full_name")
  @JsonAlias("fullName")
  public String fullName = "lujNjR2";

  @Builder.Default
  @JsonProperty("mobile_number")
  @JsonAlias("mobileNumber")
  public String mobileNumber = "4029339654";

  @Builder.Default
  @JsonProperty("packing_status")
  @JsonAlias("packingStatus")
  public String packingStatus = "COMPLETED";

  @Builder.Default
  @JsonProperty("is_packed")
  public boolean isPacked = true;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackerDetails that = (PackerDetails) o;
    return Objects.equals(packingStatus, that.packingStatus);
  }
}
