/**
 * 
 */
package com.ym.iadpush.manage.action.stock;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoCplifeBillDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeBillModifyRequest;
import com.alipay.api.request.AlipayEcoCplifeCommunityModifyRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayEcoCplifeBillDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeBillModifyResponse;
import com.alipay.api.response.AlipayEcoCplifeCommunityModifyResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.ym.common.base.BaseAction;
import com.ym.common.base.Pager;
import com.ym.common.utils.AlipayUtil;
import com.ym.common.utils.DateUtils;
import com.ym.common.utils.JuheUtile;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.common.utils.ExcelRead;
import com.ym.iadpush.common.utils.ExcelUtil;
import com.ym.iadpush.common.utils.PrintMessage;
import com.ym.iadpush.manage.entity.AdvancePaymentRecord;
import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.Building;
import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.CurrentAccountCollect;
import com.ym.iadpush.manage.entity.CurrentAccountDetail;
import com.ym.iadpush.manage.entity.Department;
import com.ym.iadpush.manage.entity.EnterBill;
import com.ym.iadpush.manage.entity.ExchangEapplyRecord;
import com.ym.iadpush.manage.entity.FaceToFacePay;
import com.ym.iadpush.manage.entity.Housing;
import com.ym.iadpush.manage.entity.Location;
import com.ym.iadpush.manage.entity.OutBill;
import com.ym.iadpush.manage.entity.PrintInfo;
import com.ym.iadpush.manage.entity.Product;
import com.ym.iadpush.manage.entity.Proprietor;
import com.ym.iadpush.manage.entity.RoomInfo;
import com.ym.iadpush.manage.entity.StockDetail;
import com.ym.iadpush.manage.entity.StockMonth;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.Warehouse;
import com.ym.iadpush.manage.entity.common.CommonParm;
import com.ym.iadpush.manage.services.balance.IBalanceService;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;
import com.ym.iadpush.manage.services.department.IDepartmentService;
import com.ym.iadpush.manage.services.f2fPay.FaceToFacePayService;
import com.ym.iadpush.manage.services.print.PrintInfoService;
import com.ym.iadpush.manage.services.product.IProductService;
import com.ym.iadpush.manage.services.stock.IStockService;
import com.ym.iadpush.manage.services.warehouse.IWarehouseService;

@Controller
public class StockAction extends BaseAction {
    private @Autowired
    IStockService stockServiceImpl;

    private @Autowired
    IDepartmentService departmentService;

    private @Autowired
    IWarehouseService warehouseServiceImpl;

    @Autowired
    private IProductService iProductService;

    private @Autowired
    ICompanyService companyServiceImpl;

    private @Autowired
    IBalanceService balanceService;

    private @Autowired
    FaceToFacePayService facePayService;
    
    @Autowired
    private PrintInfoService printInfoService;

    private String packageName = "stock";
    
    // 删除物业
    @ResponseBody
    @RequestMapping(value = "/deleteDepartment.html", method = RequestMethod.POST)
    public ModelMap deleteDepartment(HttpServletRequest request, ModelMap model) {
        String departmentId = request.getParameter("id");
        stockServiceImpl.deleteDepartment(Integer.parseInt(departmentId));
        model.put("msg", "删除完成");
        model.put("state", 1);
        return model;
    }

    // 删除业主
    @ResponseBody
    @RequestMapping(value = "/deleteProprietor.html", method = RequestMethod.POST)
    public ModelMap deleteProprietor(HttpServletRequest request, ModelMap model) {
        String proprietorId = request.getParameter("proprietorId");
        stockServiceImpl.deleteProprietorById(Integer.parseInt(proprietorId));
        model.put("msg", "删除完成");
        model.put("state", 1);
        return model;
    }

