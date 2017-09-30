package com.ym.iadpush.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ym.common.base.BaseAction;
import com.ym.iadpush.common.services.BankService;


@Controller
public class BankAction extends BaseAction {
    
    private static String FLODER = "bank/";
    
    @Autowired
    private BankService bankService;

    public BankService getBankService() {
        return bankService;
    }

    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }
    
    @RequestMapping(value = "/ceshi.html", method = RequestMethod.GET)
    public void ceshicao(HttpServletRequest request,ModelMap model){
        System.out.println("什么情况");
    }
    

    @RequestMapping(value="/query_bank.html",method=RequestMethod.POST)
    public String query(HttpServletRequest request,ModelMap model){
        String bankName = request.getParameter("bankName");
        model.put("list", bankService.query(null));
        model.put("bankName", bankName);
        return AD_HTML+FLODER+"/bank";
    }
    
    
    
}
