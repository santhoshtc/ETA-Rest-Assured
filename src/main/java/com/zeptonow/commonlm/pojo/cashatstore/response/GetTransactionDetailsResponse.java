package com.zeptonow.commonlm.pojo.cashatstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.cashatstore.subclass.RiderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionDetailsResponse {

  @JsonProperty("transactionId")
  public String transactionId;

  @JsonProperty("paymentCode")
  public String paymentCode;

  @JsonProperty("paymentStatus")
  public String paymentStatus;

  @JsonProperty("transactionStartTime")
  public String transactionStartTime;

  @JsonProperty("transactionCompletionTime")
  public String transactionCompletionTime;

  @JsonProperty("failureReason")
  public String failureReason;

  @JsonProperty("paymentMethod")
  public String paymentMethod;

  @JsonProperty("subPaymentMethod")
  public String subPaymentMethod;

  @JsonProperty("paymentFlowType")
  public String paymentFlowType;

  @JsonProperty("sellerId")
  public String sellerId;

  @JsonProperty("amountInPaise")
  public Integer amountInPaise;

  @JsonProperty("payerType")
  public String payerType;

  @JsonProperty("riderDetails")
  public RiderDetails riderDetails;

  @JsonProperty("customerDetails")
  public Object customerDetails;

  @JsonProperty("storeDetails")
  public Object storeDetails;

  public String paymentReferenceId;
}