    // 删除房屋
    @ResponseBody
    @RequestMapping(value = "/deleteRoom.html", method = RequestMethod.POST)
    public ModelMap deleteRoom(HttpServletRequest request, ModelMap model) {
        String roomId = request.getParameter("roomId");
        RoomInfo room = stockServiceImpl.getRoomInfoById(Integer.parseInt(roomId));
        Housing housing = stockServiceImpl.getHousingById(room.getHousingId());
        List<String> outRoomId = new ArrayList<String>();
        outRoomId.add(room.getOut_room_id());
        try {
            AlipayUtil.deleteRoomInfo(housing.getCommunity_id(), outRoomId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        stockServiceImpl.deleteRoom(Integer.parseInt(roomId));
        model.put("msg", "删除完成");
        model.put("state", 1);
        return model;
    }

    // 删除单元
    @ResponseBody
    @RequestMapping(value = "/deleteLocation.html", method = RequestMethod.POST)
    public ModelMap deleteLocation(HttpServletRequest request, ModelMap model) {
        String locationId = request.getParameter("locationId");
        stockServiceImpl.deleteLocation(Integer.parseInt(locationId));
        model.put("msg", "删除完成");
        model.put("state", 1);
        return model;
    }

    // 删除楼栋
    @ResponseBody
    @RequestMapping(value = "/deleteBuilding.html", method = RequestMethod.POST)
    public ModelMap deleteBuilding(HttpServletRequest request, ModelMap model) {
        String buildingId = request.getParameter("buildingId");
        stockServiceImpl.deleteBuilding(Integer.parseInt(buildingId));
        model.put("msg", "删除完成");
        model.put("state", 1);
        return model;
    }

    // 修改小区物业
    @ResponseBody
    @RequestMapping(value = "/changeDepartment.html", method = RequestMethod.POST)
    public ModelMap changeDepartment(HttpServletRequest request, ModelMap model) {
        int housingId = Integer.parseInt(request.getParameter("housingId"));
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        String departmentCode = request.getParameter("departmentCode");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("housingId", housingId);
        paramMap.put("departmentId", departmentId);
        paramMap.put("departmentCode", departmentCode);
        stockServiceImpl.changeDepartment(paramMap);
        model.put("msg", "修改成功!");
        return model;
    }

    /**
     * 根据查询条件导出账单
     * 
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportBillAccount.html", method = RequestMethod.POST)
    public ModelMap exportBillAccount(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();
        String proprietorName = request.getParameter("proprietorName");
        String sendStatus = request.getParameter("sendStatus");
        String payStatus = request.getParameter("payStatus");
        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String locationId = request.getParameter("locationId");
        String proprietorId = request.getParameter("proprietorId");
        String costType = request.getParameter("costType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startPaymentDate = request.getParameter("startPaymentDate");
        String endPaymentDate = request.getParameter("endPaymentDate");
        String roomId = request.getParameter("roomId");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("currPage", 0);
        paramMap.put("pageSize", 999999);

        String departmentCode = user.getDepartmentCode();

        paramMap.put("departmentCode", departmentCode);
        paramMap.put("proprietorName", proprietorName);
        paramMap.put("sendStatus", sendStatus);
        paramMap.put("payStatus", payStatus);
        paramMap.put("housingId", housingId);
        paramMap.put("buildingId", buildingId);
        paramMap.put("locationId", locationId);
        paramMap.put("costType", costType);
        paramMap.put("proprietorId", proprietorId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("startPaymentDate", startPaymentDate);
        paramMap.put("endPaymentDate", endPaymentDate);
        paramMap.put("roomId", roomId);
        List<BillAccount> list = stockServiceImpl.getBillAccounts(paramMap);
        List<String> field = new ArrayList<String>();// 字段名
        field.add("账单编号");
        field.add("物业");
        field.add("房屋编号");
        field.add("业主姓名");
        field.add("费用类型");
        field.add("账期");
        field.add("金额");
        field.add("出账日期");
        field.add("缴费截止");
        field.add("付款状态");
        field.add("付款方式");
        field.add("付款日期");
        field.add("状态");
        String[][] content;
        if (list.size() == 0) {
            content = new String[1][6];
            content[0][0] = "无";
            content[0][1] = "无";
            content[0][2] = "无";
            content[0][3] = "无";
            content[0][4] = "无";
            content[0][5] = "无";
            content[0][6] = "无";
            content[0][7] = "无";
            content[0][8] = "无";
            content[0][9] = "无";
            content[0][10] = "无";
            content[0][11] = "无";
            content[0][12] = "无";

        } else {
            content = new String[list.size()][13];
        }
        for (int i = 0; i < list.size(); i++) {
            content[i][0] = list.get(i).getBill_entry_id() + "";
            content[i][1] = list.get(i).getDepartmentName() + "";
            content[i][2] = list.get(i).getRoomAddress() + "";
            content[i][3] = list.get(i).getProprietorName() + "";
            content[i][4] = list.get(i).getCost_type() + "";
            content[i][5] = list.get(i).getAcct_period() + "";
            content[i][6] = list.get(i).getBill_entry_amount() + "";
            content[i][7] = list.get(i).getRelease_day() + "";
            content[i][8] = list.get(i).getDeadline() + "";
            content[i][9] = list.get(i).getPayStatus() == 0 ? "未付款" : "已付款";
            content[i][10] = list.get(i).getPayType() + "";
            content[i][11] = list.get(i).getPayDate() + "";
            content[i][12] = list.get(i).getState() == 0 ? "未同步" : "已同步";
        }
        String filePath = null;
        try {
            File file = new File(request.getSession().getServletContext().getRealPath("/") + "/downloadfile");
            if (!file.exists())
                file.mkdirs();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
            filePath = ExcelUtil.exportExcel(request.getSession().getServletContext().getRealPath("/")
                    + "/downloadfile/", field, content, "bill" + df1.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("filePath", filePath);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteHousing.html", method = RequestMethod.POST)
    public ModelMap deleteHousing(HttpServletRequest request, ModelMap model) {
        String housingId = request.getParameter("housingId");
        stockServiceImpl.deleteHousing(Integer.parseInt(housingId));
        model.put("msg", "删除完成");
        model.put("state", 1);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/getBillType.html", method = RequestMethod.POST)
    public ModelMap getBillType(HttpServletRequest request, ModelMap model) {
        model.put("list", stockServiceImpl.getBillType());
        return model;
    }

    // 增加短信条数
    @ResponseBody
    @RequestMapping(value = "/addSmsCount.html", method = RequestMethod.POST)
    public ModelMap addSmsCount(HttpServletRequest request, ModelMap model) {
        String housingId = request.getParameter("housingId");
        String smsCount = request.getParameter("smsCount");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("housingId", housingId);
        paramMap.put("smsCount", smsCount);
        stockServiceImpl.addSmsCount(paramMap);
        model.put("msg", "充值完成");
        model.put("state", 1);
        return model;
    }

    // 账单催收
    @ResponseBody
    @RequestMapping(value = "/remindBillAccount.html", method = RequestMethod.POST)
    public ModelMap remindProprietor(HttpServletRequest request, ModelMap model) {
        String accountId = request.getParameter("accountId");
        BillAccount billAccount = stockServiceImpl.getBillAccountById(Integer.parseInt(accountId));
        Housing housing = stockServiceImpl.getHousingById(billAccount.getHousingId());
        if (housing.getSmsCount() < 1) {
            model.put("msg", "短信剩余条数不足");
            model.put("state", 0);
        } else {
            Proprietor proprietor = stockServiceImpl.getProprietorById(billAccount.getProprietorId());
            try {
                JuheUtile.sendSmsMessage(proprietor.getPhone(), proprietor.getRoomInfoAddress(),
                        billAccount.getBill_entry_amount() + "", housing.getName());
                model.put("msg", "发送成功");
                model.put("state", 1);
                stockServiceImpl.subtractSmsCount(billAccount.getHousingId());
            } catch (Exception e) {
                model.put("msg", "系统繁忙");
                model.put("state", 0);
                e.printStackTrace();
            }

        }
        return model;
    }

    /**
     * 小区催收
     * 
     * @Author lixingbiao 2017年8月3日 上午11:48:01
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/remindHousing.html", method = RequestMethod.POST)
    public ModelMap remindHousing(HttpServletRequest request, ModelMap model) {
        String housingId = request.getParameter("housingId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("housingId", housingId);
        paramMap.put("currPage", 0);
        paramMap.put("pageSize", 10000);
        List<Proprietor> proprietorList = stockServiceImpl.getProprietors(paramMap);
        Housing housing = stockServiceImpl.getHousingById(Integer.parseInt(housingId));
        int count = housing.getSmsCount();
        int success = 0;
        if (count == 0) {
            model.put("msg", "短信剩余条数不足");
            model.put("state", 0);
        } else {
            for (Proprietor proprietor : proprietorList) {
                if (count < 1) {
                    break;
                }
                paramMap = new HashMap<String, Object>();
                paramMap.put("payStatus", 0);
                paramMap.put("housingId", housingId);
                paramMap.put("proprietorId", proprietor.getId());
                paramMap.put("currPage", 0);
                paramMap.put("pageSize", 10000);
                List<BillAccount> list = stockServiceImpl.getBillAccounts(paramMap);
                double totalMoney = 0;
                for (BillAccount tmp : list) {
                    totalMoney = totalMoney + tmp.getBill_entry_amount();
                }
                try {
                    JuheUtile.sendSmsMessage(proprietor.getPhone(), proprietor.getRoomInfoAddress(), totalMoney + "",
                            housing.getName());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                success++;
                stockServiceImpl.subtractSmsCount(Integer.parseInt(housingId));
                count--;
            }
            model.put("msg", "成功发送" + success + "条催收短信");
            model.put("state", 1);
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/face2facePay.html", method = RequestMethod.POST)
    public ModelMap face2facePay(HttpServletRequest request, ModelMap model) {
        FaceToFacePay f2fPay = new FaceToFacePay();
        String auth_code = request.getParameter("auth_code");
        String housingId = request.getParameter("housingId");
        Housing housing = stockServiceImpl.getHousingById(Integer.parseInt(housingId));
        String total_amount = request.getParameter("total_amount");
        String app_auth_token = request.getParameter("app_auth_token");
        f2fPay.setAuth_code(auth_code);
        f2fPay.setHousingId(Integer.parseInt(housingId));
        f2fPay.setOut_trade_no(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        f2fPay.setSubject(housing.getDepartmentName() + "-" + housing.getName());
        f2fPay.setTime(new Date());
        f2fPay.setTotal_amount(new BigDecimal(total_amount));
        f2fPay.setOut_trade_no(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        CommonParm commonParm = new CommonParm();
        String app_id = commonParm.getApp_id().trim();
        String private_key = commonParm.getPrivate_key().trim();
        String alipay_public_key = commonParm.getAlipay_public_key().trim();
        String serverUrl = commonParm.getServerUrl().trim();
        String format = commonParm.getFormat().trim();
        String charset = commonParm.getCharset().trim();
        String sign_type = commonParm.getSign_type().trim();
        System.out.println(app_id);
        System.out.println(private_key);
        System.out.println(alipay_public_key);
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);
        AlipayTradePayRequest aliPayrequest = new AlipayTradePayRequest(); // 创建API对应的request类
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", f2fPay.getOut_trade_no());
        bizContent.put("scene", "bar_code");
        bizContent.put("auth_code", f2fPay.getAuth_code());
        bizContent.put("subject", f2fPay.getSubject());
        bizContent.put("store_id", "zaozaokeji");
        bizContent.put("timeout_express", "2m");
        bizContent.put("total_amount", f2fPay.getTotal_amount());
        aliPayrequest.setBizContent(bizContent.toString());
        AlipayTradePayResponse response;
        int state = 0;
        String msg = "支付失败";
        try {
            response = alipayClient.execute(aliPayrequest, "", app_auth_token);
            System.out.print(response.getBody());
            if (response.isSuccess()) {
                state = 1;
                msg = "支付成功！";
            }
            facePayService.insertFaceToFacePay(f2fPay);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        model.put("state", state);
        model.put("msg", msg);
        return model;
    }

    @RequestMapping(value = "/roomInfoUploadExcel.html", method = RequestMethod.GET)
    public Object roomInfoUploadExcel(HttpServletRequest request, ModelMap model) {

        return AD_HTML + "" + packageName + "/roomInfoUploadExcel";
    }

    @ResponseBody
    @RequestMapping(value = "/readRoomInfoExcel.html", method = RequestMethod.POST)
    public ModelMap readRoomInfoExcel(@RequestParam(value = "excelRoomInfoFile") MultipartFile file,
            HttpServletRequest request, ModelMap model) {

        String name = file.getOriginalFilename();
        List<String> resultString = new ArrayList<String>();
        System.out.println("file name  room info  >>:" + name);
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        // 读取Excel数据到List中
        try {
            list = new ExcelRead().readExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("success", "读取excel失败，请确定单元格属性类型为文本型!");
            return model;
        }
        if (list.size() > 1000) {
            model.put("success", "单次最多导入1000条数据!");
            model.put("result", "");
            return model;
        }
        System.out.println(list.size());

        // list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
        int count = 0;
        int row = 1;
        for (ArrayList<String> arr : list) {
            row++;
            String houseingName = arr.get(0).trim();// 小区名称
            String buildingName = arr.get(1).trim();// 楼栋名称
            String locationName = arr.get(2).trim();// 单元号
            String room = arr.get(3).trim();// 房屋号
            String proprietorName = arr.get(4).trim();// 业主姓名
            String phone = arr.get(5).trim();// 业主电话

            Housing housing = null;
            Building building = null;
            Location location = null;
            System.out.println(houseingName);
            housing = stockServiceImpl.getHousingByName(houseingName);

            System.out.println(housing);

            if (housing == null) {
                resultString.add("第" + row + "行,小区不存在，跳过导入");
                continue;
            }

            if (housing != null) {
                building = stockServiceImpl.getBuildingByName(buildingName, housing.getId());
            }

            if (building == null) {
                resultString.add("第" + row + "行,楼栋不存在，跳过导入");
                continue;
            }

            if (building != null) {
                location = stockServiceImpl.getLocationByName(locationName, building.getId());
            }

            if (location == null) {
                resultString.add("第" + row + "行,单元号不存在，跳过导入");
                continue;
            }

            if (StringUtils.isEmpty(room)) {
                resultString.add("第" + row + "行,房屋号不能为空，跳过导入");
                continue;
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();

            paramMap.put("currPage", 0);
            paramMap.put("pageSize", 100);
            paramMap.put("name", room);
            paramMap.put("housingId", String.valueOf(housing.getId()));
            paramMap.put("buildingId", String.valueOf(building.getId()));
            paramMap.put("locationId", String.valueOf(location.getId()));
            List<RoomInfo> listRooms = stockServiceImpl.getRoomInfos(paramMap);

            if (listRooms.size() > 0) {
                resultString.add("第" + row + "行，房屋信息已存在，跳过导入");
                continue;
            }

            RoomInfo roomInfo = new RoomInfo();

            String out_room_id = stockServiceImpl.getNextOut_room_id("");

            roomInfo.setRoom(room);
            roomInfo.setOut_room_id(out_room_id);

            if (housing != null) {
                roomInfo.setHousingId(housing.getId());
                roomInfo.setDepartmentId(housing.getDepartmentId());
                roomInfo.setDepartmentCode(housing.getDepartmentCode());
            }

            if (building != null) {
                roomInfo.setBuildingId(building.getId());
            }

            if (location != null) {
                roomInfo.setLocationId(location.getId());
            }

            if (location != null) {
                count = count + 1;
                System.out.println("count>>>:" + count);
                roomInfo.setAddress(building.getName() + location.getName() + room);
                stockServiceImpl.insertRoomInfo(roomInfo);
                if (proprietorName != null && !proprietorName.equals("")) {
                    Proprietor proprietor = new Proprietor();
                    proprietor.setDepartmentCode(roomInfo.getDepartmentCode());
                    proprietor.setDepartmentId(roomInfo.getDepartmentId());
                    proprietor.setName(proprietorName);
                    proprietor.setCard("");
                    proprietor.setRoomInfoId(roomInfo.getId());
                    proprietor.setEntryStatus(1);
                    proprietor.setCreateUid(0);
                    proprietor.setPhone(phone);
                    proprietor.setEntryDate(DateUtils.getDate());
                    proprietor.setHousingId(housing.getId());
                    stockServiceImpl.insertProprietor(proprietor);

                    // 更新房屋入住状态
                    roomInfo.setEntryStatus(1);
                    stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());
                }
            }
        }
        model.put("success", "成功导入" + count + "条数据，失败" + (list.size() - count) + "条数据!");
        model.put("result", resultString);
        return model;
    }

    /**
     * 导入账单
     * 
     * @Author lixingbiao 2017-6-27 下午10:11:14
     * @param file
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/readExcel.html", method = RequestMethod.POST)
    public ModelMap readExcel(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
            ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        String name = file.getOriginalFilename();

        System.out.println("file name>>:" + name);

        // 读取Excel数据到List中
        List<ArrayList<String>> list;
        try {
            list = new ExcelRead().readExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            model.put("success", "读取excel失败，请确定单元格属性类型为文本型!");
            return model;
        }
        if (list.size() > 1000) {
            model.put("success", "单次最多导入1000条数据!");
            model.put("result", "");
            return model;
        }
        System.out.println("count size  >>:" + list.size());

        String month = DateUtils.getYearMonth();

        // list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
        int count = 0;
        int row = 1;
        List<String> resultString = new ArrayList<String>();
        for (ArrayList<String> arr : list) {
            row++;
            if (arr == null || arr.size() == 1) {
                continue;
            }

            String houseingName = arr.get(0).trim();// 小区名称

            if (houseingName == null || houseingName.equals("")) {
                resultString.add("第" + row + "行，小区为空,跳过导入!");
                continue;
            }

            String buildingName = arr.get(1).trim();// 楼栋名称
            String locationName = arr.get(2).trim();// 单元号
            String roomName = arr.get(3).trim();// 房屋号
            String bill_entry_amount = arr.get(6).trim();// 金额
            String acct_period = arr.get(7).trim();// 账期
            String deadline = arr.get(8).trim();// 缴费截止
            String release_day = arr.get(9).trim();// 出账日期
            String cost_type = arr.get(10).trim();// 费用项目

            System.out.println(houseingName + "--" + buildingName + "--" + locationName + "--" + roomName + "--"
                    + bill_entry_amount + "--" + acct_period);

            if (bill_entry_amount == null || bill_entry_amount.equals("") || bill_entry_amount.equals("0")) {
                System.out.println("金额为空，跳过导入");
                resultString.add("第" + row + "行，金额为空,跳过导入!");
                continue;
            }

            Housing housing = null;
            Building building = null;
            Location location = null;

            housing = stockServiceImpl.getHousingByName(houseingName);

            if (housing == null) {
                System.out.println("小区为空，跳过导入");
                resultString.add("第" + row + "行，小区为空,跳过导入!");
                continue;
            }

            if (housing != null) {
                building = stockServiceImpl.getBuildingByName(buildingName, housing.getId());
            }

            if (building == null) {
                System.out.println("楼栋名称：" + buildingName + " 小区ID：" + housing.getId());
                System.out.println("楼栋为空，跳过导入");
                resultString.add("第" + row + "行，楼栋为空,跳过导入!");
                continue;
            }

            if (building != null) {
                location = stockServiceImpl.getLocationByName(locationName, building.getId());
            }

            if (location == null) {
                resultString.add("第" + row + "行，单元为空,跳过导入!");
                System.out.println("单元为空，跳过导入");
                continue;
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();

            paramMap.put("currPage", 0);
            paramMap.put("pageSize", 1);
            paramMap.put("name", roomName);
            paramMap.put("entryStatus", 1);
            paramMap.put("departmentCode", housing.getDepartmentCode());
            paramMap.put("housingId", String.valueOf(housing.getId()));
            paramMap.put("buildingId", String.valueOf(building.getId()));
            paramMap.put("locationId", String.valueOf(location.getId()));

            List<RoomInfo> listRooms = stockServiceImpl.getRoomInfos(paramMap);

            if (listRooms.size() == 0) {
                System.out.println(paramMap);
                System.out.println("第" + row + "行，没有已入住房屋信息,跳过导入!");
                resultString.add("第" + row + "行，没有已入住房屋信息,跳过导入!");
                continue;
            }

            RoomInfo roomInfo = listRooms.get(0);

            String out_room_id = roomInfo.getOut_room_id();

            BillAccount billAccount = new BillAccount();

            int proprietorId = 0;

            Proprietor proprietor = stockServiceImpl.getProprietorByOut_room_id(out_room_id);

            if (proprietor == null) {
                resultString.add("第" + row + "行，业主为空,跳过导入!");
                continue;
            }

            if (proprietor != null) {
                proprietorId = proprietor.getId();
                billAccount.setHousingId(proprietor.getHousingId());
                billAccount.setDepartmentId(proprietor.getDepartmentId());
                billAccount.setDepartmentCode(proprietor.getDepartmentCode());
                billAccount.setProprietorId(proprietorId);
            }

            if (StringUtils.isEmpty(cost_type)) {
                resultString.add("第" + row + "行,费用类型为空,跳过导入!");
                continue;
            }

            if (StringUtils.isEmpty(acct_period)) {
                resultString.add("第" + row + "行,账期为空,跳过导入!");
                continue;
            }
            if (deadline.length() != 8) {
                resultString.add("第" + row + "行,缴费截止日期不正确,跳过导入!");
                continue;
            }
            if (release_day.length() != 8) {
                resultString.add("第" + row + "行,出账日期不正确,跳过导入!");
                continue;
            }

            Map<String, Object> paramMap2 = new HashMap<String, Object>();
            paramMap2.put("currPage", 0);
            paramMap2.put("pageSize", 1);
            paramMap2.put("roomName", roomName);
            paramMap2.put("departmentCode", housing.getDepartmentCode());
            paramMap2.put("proprietorName", proprietor.getName());
            paramMap2.put("sendStatus", "");
            paramMap2.put("payStatus", "");
            paramMap2.put("housingId", String.valueOf(housing.getId()));
            paramMap2.put("buildingId", String.valueOf(building.getId()));
            paramMap2.put("locationId", String.valueOf(location.getId()));
            paramMap2.put("costType", cost_type);
            paramMap2.put("acct_period", acct_period);

            // 查询是否存在相同账单
            List<BillAccount> billAccounts = stockServiceImpl.getBillAccounts(paramMap2);

            if (billAccounts.size() > 0) {
                System.out.println("billAccounts>>:" + billAccounts);
                System.out.println("paramMap2>>:" + paramMap2);
                resultString.add("第" + row + "行，存在相同账单,跳过导入!");
                continue;
            }

            String bill_entry_id = stockServiceImpl.getNextBillAccountBill_entry_id("");

            if (bill_entry_id.length() > 32 || bill_entry_id.length() == 0) {
                resultString.add("第" + row + "行，账单号有误,跳过导入!");
                continue;
            }

            billAccount.setCost_type(cost_type);
            billAccount.setBill_entry_id(bill_entry_id);
            billAccount.setDeadline(deadline);
            billAccount.setOut_room_id(out_room_id);
            billAccount.setRelease_day(release_day);
            billAccount.setAcct_period(acct_period);
            billAccount.setProprietorId(proprietorId);
            billAccount.setMonth(month);
            billAccount.setBill_entry_amount(new Double(bill_entry_amount));
            stockServiceImpl.insertBillAccount(billAccount);
            count = count + 1;
        }

        model.put("success", "成功导入" + count + "条数据，失败" + (list.size() - count) + "条数据!");

        model.put("result", resultString);

        return model;
    }

    @RequestMapping(value = "/billAccountUploadExcel.html", method = RequestMethod.GET)
    public Object billAccountUploadExcel(HttpServletRequest request, ModelMap model) {
        return AD_HTML + "" + packageName + "/billAccountUploadExcel";
    }

    @RequestMapping(value = "/initPayBillAccount.html", method = RequestMethod.GET)
    public Object initPayBillAccount(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        BillAccount billAccount = stockServiceImpl.getBillAccountById(new Integer(id));
        model.put("order", billAccount);
        return AD_HTML + "" + packageName + "/initPayBillAccount";
    }

    @RequestMapping(value = "/initUpdatePayBillAccount.html", method = RequestMethod.GET)
    public Object initUpdatePayBillAccount(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        BillAccount billAccount = stockServiceImpl.getBillAccountById(new Integer(id));
        model.put("order", billAccount);
        return AD_HTML + "" + packageName + "/initUpdatePayBillAccount";
    }

    @ResponseBody
    @RequestMapping(value = "/saveUpdateBillAccount.html", method = RequestMethod.POST)
    public Object saveUpdateBillAccount(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");
        String bill_entry_amount = request.getParameter("bill_entry_amount");
        String deadline = request.getParameter("deadline");
        String release_day = request.getParameter("release_day");

        BillAccount billAccount = stockServiceImpl.getBillAccountById(new Integer(id));
        billAccount.setDeadline(deadline);
        billAccount.setRelease_day(release_day);
        billAccount.setBill_entry_amount(new Double(bill_entry_amount));

        if (billAccount.getSendStatus() == 1) {
            CommonParm commonParm = new CommonParm();

            String app_id = commonParm.getApp_id();
            String private_key = commonParm.getPrivate_key();
            String alipay_public_key = commonParm.getAlipay_public_key();
            String serverUrl = commonParm.getServerUrl();
            String format = commonParm.getFormat();
            String charset = commonParm.getCharset();
            String sign_type = commonParm.getSign_type();
            AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                    alipay_public_key, sign_type);
            AlipayEcoCplifeBillModifyRequest alipayRequest = new AlipayEcoCplifeBillModifyRequest();
            JSONObject billInfo = new JSONObject();
            billInfo.put("bill_entry_id", billAccount.getBill_entry_id());
            billInfo.put("bill_entry_amount", billAccount.getBill_entry_amount());
            billInfo.put("release_day", billAccount.getRelease_day());
            billInfo.put("deadline", billAccount.getDeadline());
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("housingId", billAccount.getHousingId());
            List<Housing> list = stockServiceImpl.getHousings(paramMap);
            alipayRequest.setBizContent("{community_id:'" + list.get(0).getCommunity_id() + "',bill_entry_list:["
                    + billInfo.toString() + "]}");
            alipayRequest.putOtherTextParam("app_auth_token", list.get(0).getToken());
            AlipayEcoCplifeBillModifyResponse response;
            try {
                response = alipayClient.execute(alipayRequest);
                if (response.isSuccess()) {
                    System.out.println("调用成功");
                } else {
                    System.out.println("调用失败");
                }
            } catch (AlipayApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());
        model.put("status", true);
        model.put("msg", "账单修改完成！");

        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/savePayBillAccount.html", method = RequestMethod.POST)
    public Object savePayBillAccount(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        String payType = request.getParameter("payType");
        String payDate = request.getParameter("payDate");
        String accpetMoney = request.getParameter("accpetMoney") == null ? "" : request.getParameter("accpetMoney");
        BillAccount billAccount = stockServiceImpl.getBillAccountById(new Integer(id));

        billAccount.setPayType(payType);
        // billAccount.setPayDate(DateUtils.getDate());
        billAccount.setPayDate(payDate);
        billAccount.setPayStatus(1);
        billAccount.setAccpetMoney(StringUtils.isEmpty(accpetMoney) ? billAccount.getBill_entry_amount() + ""
                : accpetMoney);
        stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());

        model.put("status", true);
        model.put("msg", "账单线下付款完成！");
        
        PrintInfo info = printInfoService.selectBydepartmentId(billAccount.getDepartmentId());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", Integer.valueOf(billAccount.getDepartmentId()));
        Department department = departmentService.getDepartmentById(paramMap);
        paramMap.put("departmentId", Integer.valueOf(department.getId()));
        department = departmentService.getDepartmentById(paramMap);
        PrintMessage obj = new PrintMessage(info.getMachineCode(), info.getMsign());
        StringBuffer sb = new StringBuffer("");
        sb.append("<center>支付宝智慧小区</center>\r");
        sb.append("小区名称："+billAccount.getDepartmentName()+"\r");
        sb.append(billAccount.getRoomAddress() + "\r");
        sb.append("业主姓名："+billAccount.getProprietorName()+"\r");
        sb.append("付款时间："+billAccount.getPayDate()+"\r");
        sb.append("订单编号："+billAccount.getBill_entry_id()+"\r");
        sb.append("支付方式："+billAccount.getPayType()+"\r");
        sb.append("缴费金额："+billAccount.getBill_entry_amount()+"\r");
        sb.append("缴费明细：\r");
        sb.append("<table><tr><td>类别</td><td>账期</td><td>金额</td></tr><tr><td>"+billAccount.getCost_type()+"</td><td>"+billAccount.getAcct_period()+"</td><td>"+billAccount.getBill_entry_amount()+"</td></tr></table>\r");
        sb.append("<center><FB><FS>"+department.getName()+"</FS></FB></center>\r");
        sb.append("<center>技术支持：杭州早早科技 400-720-8888</center>\r");
        sb.append("----------------------\r");
        sb.append("<center>交易小票</center>\r");
        obj.sendContent(sb.toString());

        // 同步支付宝平台,下架此账单
        try {
            int status = undercarriageBillAccountFromAlipay(billAccount);

            if (status == 1) {
                model.put("msg", "账单线下付款完成！并从支付宝下架了此账单。");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return model;
    }

    /**
     * @throws AlipayApiException
     * @Author lixingbiao 2017-6-8 下午2:32:10
     */
    private int undercarriageBillAccountFromAlipay(BillAccount billAccount) throws AlipayApiException {

        int status = 0;

        Proprietor proprietor = stockServiceImpl.getProprietorById(billAccount.getProprietorId());
        Housing housing = new Housing();
        try {
            housing = stockServiceImpl.getHousingById(proprietor.getHousingId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        String token = housing.getToken();
        String community_id = housing.getCommunity_id();

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);

        JSONArray bill_set = new JSONArray();

        for (int i = 0; i < 1; i++) {
            String bill_entry_id = billAccount.getBill_entry_id();// 账单ID
            bill_set.add(bill_entry_id);
        }

        bizContent.put("bill_entry_id_list", bill_set);

        AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
        billDeleteRequest.setBizContent(bizContent.toString());
        billDeleteRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeBillDeleteResponse response = alipayClient.execute(billDeleteRequest);

        if (response.getCode().equals("10000")) {
            status = 1;
        }

        return status;
    }

    @RequestMapping(value = "/exchangEapplyRecordInput.html", method = RequestMethod.GET)
    public Object exchangEapplyRecordInput(HttpServletRequest request, ModelMap model) {

        List<Product> list = iProductService.getAllProducts();

        model.put("products", list);

        return AD_HTML + "" + packageName + "/exchangEapplyRecordInput";

    }

    @RequestMapping(value = "/exchangEapplyRecordInit.html", method = RequestMethod.GET)
    public Object exchangEapplyRecordInit(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");
        Integer billId = new Integer(id);

        ExchangEapplyRecord exchangEapplyRecord = stockServiceImpl.getExchangEapplyRecordById(billId);

        List<Product> listProducts = iProductService.getAllProductsUse();

        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);

        model.put("warehouses", warehouses);
        model.put("products", listProducts);
        model.put("exchangEapplyRecord", exchangEapplyRecord);
        model.put("productId", exchangEapplyRecord.getProductId());

        return AD_HTML + "" + packageName + "/exchangEapplyRecordInit";

    }

    @ResponseBody
    @RequestMapping(value = "/exchangEapplyRecordExit.html", method = RequestMethod.POST)
    public Object exchangEapplyRecordExit(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();
        int uid = user.getUserId();
        String id = request.getParameter("id");

        String deliverCode = request.getParameter("deliverCode");
        String remark = request.getParameter("remark");
        String outsideCode = request.getParameter("outsideCode");
        String outWarehouse = request.getParameter("outWarehouse");
        String outProdcut = request.getParameter("outProdcut");

        Integer billId = new Integer(id);

        String code = stockServiceImpl.getNextEnterBillCode("rk");
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        ExchangEapplyRecord exchangEapplyRecord = stockServiceImpl.getExchangEapplyRecordById(billId);
        exchangEapplyRecord.setWarehouseId(Integer.valueOf(outWarehouse));
        int companyId = exchangEapplyRecord.getCompanyId();
        String departmentCode = exchangEapplyRecord.getDepartmentCode();
        int departmentId = exchangEapplyRecord.getDepartmentId();

        Integer inproductId = exchangEapplyRecord.getProductId();
        Integer outProductId = Integer.valueOf(outProdcut);
        Integer warehouseId = exchangEapplyRecord.getWarehouseId();

        exchangEapplyRecord.setNewProductId(outProductId);
        exchangEapplyRecord.setOutsideCode(outsideCode);
        exchangEapplyRecord.setStatus(1);
        exchangEapplyRecord.setDeliverCode(deliverCode);
        exchangEapplyRecord.setRemark(remark);
        exchangEapplyRecord.setChecked(1);
        exchangEapplyRecord.setAuditingPeople(user.getUserId());
        exchangEapplyRecord.setAuditingDate(new Date());
        exchangEapplyRecord.setAuditingTime(new Timestamp(System.currentTimeMillis()));
        exchangEapplyRecord.setExchangNumber(exchangEapplyRecord.getNumber());

        stockServiceImpl.updateExchangEapplyRecord(exchangEapplyRecord, billId);

        Product inproduct = iProductService.findById(inproductId);

        BigDecimal price = inproduct.getPrice();
        Integer number = exchangEapplyRecord.getExchangNumber();
        float floatprice = price.floatValue();
        double money = number * floatprice;

        // 保存入库单
        EnterBill enterBill = new EnterBill();
        enterBill.setCompanyId(companyId);
        enterBill.setDepartmentId(departmentId);
        enterBill.setDepartmentCode(departmentCode);
        BigDecimal money2 = new BigDecimal(money);
        enterBill.setMoney(money2);
        enterBill.setPrice(price);
        enterBill.setRemark(remark);
        enterBill.setOutsideCode(outsideCode);
        enterBill.setCode(code);
        enterBill.setNumber(number);
        enterBill.setProductId(inproductId);
        enterBill.setWarehouseId(warehouseId);
        enterBill.setAddate(new Date());
        enterBill.setCreateTime(new Timestamp(System.currentTimeMillis()));
        enterBill.setUid(user.getUserId());
        stockServiceImpl.insertEnterBill(enterBill);

        // 保存出库单
        Integer integerNumber = new Integer(number);

        StockMonth stockMonth = saveOutStockMonth(month, warehouseId, outProductId, integerNumber);

        StockDetail stockDetail = saveOutStockDetail(month, addate, warehouseId, outProductId, integerNumber);

        saveOutBill(exchangEapplyRecord.getId(), companyId, departmentId, departmentCode, uid, String.valueOf(number),
                outsideCode, remark, floatprice, warehouseId, money2, outProductId, stockMonth, stockDetail);

        model.put("status", true);
        model.put("msg", "换货申请审批完成！");

        return model;

    }

    private StockDetail saveOutStockDetail(String month, Date addate, Integer warehouseId, Integer productId,
            Integer number) {

        StockDetail stockDetail = stockServiceImpl.getStockDetail(productId, warehouseId, addate);
        StockDetail beforeDetail = stockServiceImpl.getLatelyStockDetail(productId, warehouseId, addate);

        if (stockDetail != null) {
            int outNumber = stockDetail.getOutNumber() + number;
            stockDetail.setOutNumber(outNumber);
            stockServiceImpl.updateStockDetail(stockDetail, stockDetail.getId());
        } else {
            stockDetail = new StockDetail();
            if (beforeDetail != null) {
                stockDetail.setStartNumber(beforeDetail.getEndNumber());
            }
            stockDetail.setMonth(month);
            stockDetail.setWarehouseId(warehouseId);
            stockDetail.setProductId(productId);
            stockDetail.setOutNumber(number);
            stockDetail.setAddate(new Date());
            stockDetail.setCreateTime(new Timestamp(System.currentTimeMillis()));
            stockServiceImpl.insertStockDetail(stockDetail);
        }
        return stockDetail;
    }

    private StockMonth saveOutStockMonth(String month, Integer warehouseId, Integer productId, Integer number) {

        StockMonth stockMonth = stockServiceImpl.getStockMonth(productId, warehouseId, month);

        if (stockMonth != null) {
            if (stockMonth.getMonth().equals(month)) {
                stockMonth.setOutNumber(stockMonth.getOutNumber() + number);
                stockServiceImpl.updateStockMonth(stockMonth, stockMonth.getId());
            } else {
                Integer startNumber = stockMonth.getEndNumber();
                stockMonth = new StockMonth();
                stockMonth.setStartNumber(startNumber);
                stockMonth.setMonth(month);
                stockMonth.setWarehouseId(warehouseId);
                stockMonth.setProductId(productId);
                stockMonth.setOutNumber(number);
                stockServiceImpl.insertStockMonth(stockMonth);
            }
        } else {
            stockMonth = new StockMonth();
            stockMonth.setMonth(month);
            stockMonth.setWarehouseId(warehouseId);
            stockMonth.setProductId(productId);
            stockMonth.setOutNumber(number);
            stockServiceImpl.insertStockMonth(stockMonth);
        }
        return stockMonth;
    }

    private void saveOutBill(int orderId, int companyId, int departmentId, String departmentCode, int uid,
            String number, String outsideCode, String remark, double price, Integer warehouseId, BigDecimal money,
            Integer productId, StockMonth stockMonth, StockDetail stockDetail) {

        OutBill outBill = new OutBill();
        String code = stockServiceImpl.getNextOutBillCode("ck");
        outBill.setOrderId(orderId);
        outBill.setCompanyId(companyId);
        outBill.setDepartmentId(departmentId);
        outBill.setDepartmentCode(departmentCode);
        outBill.setStockMonth(stockMonth.getId());
        outBill.setStockDetail(stockDetail.getId());
        outBill.setWarehouseId(warehouseId);
        outBill.setOutsideCode(outsideCode);
        outBill.setCode(code);
        outBill.setRemark(remark);
        outBill.setProductId(productId);
        outBill.setUid(uid);
        outBill.setMoney(money);
        outBill.setPrice(new BigDecimal(price));
        outBill.setNumber(Integer.valueOf(number));
        outBill.setAddate(new Date());
        outBill.setCreateTime(new Timestamp(System.currentTimeMillis()));
        stockServiceImpl.insertOutBill(outBill);
    }

    @ResponseBody
    @RequestMapping(value = "/saveExchangEapplyRecordInput.html", method = RequestMethod.POST)
    public Object saveExchangEapplyRecordInput(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String id = request.getParameter("productId");
        String numberStr = request.getParameter("number");
        String outsideCode = request.getParameter("outsideCode");
        String remark = request.getParameter("remark");
        String companyName = request.getParameter("companyName");
        String code = stockServiceImpl.getNextExchangEapplyRecordCode("e");
        Integer productId = new Integer(id);
        Integer number = new Integer(numberStr);

        int companyId = 0;

        if (companyName != null && !companyName.equals("")) {
            Map<String, Object> paramMap2 = new HashMap<String, Object>();
            paramMap2.put("companyName", companyName);
            CompanyInfo company = companyServiceImpl.getCompanyInfoByName(paramMap2);

            if (company != null) {
                companyId = company.getId();
            }
        }

        int departmentId = user.getDepartmentId();
        String departmentCode = user.getDepartmentCode();

        ExchangEapplyRecord exchangEapplyRecord = new ExchangEapplyRecord();
        exchangEapplyRecord.setCompanyId(companyId);
        exchangEapplyRecord.setDepartmentId(departmentId);
        exchangEapplyRecord.setDepartmentCode(departmentCode);
        exchangEapplyRecord.setRemark(remark);
        exchangEapplyRecord.setOutsideCode(outsideCode);
        exchangEapplyRecord.setCode(code);
        exchangEapplyRecord.setNumber(number);
        exchangEapplyRecord.setProductId(productId);
        exchangEapplyRecord.setAddate(new Date());
        exchangEapplyRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        exchangEapplyRecord.setUid(user.getUserId());

        stockServiceImpl.insertExchangEapplyRecord(exchangEapplyRecord);

        model.put("status", true);
        model.put("msg", "换货申请保存完成！");

        return model;

    }

    @RequestMapping(value = "/queryExchangEapplyRecord.html", method = RequestMethod.GET)
    public String queryExchangEapplyRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return AD_HTML + packageName + "/queryExchangEapplyRecord";
    }

    @ResponseBody
    @RequestMapping(value = "/queryExchangEapplyRecordList.html", method = RequestMethod.POST)
    public Object queryExchangEapplyRecordList(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String code = request.getParameter("code");
        String outsideCode = request.getParameter("outsideCode");
        String remark = request.getParameter("remark");
        String money = request.getParameter("money");
        String departmentCode = user.getDepartmentCode();

        Integer companyId = null;

        if (user.getAssortment().equals("customer")) {
            companyId = user.getCompanyId();
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("code", code);
        paramMap.put("outsideCode", outsideCode);
        paramMap.put("remark", remark);
        paramMap.put("money", money);
        paramMap.put("companyId", companyId);
        paramMap.put("departmentCode", departmentCode);

        List<ExchangEapplyRecord> list = stockServiceImpl.getAllExchangEapplyRecord(paramMap);
        pager.setTotalRecord(stockServiceImpl.getAllExchangEapplyRecordCount(paramMap));
        QueryResult result = stockServiceImpl.getAllExchangEapplyRecordCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;

    }

    @RequestMapping(value = "/queryAdvancePaymentRecord.html", method = RequestMethod.GET)
    public String queryAdvancePaymentRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        return AD_HTML + packageName + "/queryAdvancePaymentRecord";
    }

    @ResponseBody
    @RequestMapping(value = "/queryAdvancePaymentRecordList.html", method = RequestMethod.POST)
    public Object queryAdvancePaymentRecordList(HttpServletRequest request, ModelMap model) {

        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String code = request.getParameter("code");
        String outsideCode = request.getParameter("outsideCode");
        String remark = request.getParameter("remark");
        String money = request.getParameter("money");
        String companyName = request.getParameter("companyName");

        Integer companyId = null;

        if (companyName != null && !companyName.equals("")) {
            Map<String, Object> paramMap2 = new HashMap<String, Object>();
            paramMap2.put("companyName", companyName);
            CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoByName(paramMap2);
            if (companyInfo != null) {
                companyId = companyInfo.getId();
            }
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("code", code);
        paramMap.put("outsideCode", outsideCode);
        paramMap.put("remark", remark);
        paramMap.put("money", money);
        paramMap.put("companyId", companyId);

        List<AdvancePaymentRecord> list = stockServiceImpl.getAllAdvancePaymentRecords(paramMap);
        pager.setTotalRecord(stockServiceImpl.getAllAdvancePaymentRecordsCount(paramMap));
        QueryResult result = stockServiceImpl.getAllAdvancePaymentRecordsCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;

    }

    @RequestMapping(value = "/inputAdvancePaymentRecord.html", method = RequestMethod.GET)
    public Object inputAdvancePaymentRecord(HttpServletRequest request, ModelMap model) {
        return AD_HTML + "" + packageName + "/inputAdvancePaymentRecord";
    }

    @ResponseBody
    @RequestMapping(value = "/saveAdvancePaymentRecord.html", method = RequestMethod.POST)
    public Object saveAdvancePaymentRecord(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String companyName = request.getParameter("companyName");
        String money = request.getParameter("money");
        String remark = request.getParameter("remark");
        String outsideCode = request.getParameter("outsideCode");

        if (money == null || money.equals("")) {
            money = "0";
        }

        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("companyName", companyName);
        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoByName(paramMap2);

        double advancePayment = Double.parseDouble(money);
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);
        Integer companyId = companyInfo.getId();
        String departmentCode = companyInfo.getDepartmentCode();
        Integer departmentId = companyInfo.getDepartmentId();

        // 更新汇总账
        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);

        if (collect != null) {
            if (collect.getMonth().equals(month)) {
                collect.setAdvancePayment(collect.getAdvancePayment().add(new BigDecimal(advancePayment)));
                balanceService.updateCurrentAccountCollect(collect);
            } else {
                BigDecimal startMoney = collect.getEndMoney();
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setStartMoney(startMoney);
                collect.setAdvancePayment(collect.getAdvancePayment().add(new BigDecimal(advancePayment)));
                balanceService.saveCurrentAccountCollect(collect);
            }

        } else {
            double startMoney = 0;
            collect = new CurrentAccountCollect();
            collect.setDepartmentCode(departmentCode);
            collect.setDepartmentId(departmentId);
            collect.setMonth(month);
            collect.setCompanyId(companyId);
            collect.setStartMoney(new BigDecimal(startMoney));
            collect.setAdvancePayment(collect.getAdvancePayment().add(new BigDecimal(advancePayment)));
            balanceService.saveCurrentAccountCollect(collect);
        }

        // 更新明细账
        CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
        CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

        if (detail != null) {
            detail.setAdvancePayment(detail.getAdvancePayment().add(new BigDecimal(advancePayment)));
            balanceService.updateCurrentAccountDetail(detail);
        } else {
            detail = new CurrentAccountDetail();
            if (beforeDetail != null) {
                detail.setStartMoney(beforeDetail.getEndMoney());
            }
            detail.setAddate(addate);
            detail.setDepartmentCode(departmentCode);
            detail.setDepartmentId(departmentId);
            detail.setMonth(month);
            detail.setCompanyId(companyId);
            detail.setAdvancePayment(detail.getAdvancePayment().add(new BigDecimal(advancePayment)));
            balanceService.saveCurrentAccountDetail(detail);
        }

        String code = stockServiceImpl.getNextAdvancePaymentRecordCode();

        AdvancePaymentRecord advancePaymentRecord = new AdvancePaymentRecord();
        if (companyInfo != null) {
            advancePaymentRecord.setCompanyId(companyInfo.getId());
        }

        advancePaymentRecord.setStockDetail(detail.getId());
        advancePaymentRecord.setStockMonth(collect.getId());
        advancePaymentRecord.setCode(code);
        advancePaymentRecord.setOutsideCode(outsideCode);
        advancePaymentRecord.setRemark(remark);
        advancePaymentRecord.setMoney(new BigDecimal(money));
        advancePaymentRecord.setAddate(new Date());
        advancePaymentRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        advancePaymentRecord.setUid(user.getUserId());
        stockServiceImpl.insertAdvancePaymentRecord(advancePaymentRecord);

        model.put("status", true);
        model.put("msg", "预收款录入成功！");

        return model;

    }

