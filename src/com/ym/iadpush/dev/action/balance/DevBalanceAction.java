package com.ym.iadpush.dev.action.balance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.base.Pager;
import com.ym.iadpush.dev.service.balance.DevBalanceService;
import com.ym.iadpush.manage.entity.Balance;

@Controller
public class DevBalanceAction extends BaseAction {
    private @Autowired
    DevBalanceService balanceService;

    @RequestMapping(value = "/countEarn_devloper.html", method = RequestMethod.GET)
    public String countEarnInit_devloper(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return AD_HTML + "balance/countEarn_devloper";
    }

    @ResponseBody
    @RequestMapping(value = "/countEarn_devloper.html", method = RequestMethod.POST)
    public Object queryCountEarn_devloper(HttpServletRequest request, ModelMap model) {
        String startDate = getRequestParams(String.class, request, "startDate");
        String endDate = getRequestParams(String.class, request, "endDate");
        // 页码
        String pageNo = request.getParameter("pageNo");

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("uid", getUser().getUserId());
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<Map<String, String>> list = balanceService.countEarn(paramMap);
        // 汇总
        Map<String, String> summary = balanceService.sumEarn(paramMap);
        // 总记录数
        int totalRecord = balanceService.selectTotalRecord(paramMap);

        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);
        hashMap.put("summary", summary);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }

    @RequestMapping(value = "/countEarn.html", method = RequestMethod.GET)
    public String countEarnInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return AD_HTML + "balance/countEarn";
    }

    @ResponseBody
    @RequestMapping(value = "/countEarn.html", method = RequestMethod.POST)
    public Object queryCountEarn(HttpServletRequest request, ModelMap model) {
        String startDate = getRequestParams(String.class, request, "startDate");
        String endDate = getRequestParams(String.class, request, "endDate");
        // 页码
        String pageNo = request.getParameter("pageNo");

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("uid", getUser().getUserId());
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<Map<String, String>> list = balanceService.countEarn(paramMap);
        // 汇总
        Map<String, String> summary = balanceService.sumEarn(paramMap);
        // 总记录数
        int totalRecord = balanceService.selectTotalRecord(paramMap);

        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);
        hashMap.put("summary", summary);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }

    @RequestMapping(value = "/devBalance.html", method = RequestMethod.GET)
    public String devBalanceInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return AD_HTML + "balance/devBalance";
    }

    @ResponseBody
    @RequestMapping(value = "/devBalance.html", method = RequestMethod.POST)
    public Object queryDevBalance(HttpServletRequest request, ModelMap model) {
        String status = getRequestParams(String.class, request, "status");
        String edate = getRequestParams(String.class, request, "edate");
        // 页码
        String pageNo = request.getParameter("pageNo");

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", status);
        paramMap.put("edate", edate);
        paramMap.put("uid", getUser().getUserId());
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<Balance> list = balanceService.findDevBalances(paramMap);
        // 总记录数
        int totalRecord = balanceService.SelectTotalDevBalances(paramMap);

        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }
}
