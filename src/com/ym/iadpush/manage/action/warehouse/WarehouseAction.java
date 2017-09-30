/**
 * 
 */
package com.ym.iadpush.manage.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.Warehouse;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;
import com.ym.iadpush.manage.services.warehouse.IWarehouseService;

@Controller
public class WarehouseAction extends BaseAction {
    private @Autowired
    IWarehouseService warehouseServiceImpl;

    private @Autowired
    ICompanyService companyServiceImpl;

    private String packageName = "warehouse";

    @ResponseBody
    @RequestMapping(value = "/deleteWarehouse.html", method = RequestMethod.POST)
    public Object deleteWarehouse(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");

        warehouseServiceImpl.deleteWarehouse(new Integer(id));

        model.put("status", true);
        model.put("msg", "删除仓库成功!");

        return model;
    }

    @RequestMapping(value = "/initUpdateWarehouse.html", method = RequestMethod.GET)
    public Object initUpdateWarehouse(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("id");

        Warehouse warehouse = warehouseServiceImpl.getWarehouseById(new Integer(id));

        model.put("warehouse", warehouse);

        return AD_HTML + "" + packageName + "/updateWarehouse";

    }

    @ResponseBody
    @RequestMapping(value = "/updateWarehouse.html", method = RequestMethod.POST)
    public Object updateWarehouse(HttpServletRequest request, ModelMap model) {

        String id = request.getParameter("warehouseId");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String mobilePhone = request.getParameter("mobilePhone");
        String contract_people = request.getParameter("contract_people");

        Warehouse warehouse = warehouseServiceImpl.getWarehouseById(new Integer(id));
        warehouse.setPhone(phone);
        warehouse.setMobilePhone(mobilePhone);
        warehouse.setContract_people(contract_people);
        warehouse.setName(name);
        warehouse.setAddress(address);
        warehouseServiceImpl.updateWarehouse(warehouse, new Integer(id));

        model.put("status", true);
        model.put("msg", "仓库修改成功！");

        return model;

    }

    @RequestMapping(value = "/warehouseInput.html", method = RequestMethod.GET)
    public Object warehouseInput(HttpServletRequest request, ModelMap model) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", new Integer(4));
        paramMap.put("currPage", 0);
        paramMap.put("pageSize", Integer.MAX_VALUE);
        List<CompanyInfo> list = companyServiceImpl.getAllFactorys(paramMap);
        model.put("companyInfos", list);

        return AD_HTML + "" + packageName + "/warehouseInput";
    }

    @ResponseBody
    @RequestMapping(value = "/saveWarehouse.html", method = RequestMethod.POST)
    public Object saveWarehouse(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String contract_people = request.getParameter("contract_people");
        String mobilePhone = request.getParameter("mobilePhone");
        String city = request.getParameter("city");
        String company = request.getParameter("companyId");
        Integer companyId = 0;

        if (company != null && !company.equals("")) {
            companyId = new Integer(company);
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setUid(user.getUserId());
        warehouse.setCompanyId(companyId);
        warehouse.setName(name);
        warehouse.setAddress(address);
        warehouse.setCity(city);
        warehouse.setContract_people(contract_people);
        warehouse.setMobilePhone(mobilePhone);
        warehouse.setPhone(phone);
        warehouseServiceImpl.insertWarehouse(warehouse);

        model.put("status", true);
        model.put("msg", "仓库收录完成！");

        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/queryWarehouseList.html", method = RequestMethod.POST)
    public Object queryWarehouseList(HttpServletRequest request, ModelMap model) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            List<Warehouse> listWarehouses = warehouseServiceImpl.getAllWarehouses(params);
            model.put("list", listWarehouses);
            model.put("count", listWarehouses.size());
            model.put("currPage", 0);
            model.put("pageSize", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/warehouse_list.html", method = RequestMethod.GET)
    public String warehouse_list(HttpServletRequest request, ModelMap model) {
        return AD_HTML + packageName + "/warehouseList";
    }

}
