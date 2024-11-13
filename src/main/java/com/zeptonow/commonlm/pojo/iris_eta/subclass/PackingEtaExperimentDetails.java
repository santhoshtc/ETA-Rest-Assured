package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackingEtaExperimentDetails {
  @JsonProperty("total_products")
  private Integer totalProducts;

  @JsonProperty("unique_products")
  private Integer uniqueProducts;

  @JsonProperty("original_packing_time")
  private Integer originalPackingTime;

  @JsonProperty("experiment_packing_time")
  private Integer experimentPackingTime;

  @JsonProperty("is_packing_eta_experiment_live")
  private Boolean isPackingEtaExperimentLive;

  @JsonProperty("store_id")
  private String storeId;

  @JsonProperty("total_product_multiplier")
  private Integer totalProductMultiplier;

  @JsonProperty("unique_product_multiplier")
  private Integer uniqueProductMultiplier;

  @JsonProperty("subtracting_constant")
  private Integer subtractingConstant;
}