    @RequestMapping(value = "/initUpdateEnterBill.html", method = RequestMethod.GET)
    public Object initUpdateEnterBill(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");

        EnterBill enterBill = stockServiceImpl.getEnterBillById(new Integer(id));

        model.put("order", enterBill);

        return AD_HTML + "" + packageName + "/updateEnterBill";

    }

    @ResponseBody
    @RequestMapping(value = "/updateEnterBill.html", method = RequestMethod.POST)
    public Object updateEnterBill(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");
        String remark = request.getParameter("remark");

        EnterBill enterBill = stockServiceImpl.getEnterBillById(new Integer(id));
        enterBill.setRemark(remark);
        stockServiceImpl.updateEnterBill(enterBill, new Integer(id));

        model.put("status", true);
        model.put("msg", "入库单备注修改成功！");

        return model;

    }

    @RequestMapping(value = "/enterBillView.html", method = RequestMethod.GET)
    public Object enterBillView(HttpServletRequest request, ModelMap model) {
        Integer id = Integer.valueOf(request.getParameter("id"));

        EnterBill enterBill = stockServiceImpl.getEnterBillById(id);

        model.put("order", enterBill);

        return AD_HTML + packageName + "/enterBillView";

    }

    @RequestMapping(value = "/initAuditingEnterBill.html", method = RequestMethod.GET)
    public String initAuditingEnterBill(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        SysUsers user = getUser();

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);
        List<Warehouse> lastWarehoues = new ArrayList<Warehouse>();

