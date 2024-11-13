package com.zeptonow.commonlm.pojo.iris_eta.request;

import com.zeptonow.commonlm.pojo.iris_eta.subclass.CartProducts;
import com.zeptonow.commonlm.pojo.iris_eta.subclass.ScheduleDeliverySelectedSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryConfigRequest {
  private double destLatitude;
  private double destLongitude;
  private String storeId;
  private String userAddressId;
  private String buildingId;
  private boolean forceBatching;
  private String userId;
  private String cdebId;
  private ScheduleDeliverySelectedSlot scheduledSlot;
  private List<CartProducts> cartProducts;
}
