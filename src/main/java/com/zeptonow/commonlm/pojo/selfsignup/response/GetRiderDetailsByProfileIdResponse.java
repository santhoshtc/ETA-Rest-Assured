package com.zeptonow.commonlm.pojo.selfsignup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRiderDetailsByProfileIdResponse {

  @JsonProperty("riderId")
  public String riderId;

  @JsonProperty("vendorRiderId")
  public String vendorRiderId;

  @JsonProperty("riderName")
  public String riderName;

  @JsonProperty("createdAt")
  public String createdAt;

  @JsonProperty("aadharCardNumber")
  public String aadharCardNumber;

  @JsonProperty("panCardNumber")
  public String panCardNumber;

  @JsonProperty("mobileNumber")
  public String mobileNumber;

  @JsonProperty("vehicleType")
  public String vehicleType;

  @JsonProperty("riderCohort")
  public String riderCohort;

  @JsonProperty("cityName")
  public String cityName;

  @JsonProperty("shiftName")
  public String shiftName;

  @JsonProperty("storeName")
  public String storeName;

  @JsonProperty("storeId")
  public String storeId;

  @JsonProperty("vendor")
  public String vendor;

  @JsonProperty("vehicleNumber")
  public String vehicleNumber;

  @JsonProperty("aadharAttachmentPath")
  public String aadharAttachmentPath;

  @JsonProperty("panAttachmentPath")
  public String panAttachmentPath;

  @JsonProperty("picAttachmentPath")
  public String picAttachmentPath;

  @JsonProperty("bankName")
  public String bankName;

  @JsonProperty("bankAccountNumber")
  public String bankAccountNumber;

  @JsonProperty("beneficiaryName")
  public String beneficiaryName;

  @JsonProperty("bankIfscCode")
  public String bankIfscCode;

  @JsonProperty("bankDetailsAttachmentPath")
  public String bankDetailsAttachmentPath;
}
