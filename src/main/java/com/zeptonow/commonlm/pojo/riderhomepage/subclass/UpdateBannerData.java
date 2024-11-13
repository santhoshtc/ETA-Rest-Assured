/***
 * Date: 02/03/23
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.riderhomepage.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBannerData {

  @JsonProperty("id")
  public String id;

  @JsonProperty("createdAt")
  public String createdAt;

  @JsonProperty("updatedAt")
  public String updatedAt;

  @JsonProperty("createdBy")
  public String createdBy;

  @JsonProperty("updatedBy")
  public String updatedBy;

  @JsonProperty("version")
  public Integer version;

  @JsonProperty("name")
  public String name;

  @JsonProperty("imageId")
  public String imageId;

  @JsonProperty("isActive")
  public Boolean isActive;

  @JsonProperty("priority")
  public Integer priority;

  @JsonProperty("deeplink")
  public String deeplink;

  @JsonProperty("deeplinkType")
  public String deeplinkType;

  @JsonProperty("secondaryImageId")
  public String secondaryImageId;

  @JsonProperty("clickable")
  public Boolean clickable;

  @JsonProperty("type")
  public String type;

  @JsonProperty("subtype")
  public String subtype;

  @JsonProperty("deeplinkUrl")
  public String deeplinkUrl;

  @JsonProperty("tag")
  public String tag;
}
