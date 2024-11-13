/***
 * Date: 25/10/22
 * Project Name: lastmile-api-test
 * @author abhisheksingh
 * @version 1.0
 *
 */
package com.zeptonow.commonlm.constants;

import java.time.LocalDate;

import static com.zeptonow.commonlm.helper.mysql.DbUtilApi.*;

public class DBQueries {

  public static final String updateStockAndAllocatedQuantity =
      "update "
          + zeptoBackendSchemaName
          + ".store_product set quantity = 100 ,allocated_quantity = 0 where id =  '%s'";
  public static final String updateTakingOrdersFlag =
      "update " + zeptoBackendSchemaName + ".store set taking_orders = true where id = '%s'";
  public static final String getOrderProductId =
      "select id from " + zeptoBackendSchemaName + ".order_product op where order_id = '%s'";
  public static final String getOrderCode =
      "select code  from " + zeptoBackendSchemaName + ".order o where id  = '%s'";
  public static final String getReasonId =
      "select id from " + zeptoBackendSchemaName + ".reason_master rm where  name = '%s'";
  public static final String getShiftDetailsFromShiftTable =
      "select * from " + rmsSchemaName + ".shift where id = '%s'";
  public static final String getIsActiveStatusForCodBlock =
      "select is_active  from " + zeptoBackendSchemaName + ".store_meta_scheduler where id = '%s'";
  public static final String getDefaultValueForCodBlock =
      "select actual_default_value  from "
          + zeptoBackendSchemaName
          + ".store_meta_scheduler where id = '%s'";
  public static final String getValueFromStoreMeta =
      "select * from "
          + zeptoBackendSchemaName
          + ".store_meta sm where store_id ='%s' and key = '%s'";
  public static final String getCouponCodeForOrderId =
      "select code from " + zeptoBackendSchemaName + ".coupon c  where order_id = '%s'";
  public static final String getUserIdFromOrderId =
      "select user_id  from " + zeptoBackendSchemaName + ".order where id = '%s'";
  public static final String getMobileNumberFromUserId =
      "select mobile_number  from " + zeptoBackendSchemaName + ".user where id = '%s'";
  public static final String getRefundDataForOrderId =
      "select * from " + zeptoBackendSchemaName + ".refund r where order_id = '%s'";
  public static final String getStandStillModeFlag =
      "select stand_still_mode from " + zeptoBackendSchemaName + ".store where id ='%s'";
  public static final String getAvailableCities =
      "select id,name from " + zeptoBackendSchemaName + ".city ";
  public static final String getTestCitiesId =
      "select value  from " + rmsSchemaName + ".config_master cm where key ='test_city_ids'";
  public static final String getStoresByCityId =
      "select id,name from "
          + zeptoBackendSchemaName
          + ".store where city_id='%s' and is_active = true";
  public static final String getTestStoresId =
      "select value  from " + rmsSchemaName + ".config_master cm where key ='test_stores_ids'";
  public static final String getAllCitiesId = "select id from " + zeptoBackendSchemaName + ".city";
  public static final String getConsignmentDetails =
      "select * from " + deliveryServiceSchemaName + ".consignment where id ='%s'";
  public static final String getConsignmentHistory =
      "select * from "
          + deliveryServiceSchemaName
          + ".consignment_history where id ='%s' order by _id desc";
  public static final String getDeliveryDetailsByDeliveryId =
      "select * from " + deliveryServiceSchemaName + ".delivery where id ='%s'";
  public static final String getTodayConsignmentDetailsByStoreId =
      "select * from "
          + deliveryServiceSchemaName
          + ".consignment where store_id ='%s' and created_at>='%s' and status not in('DELIVERED', 'CANCELLED', 'RETURNED', 'RETURN_TO_ORIGIN')";
  public static final String getTodayDeliveryDetailsByStoreId =
      "select * from "
          + deliveryServiceSchemaName
          + ".delivery d join "
          + deliveryServiceSchemaName
          + ".consignment c on c.id=d.consignment_id where c.store_id='%s' and d.trip_id is not null and c.created_at>='"
          + LocalDate.now()
          + "' order by c.created_at desc limit 1";
  public static final String getConsignmentByStatus =
      "select * from "
          + deliveryServiceSchemaName
          + ".consignment where status ='%s' order by id desc limit 1";
  public static final String updateConsignmentStatus =
      "update " + deliveryServiceSchemaName + ".consignment set status ='%s' where id='%s'";
  public static final String updateConsignmentPaymentStatus =
      "update "
          + deliveryServiceSchemaName
          + ".consignment set payment_status ='SUCCESS' where id='%s'";
  public static final String getDeliveryDetails =
      "select * from " + deliveryServiceSchemaName + ".delivery where consignment_id ='%s'";
  public static final String updateShiftTiming =
      "update "
          + rmsSchemaName
          + ".shift_slice set begins_at = '00:00:00', lasts_for = '23:30:00' where  shift_id = %d";
  public static final String getRiderLogRms =
      "select * from " + rmsSchemaName + ".rider_attendance_log where rider_id='%s'";
  public static final String getRiderInsuranceInfo =
      "select * from " + rmsSchemaName + ".rider_insurance_info where rider_id='%s'";
  public static final String getRiderInsuranceInfoAuditLog =
      "select * from " + rmsSchemaName + ".rider_insurance_info_audit_logs where rider_id='%s'";
  public static final String updateStoreOpeningTime =
      "update " + zeptoBackendSchemaName + ".store  set open_time = '%s' where id = '%s'";
  public static final String updateStoreClosingTime =
      "update " + zeptoBackendSchemaName + ".store  set close_time = '%s' where id = '%s'";
  public static final String updateShiftTimingToGivenTime =
      "update "
          + rmsSchemaName
          + ".shift_slice set begins_at = '%s', lasts_for = '23:30:00' where  shift_id = %d";
  public static final String getUserIdForRiderId =
      "select user_id  from " + rmsSchemaName + ".rider_profile where id = '%s'";
  public static final String getRiderStatusFromRiderId =
      "select status from " + rmsSchemaName + ".rider_status rs where rider_id = '%s'";
  public static final String getRiderSubStatusFromRiderId =
      "select sub_status from " + rmsSchemaName + ".rider_status rs where rider_id = '%s'";
  public static final String getRiderLeadInfoByRiderId =
      "select * from "
          + rmsSchemaName
          + ".rider_lead_info where user_id  = (select user_id  from "
          + rmsSchemaName
          + ".rider_profile where id = '%s')";
  public static final String getUserIdFromMobileNumber =
      "select id from " + zeptoBackendSchemaName + ".user where mobile_number = '%s'";
  public static final String getRiderStatusFromUserId =
      "select status from " + rmsSchemaName + ".rider_status rs where user_id = '%s'";
  public static final String getRiderLeadInfoByUserId =
      "select * from " + rmsSchemaName + ".rider_lead_info where user_id  = '%s'";
  public static final String getCityIdFromStoreId =
      "select city_id from " + zeptoBackendSchemaName + ".store where id = '%s'";
  public static final String getUserDetailsByRiderId =
      "select * from "
          + zeptoBackendSchemaName
          + ".user where id = (select user_id from "
          + rmsSchemaName
          + ".rider_profile where id = '%s')";
  public static final String getRiderDetails =
      "select *  from " + rmsSchemaName + ".rider_profile where id = '%s'";
  public static final String getRiderMetaData =
      "select *  from " + rmsSchemaName + ".rider_profile_meta where rider_id = '%s'";
  public static final String getVendorRiderId =
      "select vendor_rider_id  from " + rmsSchemaName + ".rider_profile where id = '%s'";
  public static final String getStoreDetailsById =
      "select * from " + zeptoBackendSchemaName + ".store s where id = '%s';";
  public static final String getShiftDetailByShiftId =
      "select * from " + rmsSchemaName + ".shift_slice where shift_id = '%s';";
  public static final String updateShiftSliceTimingToCustomTime =
      "update "
          + rmsSchemaName
          + ".shift_slice set begins_at = '%s', lasts_for = '%s' where  shift_id = %d";
  public static final String updateRiderCheckInTime =
      "update "
          + rmsSchemaName
          + ".rider_attendance_log  set online_on = '%s' where rider_id = '%s' and updated_at  = (select updated_at  from "
          + rmsSchemaName
          + ".rider_attendance_log dd  where rider_id = '%s' order by updated_at  DESC limit 1)";
  public static final String updateRiderCheckOutTime =
      "update "
          + rmsSchemaName
          + ".rider_attendance_log  set offline_on = '%s' where rider_id = '%s' and updated_at  = (select updated_at  from "
          + rmsSchemaName
          + ".rider_attendance_log dd  where rider_id = '%s' order by updated_at  DESC limit 1)";
  public static final String updateShiftTimingToCustomTime =
      "update "
          + rmsSchemaName
          + ".shift set begins_at = '%s' , lasts_for = '%s' , day_begins_at = '%s' where id = %d";
  public static final String updateTripDetailWithRiderId =
      "update "
          + deliveryServiceSchemaName
          + ".trip set rider_id = '%s' , is_valid = true where id = ( SELECT ID FROM "
          + deliveryServiceSchemaName
          + ".trip ORDER BY ID LIMIT 1)";
  public static final String updateGivenShiftSliceTimingToCustomTime =
      "update "
          + rmsSchemaName
          + ".shift_slice set begins_at = '%s', lasts_for = '%s' where  shift_id = %d and id = %d";
  public static final String getRidersForStoreIdAndIsValidField =
      "select id from "
          + rmsSchemaName
          + ".rider_profile where store_id = '%s' and is_valid = true";
  public static final String getInactiveRidersByStoreId =
      "select t1.rider_id from "
          + rmsSchemaName
          + ".rider_attendance_log t1 INNER JOIN "
          + "(select rider_id, MAX(online_on) AS max_online_on FROM "
          + rmsSchemaName
          + ".rider_attendance_log WHERE rider_id in ("
          + getRidersForStoreIdAndIsValidField
          + ") GROUP BY rider_id HAVING MIN(CASE WHEN offline_on IS NULL THEN 0 ELSE 1 END) = 1) t2 "
          + "ON t1.rider_id = t2.rider_id AND t1.online_on = t2.max_online_on "
          + "WHERE t1.offline_on >= '%s'  AND t1.source != '%s'";
  public static final String getCashDashBoardOfflineRiders =
      "select r1.user_id,r2.rider_id,r1.vendor_rider_id,r2.settlement_amount_left_in_paise from public.rider r1 "
          + "join (select sum(rsl.settlement_amount_left_in_paise) as settlement_amount_left_in_paise ,max(rsl.updated_at) as last_updated_at, rsl.rider_id from cod.rider_seller_ledger rsl "
          + "where rider_id in("
          + getInactiveRidersByStoreId
          + ") group by rsl.rider_id order by settlement_amount_left_in_paise DESC, last_updated_at DESC limit %d offset %d) r2 ON r2.rider_id=r1.id";
  public static final String getCountOfRidersStatus =
      "SELECT COUNT(CASE WHEN status = 'IN_STORE_ASSIGNED' then 1 ELSE NULL END) as assigned_count,"
          + "COUNT(CASE WHEN status = 'IN_STORE' then 1 ELSE NULL END) as unassigned_count,"
          + "COUNT(CASE WHEN status = 'OUT_FOR_DELIVERY' then 1 ELSE NULL END) as on_road_count,"
          + "COUNT(CASE WHEN status = 'ARRIVED' then 1 ELSE NULL END) as reached_gate_count,"
          + "COUNT(CASE WHEN status = 'RETURNING_TO_STORE' then 1 ELSE NULL END) as returning_to_store_count from "
          + deliveryServiceSchemaName
          + ".rider_delivery_status where rider_id in ( '%s')";
  public static final String getRiderDeliveryStatusByRider =
      "select * from "
          + deliveryServiceSchemaName
          + ".rider_delivery_status rds where rider_id in ('%s') and status = '%s'";
  public static final String getRiderDeliveryStatusByRiderId =
      "select * from "
          + deliveryServiceSchemaName
          + ".rider_delivery_status rds where rider_id in ('%s')";
  public static final String getActiveRidersByStoreId =
      "select t1.* from "
          + rmsSchemaName
          + ".rider_attendance_log t1 INNER JOIN "
          + "(select rider_id, MAX(online_on) AS max_online_on FROM "
          + rmsSchemaName
          + ".rider_attendance_log WHERE rider_id in ("
          + getRidersForStoreIdAndIsValidField
          + ") GROUP BY rider_id HAVING MIN(CASE WHEN offline_on IS NULL THEN 0 ELSE 1 END) = 0) t2 "
          + "ON t1.rider_id = t2.rider_id "
          + "WHERE t1.online_on >= '%s'  AND t1.source != '%s' AND t1.offline_on IS NULL order by online_on desc";
  public static final String getCashDashBoardOnlineRiders =
      "select r1.user_id,r2.rider_id,r1.vendor_rider_id,r2.settlement_amount_left_in_paise from public.rider r1 "
          + "join (select sum(rsl.settlement_amount_left_in_paise) as settlement_amount_left_in_paise ,max(rsl.updated_at) as last_updated_at, rsl.rider_id from cod.rider_seller_ledger rsl "
          + "where rider_id in("
          + getActiveRidersByStoreId
          + ") group by rsl.rider_id order by settlement_amount_left_in_paise DESC, last_updated_at DESC limit %d offset %d) r2 ON r2.rider_id=r1.id";
  public static final String getDeliveryTripConsignmentInfoByRiders =
      "select trip.id as trip_id, trip.rider_id as rider_id , trip.status as trip_status, trip.extra_details as trip_Extra_Details,"
          + " d.id as delivery_id, d.status as delivery_Status, d.extra_details as del_Extra_Details,  d.status_change_event as del_Status_Change, d.is_batched as is_batched, d.batched_sequence_number as batched_sequence_number, c.*"
          + "from "
          + deliveryServiceSchemaName
          + ".trip, "
          + deliveryServiceSchemaName
          + ".delivery d , "
          + deliveryServiceSchemaName
          + ".consignment c where trip.id = d.trip_id and"
          + " d.consignment_id = c.id and trip.rider_id in ('%s') and trip.created_at >= '%s' order by trip.created_at desc";
  public static final String getDeliveryTripConsignmentInfoByConsignmentId =
      "select trip.id as trip_id, trip.rider_id as rider_id , trip.status as trip_status, trip.extra_details as trip_Extra_Details,"
          + " d.id as delivery_id, d.status as delivery_Status, d.is_batched, d.extra_details as del_Extra_Details,  d.status_change_event as del_Status_Change, d.batched_sequence_number as batched_sequence_number, c.*"
          + "from "
          + deliveryServiceSchemaName
          + ".trip, "
          + deliveryServiceSchemaName
          + ".delivery d , "
          + deliveryServiceSchemaName
          + ".consignment c where trip.id = d.trip_id and"
          + " d.consignment_id = c.id and c.id = '%s'";
  public static final String getDeliveryConsignmentInfoByConsignmentId =
      "select d.id as delivery_id, d.status as delivery_Status, d.is_batched, d.extra_details as del_Extra_Details,  d.status_change_event as del_Status_Change, d.batched_sequence_number as batched_sequence_number, c.*"
          + "from "
          + deliveryServiceSchemaName
          + ".delivery d , "
          + deliveryServiceSchemaName
          + ".consignment c where "
          + " d.consignment_id = c.id and c.id = '%s'";
  public static final String getAdditionalRiderDetails =
      "select r.vendor_rider_id, r.vehicle_type, r.contract_type , u.full_name , u.mobile_number, u.email_id from "
          + rmsSchemaName
          + ".rider_profile r, "
          + zeptoBackendSchemaName
          + ".user u where r.user_id = u.id and r.id = '%s'";
  public static final String getDeliveryDetailsById =
      "select * from " + deliveryServiceSchemaName + ".delivery where id = '%s'";
  public static final String getRiderMobileNo =
      "select mobile_number from "
          + zeptoBackendSchemaName
          + ".user where id=(select user_id from "
          + rmsSchemaName
          + ".rider_profile where id='%s')";
  public static final String getTransactionDetails =
      "select * from " + codServiceSchemaName + ".cod_transaction where id='%s'";
  public static final String updateShiftSliceBufferTimingToCustomTime =
      "update " + rmsSchemaName + ".shift_slice set buffer = '%s' where  shift_id = %d";
  public static final String updateShiftSliceBreakTimingToCustomTime =
      "update " + rmsSchemaName + ".shift_slice set break = '%s' where  shift_id = %d";
  public static final String getShiftStatusByRiderId =
      "select * from " + rmsSchemaName + ".rider_shift_status where rider_id='%s'";
  public static final String updateShiftAssociatedSince =
      "update " + rmsSchemaName + ".rider_shift set associated_since ='%s' where rider_id='%s'";
  public static final String updateWeekoffAssociatedSince =
      "update " + rmsSchemaName + ".rider_week_off set associated_since ='%s' where rider_id='%s'";
  public static final String clearRiderAttendanceLog =
      "delete from " + rmsSchemaName + ".rider_attendance_log where rider_id='%s'";
  public static final String clearRiderShiftStatus =
      "delete from " + rmsSchemaName + ".rider_shift_status where rider_id='%s'";
  public static final String getOrdersHistoryByRiderId =
      "select * from "
          + deliveryServiceSchemaName
          + ".delivery d join "
          + deliveryServiceSchemaName
          + ".consignment c on c.id = d.consignment_id join "
          + deliveryServiceSchemaName
          + ".trip t on t.id = d.trip_id where t.rider_id = '%s' and c.status in ('%s', '%s') order by load_time desc";
  public static final String getMultiShiftSliceData =
      "select * from " + rmsSchemaName + ".shift_slice where shift_id = '%s' and id = '%s'";
  public static final String updateMultiShiftSliceBreakTiming =
      "update " + rmsSchemaName + ".shift_slice set break = '%s' where  shift_id = %d and id = %d";
  public static final String getTripsByRiderId =
      "select t.id as trip_id, t.rider_id as rider_id , t.status as trip_status, t.extra_details as trip_Extra_Details, t.is_valid as is_trip_valid from "
          + deliveryServiceSchemaName
          + ".trip t where t.rider_id = '%s' and t.created_at >= '%s' order by t.created_at desc";
  public static final String getDeliveryConsignmentInfoByRiders =
      "select d.id as delivery_id, d.status as delivery_Status, d.extra_details as del_Extra_Details,  d.status_change_event as del_Status_Change, d.is_batched as is_batched, d.batched_sequence_number as batched_sequence_number, c.*,"
          + " t.id as trip_id, t.rider_id as rider_id , t.status as trip_status, t.extra_details as trip_Extra_Details, t.is_valid as is_trip_valid from "
          + deliveryServiceSchemaName
          + ".delivery d join "
          + deliveryServiceSchemaName
          + ".consignment c on c.id = d.consignment_id join trip t on t.id = d.trip_id where t.id = '%s'";
  public static final String isRiderAssignedToTrip =
      "select count(*) from "
          + deliveryServiceSchemaName
          + ".trip where id = '%s' and rider_id ='%s'";
  public static final String getActiveTripsByRiderId =
      "select * from "
          + deliveryServiceSchemaName
          + ".trip t where t.rider_id = '%s' and t.is_valid = true order by t.created_at desc";
  public static final String getStoreLatLongByRiderId =
      "select latitude, longitude from "
          + zeptoBackendSchemaName
          + ".store where id = (select store_id from public.rider r where id = '%s')";
  public static final String getShiftDetailsFromShiftSliceTable =
      "select * from " + rmsSchemaName + ".shift_slice where shift_id = '%s'";
  public static final String getRiderIdFromMobileNumber =
      "select id  from "
          + rmsSchemaName
          + ".rider_profile where user_id = (select id from "
          + zeptoBackendSchemaName
          + ".user where mobile_number = '%s')";
  public static final String getFixedAndOnlineRiderId =
      "select id from "
          + rmsSchemaName
          + ".rider_profile where contract_type='FIXED_RIDER' LIMIT 1";
  public static final String getEmailRoleId =
      "select id from " + zeptoBackendSchemaName + ".zap_email_role where email = '%s'";
  public static final String getStoresByEmailRoleId =
      "select store_id from "
          + zeptoBackendSchemaName
          + ".zap_user_store where zap_email_role_id = ("
          + getEmailRoleId
          + ")";
  public static final String getAutomationRiderIds =
      "select id   from "
          + rmsSchemaName
          + ".rider_profile r where user_id in (select id from "
          + zeptoBackendSchemaName
          + ".user u where full_name like 'test-lastmile%')";
  public static final String getAllRiderToDelete =
      "select id from "
          + rmsSchemaName
          + ".rider_profile where store_id ='d48222ae-a642-4aeb-9cee-e2829ebaa5e7' and id not in ('5f028d22-3fc9-4521-bdbd-839d0164ac22')";
  public static final String getBannerDetailsById =
      "select * from " + rmsSchemaName + ".banner b where id = '%s'";
  public static final String getAttachmentDetailByAttachmentId =
      "select * from " + rmsSchemaName + ".file_attachments where id= '%s'";
  public static final String getPaymentStatus =
      "select status from " + paymentServiceSchemaName + ".transaction where code = '%s'";
  public static final String getTransactionIdNotMappedToRider =
      "select * from "
          + codServiceSchemaName
          + ".cod_transaction where payer_id not in(select user_id from "
          + rmsSchemaName
          + ".rider_profile where id='%s') limit 1";
  public static final String getBannerStoreMappingDetails =
      "select * from " + rmsSchemaName + ".banner_store_map where store_id='%s' and id ='%s';";
  public static final String getOnBoardingCentreById =
      "select * from "
          + zeptoBackendSchemaName
          + ".config_master cm where type = 'ONBOARDING_CENTRE' and id ='%s'";
  public static final String getConsignmentByOrderCode =
      "select * from " + deliveryServiceSchemaName + ".consignment where order_code = '%s'";
  public static final String getPackerKeyByStoreId =
      "select current_key  from "
          + zeptoBackendSchemaName
          + ".reusable_bags_qr_keys where store_id ='%s'";
  public static final String getDuplicateReceiptNumber =
      "select receipt_number from " + codServiceSchemaName + ".store_handover_transaction limit 1";
  public static final String getDuplicateAttachmentId =
      "select attachment_id from " + codServiceSchemaName + ".store_handover_transaction limit 1";
  public static final String insertRtbPenaltyAmount =
      "INSERT INTO "
          + deliveryServiceSchemaName
          + ".store_config (id, created_at, updated_at,created_by,store_id,key,value)\n"
          + "VALUES (22627, '2023-01-02 14:55:47.480349', '2023-01-02 14:55:47.480349','SYSTEM','%s','RTB_PENALTY_AMOUNT_IN_PAISE','2000');";
  public static final String insertRtbDeadlineHours =
      "INSERT INTO "
          + deliveryServiceSchemaName
          + ".store_config (id, created_at, updated_at,created_by,store_id,key,value)\n"
          + "VALUES (22628, '2023-01-02 14:55:47.480349', '2023-01-02 14:55:47.480349','SYSTEM','%s','RTB_DEADLINE_HOURS','50');";
  public static final String insertRtbManualQrEntry =
      "INSERT INTO "
          + deliveryServiceSchemaName
          + ".store_config (id, created_at, updated_at,created_by,store_id,key,value)\n"
          + "VALUES (22629, '2023-01-02 14:55:47.480349', '2023-01-02 14:55:47.480349','SYSTEM','%s','RTB_MANUAL_QR_ENTRY','FALSE');";
  public static final String deleteRtbEntries =
      "delete from "
          + deliveryServiceSchemaName
          + ".store_config where key in ('RTB_MANUAL_QR_ENTRY','RTB_DEADLINE_HOURS','RTB_PENALTY_AMOUNT_IN_PAISE') and store_id = '%s'";
  public static final String getTripDetailsById =
      "select * from " + deliveryServiceSchemaName + ".trip where id = '%s'";
  public static final String getAlreadysettledPaymentCode =
      "select payment_code from "
          + codServiceSchemaName
          + ".cod_transaction where payment_code <> '' and payment_status='SUCCESS' order by updated_at desc limit 1";
  public static final String getLastUpdatedTime =
      "select updated_at from " + codServiceSchemaName + ".store_seller_ledger where store_id='%s'";
  public static final String getCodePaymentConfigValueForGivenKeyAndStoreId =
      "select value  from " + codServiceSchemaName + ".cod_config where store_id='%s' and key='%s'";
  public static final String getStoreCashLimit =
      "select cash_limit_in_paise  from "
          + codServiceSchemaName
          + ".cash_limit_config  where store_id='%s' and probation = true and rider_contract_type = 'FULL_TIME' and rider_vehicle_type = 'BIKE'";
  public static final String getStoreNameByStoreId =
      "select name from " + zeptoBackendSchemaName + ".store s where id ='%s'";
  public static final String updateDeliveryUpdatedAtTo5MinutesBefore =
      "update " + deliveryServiceSchemaName + ".delivery set updated_at = '%s' where id = '%s'";
  public static String getOtp =
      "select token from "
          + zeptoBackendSchemaName
          + ".otp_token ot where user_id = (select id from "
          + zeptoBackendSchemaName
          + ".user u  where mobile_number = '%s')"
          + " order by created_on desc";
  public static String getAllStoreConfig =
      "select key,value from "
          + deliveryServiceSchemaName
          + ".store_config sc where store_id = '%s'";
  public static String getKeyValueFromStoreConfig =
      "select value from "
          + deliveryServiceSchemaName
          + ".store_config sc where store_id = '%s' and key = '%s'";
  public static String updateShiftIdByRiderId =
      "update " + rmsSchemaName + ".rider_shift set shift_id = %s where rider_id = '%s'";
  public static String getRiderConfigFromConfigMaster =
      "select * from " + rmsSchemaName + ".config_master cm where type ='%s' and key ='%s'";
  public static String deleteRiderConfigFromConfigMaster =
      "delete from " + rmsSchemaName + ".config_master cm where type ='%s' and key ='%s'";
  public static String getRiderAssetDetails =
      "select * from " + rmsSchemaName + ".rider_asset ra where rider_id = '%s'";
  public static String getCommonIdInRiderAndRiderProfileTable =
      "select * from "
          + rmsSchemaName
          + ".rider_profile where user_id  in ( select user_id  from "
          + rmsSchemaName
          + ".rider_profile)";
  public static String getRiderDetailsFromRiderProfileInfo =
      "select * from "
          + rmsSchemaName
          + ".rider_profile_info rpi where key = 'RIDER_KAPTURE_DETAILS' and rider_id ='%s'";
  public static String getRandomRiderProfileId =
      "select * from " + rmsSchemaName + ".rider_profile limit 1";
  public static String getRiderStoreIdByProfileId =
      "select store_id  from " + rmsSchemaName + ".rider_profile where id ='%s'";
  public static String getRiderDetailsByUserId =
      "select *  from " + rmsSchemaName + ".rider_profile r where user_id  ='%s'";
  public static String getRateCardStaticSet =
      "select * from " + rmsSchemaName + ".ratecard_slab where ratecard_id = %s";
  public static String updateCreatedAtTimeToYesterdayDate =
      "update "
          + codServiceSchemaName
          + ".store_cash_collection set created_at = '%s' where store_id = '%s' and transaction_id = '%s'";
  public static String updateTransactionTimeToYesterdayDate =
      "update "
          + codServiceSchemaName
          + ".cod_transaction set transaction_time = '%s' where id = '%s'";
  public static String getUnsettledAmountForStore =
      "select * from "
          + codServiceSchemaName
          + ".store_cash_collection where store_id = '%s' and seller_id = '%s' and settlement_status in ('%s', '%s') and created_at >= '%s'";
  public static String getPnbAmountForStoreBeforeCloseTime =
      "select * from "
          + codServiceSchemaName
          + ".store_cash_collection where store_id = '%s' and seller_id = '%s' and settlement_status in ('%s', '%s') and created_at < '%s'";
  public static String getActiveDepositTransactions =
      "select * from "
          + codServiceSchemaName
          + ".cod_transaction where payment_status = 'INITIATED' and seller_id = '%s' and payer_store_id = '%s' and sub_payment_method = 'PAY_NEARBY' and payer_type = 'STORE' order by updated_at desc";
  public static String getCodTransactionByPaymentCode =
      "select * from " + codServiceSchemaName + ".cod_transaction where payment_code = '%s'";
  public static String getLatestStoreHandoverTransaction =
      "select * from "
          + codServiceSchemaName
          + ".store_handover_transaction where store_id = '%s' order by created_at desc limit 1";
  public static String getStoreSellerLedger =
      "select * from "
          + codServiceSchemaName
          + ".store_seller_ledger where store_id='%s' and settlement_amount_left_in_paise > 0 order by created_at";
  public static String setActiveDepositToFailedForSeller =
      "update "
          + codServiceSchemaName
          + ".cod_transaction set is_active  = false, payment_status = 'FAILED' where payer_store_id  = '%s' and payment_status = 'INITIATED' and seller_id = '%s'";
  public static String getRiderPaymentInfo =
      "select * from " + codServiceSchemaName + ".rider_payout_info where rider_id ='%s'";
  public static String getRiderSellerLedger =
      "select * from " + codServiceSchemaName + ".rider_seller_ledger where rider_id ='%s'";
  public static String getWeeklyPayoutInfo =
      "select * from " + codServiceSchemaName + ".weekly_payout_info where id ='%s'";
  public static String getCodSettlementInfo =
      "select * from " + codServiceSchemaName + ".cod_settlement where rider_id  ='%s'";
  public static final String updateMinDueInRiderSellerLedger =
      "update "
          + codServiceSchemaName
          + ".rider_seller_ledger set min_due = '%s' where rider_id = '%s'";
  public static final String updateMultiShiftSliceBufferTiming =
      "update " + rmsSchemaName + ".shift_slice set buffer = '%s' where  shift_id = %d and id = %d";
  public static final String getRateCardStaticSetSortedBySequence =
      "select * from " + rmsSchemaName + ".ratecard_slab where ratecard_id = %s order by sequence";
  public static final String getRateCardStaticSetBySequence =
      "select * from " + rmsSchemaName + ".ratecard_slab where ratecard_id = %s and  sequence = %d";
  public static final String getOrderDetails =
      "select * from " + rmsSchemaName + ".order where order_code = '%s'";
  public static final String getConfigDetailBasedOnType =
      "select id,type,key,value,is_active,created_by_id from "
          + rmsSchemaName
          + ".config_master cm where type ='%s' and is_active = true";
  public static final String getRandomRiderProfile =
      "select rpm.rider_id, rp.store_id from "
          + rmsSchemaName
          + ".rider_profile_meta rpm join "
          + rmsSchemaName
          + ".rider_profile rp on rpm.rider_id = rp.id limit 1";
  public static final String getRiderDetailsFromProfileMetaTable =
      "select * from " + rmsSchemaName + ".rider_profile_meta rpm  where rider_id ='%s'";
  public static final String getAllocationSetting =
      "select * from "
          + deliveryServiceSchemaName
          + ".rule_setting where super_setting_id ='%s' and rule_id='4'";
  public static final String updateRiderCheckInTimeById =
      "update "
          + rmsSchemaName
          + ".rider_attendance_log  set online_on = '%s' where rider_id = '%s' and id = %d ";
  public static final String updateRiderCheckOutTimeById =
      "update "
          + rmsSchemaName
          + ".rider_attendance_log  set offline_on = '%s' where rider_id = '%s' and id = %d ";
  public static final String getRiderAttendanceLogPrimaryKeyValues =
      "select id from " + rmsSchemaName + ".rider_attendance_log where rider_id='%s' ";
  public static final String disableRiderRateCard =
      "update "
          + rmsSchemaName
          + ".rider_ratecard set active = false where rider_id = '%s' and ratecard_id = %d";
  public static final String getRiderDetailsBasedOnCityId =
      "select r.id, r.vendor_rider_id from "
          + rmsSchemaName
          + ".rider_profile r join store s on r. store_id =s.id and s.city_id in (%s)";
  public static final String getRiderDetailsBasedOnStoreId =
      "select * from "
          + rmsSchemaName
          + ".rider_profile where store_id in (%s) and is_valid = true";
  public static final String getRiderDetailsByStoreId =
      "select * from " + rmsSchemaName + ".rider_profile where store_id='%s' and is_valid='true'";
  public static final String getRiderDetailsByRiderId =
      "select * from " + rmsSchemaName + ".rider_profile where id='%s'";
  public static final String getRiderConfigById =
      "select * from " + rmsSchemaName + ".config_master cm where id='%s'";
  public static final String getRainModeBlockFlag =
      "select value from "
          + zeptoBackendSchemaName
          + ".store_meta where store_id ='%s' and key = 'IS_RAIN_MODE_BLOCKED'";
  public static final String getRateCardEligibility =
      "select * from " + rmsSchemaName + ".ratecard_eligibility where ratecard_id = %d";
  public static final String updateRiderToInvalid =
      "update " + rmsSchemaName + ".rider_profile set is_valid = false where id = '%s'";
  public static final String updateUserCreatedOn =
      "update "
          + zeptoBackendSchemaName
          + ".user set created_on = '2021-01-01 18:37:28.822 +0530' where id = '%s'";
  public static final String updateRiderToValid =
      "update " + rmsSchemaName + ".rider_profile set is_valid = true where id = '%s'";
  public static final String getRiderOnBoardingDetails =
      "select * from " + rmsSchemaName + ".onboarding_rider db where phone_number = '%s'";
  public static final String getVehicleIdByVehicleType =
      "select id from payouts.vehicles v where type = '%s'";
  public static final String getRateCardReferralCriteriaMappingByRateCardId =
      "select * from "
          + referralSchemaName
          + ".ratecard_referral_criteria_mapping where ratecard_id = '%s' order by sequence";
  public static final String getRateCardByName =
      "select * from " + referralSchemaName + ".ratecard where name = '%s'";
  public static final String getReferralCriteria =
      "select * from " + referralSchemaName + ".referral_criteria where id = '%s'";
  public static final String getActiveRateCard =
      "select * from "
          + referralSchemaName
          + ".ratecard where is_active = 'true' order by created_at desc";
  public static final String getRiderCohortByCohortInfo =
      "select * from "
          + referralSchemaName
          + ".rider_cohort where store_id ='%s' and vehicle_type = '%s' and contract_type='%s' and ratecard_id ='%s' and end_time='%s'";
  public static final String getRiderCohortByCohortDetails =
      "select * from "
          + referralSchemaName
          + ".rider_cohort where store_id ='%s' and vehicle_type = '%s' and contract_type='%s' and ratecard_id ='%s' order by start_time desc";
  public static final String deleteRiderCohortByCohortInfo =
      "delete from "
          + referralSchemaName
          + ".rider_cohort where store_id ='%s' and vehicle_type = '%s' and contract_type='%s'";
  public static final String getRiderCohort =
      "select * from " + referralSchemaName + ".rider_cohort order by created_at desc";
  public static final String getRiderCohortByStoreId =
      "select * from "
          + referralSchemaName
          + ".rider_cohort where store_id='%s' order by created_at desc";
  public static final String getReferralEarningsByRiderId =
      "select * from "
          + referralSchemaName
          + ".referral_earnings where user_id='%s' and is_claimed='true' order by created_at desc";
  public static final String getReferralEarnings =
      "select * from " + referralSchemaName + ".referral_earnings order by created_at desc";
  public static final String getUserReferralInfo =
      "select * from " + referralSchemaName + ".user_referral_info where user_id='%s'";
  public static final String clearReferralLeaderboardData =
      "delete from " + referralSchemaName + ".leaderboard where 1=1";
  public static final String clearReferralLeaderboardProgressData =
      "delete from " + referralSchemaName + ".leaderboard_progress where 1=1";
  public static final String clearReferralLeaderboardResultData =
      "delete from " + referralSchemaName + ".leaderboard_result where 1=1";
  public static final String getRiderCohortByRiderInfo =
      "select ratecard_id from "
          + referralSchemaName
          + ".rider_cohort where store_id ='%s' and vehicle_type = '%s' and contract_type='%s'";
  public static final String getReferralRateCardData =
      "select * from "
          + referralSchemaName
          + ".ratecard_referral_criteria_mapping where ratecard_id=("
          + getRiderCohortByRiderInfo
          + " order by created_at desc limit 1) order by sequence";
  public static final String getLeaderboardByName =
      "select * from " + referralSchemaName + ".leaderboard where name = '%s'";
  public static final String getLatestAutomationLeaderboard =
      "select * from "
          + referralSchemaName
          + ".leaderboard where name ='Automation_Leaderboard' order by created_at desc";
  public static final String getReferralData =
      "select * from " + referralSchemaName + ".referral where referee_id='%s'";
  public static final String getRefereeOrderCount =
      "select * from " + referralSchemaName + ".referee_milestone_progress where referee_id = '%s'";
  public static final String getReferralLedger =
      "select * from " + referralSchemaName + ".referral_ledger where user_id='%s'";
  public static final String getReferralByReferralId =
      "select * from " + referralSchemaName + ".referral where referral_id='%s'";
  public static final String getRiderReferralEarning =
      "select * from "
          + referralSchemaName
          + ".referral_earnings where user_id='%s' and earning_type = 'REFERRAL_EARNINGS' order by created_at desc";
  public static final String getRiderReferralLedgerData =
      "select * from " + referralSchemaName + ".referral_ledger where user_id='%s'";
  public static final String getGlobalReferralRateCardData =
      "select * from "
          + referralSchemaName
          + ".ratecard_referral_criteria_mapping where ratecard_id=(select id from "
          + referralSchemaName
          + ".ratecard where name='GLOBAL_MILESTONE_RATECARD') order by sequence";
  public static final String updateRefereeOnBoardingTimeWithCreatedAtTime =
      "update "
          + referralSchemaName
          + ".referral set referee_onboarding_time =(select created_at from "
          + referralSchemaName
          + ".referral where referee_id='%s') where referee_id='%s'";
  public static final String getReferralScore =
      "select score from " + referralSchemaName + ".leaderboard_progress where user_id='%s'";
  public static final String updateLeaderboardEndTime =
      "update " + referralSchemaName + ".leaderboard set end_time='%s' where id='%s'";
  public static final String getLeaderboardProgressDetails =
      "select lp.user_id, lp.score, rl.earning_amount_in_paise from "
          + referralSchemaName
          + ".leaderboard_progress lp join "
          + referralSchemaName
          + ".referral_ledger rl on lp.user_id = rl.user_id where leaderboard_id = '%s' order by lp.score desc, rl.earning_amount_in_paise desc";
  public static final String getRiderDocumentData =
      "select * from "
          + rmsSchemaName
          + ".onboarding_rider_document where user_id ='%s' and key = '%s'";
  public static final String getKycDocumentStatus =
      "select * from " + kycServiceSchemaName + ".document where entity_id = '%s'";
  public static final String getAllDocumentId =
      "SELECT id FROM " + kycServiceSchemaName + ".document_type";
  public static final String getDocumentStatusByUserIdAndDocumentId =
      "select status  from kyc.document where entity_id = '%s' and  document_type_id = %d";
  public static final String getDocumentIdByType =
      "SELECT id FROM " + kycServiceSchemaName + ".document_type WHERE type = '%s'";
  public static final String getRejectedReasonByCategory =
      "select * from " + rmsSchemaName + ".reason_master rm where category = '%s'";
  public static final String updateValueForKeyInStoreConfig =
      "update "
          + deliveryServiceSchemaName
          + ".store_config sc set value = '%s' where store_id = '%s' and key = '%s'";
  public static final String getQuestionDetailsOfSurvey =
      "select * from "
          + surveySchemaName
          + ".survey_question sq , survey_option so where sq.question_category = '%s' and sq.question_text = '%s' and so.question_id = sq.id";
  public static final String getAnswerRecordFromQuestionId =
      "select entity_id, survey_option_id from "
          + surveySchemaName
          + ".survey_question_answer sqa where entity_id='%s' and question_id = %d";
  public static final String getTotalOrderCountOfRefereesByReferralId =
      "select SUM(CAST(rmp.value as INTEGER)) as ordercount from "
          + referralSchemaName
          + ".referral r inner join "
          + referralSchemaName
          + ".referee_milestone_progress rmp on r.referee_id=rmp.referee_id where r.referral_id='%s'";
  public static final String getRandomReferralCode =
      "select referral_code,user_id from "
          + referralSchemaName
          + ".user_referral_info where user_id= (select referral_id from "
          + referralSchemaName
          + ".referral where referral_state = 'SUCCESS' group by referral_id having count(referee_id)<5 limit 1)";
  public static final String getRiderIdByUserId =
      "select id  from " + rmsSchemaName + ".rider_profile rp where user_id  = '%s'";
  public static final String insuranceDetailsByUserId =
      "SELECT insurance_details  from "
          + rmsSchemaName
          + ".onboarding_rider ord where user_id = '%s'";
  public static final String updateConsignmentCreatedAtTime =
      "update " + deliveryServiceSchemaName + ".consignment set created_at = '%s' where id = '%s'";
  public static final String getEligibleConsignmentDetailsForBulkUpdate =
      "select * from "
          + deliveryServiceSchemaName
          + ".consignment where store_id = '%s' and status in ('%s') and created_at < '%s'";
  public static final String deleteRiderShiftStatusDataForADate =
      "delete from "
          + rmsSchemaName
          + ".rider_shift_status where rider_id ='%s' and  CAST(payouts.rider_shift_status.status_date AS VARCHAR) LIKE '%s";
  public static final String clearRiderShiftStatusData =
      "delete from " + rmsSchemaName + ".rider_shift_status where rider_id='%s'";
  public static final String updateEtaRelatedConfig =
      "update "
          + zeptoBackendSchemaName
          + ".store set order_packing_time_in_secs = %d, packer_assignment_time_in_secs = %d, packer_rider_handshake_time_in_secs = %d, rider_arrival_buffer_in_secs = %d, surge_rider_handshake_time_in_secs = %d where id = '%s'";
  public static final String updateEtaFieldsInStoreMeta =
      "update "
          + zeptoBackendSchemaName
          + ".store_meta set value = '%s' where store_id = '%s' and key = '%s'";
  public static final String updateStandStillFlagForStore =
      "update " + zeptoBackendSchemaName + ".store set stand_still_mode = '%s' where id = '%s'";

