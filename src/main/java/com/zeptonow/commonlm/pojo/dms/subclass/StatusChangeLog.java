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
public class StatusChangeLog {

  @JsonProperty("status")
  private String status;

  @JsonProperty("timestamp")
  @JsonAlias("time_stamp")
  private String timestamp;

  @JsonProperty("reason_name")
  private String reasonName;

  @JsonProperty("reasonId")
  @JsonAlias("reason_id")
  private String reasonId;

  @JsonProperty("location")
  private Object location;

  @JsonProperty("data")
  private Object data;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatusChangeLog that = (StatusChangeLog) o;
    return Objects.equals(status, that.status)
        && Objects.equals(reasonId, that.reasonId)
        && Objects.equals(location, that.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, reasonName, reasonId, location);
  }
}
