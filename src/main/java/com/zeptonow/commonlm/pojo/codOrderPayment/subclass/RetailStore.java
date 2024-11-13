package com.zeptonow.commonlm.pojo.codOrderPayment.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetailStore {

  @JsonProperty("storeType")
  public String storeType;

  @JsonProperty("storeTransactionReference")
  public String storeTransactionReference;

  @JsonProperty("instructions")
  public List<Instruction> instructions;

  @JsonProperty("image")
  public String image;
}
