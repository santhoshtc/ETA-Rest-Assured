/***
 * Date: 16/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Instructions {

  @Builder.Default
  @JsonProperty("label")
  public String label = "Collect Reusable Bag";

  @Builder.Default
  @JsonProperty("name")
  public String name = "Collect Reusable Bag";
}
