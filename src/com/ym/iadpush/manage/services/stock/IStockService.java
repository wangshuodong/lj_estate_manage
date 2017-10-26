/**
 * 
 */
package com.ym.iadpush.manage.services.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.AdvancePaymentRecord;
import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.Building;
import com.ym.iadpush.manage.entity.EnterBill;
import com.ym.iadpush.manage.entity.ExchangEapplyRecord;
import com.ym.iadpush.manage.entity.Housing;
import com.ym.iadpush.manage.entity.Location;
import com.ym.iadpush.manage.entity.OutBill;
import com.ym.iadpush.manage.entity.Proprietor;
import com.ym.iadpush.manage.entity.RoomInfo;
import com.ym.iadpush.manage.entity.StockDetail;
import com.ym.iadpush.manage.entity.StockMonth;

public interface IStockService {
    List<BillAccount> getBillAccountsBusiness(Map<String, Object> paramMap);
    //删除物业
    int deleteDepartment(int departmentId);
    //删除业主
    int deleteProprietorById(int id);
    //删除房屋
    Integer deleteRoom(int roomId);
    //删除单元
    Integer deleteLocation(int locationId);
    //删除楼栋
    Integer deleteBuilding(int buildingId);
    //删除小区
    Integer deleteHousing(int housingId);
    Integer addSmsCount(Map<String, Object> paramMap);
    Integer subtractSmsCount(int housingId);
    int getBuildingsCount(Map<String,Object> paramMap);
    int getLocationsCount(Map<String,Object> paramMap);
    Integer saveProprietor(Proprietor proprietor, int id);
    
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    
    List<String> getBillType();
    List<Housing> getAllHousings();

    BillAccount getBillAccountByBill_entry_id(String bill_entry_id);

    Integer updateBillAccount(BillAccount billAccount, String bill_entry_id);

    Proprietor getProprietorById(Integer id);

    Integer insertBillAccount(BillAccount billAccount);

    List<BillAccount> getBillAccounts(Map<String, Object> paramMap);

    BillAccount getBillAccountById(Integer id);

    List<Location> getLocationsByBuildingId(int buildingId);

    List<Building> getBuildingsByHouseId(int houseId);

    Integer updateRoomInfo(String room_id, String out_room_id);

    RoomInfo getRoomInfoById(Integer id);

    Location getLocationById(Integer id);

    Building getBuildingById(Integer id);

    Integer insertProprietor(Proprietor proprietor);

    List<Proprietor> getProprietors(Map<String, Object> paramMap);

    Integer insertRoomInfo(RoomInfo roomInfo);

    List<RoomInfo> getRoomInfos(Map<String, Object> paramMap);

    Integer updateHousing(Housing housing, Integer housingId);

    Housing getHousingById(Integer id);

    Integer insertBuilding(Building building);

    Integer insertLocation(com.ym.iadpush.manage.entity.Location location);

    List<com.ym.iadpush.manage.entity.Location> getLocations(Map<String, Object> paramMap);

    List<Building> getBuildings(Map<String, Object> paramMap);

    Integer insertHousing(Housing housing);

    List<Housing> getHousings(Map<String, Object> paramMap);

    Integer insertExchangEapplyRecord(ExchangEapplyRecord exchangEapplyRecord);

    Integer insertEnterBill(EnterBill enterBill);

    EnterBill getEnterBillById(Integer id);

    Integer getAllEnterBillsCount(Map<String, Object> paramMap);

    List<EnterBill> getAllEnterBills(Map<String, Object> paramMap);

    List<EnterBill> getAllNoauditingEnterBills(Map<String, Object> paramMap);

    Integer auditingEnterBill(EnterBill enterBill, Integer id);

    Integer insertOutBill(OutBill outBill);

    OutBill getOutBillById(Integer id);

    List<OutBill> getAllOutBills(Map<String, Object> paramMap);

    Integer getAllOutBillsCount(Map<String, Object> paramMap);

    String getNextEnterBillCode(String pre);

    EnterBill getMaxEnterBillCode(String date);

    String getNextOutBillCode(String pre);

    OutBill getMaxOutBillCode(String date);

    StockMonth getStockMonth(Integer productId, Integer warehouseId, String month);

    StockDetail getStockDetail(Integer productId, Integer warehouseId, Date addate);

    Integer insertStockMonth(StockMonth stockMonth);

    Integer insertStockDetail(StockDetail stockDetail);

    Integer updateStockMonth(StockMonth stockMonth, Integer id);

