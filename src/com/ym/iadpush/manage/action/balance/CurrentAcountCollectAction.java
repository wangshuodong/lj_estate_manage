/**
 * 
 */
package com.ym.iadpush.manage.action.balance;

import java.util.ArrayList;
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
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.CurrentAccountCollect;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.balance.IBalanceService;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;

@Controller
public class CurrentAcountCollectAction extends BaseAction {
    private @Autowired
    IBalanceService balanceService;

    private @Autowired
    ICompanyService companyServiceImpl;

    private String packageName = "balance";

    @RequestMapping(value = "/currentAcountCollect.html", method = RequestMethod.GET)
    public String currentAcountCollect(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return AD_HTML + packageName + "/currentAcountCollect";
    }

    @ResponseBody
    @RequestMapping(value = "/queryCurrentAcountCollect.html", method = RequestMethod.POST)
    public Object queryCurrentAcountCollect(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 角色类型
        String assortment = user.getAssortment();
        String dqsaleCode = request.getParameter("dqsale");
        String ser = request.getParameter("ser");
        String month = request.getParameter("month");
        String companyName = request.getParameter("companyName");
        String djsale = request.getParameter("djsale");
        String factory = request.getParameter("factory");

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();

        // 经销商
        if (djsale != null && !djsale.equals("")) {
            paramMap.put("djcompanyName", djsale.trim());
        }

        // 总统筹
        List<CompanyInfo> listSales = new ArrayList<CompanyInfo>();
        Map<String, Object> paramMap3 = new HashMap<String, Object>();
        paramMap3.put("assortment", "dqsale");
        paramMap3.put("currPage", 0);
        paramMap3.put("pageSize", Integer.MAX_VALUE);
        listSales = companyServiceImpl.getAllSales(paramMap3);

        paramMap.put("factory", factory);
        paramMap.put("dqsaleCode", dqsaleCode);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("companyName", companyName);
        paramMap.put("month", month);

        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            paramMap.put("companyId", String.valueOf(user.getCompanyId()));
        }

        List<CurrentAccountCollect> list = balanceService.queryCurrentAccountCollect(paramMap);

        pager.setTotalRecord(balanceService.queryCurrentAccountCollectCount(paramMap));

        QueryResult result = balanceService.getAccountCollectDataStatement(paramMap);

        model.put("collect", result.getCollect());
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("list", list);
        model.put("dqsaleCode", dqsaleCode);
        model.put("listSales", listSales);

        return model;

    }
}
