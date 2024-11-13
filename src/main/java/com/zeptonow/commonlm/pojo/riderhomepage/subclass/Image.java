/***
 * Date: 07/03/23
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
public class Image {

  @JsonProperty("id")
  public String id;

  @JsonProperty("createdAt")
  public String createdAt;

  @JsonProperty("updatedAt")
  public String updatedAt;

  @JsonProperty("version")
  public Integer version;

  @JsonProperty("name")
  public String name;

  @JsonProperty("path")
  public String path;

  @JsonProperty("mediaType")
  public String mediaType;

  @JsonProperty("sizeInBytes")
  public Integer sizeInBytes;

  @JsonProperty("width")
  public Integer width;

  @JsonProperty("height")
  public Integer height;
}
