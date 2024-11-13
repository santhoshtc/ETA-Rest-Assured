package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadReceiptResponse {

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
