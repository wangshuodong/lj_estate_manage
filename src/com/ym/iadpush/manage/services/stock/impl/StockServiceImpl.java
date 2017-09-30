/**
 * 
 */
package com.ym.iadpush.manage.services.stock.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi2.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.AlipayUtil;
import com.ym.common.utils.DateUtils;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.common.utils.DecimalFormatUtils;
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
import com.ym.iadpush.manage.mapper.AdvancePaymentRecordMapper;
import com.ym.iadpush.manage.mapper.BillAccountMapper;
import com.ym.iadpush.manage.mapper.BuildingMapper;
import com.ym.iadpush.manage.mapper.DepartmentMapper;
import com.ym.iadpush.manage.mapper.EnterBillMapper;
import com.ym.iadpush.manage.mapper.ExchangEapplyRecordMapper;
import com.ym.iadpush.manage.mapper.HousingMapper;
import com.ym.iadpush.manage.mapper.LocationMapper;
import com.ym.iadpush.manage.mapper.OutBillMapper;
import com.ym.iadpush.manage.mapper.ProprietorMapper;
import com.ym.iadpush.manage.mapper.RoomInfoMapper;
import com.ym.iadpush.manage.mapper.StockDetailMapper;
import com.ym.iadpush.manage.mapper.StockMonthMapper;
import com.ym.iadpush.manage.services.stock.IStockService;

