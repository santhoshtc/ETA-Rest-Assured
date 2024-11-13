/***
 * Date: 12/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.constants;

import com.zepto.api.restassured.EndPoint;

import java.util.Map;

public enum Services {
  CONFIG,
  CART,
  CUSTOMER,
  CATALOGUE,
  INVENTORY_STORE,
  INVENTORY,
  ORDER,
  LOGIN,
  PAYMENT,
  GOOGLE,
  COUPON,
  ORDERL,
  WALLET,
  ZEPTOWALLET,
  REASON,
  PRODUCT,
  CATEGORY,
  DISCOUNT,
  LAYOUTS,
  BANNER,
  BIFROST,
  SHIFTADHERENCEV1,
  RIDER,
  ZEPTOUTILITY,
  SHIFTADHERENCEV2,
  DELIVERY,
  ORDERREFUNDS,
  CODBLOCK,
  SELFSIGNUP,
  DELIVERYSERVICE,
  RMS,
  RIDERHOMEPAGE,
  PROFILE,
  SESSIONMANAGEMENT,
  COD,
  CONFIGMANAGEMENT,
  CASHATSTORE,
  SHADOWFAX,
  INSURANCE,
  ADMINPANEL,
  LSQ,
  KAFKA,
  DMS_SURVEY,
  SSU,
  REFERRAL,
  IRIS_ETA,
  PACKER,
  SLOTS,
  LOADSHARE,
  ZEPTOMAP,
  JOININGBONUS;

  public static Map<String, String> getServiceEndPoint(Services service) {
    switch (service) {
      case CONFIG:
        return EndPoint.endpoints.get(CONFIG.toString().toLowerCase());
      case CART:
        return EndPoint.endpoints.get(CART.toString().toLowerCase());
      case CUSTOMER:
        return EndPoint.endpoints.get(CUSTOMER.toString().toLowerCase());
      case CATALOGUE:
        return EndPoint.endpoints.get(CATALOGUE.toString().toLowerCase());
      case INVENTORY_STORE:
        return EndPoint.endpoints.get(INVENTORY_STORE.toString().toLowerCase());
      case INVENTORY:
        return EndPoint.endpoints.get(INVENTORY.toString().toLowerCase());
      case ORDER:
        return EndPoint.endpoints.get(ORDER.toString().toLowerCase());
      case LOGIN:
        return EndPoint.endpoints.get(LOGIN.toString().toLowerCase());
      case PAYMENT:
        return EndPoint.endpoints.get(PAYMENT.toString().toLowerCase());
      case GOOGLE:
        return EndPoint.endpoints.get(GOOGLE.toString().toLowerCase());
      case COUPON:
        return EndPoint.endpoints.get(COUPON.toString().toLowerCase());
      case ORDERL:
        return EndPoint.endpoints.get(ORDERL.toString().toLowerCase());
      case WALLET:
        return EndPoint.endpoints.get(WALLET.toString().toLowerCase());
      case ZEPTOWALLET:
        return EndPoint.endpoints.get(ZEPTOWALLET.toString().toLowerCase());
      case REASON:
        return EndPoint.endpoints.get(REASON.toString().toLowerCase());
      case PRODUCT:
        return EndPoint.endpoints.get(PRODUCT.toString().toLowerCase());
      case CATEGORY:
        return EndPoint.endpoints.get(CATEGORY.toString().toLowerCase());
      case DISCOUNT:
        return EndPoint.endpoints.get(DISCOUNT.toString().toLowerCase());
      case LAYOUTS:
        return EndPoint.endpoints.get(LAYOUTS.toString().toLowerCase());
      case BANNER:
        return EndPoint.endpoints.get(BANNER.toString().toLowerCase());
      case BIFROST:
        return EndPoint.endpoints.get(BIFROST.toString().toLowerCase());
      case SHIFTADHERENCEV1:
        return EndPoint.endpoints.get(SHIFTADHERENCEV1.toString().toLowerCase());
      case RIDER:
        return EndPoint.endpoints.get(RIDER.toString().toLowerCase());
      case ZEPTOUTILITY:
        return EndPoint.endpoints.get(ZEPTOUTILITY.toString().toLowerCase());
      case SHIFTADHERENCEV2:
        return EndPoint.endpoints.get(SHIFTADHERENCEV2.toString().toLowerCase());
      case DELIVERY:
        return EndPoint.endpoints.get(DELIVERY.toString().toLowerCase());
      case ORDERREFUNDS:
        return EndPoint.endpoints.get(ORDERREFUNDS.toString().toLowerCase());
      case CODBLOCK:
        return EndPoint.endpoints.get(CODBLOCK.toString().toLowerCase());
      case SELFSIGNUP:
        return EndPoint.endpoints.get(SELFSIGNUP.toString().toLowerCase());
      case DMS_SURVEY:
      case DELIVERYSERVICE:
        return EndPoint.endpoints.get(DELIVERYSERVICE.toString().toLowerCase());
      case RIDERHOMEPAGE:
        return EndPoint.endpoints.get(RIDERHOMEPAGE.toString().toLowerCase());
      case PROFILE:
        return EndPoint.endpoints.get(PROFILE.toString().toLowerCase());
      case SESSIONMANAGEMENT:
        return EndPoint.endpoints.get(SESSIONMANAGEMENT.toString().toLowerCase());
      case COD:
        return EndPoint.endpoints.get(COD.toString().toLowerCase());
      case CONFIGMANAGEMENT:
        return EndPoint.endpoints.get(CONFIGMANAGEMENT.toString().toLowerCase());
      case CASHATSTORE:
        return EndPoint.endpoints.get(CASHATSTORE.toString().toLowerCase());
      case INSURANCE:
        return EndPoint.endpoints.get(INSURANCE.toString().toLowerCase());
      case SHADOWFAX:
        return EndPoint.endpoints.get(SHADOWFAX.toString().toLowerCase());
      case ADMINPANEL:
        return EndPoint.endpoints.get(ADMINPANEL.toString().toLowerCase());
      case LSQ:
        return EndPoint.endpoints.get(LSQ.toString().toLowerCase());
      case KAFKA:
        return EndPoint.endpoints.get(KAFKA.toString().toLowerCase());
      case SSU:
        return EndPoint.endpoints.get(SSU.toString().toLowerCase());
      case REFERRAL:
        return EndPoint.endpoints.get(REFERRAL.toString().toLowerCase());
      case PACKER:
        return EndPoint.endpoints.get(PACKER.toString().toLowerCase());
      case IRIS_ETA:
        return EndPoint.endpoints.get(IRIS_ETA.toString().toLowerCase());
      case SLOTS:
        return EndPoint.endpoints.get(SLOTS.toString().toLowerCase());
      case LOADSHARE:
        return EndPoint.endpoints.get(LOADSHARE.toString().toLowerCase());
      case ZEPTOMAP:
        return EndPoint.endpoints.get(ZEPTOMAP.toString().toLowerCase());
      case JOININGBONUS:
        return EndPoint.endpoints.get(JOININGBONUS.toString().toLowerCase());
    }
    return null;
  }

  public class Name {

    public static final String CONFIG = "CONFIG";
    public static final String CART = "CART";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String CATALOGUE = "CATALOGUE";
    public static final String INVENTORY_STORE = "INVENTORY_STORE";
    public static final String INVENTORY = "INVENTORY";
    public static final String ORDER = "ORDER";
    public static final String LOGIN = "LOGIN";
    public static final String PAYMENT = "PAYMENT";
    public static final String GOOGLE = "GOOGLE";
    public static final String COUPON = "COUPON";
    public static final String ORDERL = "ORDERL";
    public static final String WALLET = "WALLET";
    public static final String ZEPTOWALLET = "ZEPTOWALLET";
    public static final String REASON = "REASON";
    public static final String PRODUCT = "PRODUCT";
    public static final String CATEGORY = "CATEGORY";
    public static final String DISCOUNT = "DISCOUNT";
    public static final String LAYOUTS = "LAYOUTS";
    public static final String BANNER = "BANNER";
    public static final String BIFROST = "BIFROST";
    public static final String PACKER = "PACKER";
    public static final String DELIVERY = "DELIVERY";
    public static final String WIDGET = "WIDGET";
    public static final String WMSAPI = "WMSAPI";
    public static final String CUSTOMERFRAUD = "CUSTOMERFRAUD";
    public static final String SHIFTADHERENCEV1 = "SHIFTADHERENCEV1";
    public static final String SHIFTADHERENCEV2 = "SHIFTADHERENCEV2";
    public static final String ORDERREFUND = "ORDERREFUND";
    public static final String ORDERE2E = "ORDERE2E";
    public static final String CODBLOCK = "codblock";
    public static final String EMBARGO = "embargo";
    public static final String SELFSIGNUP = "selfsignup";
    public static final String DELIVERYSERVICE = "deliveryservice";
    public static final String RIDERHOMEPAGE = "riderhomepage";
    public static final String INSURANCE = "insurance";
    public static final String LSQ = "lsq";
    public static final String ADMINPANEL = "adminpanel";
    public static final String PROFILE = "profile";
    public static final String SESSIONMANAGEMENT = "sessionmanagement";
    public static final String SHIFTHISTORY = "SHIFTHISTORY";
    public static final String COD = "cod";
    public static final String CONFIGMANAGEMENT = "configmanagement";
    public static final String CASHATSTORE = "cashAtStore";
    public static final String REUSABLEBAGS = "reusablebags";
    public static final String SHADOWFAX = "shadowfax";
    public static final String EARNINGS = "earnings";
    public static final String INSULATEDBAGS = "insulatedbags";
    public static final String EXPRESSFLEET = "expressfleet";
    public static final String SSU = "ssu";
    public static final String REFERRAL = "referral";
    public static final String IRIS_ETA = "ETA";
    public static final String SLOTS = "SLOTS";
    public static final String LOADSHARE = "loadshare";
    public static final String ZEPTOMAP = "ZEPTOMAP";
    public static final String JOININGBONUS = "JOININGBONUS";

    public Name() {}
  }
}