    Integer updateStockDetail(StockDetail stockDetail, Integer id);

    StockDetail getLatelyStockDetail(Integer productId, Integer warehouseId, Date addate);

    List<StockDetail> queryStockDetail(Map<String, Object> paramMap);

    List<StockMonth> queryStockMonth(Map<String, Object> paramMap);

    Integer queryStockMonthCount(Map<String, Object> paramMap);

    Integer queryStockDetailCount(Map<String, Object> paramMap);

    QueryResult getAllEnterBillsCollect(Map<String, Object> paramMap);

    QueryResult getAllOutBillsCollect(Map<String, Object> paramMap);

    QueryResult queryStockMonthCollect(Map<String, Object> paramMap);

    QueryResult queryStockDetailCollect(Map<String, Object> paramMap);

    Integer updateEnterBill(EnterBill enterBill, Integer id);

    Integer insertAdvancePaymentRecord(AdvancePaymentRecord advancePaymentRecord);

    String getNextAdvancePaymentRecordCode();

    AdvancePaymentRecord getMaxAdvancePaymentRecordCode(String date);

    List<AdvancePaymentRecord> getAllAdvancePaymentRecords(Map<String, Object> paramMap);

    Integer getAllAdvancePaymentRecordsCount(Map<String, Object> paramMap);

    QueryResult getAllAdvancePaymentRecordsCollect(Map<String, Object> paramMap);

    String getNextExchangEapplyRecordCode(String pre);

    ExchangEapplyRecord getMaxExchangEapplyRecordCode(String date);

    QueryResult getAllExchangEapplyRecordCollect(Map<String, Object> paramMap);

    List<ExchangEapplyRecord> getAllExchangEapplyRecord(Map<String, Object> paramMap);

    Integer getAllExchangEapplyRecordCount(Map<String, Object> paramMap);

    ExchangEapplyRecord getExchangEapplyRecordById(Integer id);

    Integer updateExchangEapplyRecord(ExchangEapplyRecord exchangEapplyRecord, Integer id);

    RoomInfo getMaxOut_room_id(String date);

    String getNextOut_room_id(String pre);

    BillAccount getMaxBill_entry_id(String date);

    String getNextBillAccountBill_entry_id(String pre);

    Integer saveRoomInfo(RoomInfo roomInfo, int id);

    /**
     * @Author lixingbiao 2017-6-1 下午9:39:22
     * @param paramMap
     * @return
     */
    List<BillAccount> getBillAccountsBySendStatus(Map<String, Object> paramMap);

    /**
     * @Author lixingbiao 2017-6-9 下午3:42:16
     * @param out_room_id
     * @return TODO
     */
    Proprietor getProprietorByOut_room_id(String out_room_id);

    /**
     * @Author lixingbiao 2017-6-9 下午10:31:36
     * @param paramMap
     * @return
     */
    int getBillAccountsCount(Map<String, Object> paramMap);

    /**
     * @Author lixingbiao 2017-6-13 下午1:55:09
     * @param proprietorId
     * @param month TODO
     */
    BillAccount getPrecedingMonthBillMoney(int proprietorId, String month);

    /**
     * @Author lixingbiao 2017-6-20 上午10:42:07
     * @param houseingName
     */
    Housing getHousingByName(String houseingName);

    /**
     * @Author lixingbiao 2017-6-20 上午10:42:12
     * @param buildingName
     * @param houseId TODO
     */
    Building getBuildingByName(String buildingName, int houseId);

    /**
     * @Author lixingbiao 2017-6-20 上午10:43:22
     * @param locationName
     * @param buildingId TODO
     */
    Location getLocationByName(String locationName, int buildingId);

    /**
     * @Author lixingbiao 2017-6-20 上午11:25:36
     * @param out_room_id
     */
    RoomInfo getRoomInfoByOut_room_id(String out_room_id);

    /**
     * @Author lixingbiao 2017-6-26 下午12:39:37
     * @param paramMap
     * @return
     */
    int getRoomInfosCount(Map<String, Object> paramMap);

    /**
     * @Author lixingbiao 2017-6-26 下午4:15:18
     * @param paramMap
     * @return
     */
    int getProprietorsCount(Map<String, Object> paramMap);

    /**
     * @Author lixingbiao 2017-7-4 下午9:10:11
     * @param paramMap
     * @return
     */
    QueryResult getBillAccountCollect(Map<String, Object> paramMap);
    
    int getHousingsCount(Map<String, Object> paramMap);
}
