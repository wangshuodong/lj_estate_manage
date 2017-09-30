/**
 * 
 */
package com.ym.iadpush.manage.action.account;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Account;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.account.AccountService;


@Controller
public class AccountAction extends BaseAction {

    @Autowired
    private AccountService accountService;

    private static String FLODER = "account";

 
    @ResponseBody
    @RequestMapping(value = "/account_delete.do", method = RequestMethod.POST)
    public ModelMap delete(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        // 角色类型
        String assortment = user.getAssortment();

        if (!assortment.equals("manger")) {
            model.put("status", true);
            model.put("msg", "你没有删除权限!");
            return model;
        }

        try {
            String id = request.getParameter("id");
            accountService.delById(Integer.valueOf(id));
            model.put("status", true);
            model.put("msg", "账户维护成功");
        } catch (Exception e) {
            model.put("status", false);
            model.put("msg", "操作失败");
            LogFactory.getLog(AccountAction.class).error("删除账户时异常", e);
        }
        return model;
    }


    @RequestMapping(value = "/account_update.html", method = RequestMethod.GET)
    public String jumpToUpdate(HttpServletRequest request, ModelMap model) {

        try {
            String id = request.getParameter("id");
            Account a = accountService.findById(Integer.valueOf(id));
            model.put("result", a);
        } catch (Exception e) {
            LogFactory.getLog(AccountAction.class).error("查询账户信息异常", e);
        }
        return AD_HTML + FLODER + "/account_update";
    }


    @ResponseBody
    @RequestMapping(value = "/account_update.do", method = RequestMethod.POST)
    public ModelMap updateAccount(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        // 角色类型
        String assortment = user.getAssortment();

        if (!assortment.equals("manger")) {
            model.put("status", true);
            model.put("msg", "你没有修改权限!");
            return model;
        }

        String name = request.getParameter("name");
        String bankNo = request.getParameter("bankNo");
        String bank = request.getParameter("bank");
        String bankName = request.getParameter("bankName");
        String bankAddress = request.getParameter("bankAddress");
        String contract_phone = request.getParameter("contract_phone");
        String contract_people = request.getParameter("contract_people");
        String id = request.getParameter("id");

        Account a = new Account();
        a.setName(name);
        a.setBankNo(bankNo);
        a.setBank(bank);
        a.setBankName(bankName);
        a.setBankAddress(bankAddress);
        a.setContract_people(contract_people);
        a.setContract_phone(contract_phone);

        try {
            a.setId(Integer.valueOf(id));
            accountService.update(a);
            model.put("status", true);
            model.put("msg", "账户维护成功");
        } catch (Exception e) {
            model.put("status", false);
            model.put("msg", "操作失败");
            LogFactory.getLog(AccountAction.class).error("添加账户时异常", e);
        }
        return model;
    }


    @ResponseBody
    @RequestMapping(value = "/account_add.do", method = RequestMethod.POST)
    public ModelMap addAcount(HttpServletRequest request, ModelMap model) {

        String name = request.getParameter("name");
        String bankNo = request.getParameter("bankNo");
        String bank = request.getParameter("bank");
        String bankName = request.getParameter("bankName");
        String bankAddress = request.getParameter("bankAddress");
        String contract_phone = request.getParameter("contract_phone");
        String contract_people = request.getParameter("contract_people");

        Account a = new Account();
        a.setName(name);
        a.setBankNo(bankNo);
        a.setBank(bank);
        a.setBankName(bankName);
        a.setBankAddress(bankAddress);
        a.setContract_people(contract_people);
        a.setContract_phone(contract_phone);

        try {
            accountService.insert(a);
            model.put("status", true);
            model.put("msg", "操作成功");
        } catch (Exception e) {
            model.put("status", false);
            model.put("msg", "操作失败");
            LogFactory.getLog(AccountAction.class).error("添加账户时异常", e);
        }

        return model;
    }


    @RequestMapping(value = "/account_add.html", method = RequestMethod.GET)
    public String jumpToAdd(HttpServletRequest request, ModelMap model) {
        return AD_HTML + FLODER + "/account_add";
    }


    @RequestMapping(value = "/account_list.html", method = RequestMethod.GET)
    public String jumpToList(HttpServletRequest request, ModelMap model) {
        return AD_HTML + FLODER + "/account_list";
    }

    @ResponseBody
    @RequestMapping(value = "/account_list.do", method = RequestMethod.POST)
    public ModelMap list(HttpServletRequest request, ModelMap model) {

        String currPage = request.getParameter("currPage");
        String productName = request.getParameter("productName");

        Map<String, Object> params = new HashMap<String, Object>();
        if (currPage == null || currPage.trim().equalsIgnoreCase("")) {
            params.put("currPage", Integer.valueOf(0));
        } else {
            params.put("currPage", Integer.valueOf(currPage.trim()) - 1);
        }
        if (productName != null && !productName.trim().equalsIgnoreCase("")) {
            params.put("productName", productName.trim());
        }
        params.put("pageSize", this.defaultPageSize);

        try {
            QueryResult result = accountService.query(params);
            model.put("list", result.getData());
            model.put("count", result.getCount());
            model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage"))) + 1);
            model.put("pageSize", params.get("pageSize"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}
