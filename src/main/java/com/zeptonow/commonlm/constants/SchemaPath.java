/***
 * Date: 12/08/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.constants;

/**
 * @author Abhishek_Singh @Date 18/08/22
 */
public class SchemaPath {

  /** shift adherence schema Path */
  public static final String postShiftWorkPlan =
      "src/test/resources/api/sanity/schema/shift-adherence/post_shiftWorkPlan.json";

  public static final String postRiderContractShiftPlan =
      "src/test/resources/api/sanity/schema/shift-adherence/post_riderContractShiftPlan.json";
  public static final String postWeekOffPlan =
      "src/test/resources/api/sanity/schema/shift-adherence/post_weekOffPlan.json";
  public static final String postCustomerSignUp =
      "src/test/resources/api/sanity/schema/customer/post_customerSignUp.json";
  public static final String getDashboardValidation =
      "src/test/resources/api/sanity/schema/shift-adherence/get_dashboardValidation.json";
  public static final String postRiderDetailsValidation =
      "src/test/resources/api/sanity/schema/shift-adherence/post_riderDetailsValidation.json";
  public static final String getShiftHistory =
      "src/test/resources/api/sanity/schema/shift-adherence/get_shiftHistory.json";
  public static final String getWeekOffHistory =
      "src/test/resources/api/sanity/schema/shift-adherence/get_weekOffHistory.json";
  public static final String getShiftUpdateEligibility =
      "src/test/resources/api/sanity/schema/shift-adherence/get_shiftUpdateEligible.json";
  public static final String getAvailableShiftsForRider =
      "src/test/resources/api/sanity/schema/shift-adherence/get_availableRiderShifts.json";
  public static final String getRiderWorkPlan =
      "src/test/resources/api/sanity/schema/shift-adherence/get_riderWorkPlan.json";
  public static final String getAvailableRiderWeekOffs =
      "src/test/resources/api/sanity/schema/shift-adherence/get_availableWeekOffs.json";
  public static final String postInitiateRefund =
      "src/test/resources/api/sanity/schema/order-refund/post_initiateRefund.json";
  public static final String putRiderWorkPlan =
      "src/test/resources/api/sanity/schema/shift-adherence/put_riderWorkPlan.json";
  public static final String postGenerateFixedCouponForOrderRefund =
      "src/test/resources/api/sanity/schema/order-refund/post_generateFixedValueCoupon.json";
  public static final String getCurrentScheduleStoreMeta =
      "src/test/resources/api/sanity/schema/codblock/get_currentScheduleStoreMeta.json";
  public static final String postEmbargoStore =
      "src/test/resources/api/sanity/schema/embargo/post_embargo.json";
  public static final String postGeneratePercentCouponForOrderRefund =
      "src/test/resources/api/sanity/schema/order-refund/post_generatePercentValueCoupon.json";
  public static final String getOrderDetailsById =
      "src/test/resources/api/sanity/schema/order/get_orderDetailsById.json";
  public static final String getUserConfig =
      "src/test/resources/api/sanity/schema/config/get_userConfig.json";
  public static final String postStandStillMode =
      "src/test/resources/api/sanity/schema/standstill/post_standStillMode.json";
  public static final String selfSignupGetCities =
      "src/test/resources/api/sanity/schema/selfsignup/get_getCities.json";
  public static final String selfSignupGetStoresByCityId =
      "src/test/resources/api/sanity/schema/selfsignup/get_getStoresByCityId.json";
  public static final String createConsignment =
      "src/test/resources/api/sanity/schema/dms/post_createConsignment.json";
  public static final String updateConsignment =
      "src/test/resources/api/sanity/schema/dms/updateConsignment.json";
  public static final String riderCheckInCheckOut =
      "src/test/resources/api/sanity/schema/riderhomepage/put_RiderCheckInCheckOut.json";
  public static final String getRiderStatus =
      "src/test/resources/api/sanity/schema/selfsignup/get_getRiderStatus.json";
  public static final String updateRiderLead =
      "src/test/resources/api/sanity/schema/selfsignup/put_updateRiderLead.json";
  public static final String getProfile =
      "src/test/resources/api/sanity/schema/profile/getProfile.json";
  public static final String postUpdateRiderStatus =
      "src/test/resources/api/sanity/schema/selfsignup/post_updateRiderStatus.json";
  public static final String getDocument =
      "src/test/resources/api/sanity/schema/profile/getDocument.json";
  public static final String postRiderDeliveryStatus =
      "src/test/resources/api/sanity/schema/dms/post_riderDeliveryStatus.json";
  public static final String getRiderCheckInCheckOutStatus =
      "src/test/resources/api/sanity/schema/riderhomepage/get_RiderCheckInCheckOutStatus.json";
  public static final String postGenerateSessionToken =
      "src/test/resources/api/sanity/schema/sessionmanagement/post_generateToken.json";
  public static final String getShiftDetailError =
      "src/test/resources/api/sanity/schema/shift-adherence/get_ShiftDetail_error.json";
  public static final String getShiftStatusError =
      "src/test/resources/api/sanity/schema/shift-adherence/get_ShiftStatus_error.json";
  public static final String getShiftStatus =
      "src/test/resources/api/sanity/schema/shift-adherence/get_ShiftStatus.json";
  public static final String postRefreshToken =
      "src/test/resources/api/sanity/schema/sessionmanagement/post_refreshToken.json";
  public static final String getLogoutAll =
      "src/test/resources/api/sanity/schema/sessionmanagement/get_logoutAll.json";
  public static final String getValidateToken =
      "src/test/resources/api/sanity/schema/sessionmanagement/get_validateToken.json";
  public static final String getShiftDetails =
      "src/test/resources/api/sanity/schema/riderhomepage/get_ShiftDetails.json";
  public static final String getConsignmentByConsignmentId =
      "src/test/resources/api/sanity/schema/dms/getConsignment.json";
  public static final String getConsignmentHistoryByConsignmentId =
      "src/test/resources/api/sanity/schema/dms/getConsignmentHistory.json";
  public static final String getConsignmentActivityLogByConsignmentId =
      "src/test/resources/api/sanity/schema/dms/getConsignmentActivity.json";
  public static final String updateTripStatus =
      "src/test/resources/api/sanity/schema/dms/updateTripStatus.json";
  public static final String updateDeliveryStatus =
      "src/test/resources/api/sanity/schema/dms/updateDeliveryStatus.json";
  public static final String deleteConsignment =
      "src/test/resources/api/sanity/schema/dms/deleteConsignment.json";