  public static final String updateCallMaskingMetaData =
      "update "
          + deliveryServiceSchemaName
          + ".call_masking_mapping set metadata ='%s' where order_id='%s'";
  public static final String getSurgeByName =
      "select * from " + rmsSchemaName + ".surge where name ='%s'";
  public static final String getOrderEarning =
      "select * from " + rmsSchemaName + ".order_earning where rider_id ='%s'";

  public static final String createZebRiderEntry =
      "insert into "
          + zeptoBackendSchemaName
          + ".rider(id, created_on,updated_on,user_id,vendor_rider_id, delivery_vendor, store_id, is_valid, available_on, vehicle_type, contract_type, rider_3pl_vendor_id, covid_vaccination_status)\n"
          + "values('%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, '%s', '%s', '%s', '%s', '%s')";
  public static final String GET_OTP_TOKEN =
      "select token from " + rmsSchemaName + ".otp_token WHERE user_id = (SELECT id FROM \"user\" WHERE mobile_number = '%s') ORDER BY created_on DESC LIMIT 1";
  public static final String getShiftByRiderId =
      "select * from " + rmsSchemaName + ".rider_shift where rider_id='%s'";
  public static final String getWeekOffRiderId =
      "select * from " + rmsSchemaName + ".rider_week_off where rider_id='%s'";
  public static final String getCurrentDayAvailableSlot =
      "select * from "
          + rmsSchemaName
          + ".slot where store_id='%s' and vehicle_group_type ='%s' and end_time>now() and start_time<CURRENT_DATE+1 order by start_time asc";
  public static final String getFutureDateAvailableSlot =
      "select * from "
          + rmsSchemaName
          + ".slot where store_id='%s' and vehicle_group_type ='%s' and start_time>='%s' and start_time<'%s' order by start_time asc";
  public static final String getSlotByGroupId =
      "select * from "
          + rmsSchemaName
          + ".slot where group_id='%s' and vehicle_group_type ='%s' order by start_time desc";
  public static final String getRiderSlotByRiderIdAndSlotId =
      "select * from " + rmsSchemaName + ".rider_slot where rider_id='%s' and slot_id ='%s'";
  public static final String deleteFromRiderSlotHistoryTable =
      "delete from "
          + rmsSchemaName
          + ".rider_slot_history where slot_id in (select id from payouts.slot where store_id='%s')";
  public static final String deleteFromRiderSlotTable =
      "delete from "
          + rmsSchemaName
          + ".rider_slot where slot_id in (select id from payouts.slot where store_id='%s')";
  public static final String deleteFromSlotTable =
      "delete from " + rmsSchemaName + ".slot where store_id='%s'";
  public static final String updateSlotFilledCapacity =
      "update "
          + rmsSchemaName
          + ".slot set filled_capacity ='%s' where group_id='%s' and vehicle_group_type='%s'";