@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private OutBillMapper outBillMapper;

    @Autowired
    private EnterBillMapper enterBillMapper;

    @Autowired
    private StockDetailMapper stockDetailMapper;

    @Autowired
    private StockMonthMapper stockMonthMapper;

    @Autowired
    private AdvancePaymentRecordMapper advancePaymentRecordMapper;

    @Autowired
    private ExchangEapplyRecordMapper exchangEapplyRecordMapper;

    @Autowired
    private HousingMapper housingMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private ProprietorMapper proprietorMapper;

    @Autowired
    private BillAccountMapper billAccountMapper;

    @Override
    public Integer insertEnterBill(EnterBill enterBill) {
        System.out.println("李杨是哈皮");
        return enterBillMapper.insertEnterBill(enterBill);
    }

    @Override
    public EnterBill getEnterBillById(Integer id) {
        return enterBillMapper.getEnterBillById(id);
    }

    @Override
    public List<EnterBill> getAllEnterBills(Map<String, Object> paramMap) {
        System.out.println("李杨是哈皮");
        return enterBillMapper.getAllEnterBills(paramMap);
    }

    @Override
    public Integer insertOutBill(OutBill outBill) {
        System.out.println("李杨是哈皮");
        return outBillMapper.insertOutBill(outBill);
    }

    @Override
    public OutBill getOutBillById(Integer id) {
        return outBillMapper.getOutBillById(id);
    }

    @Override
    public List<OutBill> getAllOutBills(Map<String, Object> paramMap) {
        return outBillMapper.getAllOutBills(paramMap);
    }

    @Override
    public String getNextEnterBillCode(String pre) {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        EnterBill enterBill = getMaxEnterBillCode(pre + "" + date);

        String billCode = "";

        if (enterBill != null) {
            billCode = enterBill.getCode();
        }

        if (billCode != null && !billCode.equals("")) {
            int seq = Integer.parseInt(billCode.substring(billCode.length() - 4, billCode.length())) + 1;
            date += DecimalFormatUtils.format("0000", seq);
        } else {
            date += "0001";
        }

        return pre + "" + date;
    }

    @Override
    public EnterBill getMaxEnterBillCode(String date) {
        EnterBill enterBill = enterBillMapper.getMaxEnterBillCode(date);
        return enterBill;
    }

    @Override
    public String getNextOutBillCode(String pre) {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        OutBill outBill = getMaxOutBillCode(pre + "" + date);

        String billCode = "";

        if (outBill != null) {
            billCode = outBill.getCode();
        }

        if (billCode != null && !billCode.equals("")) {
            int seq = Integer.parseInt(billCode.substring(billCode.length() - 4, billCode.length())) + 1;
            date += DecimalFormatUtils.format("0000", seq);
        } else {
            date += "0001";
        }

        return pre + "" + date;
    }

    @Override
    public OutBill getMaxOutBillCode(String date) {
        OutBill outBill = outBillMapper.getMaxOutBillCode(date);
        return outBill;
    }

    @Override
    public StockMonth getStockMonth(Integer productId, Integer warehouseId, String month) {

        StockMonth stockMonth = stockMonthMapper.getStockMonth(productId, warehouseId, month);

        if (stockMonth == null) {
            stockMonth = stockMonthMapper.getStockMonthByProductAndWarehouse(productId, warehouseId);
        }

        return stockMonth;
    }

    @Override
    public StockDetail getStockDetail(Integer productId, Integer warehouseId, Date addate) {
        return stockDetailMapper.getStockDetail(productId, warehouseId, addate);
    }

    @Override
    public Integer insertStockMonth(StockMonth stockMonth) {
        return stockMonthMapper.insertStockMonth(stockMonth);
    }

    @Override
    public Integer insertStockDetail(StockDetail stockDetail) {
        return stockDetailMapper.insertStockDetail(stockDetail);
    }

    @Override
    public Integer updateStockMonth(StockMonth stockMonth, Integer id) {
        return stockMonthMapper.updateStockMonth(stockMonth, id);
    }

    @Override
    public Integer updateStockDetail(StockDetail stockDetail, Integer id) {
        return stockDetailMapper.updateStockDetail(stockDetail, id);
    }

    @Override
    public StockDetail getLatelyStockDetail(Integer productId, Integer warehouseId, Date addate) {
        return stockDetailMapper.getLatelyStockDetail(productId, warehouseId, addate);
    }

    @Override
    public List<StockDetail> queryStockDetail(Map<String, Object> paramMap) {
        return stockDetailMapper.queryStockDetailList(paramMap);
    }

    @Override
    public List<StockMonth> queryStockMonth(Map<String, Object> paramMap) {
        return stockMonthMapper.queryStockMonthList(paramMap);
    }

    @Override
    public Integer queryStockMonthCount(Map<String, Object> paramMap) {
        return stockMonthMapper.queryStockMonthCount(paramMap);
    }

    @Override
    public Integer queryStockDetailCount(Map<String, Object> paramMap) {
        return stockDetailMapper.queryStockDetailCount(paramMap);
    }

    @Override
    public Integer getAllEnterBillsCount(Map<String, Object> paramMap) {
        return enterBillMapper.getAllEnterBillsCount(paramMap);
    }

    @Override
    public Integer getAllOutBillsCount(Map<String, Object> paramMap) {
        return outBillMapper.getAllOutBillsCount(paramMap);
    }

    @Override
    public QueryResult getAllEnterBillsCollect(Map<String, Object> paramMap) {

        QueryResult result = new QueryResult();

        result.setCollect(enterBillMapper.getAllEnterBillsCollect(paramMap));

        return result;

    }

    @Override
    public QueryResult getAllOutBillsCollect(Map<String, Object> paramMap) {

        QueryResult result = new QueryResult();

        result.setCollect(outBillMapper.getAllOutBillsCollect(paramMap));

        return result;

    }

    @Override
    public QueryResult queryStockMonthCollect(Map<String, Object> paramMap) {
        QueryResult result = new QueryResult();

        result.setCollect(stockMonthMapper.queryStockMonthCollect(paramMap));

        return result;
    }

    @Override
    public QueryResult queryStockDetailCollect(Map<String, Object> paramMap) {
        QueryResult result = new QueryResult();

        result.setCollect(stockDetailMapper.queryStockDetailCollect(paramMap));

        return result;
    }

    @Override
    public List<EnterBill> getAllNoauditingEnterBills(Map<String, Object> paramMap) {
        return enterBillMapper.getAllNoauditingEnterBills(paramMap);
    }

    @Override
    public Integer auditingEnterBill(EnterBill enterBill, Integer id) {
        return enterBillMapper.auditingEnterBill(enterBill, id);
    }

    @Override
    public Integer updateEnterBill(EnterBill enterBill, Integer id) {
        return enterBillMapper.updateEnterBill(enterBill, id);
    }

    @Override
    public Integer insertAdvancePaymentRecord(AdvancePaymentRecord advancePaymentRecord) {
        return advancePaymentRecordMapper.insertAdvancePaymentRecord(advancePaymentRecord);
    }

    @Override
    public String getNextAdvancePaymentRecordCode() {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        AdvancePaymentRecord advancePaymentRecord = getMaxAdvancePaymentRecordCode(date);

        String billCode = "";

        if (advancePaymentRecord != null) {
            billCode = advancePaymentRecord.getCode();
        }

        if (billCode != null && !billCode.equals("")) {
            int seq = Integer.parseInt(billCode.substring(billCode.length() - 4, billCode.length())) + 1;
            date += DecimalFormatUtils.format("0000", seq);
        } else {
            date += "0001";
        }

        return date;
    }

    @Override
    public AdvancePaymentRecord getMaxAdvancePaymentRecordCode(String date) {
        return advancePaymentRecordMapper.getMaxAdvancePaymentRecordCode(date);
    }

    @Override
    public List<AdvancePaymentRecord> getAllAdvancePaymentRecords(Map<String, Object> paramMap) {
        return advancePaymentRecordMapper.getAllAdvancePaymentRecords(paramMap);
    }

    @Override
    public Integer getAllAdvancePaymentRecordsCount(Map<String, Object> paramMap) {
        return advancePaymentRecordMapper.getAllAdvancePaymentRecordsCount(paramMap);
    }

    @Override
    public QueryResult getAllAdvancePaymentRecordsCollect(Map<String, Object> paramMap) {

        QueryResult result = new QueryResult();

        result.setCollect(advancePaymentRecordMapper.getAllAdvancePaymentRecordsCollect(paramMap));

        return result;
    }

    @Override
    public Integer insertExchangEapplyRecord(ExchangEapplyRecord exchangEapplyRecord) {
        return exchangEapplyRecordMapper.insertExchangEapplyRecord(exchangEapplyRecord);
    }

    @Override
    public String getNextExchangEapplyRecordCode(String pre) {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        ExchangEapplyRecord exchangEapplyRecord = getMaxExchangEapplyRecordCode(pre + date);

        String billCode = "";

        if (exchangEapplyRecord != null) {
            billCode = exchangEapplyRecord.getCode();
        }

        if (billCode != null && !billCode.equals("")) {
            int seq = Integer.parseInt(billCode.substring(billCode.length() - 4, billCode.length())) + 1;
            date += DecimalFormatUtils.format("0000", seq);
        } else {
            date += "0001";
        }

        return pre + date;
    }

    @Override
    public ExchangEapplyRecord getMaxExchangEapplyRecordCode(String date) {
        return exchangEapplyRecordMapper.getMaxExchangEapplyRecordCode(date);
    }

    @Override
    public QueryResult getAllExchangEapplyRecordCollect(Map<String, Object> paramMap) {

        QueryResult result = new QueryResult();

        result.setCollect(exchangEapplyRecordMapper.getAllExchangEapplyRecordCollect(paramMap));

        return result;

    }

    @Override
    public List<ExchangEapplyRecord> getAllExchangEapplyRecord(Map<String, Object> paramMap) {
        return exchangEapplyRecordMapper.getAllExchangEapplyRecord(paramMap);
    }

    @Override
    public Integer getAllExchangEapplyRecordCount(Map<String, Object> paramMap) {
        return exchangEapplyRecordMapper.getAllExchangEapplyRecordCount(paramMap);
    }

    @Override
    public ExchangEapplyRecord getExchangEapplyRecordById(Integer id) {
        return exchangEapplyRecordMapper.getExchangEapplyRecordById(id);
    }

    @Override
    public Integer updateExchangEapplyRecord(ExchangEapplyRecord exchangEapplyRecord, Integer id) {
        return exchangEapplyRecordMapper.updateExchangEapplyRecord(exchangEapplyRecord, id);
    }

    @Override
    public Integer insertHousing(Housing housing) {
        return housingMapper.insertHousing(housing);
    }

    @Override
    public List<Housing> getHousings(Map<String, Object> paramMap) {
        return housingMapper.getHousings(paramMap);
    }

    @Override
    public Integer insertBuilding(Building building) {
        return buildingMapper.insertBuilding(building);
    }

    @Override
    public List<Building> getBuildings(Map<String, Object> paramMap) {
        return buildingMapper.getBuildings(paramMap);
    }

    @Override
    public Housing getHousingById(Integer id) {
        return housingMapper.getHousingById(id);
    }

    @Override
    public Integer updateHousing(Housing housing, Integer housingId) {
        return housingMapper.updateHousing(housing, housingId);
    }

    @Override
    public Integer insertLocation(com.ym.iadpush.manage.entity.Location location) {
        return locationMapper.insertLocation(location);
    }

    @Override
    public List<com.ym.iadpush.manage.entity.Location> getLocations(Map<String, Object> paramMap) {
        return locationMapper.getLocations(paramMap);
    }

    @Override
    public Integer insertRoomInfo(RoomInfo roomInfo) {
        return roomInfoMapper.insertRoomInfo(roomInfo);
    }

    @Override
    public List<RoomInfo> getRoomInfos(Map<String, Object> paramMap) {
        return roomInfoMapper.getRoomInfos(paramMap);
    }

    @Override
    public Integer insertProprietor(Proprietor proprietor) {
        return proprietorMapper.insertProprietor(proprietor);
    }

    @Override
    public List<Proprietor> getProprietors(Map<String, Object> paramMap) {
        return proprietorMapper.getProprietors(paramMap);
    }

    @Override
    public Location getLocationById(Integer id) {
        return locationMapper.getLocationById(id);
    }

    @Override
    public Building getBuildingById(Integer id) {
        return buildingMapper.getBuildingById(id);
    }

    @Override
    public RoomInfo getRoomInfoById(Integer id) {
        return roomInfoMapper.getRoomInfoById(id);
    }

    @Override
    public RoomInfo getMaxOut_room_id(String date) {
        return roomInfoMapper.getMaxOut_room_id(date);
    }

    @Override
    public String getNextOut_room_id(String pre) {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        RoomInfo roomInfo = getMaxOut_room_id(pre + date);

        String out_room_id = "";

        if (roomInfo != null) {
            out_room_id = roomInfo.getOut_room_id();
        }

        if (out_room_id != null && !out_room_id.equals("")) {
            int seq = Integer.parseInt(out_room_id.substring(out_room_id.length() - 4, out_room_id.length())) + 1;
            date += DecimalFormatUtils.format("00000", seq);
        } else {
            date += "00001";
        }

        return pre + date;
    }

    @Override
    public Integer updateRoomInfo(String room_id, String out_room_id) {
        return roomInfoMapper.updateRoomInfo(room_id, out_room_id);
    }

    @Override
    public List<Building> getBuildingsByHouseId(int houseId) {
        return buildingMapper.getBuildingsByHouseId(houseId);
    }

    @Override
    public Integer saveRoomInfo(RoomInfo roomInfo, int id) {
        return roomInfoMapper.saveRoomInfo(roomInfo, id);
    }

    @Override
    public List<Location> getLocationsByBuildingId(int buildingId) {
        return locationMapper.getLocationsByBuildingId(buildingId);
    }

    @Override
    public BillAccount getMaxBill_entry_id(String date) {
        return billAccountMapper.getMaxBill_entry_id(date);
    }

    @Override
    public String getNextBillAccountBill_entry_id(String pre) {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        BillAccount billAccount = getMaxBill_entry_id(pre + date);

        String bill_entry_id = "";

        if (billAccount != null) {
            bill_entry_id = billAccount.getBill_entry_id();
        }

        if (bill_entry_id != null && !bill_entry_id.equals("")) {
            int seq = Integer.parseInt(bill_entry_id.substring(bill_entry_id.length() - 4, bill_entry_id.length())) + 1;
            date += DecimalFormatUtils.format("0000000", seq);
        } else {
            date += "0000001";
        }

        return pre + date;
    }

    @Override
    public Integer insertBillAccount(BillAccount billAccount) {
        return billAccountMapper.insertBillAccount(billAccount);
    }

    @Override
    public List<BillAccount> getBillAccounts(Map<String, Object> paramMap) {
        return billAccountMapper.getBillAccounts(paramMap);
    }

    @Override
    public BillAccount getBillAccountById(Integer id) {
        return billAccountMapper.getBillAccountById(id);
    }

    @Override
    public Proprietor getProprietorById(Integer id) {
        return proprietorMapper.getProprietorById(id);
    }

    @Override
    public Integer updateBillAccount(BillAccount billAccount, String bill_entry_id) {
        return billAccountMapper.updateBillAccount(billAccount, bill_entry_id);
    }

    @Override
    public BillAccount getBillAccountByBill_entry_id(String bill_entry_id) {
        return billAccountMapper.getBillAccountByBill_entry_id(bill_entry_id);
    }

    @Override
    public List<BillAccount> getBillAccountsBySendStatus(Map<String, Object> paramMap) {
        return billAccountMapper.getBillAccountsBySendStatus(paramMap);
    }

    @Override
    public List<Housing> getAllHousings() {
        return housingMapper.getAllHousings();
    }

    @Override
    public Proprietor getProprietorByOut_room_id(String out_room_id) {
        return proprietorMapper.getProprietorByOut_room_id(out_room_id);
    }

    @Override
    public int getBillAccountsCount(Map<String, Object> paramMap) {
        return billAccountMapper.getBillAccountsCount(paramMap);
    }

    @Override
    public BillAccount getPrecedingMonthBillMoney(int proprietorId, String month) {
        return null;
    }

    @Override
    public Housing getHousingByName(String houseingName) {
        return housingMapper.getHousingByName(houseingName);
    }

    @Override
    public Building getBuildingByName(String buildingName, int houseId) {
        return buildingMapper.getBuildingByName(buildingName, houseId);
    }

    @Override
    public Location getLocationByName(String locationName, int buildingId) {
        return locationMapper.getLocationByName(locationName, buildingId);
    }

    @Override
    public RoomInfo getRoomInfoByOut_room_id(String out_room_id) {
        return roomInfoMapper.getRoomInfoByOut_room_id(out_room_id);
    }

    @Override
    public Integer saveProprietor(Proprietor proprietor, int id) {
        return proprietorMapper.saveProprietor(proprietor, id);
    }

    @Override
    public int getRoomInfosCount(Map<String, Object> paramMap) {
        return roomInfoMapper.getRoomInfosCount(paramMap);
    }

    @Override
    public int getProprietorsCount(Map<String, Object> paramMap) {
        return proprietorMapper.getProprietorsCount(paramMap);
    }

    @Override
    public int deleteProprietorById(int id) {
        return proprietorMapper.deleteProprietorById(id);
    }

    @Override
    public QueryResult getBillAccountCollect(Map<String, Object> paramMap) {

        QueryResult result = new QueryResult();

        result.setCollect(billAccountMapper.getBillAccountCollect(paramMap));

        return result;
    }
    @Override
    public int getHousingsCount(Map<String, Object> paramMap) {
        return housingMapper.getHousingsCount(paramMap);
    }

    @Override
    public int getBuildingsCount(Map<String, Object> paramMap) {
        return buildingMapper.getBuildingsCount(paramMap);
    }

    @Override
    public int getLocationsCount(Map<String, Object> paramMap) {
        return locationMapper.getLocationsCount(paramMap);
    }

    @Override
    public Integer subtractSmsCount(int housingId) {
        return housingMapper.subtractSmsCount(housingId);
    }

    @Override
    public Integer addSmsCount(Map<String, Object> paramMap) {
        return housingMapper.addSmsCount(paramMap);
    }

    @Override
    public Integer deleteHousing(int housingId) {
        return housingMapper.deleteHousing(housingId);
    }

    @Override
    public List<String> getBillType() {
        return billAccountMapper.getBillType();
    }
    //修改物业
    @Override
    public Integer changeDepartment(Map<String, Object> paramMap) {
        housingMapper.changeDepartment(paramMap);
        buildingMapper.changeDepartment(paramMap);
        locationMapper.changeDepartment(paramMap);
        roomInfoMapper.changeDepartment(paramMap);
        proprietorMapper.changeDepartment(paramMap);
        billAccountMapper.changeDepartment(paramMap);
        return null;
    }

    @Override
    public Integer deleteRoom(int roomId) {
        return roomInfoMapper.deleteRoom(roomId);
    }
    
    @Override
    public Integer deleteLocation(int locationId) {
        return locationMapper.deleteLocation(locationId);
    }

    @Override
    public Integer deleteBuilding(int buildingId) {
        return buildingMapper.deleteBuilding(buildingId);
    }

    @Override
    public int deleteDepartment(int departmentId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("departmentId", departmentId);
        params.put("currPage", 0);
        params.put("pageSize", 9999999);
        List<Housing> housingList = housingMapper.getHousings(params);
        housingMapper.deleteHousingByParams(params);
        buildingMapper.deleteBuildingByParams(params);
        locationMapper.deleteLocationByParams(params);
        roomInfoMapper.deleteRoomByParams(params);
        proprietorMapper.deleteProprietorByParams(params);
        billAccountMapper.deleteBillAccountByParams(params);
        departmentMapper.delById(departmentId);
        System.out.println("小区列表:"+housingList.size());
        for(Housing housing:housingList){
            //支付宝下架房屋，支付宝会自动下架对应房屋的账单信息
            List<RoomInfo> roomList = roomInfoMapper.getRoomInfos(params);
            List<String> out_room_id_set = new ArrayList<String>();
            for(int i=0;i<roomList.size();i++){
                out_room_id_set.add(roomList.get(i).getOut_room_id());
                //每次下架200间
                if((i+1)%200==0){
                    try {
//                        AlipayUtil.deleteRoomInfo(housing.getCommunity_id(), out_room_id_set);
                        System.out.println("下架"+i);
                    } catch (Exception e) {
                        System.out.println("下架失败:"+e.getMessage());
                        e.printStackTrace();
                    }
                    out_room_id_set = new ArrayList<String>();
                }
            }
            //下架剩余房屋
            if(out_room_id_set.size()>0){
                try {
//                    AlipayUtil.deleteRoomInfo(housing.getCommunity_id(), out_room_id_set);
                    System.out.println("下架"+out_room_id_set.size());
                } catch (Exception e) {
                    System.out.println("下架失败:"+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

}