  public static final String makePayment_cod_cash =
      "src/test/resources/api/sanity/schema/codPayment/post_makePayment_CASH.json";
  public static final String makePayment_cod_qr =
      "src/test/resources/api/sanity/schema/codPayment/post_makePayment_QR.json";
  public static final String checkPaymentStatus =
      "src/test/resources/api/sanity/schema/codPayment/get_checkPaymentStatus.json";
  public static final String checkErrorPaymentStatus =
      "src/test/resources/api/sanity/schema/codPayment/get_CheckPaymentStatusError.json";
  public static final String getStoreConfig =
      "src/test/resources/api/sanity/schema/configmanagement/get_storeConfig.json";
  public static final String getStoreConfigKeys =
      "src/test/resources/api/sanity/schema/configmanagement/get_storeConfigKeys.json";
  public static final String postUploadStoreConfig =
      "src/test/resources/api/sanity/schema/configmanagement/post_uploadStoreConfig.json";
  public static final String getRiderOrdersHistory =
      "src/test/resources/api/sanity/schema/dms/getRiderOrdersHistory.json";
  public static final String getActiveTripInfoByRider =
      "src/test/resources/api/sanity/schema/dms/getActiveTripInfoByRider.json";
  public static final String bulkUpdateRiderStatus =
      "src/test/resources/api/sanity/schema/selfsignup/put_bulkUpdateRiderStatus.json";
  public static final String getShiftDetail =
      "src/test/resources/api/sanity/schema/shift-adherence/get_shiftDetail.json";
  public static final String getRiderStatusReason =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderStatusReason.json";
  public static final String cancelPayment =
      "src/test/resources/api/sanity/schema/codPayment/post_cancelPayment.json";
  public static final String riderPaymentSettlement =
      "src/test/resources/api/sanity/schema/codPayment/post_riderPaymentSettlement.json";
  public static final String getRiderCashBalance =
      "src/test/resources/api/sanity/schema/codPayment/get_riderCashBalance.json";
  public static final String getUnsettledOrders =
      "src/test/resources/api/sanity/schema/codPayment/get_unsettledOrders.json";
  public static final String getVerifySettlementAmount =
      "src/test/resources/api/sanity/schema/codPayment/get_verifySettlementAmount.json";
  public static final String getRiderDeposit =
      "src/test/resources/api/sanity/schema/codPayment/get_riderCashDeposit.json";
  public static final String getRiderHomeInfo =
      "src/test/resources/api/sanity/schema/riderhomepage/get_HomeInfo.json";
  public static final String postCreateBanner =
      "src/test/resources/api/sanity/schema/riderhomepage/post_createBanner.json";
  public static final String updateBanner =
      "src/test/resources/api/sanity/schema/riderhomepage/put_updateBanner.json";
  public static final String getTransactionDetails =
      "src/test/resources/api/sanity/schema/codPayment/get_transactionDetails.json";
  public static final String postBannerStoreMapping =
      "src/test/resources/api/sanity/schema/riderhomepage/post_BannerStoreMapping.json";
  public static final String getBanner =
      "src/test/resources/api/sanity/schema/riderhomepage/get_BannerDetails.json";
  public static final String getCashAtStoreTransactionDetails =
      "src/test/resources/api/sanity/schema/cashatstore/get_transactionDetails.json";
  public static final String postCollectCashByStore =
      "src/test/resources/api/sanity/schema/cashatstore/post_collectCash.json";
  public static final String postCreateOnBoardingCentre =
      "src/test/resources/api/sanity/schema/selfsignup/post_CreateOnBoardingCentre.json";
  public static final String getStoreLedgerBalance =
      "src/test/resources/api/sanity/schema/cashatstore/get_storeLedgerInfo.json";
  public static final String getOnBoardingCentre =
      "src/test/resources/api/sanity/schema/selfsignup/get_OnBoardingCentre.json";
  public static final String postUploadReceipt =
      "src/test/resources/api/sanity/schema/cashatstore/post_uploadReceipt.json";
  public static final String getUnsettledTransactionDetails =
      "src/test/resources/api/sanity/schema/cashatstore/get_UnsettledTransactionDetails.json";
  public static final String getRiderDetails =
      "src/test/resources/api/sanity/schema/cashatstore/get_riderDetails.json";
  public static final String getDashboardByStoreIdAndRiderStatus =
      "src/test/resources/api/sanity/schema/cashatstore/get_cashDashboardByStoreIdAndRiderStatus.json";
  public static final String postHandOverCashToSeller =
      "src/test/resources/api/sanity/schema/cashatstore/post_handOverCashToSeller.json";
  public static final String putForceCheckout =
      "src/test/resources/api/sanity/schema/dms/putForceCheckout.json";
  public static final String getCodPaymentConfig =
      "src/test/resources/api/sanity/schema/configmanagement/get_CodPaymentConfig.json";
  public static final String postCodConfig =
      "src/test/resources/api/sanity/schema/configmanagement/post_codConfig.json";
  public static final String getCashLimitInfo =
      "src/test/resources/api/sanity/schema/configmanagement/get_cashLimitInfo.json";
  public static final String postCreateRiderConfig =
      "src/test/resources/api/sanity/schema/selfsignup/post_CreateRiderConfig.json";
  public static final String postKaptureApiResponse =
      "src/test/resources/api/sanity/schema/selfsignup/post_kaptureApi.json";
  public static final String putRiderStoreIdUpdate =
      "src/test/resources/api/sanity/schema/selfsignup/put_riderStoreUpdate.json";
  public static final String getHomeEarnings =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_homeEarnings.json";
  public static final String getDayEarnings =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_dayEarnings.json";
  public static final String getWeekleEarnings =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_weeklyEarnings.json";
  public static final String postRiderPayouts =
      "src/test/resources/api/sanity/schema/codPayment/post_riderPayouts.json";
  public static final String getOrderEarnings =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_orderEarnings.json";
  public static final String getRiderRateCard =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_RiderRateCard.json";
  public static final String getRiderDocumentsValidate =
      "src/test/resources/api/sanity/schema/adminpanel/getRiderValidateDocuments.json";
  public static final String getOrderRiderRateCard =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_OrderRiderRateCard.json";
  public static final String getRider3plVendorConfig =
      "src/test/resources/api/sanity/schema/selfsignup/get_rider3plVendorDetails.json";
  public static final String getRiderCohortConfig =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderCohortConfig.json";
  public static final String getRiderOnBoardingCentreConfig =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderOnBoardingConfig.json";
  public static final String getDepositTransactionDetails =
      "src/test/resources/api/sanity/schema/codPayment/get_depositTransactionDetails.json";
  public static final String patchBulkUpdateRiderDetails =
      "src/test/resources/api/sanity/schema/selfsignup/patch_bulkUpdateRiderDetails.json";
  public static final String patchBulkUpdateRiderDetailsError =
      "src/test/resources/api/sanity/schema/selfsignup/patch_bulkUpdateRiderDetailsError.json";
  public static final String postAllocationEngineConfig =
      "src/test/resources/api/sanity/schema/configmanagement/post_updateAllocationConfig.json";
  public static final String getRiderDetailsByStoreId =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderDetailsByStoreId.json";
  public static final String getRiderDetailsByProfileId =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderDetailsByProfileId.json";
  public static final String getRiderAssetInfo =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderAssetInfo.json";
  public static final String getAIRiderRateCard =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_AIRiderRateCard.json";
  public static final String getRiderListing =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderListing.json";
  public static final String updateRiderConfig =
      "src/test/resources/api/sanity/schema/selfsignup/put_updateRiderConfig.json";
  public static final String getRiderConfig =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderConfig.json";
  public static final String getRiderProfileExport =
      "src/test/resources/api/sanity/schema/selfsignup/get_riderProfileExport.json";
  public static final String getAIOrderRiderRateCard =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_AttendanceOrderRateCard.json";
  public static final String postRiderOnBoardingDetails =
      "src/test/resources/api/sanity/schema/ssu/post_onBoardingDetails.json";
  public static final String getAllDetails =
      "src/test/resources/api/sanity/schema/ssu/get_allDetails.json";
  public static final String getAadharCaptcha =
      "src/test/resources/api/sanity/schema/ssu/get_aadharCaptcha.json";
  public static final String getS3LinkByAttachmentId =
      "src/test/resources/api/sanity/schema/ssu/get_s3LinkByAttachmentId.json";
  public static final String postCreateRateCard =
      "src/test/resources/api/sanity/schema/referral/post_CreateRateCard.json";
  public static final String getRateCardById =
      "src/test/resources/api/sanity/schema/referral/get_RateCardById.json";
  public static final String getActiveRateCard =
      "src/test/resources/api/sanity/schema/referral/get_ActiveRateCard.json";
  public static final String getRiderCohort =
      "src/test/resources/api/sanity/schema/referral/get_RiderCohort.json";
  public static final String getReferralEarnings =
      "src/test/resources/api/sanity/schema/referral/get_ReferralEarnings.json";
  public static final String getRiderReferralDashboard =
      "src/test/resources/api/sanity/schema/referral/get_RiderReferralDashboard.json";
  public static final String getRiderReferralInfo =
      "src/test/resources/api/sanity/schema/referral/get_RiderReferralInfo.json";
  public static final String getCurrentLeaderboard =
      "src/test/resources/api/sanity/schema/referral/get_currentLeaderboard.json";
  public static final String getLastLeaderboard =
      "src/test/resources/api/sanity/schema/referral/get_lastLeaderboard.json";

