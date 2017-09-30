/**
 * 
 */
package com.ym.iadpush.manage.action.saleuser;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.Constants;
import com.ym.common.utils.MD5Encrypt;
import com.ym.iadpush.manage.entity.SaleUser;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.saleuser.SaleUserService;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年8月1日 上午11:46:54 
 * @Version: $Id$
 * @Desc: <p></p>
 */
@Controller
public class SaleUserAction {
    @Autowired
    private SaleUserService saleUserService;
    private @Autowired
    IUserMgr userService;
    @ResponseBody
    @RequestMapping(value = "/addSaleUser.html", method = RequestMethod.POST)
    public ModelMap addSaleUser(HttpServletRequest request, ModelMap model) {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        SaleUser saleUser = new SaleUser();
        saleUser.setName(name);
        saleUser.setPhone(phone);
        saleUserService.insertSaleUser(saleUser);
        SysUsers user = new SysUsers();
        user.setNick(name);
        user.setUsername(phone);
        user.setStatus(1);
        user.setCreateDate(new Date());
        user.setPhone(phone);
        user.setRegeditTime(new Date());
        user.setType("saler");
        user.setDepartmentId(1);
        user.setDepartmentCode("001");
        user.setSaleUserId(saleUser.getId());
        userService.addUser(user, 15);
        model.put("state", 0);
        return model;
    }
    @ResponseBody
    @RequestMapping(value = "/getAllSaleUser.html", method = RequestMethod.POST)
    public ModelMap getAllSaleUser(HttpServletRequest request, ModelMap model) {
        List<SaleUser> list = saleUserService.getALlSaleUser();
        model.put("saleUser", list);
        return model;
    }
}
