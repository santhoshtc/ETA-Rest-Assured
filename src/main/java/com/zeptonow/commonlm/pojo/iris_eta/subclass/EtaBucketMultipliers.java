package com.zeptonow.commonlm.pojo.iris_eta.subclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtaBucketMultipliers {
  private List<Bucket> bike;
  private List<Bucket> cycle;
  private List<Bucket> e_cycle;
  private List<Bucket> e_scooter;
  private List<Bucket> walker;
}