  public static final String getAllDetailsAdminApi =
      "src/test/resources/api/sanity/schema/ssu/get_allDetailsAdminApi.json";
  public static final String getAdminVerificationTasks =
      "src/test/resources/api/sanity/schema/ssu/get_adminVerificationTasks.json";

  public static final String getAdminDocumentsVerificationDetails =
      "src/test/resources/api/sanity/schema/ssu/get_adminDocumentVerificationDetails.json";

  public static final String postCreateRiderAsset =
      "src/test/resources/api/sanity/schema/selfsignup/post_createRiderAsset.json";

  public static final String putAdminVerify =
      "src/test/resources/api/sanity/schema/ssu/put_adminVerify.json";
  public static final String getSurveyQuestions =
      "src/test/resources/api/sanity/schema/dms/getSurveyQuestion.json";

  public static final String getPollingData =
      "src/test/resources/api/sanity/schema/ssu/post_polling.json";
  public static final String getAIType3RiderRateCard =
      "src/test/resources/api/sanity/schema/earningsandpayouts/get_AIType3RiderRateCard.json";

  public static final String getSlotSummary =
      "src/test/resources/api/sanity/schema/slots/get_slotSummary.json";
  public static final String getAutocompleteMap =
      "src/test/resources/api/sanity/schema/zeptomap/get_autocomplete.json";
  public static final String getLocationDetails =
      "src/test/resources/api/sanity/schema/zeptomap/get_locationDetails.json";
  public static final String getGeocodeDetails =
      "src/test/resources/api/sanity/schema/zeptomap/get_geocodeDetails.json";

  // below schema path used in prod automation
  public static final String GET_PACKERS =
      "src/test/resources/api/sanity/schema/production/get_packers.json";
  public static final String LOGIN_PACKERS =
      "src/test/resources/api/sanity/schema/production/login_packer.json";
  public static final String SET_PACKER_AVAILABLE =
      "src/test/resources/api/sanity/schema/production/set_packer_availability.json";
  public static final String POST_UPDATEORDER_PAYMENT =
      "src/test/resources/api/sanity/schema/production/post_updateOrder_payment_schema.json";
  public static final String MARK_SUB_ORDER_BIN_PLACED =
      "src/test/resources/api/sanity/schema/production/mark_sub_order_bin_placed.json";
  public static final String MARK_SUB_ORDER_PACKED_V2 =
      "src/test/resources/api/sanity/schema/production/mark_sub_order_packed_v2.json";
  public static final String FETCH_ORDER =
      "src/test/resources/api/sanity/schema/production/fetch_order.json";
  public static final String CREATE_ORDER =
      "src/test/resources/api/sanity/schema/production/post_create_order_schema.json";
  // end of schema path used in prod automation

}