  public static final String updateRiderAsExistingRider =
      "update "
          + rmsSchemaName
          + ".rider_profile_info set value ='%s' where rider_id='%s' and key='EXTRA_DETAILS'";
  public static String getRiderExtraDetailsFromRiderProfileInfo =
      "select * from "
          + rmsSchemaName
          + ".rider_profile_info rpi where key = 'EXTRA_DETAILS' and rider_id ='%s'";

  public static final String updateSlotEndTime =
      "update "
          + rmsSchemaName
          + ".slot set end_time ='%s' where group_id='%s' and vehicle_group_type='%s'";

  public static final String getRateCardInfoByJoiningBonusName =
      "select * from "
          + rmsSchemaName
          + ".ratecard_info where name='%s'";
  public static final String getJoiningBonusRateCardById =
      "select * from "
          + rmsSchemaName
          + ".ratecard where id='%s'";
  public static final String getJoiningBonusRateCardSlabById =
      "select * from "
          + rmsSchemaName
          + ".ratecard_slab where ratecard_id='%s'";
  public static final String getJoiningBonusList =
      "select * from "
          + rmsSchemaName
          + ".ratecard_info where created_at > now() - interval '45' day order by created_at desc";
  public static final String updateRateCardInfoEndTime =
      "update "
          + rmsSchemaName
          + ".ratecard_info set end_time='%s' where name='%s'";
  public static final String deleteFromRateCardInfoTable =
      "delete from "
          + rmsSchemaName
          + ".ratecard_info where cohort::jsonb -> 'storeId' @> '[\"%s\"]'::jsonb";
  public static final String deleteFromRateCardTable =
      "delete from "
          + rmsSchemaName
          + ".ratecard where id in (select id from  "+rmsSchemaName+".ratecard_info where cohort::jsonb -> 'storeId' @> '[\"%s\"]'::jsonb)";
  public static final String deleteFromRateCardSlabTable =
      "delete from "
          + rmsSchemaName
          + ".ratecard_slab where ratecard_id in (select id from  "+rmsSchemaName+".ratecard_info where cohort::jsonb -> 'storeId' @> '[\"%s\"]'::jsonb)";
  public static final String getRiderRateCardByRiderId =
      "select * from "
          + rmsSchemaName
          + ".rider_ratecard where rider_id='%s'";
  public static final String updateRiderRateCardStartTime =
      "update "
          + rmsSchemaName
          + ".rider_ratecard set associated_since ='%s' where rider_id='%s'";
}