        if (user.getAssortment().equals("factory")) {
            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCompanyId() == user.getCompanyId()) {
                    lastWarehoues.add(warehouse);
                }
            }
        } else {
            lastWarehoues = warehouses;
        }

        model.put("warehouses", lastWarehoues);

        return AD_HTML + packageName + "/initAuditingEnterBill";
    }

    @RequestMapping(value = "/auditingEnterBill.html", method = RequestMethod.GET)
    public String auditingEnterBill(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        SysUsers user = getUser();

        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        String idString = request.getParameter("id");
        Integer id = new Integer(idString);

        EnterBill enterBill = stockServiceImpl.getEnterBillById(id);

        if (enterBill != null && enterBill.getChecked() == 1) {
            return AD_HTML + packageName + "/initAuditingEnterBill";
        }

        if (enterBill != null) {
            try {
                Integer warehouseId = enterBill.getWarehouseId();
                Integer productId = enterBill.getProductId();
                Integer number = enterBill.getNumber();

                StockMonth stockMonth = saveStockMonth(month, warehouseId, productId, number);
                StockDetail stockDetail = saveStockDetail(month, addate, warehouseId, productId, number);

                if (stockMonth != null) {
                    enterBill.setStockMonth(stockMonth.getId());
                }
                if (stockDetail != null) {
                    enterBill.setStockDetail(stockDetail.getId());
                }
                enterBill.setChecked(1);
                enterBill.setAuditingPeople(user.getUserId());
                enterBill.setAuditingDate(new Date());
                enterBill.setAuditingTime(new Timestamp(System.currentTimeMillis()));
                stockServiceImpl.auditingEnterBill(enterBill, id);

            } catch (Exception e) {

            }
        }

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);

        List<Warehouse> lastWarehoues = new ArrayList<Warehouse>();

        if (user.getAssortment().equals("factory")) {
            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCompanyId() == user.getCompanyId()) {
                    lastWarehoues.add(warehouse);
                }
            }
        } else {
            lastWarehoues = warehouses;
        }

        model.put("warehouses", lastWarehoues);

        return AD_HTML + packageName + "/initAuditingEnterBill";
    }

    @ResponseBody
    @RequestMapping(value = "/queryNoAuditingEnteringBillList", method = RequestMethod.POST)
    public Object queryNoAuditingEnteringBillList(HttpServletRequest request, ModelMap model) {

        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String code = request.getParameter("code");
        String outsideCode = request.getParameter("outsideCode");
        String remark = request.getParameter("remark");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;

        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("code", code);
        paramMap.put("outsideCode", outsideCode);
        paramMap.put("remark", remark);

        List<EnterBill> list = stockServiceImpl.getAllNoauditingEnterBills(paramMap);

        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;

    }

    @RequestMapping(value = "/queryEnteringBill.html", method = RequestMethod.GET)
    public String queryEnteringBill(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        SysUsers user = getUser();

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);
        List<Warehouse> lastWarehoues = new ArrayList<Warehouse>();

        if (user.getAssortment().equals("factory")) {
            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCompanyId() == user.getCompanyId()) {
                    lastWarehoues.add(warehouse);
                }
            }
        } else {
            lastWarehoues = warehouses;
        }

        model.put("warehouses", lastWarehoues);

        return AD_HTML + packageName + "/queryEnteringBill";
    }

    @ResponseBody
    @RequestMapping(value = "/queryEnteringBillList.html", method = RequestMethod.POST)
    public Object queryEnteringBillList(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String code = request.getParameter("code");
        String outsideCode = request.getParameter("outsideCode");
        String remark = request.getParameter("remark");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("code", code);
        paramMap.put("outsideCode", outsideCode);
        paramMap.put("remark", remark);

        if (user.getAssortment().equals("factory")) {
            paramMap.put("companyId", user.getCompanyId());
        }

        List<EnterBill> list = stockServiceImpl.getAllEnterBills(paramMap);
        pager.setTotalRecord(stockServiceImpl.getAllEnterBillsCount(paramMap));
        QueryResult result = stockServiceImpl.getAllEnterBillsCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;
    }

    @RequestMapping(value = "/queryOutBill.html", method = RequestMethod.GET)
    public String queryOutBill(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);
        model.put("warehouses", warehouses);

        return AD_HTML + packageName + "/queryOutBill";

    }

    @ResponseBody
    @RequestMapping(value = "/queryOutBillList.html", method = RequestMethod.POST)
    public Object queryOutBillList(HttpServletRequest request, ModelMap model) {

        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String code = request.getParameter("code");
        String outsideCode = request.getParameter("outsideCode");
        String orderCode = request.getParameter("orderCode");
        String companyName = request.getParameter("companyName");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("code", code);
        paramMap.put("outsideCode", outsideCode);
        paramMap.put("companyName", companyName);
        paramMap.put("orderCode", orderCode);

        List<OutBill> list = stockServiceImpl.getAllOutBills(paramMap);
        pager.setTotalRecord(stockServiceImpl.getAllOutBillsCount(paramMap));
        QueryResult result = stockServiceImpl.getAllOutBillsCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;

    }

    @RequestMapping(value = "/stockMonth.html", method = RequestMethod.GET)
    public String stockMonth(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        SysUsers user = getUser();

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);
        List<Warehouse> lastWarehoues = new ArrayList<Warehouse>();

        if (user.getAssortment().equals("factory")) {
            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCompanyId() == user.getCompanyId()) {
                    lastWarehoues.add(warehouse);
                }
            }
        } else {
            lastWarehoues = warehouses;
        }

        model.put("warehouses", lastWarehoues);
        return AD_HTML + packageName + "/stockMonth";
    }

    @ResponseBody
    @RequestMapping(value = "/queryStockMonth.html", method = RequestMethod.POST)
    public Object queryStockMonth(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);

        if (user.getAssortment().equals("factory")) {
            paramMap.put("companyId", user.getCompanyId());
        }

        List<StockMonth> list = stockServiceImpl.queryStockMonth(paramMap);
        pager.setTotalRecord(stockServiceImpl.queryStockMonthCount(paramMap));
        QueryResult result = stockServiceImpl.queryStockMonthCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;

    }

    @RequestMapping(value = "/stockDetail.html", method = RequestMethod.GET)
    public String stockDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        SysUsers user = getUser();

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);
        List<Warehouse> lastWarehoues = new ArrayList<Warehouse>();

        if (user.getAssortment().equals("factory")) {
            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCompanyId() == user.getCompanyId()) {
                    lastWarehoues.add(warehouse);
                }
            }
        } else {
            lastWarehoues = warehouses;
        }

        model.put("warehouses", lastWarehoues);

        return AD_HTML + packageName + "/stockDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/queryStockDetail.html", method = RequestMethod.POST)
    public Object queryStockDetail(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String ser = request.getParameter("ser");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String month = request.getParameter("month");
        String warehouse = request.getParameter("warehouse");
        String product = request.getParameter("product");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("month", month);
        paramMap.put("warehouse", warehouse);
        paramMap.put("product", product);

        if (user.getAssortment().equals("factory")) {
            paramMap.put("companyId", user.getCompanyId());
        }

        List<StockDetail> list = stockServiceImpl.queryStockDetail(paramMap);
        pager.setTotalRecord(stockServiceImpl.queryStockDetailCount(paramMap));
        QueryResult result = stockServiceImpl.queryStockDetailCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);

        return model;

    }

    @RequestMapping(value = "/enterBillInput.html", method = RequestMethod.GET)
    public Object enterInput(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehousesByCompanyId(user.getCompanyId());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);

        model.put("warehouses", warehouses);

        List<Product> list = iProductService.getAllProducts();
        model.put("products", list);

        return AD_HTML + "" + packageName + "/enterBillInput";
    }

    @ResponseBody
    @RequestMapping(value = "/saveEnterBill.html", method = RequestMethod.POST)
    public Object saveEnterBill(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String id = request.getParameter("productId");
        String warehouse = request.getParameter("warehouseId");
        String numberStr = request.getParameter("number");
        String outsideCode = request.getParameter("outsideCode");
        String remark = request.getParameter("remark");
        String code = stockServiceImpl.getNextEnterBillCode("rk");

        Integer warehouseId = new Integer(0);
        if (warehouse != null && !warehouse.equals("")) {
            warehouseId = new Integer(warehouse);
        }

        Integer productId = new Integer(id);
        Integer number = new Integer(numberStr);
        Product product = iProductService.findById(productId);

        try {
            // StockMonth stockMonth = saveStockMonth(month, warehouseId, productId, number);

            // StockDetail stockDetail = saveStockDetail(month, addate, warehouseId, productId, number);

            saveEnterBill(user, outsideCode, remark, code, warehouseId, productId, product, number, null, null);

        } catch (Exception e) {

        }

        model.put("status", true);
        model.put("msg", "产品入库完成！");

        return model;

    }

    private StockMonth saveStockMonth(String month, Integer warehouseId, Integer productId, Integer number) {

        StockMonth stockMonth = stockServiceImpl.getStockMonth(productId, warehouseId, month);

        if (stockMonth != null) {
            if (stockMonth.getMonth().equals(month)) {
                stockMonth.setEnterNumber(stockMonth.getEnterNumber() + number);
                stockServiceImpl.updateStockMonth(stockMonth, stockMonth.getId());
            } else {
                Integer startNumber = stockMonth.getEndNumber();
                stockMonth = new StockMonth();
                stockMonth.setStartNumber(startNumber);
                stockMonth.setMonth(month);
                stockMonth.setWarehouseId(warehouseId);
                stockMonth.setProductId(productId);
                stockMonth.setEnterNumber(number);
                stockServiceImpl.insertStockMonth(stockMonth);
            }
        } else {
            stockMonth = new StockMonth();
            stockMonth.setMonth(month);
            stockMonth.setWarehouseId(warehouseId);
            stockMonth.setProductId(productId);
            stockMonth.setEnterNumber(number);
            stockServiceImpl.insertStockMonth(stockMonth);
        }
        return stockMonth;
    }

    private StockDetail saveStockDetail(String month, Date addate, Integer warehouseId, Integer productId,
            Integer number) {

        StockDetail stockDetail = stockServiceImpl.getStockDetail(productId, warehouseId, addate);
        StockDetail beforeDetail = stockServiceImpl.getLatelyStockDetail(productId, warehouseId, addate);

        if (stockDetail != null) {
            int enterNumber = stockDetail.getEnterNumber() + number;
            stockDetail.setEnterNumber(enterNumber);
            stockServiceImpl.updateStockDetail(stockDetail, stockDetail.getId());
        } else {
            stockDetail = new StockDetail();
            if (beforeDetail != null) {
                stockDetail.setStartNumber(beforeDetail.getEndNumber());
            }
            stockDetail.setMonth(month);
            stockDetail.setWarehouseId(warehouseId);
            stockDetail.setProductId(productId);
            stockDetail.setEnterNumber(number);
            stockDetail.setAddate(new Date());
            stockDetail.setCreateTime(new Timestamp(System.currentTimeMillis()));
            stockServiceImpl.insertStockDetail(stockDetail);
        }
        return stockDetail;
    }

    private void saveEnterBill(SysUsers user, String outsideCode, String remark, String code, Integer warehouseId,
            Integer productId, Product product, Integer number, StockMonth stockMonth, StockDetail stockDetail) {

        int companyId = user.getCompanyId();
        int departmentId = user.getDepartmentId();
        String departmentCode = user.getDepartmentCode();

        EnterBill enterBill = new EnterBill();
        BigDecimal price = product.getPrice();

        double money = number * price.floatValue();

        if (stockMonth != null) {
            enterBill.setStockMonth(stockMonth.getId());
        }

        if (stockDetail != null) {
            enterBill.setStockDetail(stockDetail.getId());
        }

        enterBill.setCompanyId(companyId);
        enterBill.setDepartmentId(departmentId);
        enterBill.setDepartmentCode(departmentCode);
        enterBill.setMoney(new BigDecimal(money));
        enterBill.setPrice(price);
        enterBill.setRemark(remark);
        enterBill.setOutsideCode(outsideCode);
        enterBill.setCode(code);
        enterBill.setNumber(number);
        enterBill.setProductId(productId);
        enterBill.setWarehouseId(warehouseId);
        enterBill.setAddate(new Date());
        enterBill.setCreateTime(new Timestamp(System.currentTimeMillis()));
        enterBill.setUid(user.getUserId());

        stockServiceImpl.insertEnterBill(enterBill);
    }

    @RequestMapping(value = "/housing.html", method = RequestMethod.GET)
    public String housing(HttpServletRequest request, ModelMap model) {

        return AD_HTML + "" + packageName + "/housing";
    }

    @RequestMapping(value = "/housingWy.html", method = RequestMethod.GET)
    public String housingWy(HttpServletRequest request, ModelMap model) {

        return AD_HTML + "" + packageName + "/housingWy";
    }

    @ResponseBody
    @RequestMapping(value = "/queryHousingWy.html", method = RequestMethod.POST)
    public ModelMap queryHousingWy(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        int pageSize = 10;

        int currPage = 1;
        if (request.getParameter("currPage") == null) {
            pageSize = 100000;
        } else {
            currPage = Integer.parseInt(request.getParameter("currPage"));
        }

        String departmentCode = user.getDepartmentCode();
        String housingId = request.getParameter("housingId");
        Pager pager = new Pager(pageSize, currPage);
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", "");
        paramMap.put("remark", "");
        paramMap.put("housingId", housingId);
        List<Housing> list = stockServiceImpl.getHousings(paramMap);

        model.put("list", list);
        model.put("count", stockServiceImpl.getHousingsCount(paramMap));
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);

        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/queryHousing.html", method = RequestMethod.POST)
    public ModelMap getProductList(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        int pageSize = 1;
        int currPage = 1;

        String departmentCode = user.getDepartmentCode();

        Pager pager = new Pager(pageSize, currPage);
        System.out.println(user.getType().equals("saler"));
        System.out.println(user.getSaleUserId());
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", "");
        paramMap.put("remark", "");

        List<Housing> list = stockServiceImpl.getHousings(paramMap);

        model.put("list", list);
        model.put("count", 0);
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);

        return model;

    }

    @RequestMapping(value = "/initQueryStatusHouseing.html", method = RequestMethod.GET)
    public String initQueryStatusHouseing(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");

        Housing housing = stockServiceImpl.getHousingById(new Integer(id));

        model.put("order", housing);

        return AD_HTML + "" + packageName + "/initQueryStatusHouseing";

    }

    @RequestMapping(value = "/initUpdateProprietor.html", method = RequestMethod.GET)
    public String initUpdateProprietor(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");

        Proprietor proprietor = stockServiceImpl.getProprietorById(new Integer(id));

        model.put("order", proprietor);

        return AD_HTML + "" + packageName + "/initUpdateProprietor";
    }

    @ResponseBody
    @RequestMapping(value = "/saveUpdateProprietor.html", method = RequestMethod.POST)
    public ModelMap saveUpdateProprietor(HttpServletRequest request, ModelMap model) {

        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        String card = request.getParameter("card");
        String id = request.getParameter("id");
        String entryDate = request.getParameter("entryDate");
        Proprietor proprietor = stockServiceImpl.getProprietorById(new Integer(id));
        proprietor.setName(name);
        proprietor.setPhone(phone);
        proprietor.setCard(card);
        proprietor.setEntryDate(entryDate);
        stockServiceImpl.saveProprietor(proprietor, proprietor.getId());

        model.put("status", true);
        model.put("msg", "业主信息修改完成！");

        return model;

    }

    @RequestMapping(value = "/initUpdateHouseing.html", method = RequestMethod.GET)
    public String initUpdateHouseing(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");

        Housing housing = stockServiceImpl.getHousingById(new Integer(id));

        model.put("order", housing);

        return AD_HTML + "" + packageName + "/initUpdateHouseing";

    }

    @ResponseBody
    @RequestMapping(value = "/saveUpdateHousing.html", method = RequestMethod.POST)
    public ModelMap saveUpdateHousing(HttpServletRequest request, ModelMap model) {

        String address = request.getParameter("address");
        String community_address = request.getParameter("community_address");
        String token = request.getParameter("token");
        String phone = request.getParameter("phone");
        String payAccount = request.getParameter("payAccount");
        String pid = request.getParameter("pid");
        String community_locations = request.getParameter("community_locations");
        String name = request.getParameter("name");
        String housingId = request.getParameter("id");
        String province_code = request.getParameter("province_code");
        String city_code = request.getParameter("city_code");
        String district_code = request.getParameter("district_code");
        String saleId = request.getParameter("saleId");
        Housing housing = stockServiceImpl.getHousingById(new Integer(housingId));
        housing.setProvince_code(province_code);
        housing.setCity_code(city_code);
        housing.setDistrict_code(district_code);
        housing.setName(name);
        housing.setPhone(phone);
        housing.setAddress(address);
        housing.setCommunity_address(community_address);
        housing.setToken(token.trim());
        housing.setPayAccount(payAccount);
        housing.setCommunity_locations(community_locations.trim());
        housing.setPid(pid);
        housing.setSaleId(Integer.parseInt(saleId));
        stockServiceImpl.updateHousing(housing, new Integer(housingId));

        model.put("status", true);
        model.put("msg", "小区修改完毕！");

        if (housing.getCommunity_id() != null && !housing.getCommunity_id().equals("")) {
            return updateHouseToAliPay(model, token, housing);
        }

        return model;
    }

    private ModelMap updateHouseToAliPay(ModelMap model, String token, Housing housing) {

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String indexToken = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", housing.getCommunity_id());
        bizContent.put("community_name", housing.getName());// 小区名称
        bizContent.put("community_address", housing.getCommunity_address());// 小区主要详细地址
        bizContent.put("district_code", housing.getDistrict_code());// 区县编码，国标码，详见国家统计局数据
        bizContent.put("city_code", housing.getCity_code());// 地级市编码，国标码，详见国家统计局数
        bizContent.put("province_code", housing.getProvince_code());// 省份编码，国标码，详见国家统计局数据
        bizContent.put("community_locations", housing.getCommunity_locations());// ["114.032395|22.519725\","114.032469|22.519336"]   小区所在的经纬度列表（注：需要是高德坐标系），每对经纬度用"|"分隔，经度在前，纬度在后，经纬度小数点后不超过6位。
        bizContent.put("hotline", housing.getPhone());// 需要提供物业服务热线或联系电话，便于用户在需要时联系物业。

        System.out.println("bizContent>>>:" + bizContent.toString());
        System.out.println("token>>>:" + token);
        System.out.println("housing.getCommunity_locations()>>>:" + housing.getCommunity_locations());

        AlipayEcoCplifeCommunityModifyRequest requestAlipayEcoCplifeCommunityCreateRequest = new AlipayEcoCplifeCommunityModifyRequest();
        requestAlipayEcoCplifeCommunityCreateRequest.putOtherTextParam("app_auth_token", indexToken);
        requestAlipayEcoCplifeCommunityCreateRequest.setBizContent(bizContent.toString());

        AlipayEcoCplifeCommunityModifyResponse response = null;

        try {
            response = alipayClient.execute(requestAlipayEcoCplifeCommunityCreateRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println("response>>>:" + response.getBody());

        if (response.getCode().equals("10000")) {
            model.put("status", true);
            model.put("msg", "小区修改完成，并且和支付宝同步完成！");
        } else {
            model.put("status", true);
            model.put("msg", "小区同步失败！" + response.getSubMsg());
        }

        return model;
    }

    @RequestMapping(value = "/addHouseing.html", method = RequestMethod.GET)
    public String addHouseing(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap);

        model.put("departments", departments);

        return AD_HTML + "" + packageName + "/addHouseing";
    }

    @ResponseBody
    @RequestMapping(value = "/saveHousing.html", method = RequestMethod.POST)
    public ModelMap saveHousing(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentId = request.getParameter("department");
        String name = request.getParameter("name").trim();
        String remark = request.getParameter("remark");
        String address = request.getParameter("address");
        String contractPeople = request.getParameter("contractPeople");
        String phone = request.getParameter("phone");
        String payAccount = request.getParameter("payAccount");
        String pid = request.getParameter("pid");
        String community_address = request.getParameter("community_address");
        String province_code = request.getParameter("province_code");
        String city_code = request.getParameter("city_code");
        String district_code = request.getParameter("district_code");
        String community_locations = request.getParameter("community_locations");
        String token = request.getParameter("token");
        String saleUserId = request.getParameter("saleUserId");
        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", Integer.valueOf(departmentId));
        Department department = departmentService.getDepartmentById(paramMap);

        Housing housing = new Housing();
        housing.setSaleId(department.getSaleId());
        housing.setDepartmentId(department.getId());
        housing.setDepartmentCode(department.getCode());
        housing.setName(name);
        housing.setRemark(remark);
        housing.setAddress(address);
        housing.setContractPeople(contractPeople);
        housing.setPhone(phone);
        housing.setPayAccount(payAccount);
        housing.setPid(pid);
        housing.setApp_id(app_id);
        housing.setCreateUid(user.getUserId());
        housing.setCommunity_address(community_address);
        housing.setProvince_code(province_code);
        housing.setCity_code(city_code);
        housing.setDistrict_code(district_code);
        housing.setCommunity_locations(community_locations);
        housing.setPrivate_key(private_key);
        housing.setAlipay_public_key(alipay_public_key);
        housing.setToken(token);
        housing.setSaleId(Integer.parseInt(saleUserId));
        stockServiceImpl.insertHousing(housing);

        model.put("status", true);
        model.put("msg", "小区收录完成！");

        return model;
    }

    @RequestMapping(value = "/addBuilding.html", method = RequestMethod.GET)
    public String addBuilding(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        // 组织机构
        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap2);

        model.put("departments", departments);

        return AD_HTML + "" + packageName + "/addBuilding";
    }

    @ResponseBody
    @RequestMapping(value = "/saveBuilding.html", method = RequestMethod.POST)
    public ModelMap saveBuilding(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String housingId = request.getParameter("housingId");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");

        Housing housing = stockServiceImpl.getHousingById(new Integer(housingId));

        Building building = new Building();
        building.setDepartmentId(housing.getDepartmentId());
        building.setDepartmentCode(housing.getDepartmentCode());
        building.setHouseId(new Integer(housingId));
        building.setName(name);
        building.setCreateUid(user.getUserId());
        stockServiceImpl.insertBuilding(building);

        model.put("status", true);
        model.put("msg", "楼栋收录完成！");

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/quyerBuilding.html", method = RequestMethod.POST)
    public ModelMap queryBuilding(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        int pageSize = 10;
        int currPage = request.getParameter("currPage") == null ? 1 : Integer
                .parseInt(request.getParameter("currPage"));
        String departmentCode = user.getDepartmentCode();
        String housingId = request.getParameter("housingId");
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        paramMap.put("housingId", housingId);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", "");
        paramMap.put("remark", "");

        List<Building> list = stockServiceImpl.getBuildings(paramMap);

        model.put("list", list);
        model.put("count", stockServiceImpl.getBuildingsCount(paramMap));
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);

        return model;

    }

    @RequestMapping(value = "/building.html", method = RequestMethod.GET)
    public String building(HttpServletRequest request, ModelMap model) {
        return AD_HTML + "" + packageName + "/building";
    }

    @RequestMapping(value = "/location.html", method = RequestMethod.GET)
    public String location(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);

        return AD_HTML + "" + packageName + "/location";
    }

    @RequestMapping(value = "/addLocation.html", method = RequestMethod.GET)
    public String addLocation(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        // 组织机构
        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap2);

        model.put("departments", departments);

        return AD_HTML + "" + packageName + "/addLocation";
    }

    @ResponseBody
    @RequestMapping(value = "/queryLocation.html", method = RequestMethod.POST)
    public ModelMap queryLocation(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int pageSize = 10;
        int currPage = Integer.parseInt(request.getParameter("currPage"));
        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String locationId = request.getParameter("locationId");
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        paramMap.put("code", departmentCode);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", "");
        paramMap.put("remark", "");
        paramMap.put("housingId", housingId);
        paramMap.put("buildingId", buildingId);
        paramMap.put("locationId", locationId);
        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);
        Pager pager = new Pager(pageSize, currPage);
        List<Location> list = stockServiceImpl.getLocations(paramMap);

        model.put("list", list);
        model.put("count", stockServiceImpl.getLocationsCount(paramMap));
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);

        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/saveLocation.html", method = RequestMethod.POST)
    public ModelMap saveLocation(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");

        Housing housing = stockServiceImpl.getHousingById(new Integer(housingId));

        Location location = new Location();
        location.setDepartmentId(housing.getDepartmentId());
        location.setDepartmentCode(housing.getDepartmentCode());
        location.setHousingId(new Integer(housingId));
        location.setName(name);
        location.setCreateUid(user.getUserId());
        location.setBuildingId(new Integer(buildingId));
        location.setRemark(remark);
        stockServiceImpl.insertLocation(location);

        model.put("status", true);
        model.put("msg", "单元号收录完成！");

        return model;
    }

    @RequestMapping(value = "/roomInfo.html", method = RequestMethod.GET)
    public String roomInfo(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);

        return AD_HTML + "" + packageName + "/roomInfo";
    }

    @RequestMapping(value = "/proprietor.html", method = RequestMethod.GET)
    public String proprietor(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        return AD_HTML + "" + packageName + "/proprietor";
    }

    @ResponseBody
    @RequestMapping(value = "/queryProprietor.html", method = RequestMethod.POST)
    public ModelMap queryProprietor(HttpServletRequest request, ModelMap model) {

        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String locationId = request.getParameter("locationId");
        String roomId = request.getParameter("roomId");
        SysUsers user = getUser();

        String name = request.getParameter("name");

        Map<String, Object> paramMap = new HashMap<String, Object>();

        int pageSize = 10;

        String departmentCode = user.getDepartmentCode();

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        String entryStatus = request.getParameter("entryStatus");

        Pager pager = new Pager(pageSize, currPage);
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", name);
        paramMap.put("remark", "");
        paramMap.put("housingId", housingId);
        paramMap.put("buildingId", buildingId);
        paramMap.put("locationId", locationId);
        paramMap.put("roomId", roomId);
        List<Proprietor> list = stockServiceImpl.getProprietors(paramMap);

        int sumCount = stockServiceImpl.getProprietorsCount(paramMap);

        pager.setTotalRecord(sumCount);
        model.put("list", list);
        model.put("count", pager.getTotalRecord());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/quyerRoomInfo.html", method = RequestMethod.POST)
    public ModelMap queryRoomInfo(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        String entryStatus = request.getParameter("entryStatus");
        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String locationId = request.getParameter("locationId");
        String sendStatus = request.getParameter("sendStatus");
        int pageSize = 10;
        if (null == request.getParameter("currPage")) {
            pageSize = 1000;
        }
        Pager pager = new Pager(pageSize, currPage);
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", "");
        paramMap.put("remark", "");
        paramMap.put("entryStatus", entryStatus);
        paramMap.put("housingId", housingId);
        paramMap.put("buildingId", buildingId);
        paramMap.put("locationId", locationId);
        paramMap.put("sendStatus", sendStatus);
        List<RoomInfo> list = stockServiceImpl.getRoomInfos(paramMap);

        int sumCount = stockServiceImpl.getRoomInfosCount(paramMap);

        pager.setTotalRecord(sumCount);

        model.put("list", list);
        model.put("count", pager.getTotalRecord());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);

        return model;

    }

    @RequestMapping(value = "/addRoomInfo.html", method = RequestMethod.GET)
    public String addRoomInfo(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);

        // 组织机构
        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap2);

        model.put("departments", departments);

        return AD_HTML + "" + packageName + "/addRoomInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/saveRoomInfo.html", method = RequestMethod.POST)
    public ModelMap saveRoomInfo(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String room = request.getParameter("room");
        String remark = request.getParameter("remark");
        String locationId = request.getParameter("locationId");

        Housing housing = stockServiceImpl.getHousingById(new Integer(housingId));

        Building building = stockServiceImpl.getBuildingById(new Integer(buildingId));

        Location location = stockServiceImpl.getLocationById(new Integer(locationId));

        String out_room_id = stockServiceImpl.getNextOut_room_id("");

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setLocationId(new Integer(locationId));
        roomInfo.setDepartmentId(housing.getDepartmentId());
        roomInfo.setDepartmentCode(housing.getDepartmentCode());
        roomInfo.setHousingId(new Integer(housingId));
        roomInfo.setCreateUid(user.getUserId());
        roomInfo.setBuildingId(new Integer(buildingId));
        roomInfo.setRemark(remark);
        roomInfo.setRoom(room);
        roomInfo.setOut_room_id(out_room_id);

        if (location != null) {
            roomInfo.setAddress(building.getName() + location.getName() + room);
        } else {
            roomInfo.setAddress(building.getName() + room);
        }

        stockServiceImpl.insertRoomInfo(roomInfo);

        model.put("status", true);
        model.put("msg", "房屋信息收录完成！");

        return model;
    }

    @RequestMapping(value = "/proprietorEntry.html", method = RequestMethod.GET)
    public String proprietorEntry(HttpServletRequest request, ModelMap model) {
        String roomId = request.getParameter("id");

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);

        // 组织机构
        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap2);

        model.put("departments", departments);

        RoomInfo roomInfo = stockServiceImpl.getRoomInfoById(new Integer(roomId));
        model.put("result", roomInfo);
        model.put("entryDate", DateUtils.getDate());

        return AD_HTML + "" + packageName + "/proprietorEntry";

    }

    @ResponseBody
    @RequestMapping(value = "/saveProprietorEntry.do", method = RequestMethod.POST)
    public ModelMap saveProprietorEntry(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String roomId = request.getParameter("id");
        String name = request.getParameter("name");
        String card = request.getParameter("card");
        String phone = request.getParameter("phone");
        String entryDate = request.getParameter("entryDate");

        RoomInfo roomInfo = stockServiceImpl.getRoomInfoById(new Integer(roomId));
        roomInfo.setEntryStatus(1);
        stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

        Proprietor proprietor = new Proprietor();
        proprietor.setDepartmentCode(roomInfo.getDepartmentCode());
        proprietor.setDepartmentId(roomInfo.getDepartmentId());
        proprietor.setName(name);
        proprietor.setCard(card);
        proprietor.setRoomInfoId(roomInfo.getId());
        proprietor.setEntryStatus(1);
        proprietor.setCreateUid(user.getUserId());
        proprietor.setPhone(phone);
        proprietor.setEntryDate(entryDate);
        proprietor.setHousingId(roomInfo.getHousingId());
        stockServiceImpl.insertProprietor(proprietor);

        model.put("status", true);
        model.put("msg", "业主入住完成！");

        return model;

    }

    @RequestMapping(value = "/billAccount.html", method = RequestMethod.GET)
    public String billAccount(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);

        return AD_HTML + "" + packageName + "/billAccount";
    }

    @ResponseBody
    @RequestMapping(value = "/queryPayBillAccount.html", method = RequestMethod.POST)
    public ModelMap queryPayBillAccount(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        String departmentCode = user.getDepartmentCode();

        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", "");
        paramMap.put("remark", "");

        List<BillAccount> list = stockServiceImpl.getBillAccounts(paramMap);

        pager.setTotalRecord(stockServiceImpl.getBillAccountsCount(paramMap));

        model.put("list", list);
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());

        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/queryBillAccount.html", method = RequestMethod.POST)
    public ModelMap queryBillAccount(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();
        String proprietorName = request.getParameter("proprietorName");
        String sendStatus = request.getParameter("sendStatus");
        String payStatus = request.getParameter("payStatus");
        String housingId = request.getParameter("housingId");
        String buildingId = request.getParameter("buildingId");
        String locationId = request.getParameter("locationId");
        String proprietorId = request.getParameter("proprietorId");
        String costType = request.getParameter("costType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String roomId = request.getParameter("roomId");
        String startPaymentDate = request.getParameter("startPaymentDate");
        String endPaymentDate = request.getParameter("endPaymentDate");
        Map<String, Object> paramMap = new HashMap<String, Object>();

        int currPage = request.getParameter("currPage") == null ? 1 : Integer.valueOf(request.getParameter("currPage"));

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        if (user.getType().equals("saler")) {
            paramMap.put("saleUserId", user.getSaleUserId());
        }
        String departmentCode = user.getDepartmentCode();
        System.out.println(departmentCode);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("proprietorName", proprietorName);
        paramMap.put("sendStatus", sendStatus);
        paramMap.put("payStatus", payStatus);
        paramMap.put("housingId", housingId);
        paramMap.put("buildingId", buildingId);
        paramMap.put("locationId", locationId);
        paramMap.put("costType", costType);
        paramMap.put("proprietorId", proprietorId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("roomId", roomId);
        if (!payStatus.equals("0")) {
            paramMap.put("startPaymentDate", startPaymentDate);
            paramMap.put("endPaymentDate", endPaymentDate);
        }

        List<BillAccount> list = stockServiceImpl.getBillAccounts(paramMap);

        pager.setTotalRecord(stockServiceImpl.getBillAccountsCount(paramMap));

        QueryResult result = stockServiceImpl.getBillAccountCollect(paramMap);

        System.out.println(result);

        model.put("collect", result.getCollect());
        model.put("list", list);
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());

        return model;

    }

    @RequestMapping(value = "/addBillAccount.html", method = RequestMethod.GET)
    public String addBillAccount(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);
        paramMap.put("currPage", 0);
        paramMap.put("pageSize", 1000);

        List<Housing> housings = stockServiceImpl.getHousings(paramMap);
        model.put("housings", housings);

        List<Building> buildings = stockServiceImpl.getBuildings(paramMap);
        model.put("buildings", buildings);

        List<Location> locations = stockServiceImpl.getLocations(paramMap);
        model.put("locations", locations);

        List<Proprietor> proprietors = stockServiceImpl.getProprietors(paramMap);
        model.put("proprietors", proprietors);

        // 组织机构
        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap2);

        model.put("departments", departments);

        return AD_HTML + "" + packageName + "/addBillAccount";
    }

    @ResponseBody
    @RequestMapping(value = "/saveBillAccount.html", method = RequestMethod.POST)
    public ModelMap saveBillAccount(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String proprietorId = request.getParameter("proprietorId");
        String bill_entry_amount = request.getParameter("bill_entry_amount");
        String release_day = request.getParameter("release_day");
        String deadline = request.getParameter("deadline");
        String acct_period = request.getParameter("acct_period");
        String cost_type = request.getParameter("cost_type");
        request.getParameter("departmentCode");
        String month = DateUtils.getYearMonth();

        String bill_entry_id = stockServiceImpl.getNextBillAccountBill_entry_id("");

        Proprietor proprietor = stockServiceImpl.getProprietorById(new Integer(proprietorId));

        BillAccount billAccount = new BillAccount();
        billAccount.setBill_entry_id(bill_entry_id);
        billAccount.setProprietorId(new Integer(proprietorId));
        billAccount.setDepartmentCode(proprietor.getDepartmentCode());
        billAccount.setDepartmentId(proprietor.getDepartmentId());
        billAccount.setCreateUid(user.getUserId());
        billAccount.setRelease_day(release_day);
        billAccount.setBill_entry_amount(Double.parseDouble(bill_entry_amount));
        billAccount.setDeadline(deadline);
        billAccount.setAcct_period(acct_period);
        billAccount.setMonth(month);
        billAccount.setCost_type(cost_type);
        billAccount.setHousingId(proprietor.getHousingId());
        stockServiceImpl.insertBillAccount(billAccount);

        model.put("status", true);
        model.put("msg", "账单生成成功！");

        return model;

    }
}
