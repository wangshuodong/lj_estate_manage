package com.ym.iadpush.manage.action.balance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.base.Pager;
import com.ym.common.utils.DateUtils;
import com.ym.common.utils.ExcelUtil;
import com.ym.iadpush.manage.entity.BalanceCollect;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.UserBalance;
import com.ym.iadpush.manage.services.balance.IBalanceService;
import com.ym.iadpush.manage.services.common.impl.ConfigMgrSv;

@Controller
public class BalanceAction extends BaseAction {

    private @Autowired
    IBalanceService balanceService;
    private @Autowired
    ConfigMgrSv configMgrSv;
    private String packageName = "balance";

    @RequestMapping(value = "/balance.html", method = RequestMethod.GET)
    public String queryBalanceInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        getSession().removeAttribute("balances");

        return AD_HTML + packageName + "/balance";
    }

    @RequestMapping(value = "/dev_balance.html", method = RequestMethod.GET)
    public String dev_balance(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        getSession().removeAttribute("balances");

        return AD_HTML + packageName + "/dev_balance";
    }

    @RequestMapping(value = "/dev_balance_devloper.html", method = RequestMethod.GET)
    public String dev_balance_devloper(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        getSession().removeAttribute("balances");

        return AD_HTML + packageName + "/dev_balance_devloper";
    }

    @ResponseBody
    @RequestMapping(value = "/getCountAccountData.html", method = RequestMethod.POST)
    public Object queryBalanceList_devloper(HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String housingId = request.getParameter("housingId");
        String saleUserId = request.getParameter("saleUserId");
        String startPaymentDate = request.getParameter("startPaymentDate");
        String endPaymentDate = request.getParameter("endPaymentDate");
        SysUsers user = getUser();
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        if (user.getType().equals("saler")) {
            params.put("saleUserId", user.getSaleUserId());
        } else {
            params.put("saleUserId", saleUserId);
        }
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("startPaymentDate", startPaymentDate);
        params.put("endPaymentDate", endPaymentDate);
        params.put("departmentCode", user.getDepartmentCode());
        if (housingId.isEmpty()) {
            params.put("housingId", null);
        } else {
            params.put("housingId", housingId);
        }
        hashMap.put("data", balanceService.getCountAccountData(params));
        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/queryBalance_devloper.html", method = RequestMethod.POST)
    public Object queryBalanceList_devloper(HttpServletRequest request, ModelMap model) {
        String status = request.getParameter("status");
        String certification = request.getParameter("certification");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String edate = request.getParameter("edate");
        // 页码
        String pageNo = request.getParameter("pageNo");

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 5;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();

        // 判断如果是开发者，则查询属于自己的账单
        paramMap.put("userId", getUser().getUserId());

        // 判断当前用户角色类型是否为渠道服务
        if (getUser().getRoleType().equals("outService")) {
            paramMap.put("serviceId", getUser().getUserId());
        }
        paramMap.put("status", status);
        paramMap.put("certification", certification);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("edate", edate);
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<UserBalance> list = balanceService.findUserBlances(paramMap);

        // 缓存账单信息,用于批量下载
        getSession().setAttribute("balances", paramMap);

        // 总记录数
        int totalRecord = balanceService.selectTotalRecord(paramMap);

        // 汇总
        BalanceCollect collect = balanceService.collectUserBlances(paramMap);

        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);
        hashMap.put("collect", collect == null ? new BalanceCollect() : collect);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }

    @RequestMapping(value = "/settle.html", method = RequestMethod.GET)
    public String settleInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        return AD_HTML + packageName + "/settle";
    }

    @ResponseBody
    @RequestMapping(value = "/queryBalance.html", method = RequestMethod.POST)
    public Object queryBalanceList(HttpServletRequest request, ModelMap model) {
        String status = request.getParameter("status");
        String uid = request.getParameter("uid");
        String certification = request.getParameter("certification");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String edate = request.getParameter("edate");
        // 页码
        String pageNo = request.getParameter("pageNo");

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 5;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();

        // 判断如果是开发者，则查询属于自己的账单
        if (getUser().getRoleId() == 2 && getUser().getRoleType().equalsIgnoreCase("dev")) {
            paramMap.put("userId", getUser().getUserId());
        }

        // 判断当前用户角色类型是否为渠道服务
        if (getUser().getRoleType().equals("outService")) {
            paramMap.put("serviceId", getUser().getUserId());
        }
        paramMap.put("status", status);
        paramMap.put("uid", uid);
        paramMap.put("certification", certification);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("edate", edate);
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<UserBalance> list = balanceService.findUserBlances(paramMap);
        // 缓存账单信息,用于批量下载
        getSession().setAttribute("balances", paramMap);
        // 总记录数
        int totalRecord = balanceService.selectTotalRecord(paramMap);
        // 汇总
        BalanceCollect collect = balanceService.collectUserBlances(paramMap);
        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);
        hashMap.put("collect", collect == null ? new BalanceCollect() : collect);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/settle.html", method = RequestMethod.POST)
    /**
     * 结算
     */
    public Object settleAccount(HttpServletRequest request, ModelMap model) {
        String edate = request.getParameter("settleDate");
        String pass = request.getParameter("settlePass");
        String token = request.getParameter("token");

        String lastToken = (String) getSession().getAttribute("token");

        if (lastToken != null && lastToken.trim().equalsIgnoreCase(token)) {
            model.put(MESSAGE, "请勿重复提交数据");
            model.put("status", false);
        } else {

            String balancePass = configMgrSv.selectByKey("balancePassword");

            if (StringUtils.isBlank(pass) || !balancePass.equals(pass.trim())) {
                model.put(MESSAGE, "密码错误");
                model.put("status", false);
            } else {
                synchronized (this) {
                    balanceService.settleAccount(edate, getUser().getUserId());
                }
                model.put(MESSAGE, "结算成功");
                model.put("status", true);
            }
        }
        model.put("token", getToken());
        return model;
    }

    @RequestMapping(value = "/export.html", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        // 查询参数
        Map<String, Object> paramMap = (Map<String, Object>) getSession().getAttribute("balances");

        if (paramMap != null) {
            try {
                paramMap.remove("pageSize");
                paramMap.remove("begRow");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        List<UserBalance> balanceList = balanceService.findUserBlances(paramMap);

        // 汇总
        BalanceCollect collect = balanceService.collectUserBlances(paramMap);

        List<List<Comparable>> listData = new ArrayList<List<Comparable>>();

        // 增加标题行
        listData.add(setHeader());

        String temp = "";
        for (int i = 0; i < balanceList.size(); i++) {
            UserBalance info = balanceList.get(i);
            List<Comparable> list = new ArrayList<Comparable>();

            list.add(i + 1);
            list.add(info.getUsername());
            list.add(info.getBalanceId());
            list.add(info.getQq());
            if ("0".equals(info.getCertification())) {
                temp = "待上传";
            } else if ("1".equals(info.getCertification())) {
                temp = "待审核";
            } else if ("2".equals(info.getCertification())) {
                temp = "已认证";
            }

            list.add(temp);
            temp = info.getSdate() + "至" + info.getEdate();
            list.add(temp);
            list.add(String.valueOf(info.getEarn()));
            list.add(String.valueOf(info.getBonus()));
            list.add(String.valueOf(info.getFee()));
            list.add(String.valueOf(info.getEarnNoFee()));
            list.add(info.getProvince());
            list.add(info.getCity());
            list.add(info.getBankname());
            list.add(info.getBankAddress());
            list.add(info.getBankUserName());
            list.add(info.getBankNo());
            list.add(info.getCertificate());
            if (info.getStatus() == 0) {
                temp = "未付款";
            } else if (info.getStatus() == 1) {
                temp = "已付款";
            } else if (info.getStatus() == 2) {
                temp = "已拒绝";
            }

            list.add(temp);
            list.add(info.getPayDate());
            list.add(info.getRemark());
            listData.add(list);
        }

        // 合计行
        List<Comparable> sumList = new ArrayList<Comparable>();
        sumList.add("");
        sumList.add("合计");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add(String.valueOf(collect != null ? collect.getEarn() : 0));
        sumList.add(String.valueOf(collect != null ? collect.getBonus() : 0));
        sumList.add(String.valueOf(collect != null ? collect.getFee() : 0));
        sumList.add(String.valueOf(collect != null ? collect.getEarnNofee() : 0));
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");
        sumList.add("");

        listData.add(sumList);

        String fileName = "账单管理";
        ExcelUtil.createExcel(request, response, getLog(this), listData, fileName);
    }

    private List<Comparable> setHeader() {
        List<Comparable> list = new ArrayList<Comparable>();
        list.add("序号");
        list.add("用户名");
        list.add("ID");
        list.add("QQ");
        list.add("身份证状态");
        list.add("结算周期");
        list.add("税前金额");
        list.add("奖励金额");
        list.add("代扣税金额");
        list.add("实际金额");
        list.add("省");
        list.add("市");
        list.add("银行");
        list.add("开户行名称");
        list.add("账户名");
        list.add("卡号");
        list.add("身份证号");
        list.add("状态");
        list.add("付款日期");
        list.add("备注");

        return list;
    }

    /**
     * 付款
     */
    @ResponseBody
    @RequestMapping(value = "/payBalance.html", method = RequestMethod.POST)
    public Object payBalance(HttpServletRequest request, ModelMap model) {
        Integer balanceId = getRequestParams(Integer.class, request, "id");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("balanceId", balanceId);
        paramsMap.put("status", getRequestParams(Integer.class, request, "status"));
        paramsMap.put("payUid", getUser().getUserId());
        paramsMap.put("payDate", getRequestParams(String.class, request, "payDate"));
        paramsMap.put("remark", getRequestParams(String.class, request, "remark"));
        paramsMap.put("payTime", new Timestamp(System.currentTimeMillis()));

        Map<String, Object> result = getResultMap();
        if (balanceService.updateStatus(paramsMap) != 0) {
            result.put(RESULT, true);
            result.put(MESSAGE, "操作成功");
        } else {
            result.put(RESULT, false);
            result.put(MESSAGE, "操作失败");
        }

        return result;
    }

    private boolean contains(String fied) {
        List<String> list = new ArrayList<String>();
        list.add("payedAll");
        list.add("refuseAll");
        return list.contains(fied);
    }

    @ResponseBody
    @RequestMapping(value = "/batchPayBalance.html", method = RequestMethod.POST)
    public ModelMap batchPayBalance(HttpServletRequest request, ModelMap model) {

        // 账单id
        String ids = request.getParameter("ids");
        // 操作类型
        String oper_type = request.getParameter("oper_type");
        // 付款时间
        String pay_date = request.getParameter("pay_date");
        // 备注
        String note = request.getParameter("note");

        if (oper_type != null && contains(oper_type.trim())) {
            // 查询参数
            Map<String, Object> paramMap = (Map<String, Object>) getSession().getAttribute("balances");

            if (paramMap != null) {
                try {
                    paramMap.remove("pageSize");
                    paramMap.remove("begRow");
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            List<UserBalance> balanceList = balanceService.findUserBlances(paramMap);
            String balanceIds = "";
            boolean flag = false;
            for (UserBalance balance : balanceList) {
                balanceIds += flag ? "," + balance.getBalanceId() : balance.getBalanceId();
                flag = true;
            }
            ids = balanceIds;
        }

        if (pay_date == null || pay_date.trim().equalsIgnoreCase("")) {
            pay_date = DateUtils.getDate();
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("balanceIds", ids);
        params.put("note", note);
        params.put("pay_uid", getUser().getUserId());
        params.put("payTime", new Timestamp(System.currentTimeMillis()));
        params.put("payDate", pay_date);

        if ("payedAll".trim().equalsIgnoreCase(oper_type)) {
            params.put("status", 1);
        } else if ("payselected".trim().equalsIgnoreCase(oper_type)) {
            params.put("status", 1);
        } else if ("refuseAll".trim().equalsIgnoreCase(oper_type)) {
            params.put("status", 2);
        } else if ("refuseSelected".trim().equalsIgnoreCase(oper_type)) {
            params.put("status", 2);
        } else {
            params.put("status", 0);
        }

        try {
            int count = balanceService.updateStatusByBatch(params);
            model.put("status", true);
            model.put("msg", "操作成功,本次操作共计修改了【" + count + "】条记录");
        } catch (Exception e) {
            // TODO: handle exception
            model.put("status", false);
            model.put("msg", "操作失败");
        }

        return model;
    }
}
