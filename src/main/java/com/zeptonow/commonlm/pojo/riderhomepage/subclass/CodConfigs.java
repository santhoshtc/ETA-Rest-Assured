/***
 * Date: 23/02/23
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
public class CodConfigs {

  @JsonProperty("AIRTEL_MONEY")
  public Boolean airtelMoney;

  @JsonProperty("DOORSTEP_QR")
  public Boolean doorstepQr;

  @JsonProperty("SPICE_MONEY")
  public Boolean spiceMoney;

  @JsonProperty("UPI")
  public Boolean upi;

  @JsonProperty("CASH_AT_STORE")
  public Boolean cashAtStore;
}
