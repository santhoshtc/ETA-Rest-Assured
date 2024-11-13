/***
 * Date: 30/11/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.pojo.dms.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeptonow.commonlm.pojo.dms.subclass.CustomerDetails;
import com.zeptonow.commonlm.pojo.dms.subclass.Feedback;
import com.zeptonow.commonlm.pojo.dms.subclass.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentRequest {

  @Builder.Default
  @JsonProperty("order_id")
  public String orderId = "005d034d-84eb-460c-0dfc-69b95b9f9878";

  @Builder.Default
  @JsonProperty("distance_in_meters")
  public Integer distanceInMeters = 1000;

  @Builder.Default
  @JsonProperty("customer_details")
  public CustomerDetails customerDetails = new CustomerDetails();

  @Builder.Default
  @JsonProperty("load_time")
  public String loadTime = "2022-10-14T10:00:46.245519Z";

  @Builder.Default
  @JsonProperty("payment_mode")
  public String paymentMode = "COD";

  @Builder.Default
  @JsonProperty("payment_amount")
  public Integer paymentAmount = 44000;

  @Builder.Default
  @JsonProperty("store_id")
  public String storeId = "d48222ae-a642-4aeb-9cee-e2829ebaa5e7";

  @Builder.Default
  @JsonProperty("instructions")
  public String instructions = "";

  @Builder.Default
  @JsonProperty("order_details")
  public OrderDetails orderDetails = new OrderDetails();

  @Builder.Default
  @JsonProperty("rating")
  public Integer rating = 0;

  @Builder.Default
  @JsonProperty("feedback")
  public Feedback feedback = new Feedback();
}
