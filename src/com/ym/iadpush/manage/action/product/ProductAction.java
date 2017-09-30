/**
 * 
 */
package com.ym.iadpush.manage.action.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.ym.common.utils.DateUtils;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Building;
import com.ym.iadpush.manage.entity.Location;
import com.ym.iadpush.manage.entity.Product;
import com.ym.iadpush.manage.entity.Product_property;
import com.ym.iadpush.manage.services.product.IProductService;
import com.ym.iadpush.manage.services.stock.IStockService;

@Controller
public class ProductAction extends BaseAction {

    private static String FlODER = "product";

    @Autowired
    private IProductService iProductService;

    private @Autowired
    IStockService stockServiceImpl;

    @ResponseBody
    @RequestMapping(value = "/getBuidingsById.html", method = RequestMethod.POST)
    public ModelMap getBuidingsById(HttpServletRequest request, ModelMap model) {
        String houseId = request.getParameter("id");
        
        System.out.println("houseId》》："+houseId);

        List<Building> buildings = null;

        if (houseId != null && !houseId.equals("")) {
            buildings = stockServiceImpl.getBuildingsByHouseId(new Integer(houseId));
            model.put("list", buildings);
        } else {
            model.put("list", buildings);
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/getLocationsByBuildingId.html", method = RequestMethod.POST)
    public ModelMap getLocationsByBuildingId(HttpServletRequest request, ModelMap model) {

        String buildingId = request.getParameter("id");

        List<Location> locations = null;

        if (buildingId != null && !buildingId.equals("")) {
            locations = stockServiceImpl.getLocationsByBuildingId(new Integer(buildingId));
            model.put("list", locations);
        } else {
            model.put("list", locations);
        }

        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/product_update.do", method = RequestMethod.POST)
    public ModelMap store(HttpServletRequest request, ModelMap model) {
        String pid = request.getParameter("id");
        String productName = request.getParameter("productName");
        String price = request.getParameter("price");
        String unit = request.getParameter("unit");
        String material = request.getParameter("material");
        String manufacture = request.getParameter("manufacture");
        String productDate = request.getParameter("productDate");
        String endDate = request.getParameter("endDate");
        String weight = request.getParameter("weight");
        String standard = request.getParameter("standard");
        String remark = request.getParameter("remark");
        String code = request.getParameter("code");
        String facadeDesc = request.getParameter("facadeDesc");

        Product product = new Product();

        product.setName(productName);
        product.setCode(code);

        try {
            product.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
        } catch (Exception e) {
        }

        try {
            product.setId(Integer.valueOf(pid));
        } catch (Exception e) {
        }

        product.setFacadeDesc(facadeDesc);
        product.setUnit(unit);
        product.setMaterial(material);
        product.setManufacture(manufacture);

        try {
            product.setProductDate(DateUtils.parseDate(productDate));
        } catch (Exception e) {
            product.setProductDate(new Date());

        }

        try {
            product.setEndDate(DateUtils.parseDate(endDate));
        } catch (Exception e) {
            product.setEndDate(new Date());
        }

        try {
            product.setWeight(BigDecimal.valueOf(Double.valueOf(weight)));
        } catch (Exception e) {

        }

        product.setStandard(standard);
        product.setRemark(remark);

        try {
            iProductService.updateById(product);
            model.put("status", true);
            model.put("msg", "产品修改成功");
        } catch (Exception e) {
            model.put("status", false);
            model.put("msg", "产品修改失败");
            LogFactory.getLog(ProductAction.class).error("修改产品时异常", e);
        }

        return model;
    }

    @RequestMapping(value = "/product_update.html", method = RequestMethod.GET)
    public String jumpToUpdate(HttpServletRequest request, ModelMap model) {
        String pid = request.getParameter("pid");
        try {
            if (pid != null && !pid.trim().equalsIgnoreCase("")) {
                Product p = iProductService.findById(Integer.valueOf(pid));
                model.put("result", p);
            }
        } catch (Exception e) {
            LogFactory.getLog(ProductAction.class).error("查询产品时异常", e);
        }
        return AD_HTML + "" + FlODER + "/product_update";
    }

    @ResponseBody
    @RequestMapping(value = "/product_del.do", method = RequestMethod.POST)
    public ModelMap delById(HttpServletRequest request, ModelMap model) {
        String pid = request.getParameter("pid");
        if (pid != null && !pid.trim().equalsIgnoreCase("")) {
            try {
                iProductService.delById(Integer.valueOf(pid.trim()));
                model.put("status", true);
                model.put("msg", "删除成功");
            } catch (Exception e) {
                model.put("status", false);
                model.put("msg", "产品id不允许为空");
                LogFactory.getLog(ProductAction.class).error("删除产品时异常", e);
            }
        } else {
            model.put("status", false);
            model.put("msg", "产品id不允许为空");
        }
        return model;
    }

    @RequestMapping(value = "/product_property.html", method = RequestMethod.GET)
    public Object product_property(HttpServletRequest request, ModelMap model) {

        List<Product> list = iProductService.getAllProducts();

        model.put("products", list);

        return AD_HTML + FlODER + "/product_property";
    }

    @ResponseBody
    @RequestMapping(value = "/saveProduct_property.html", method = RequestMethod.POST)
    public ModelMap saveProduct_property(HttpServletRequest request, ModelMap model) {

        String productId = request.getParameter("productId");
        String color = request.getParameter("color");
        String thickness = request.getParameter("thickness");
        String length = request.getParameter("length");

        Product_property product_property = new Product_property();
        product_property.setLength(length);
        product_property.setColor(color);
        product_property.setThickness(thickness);
        product_property.setProductId(Integer.valueOf(productId));

        iProductService.insertProduct_property(product_property);

        model.put("status", true);
        model.put("msg", "产品属性设定成功！");

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/product_add.html", method = RequestMethod.POST)
    public ModelMap save_product_add(HttpServletRequest request, ModelMap model) {
        String productName = request.getParameter("productName");
        String price = request.getParameter("price");
        String unit = request.getParameter("unit");
        String material = request.getParameter("material");
        String manufacture = request.getParameter("manufacture");
        String productDate = request.getParameter("productDate");
        String endDate = request.getParameter("endDate");
        String weight = request.getParameter("weight");
        String standard = request.getParameter("standard");
        String remark = request.getParameter("remark");
        String code = request.getParameter("code");
        String facadeDesc = request.getParameter("facadeDesc");

        Product product = new Product();
        product.setName(productName);

        try {
            product.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
        } catch (Exception e) {
        }

        product.setCode(code);
        product.setUnit(unit);
        product.setMaterial(material);
        product.setManufacture(manufacture);
        product.setFacadeDesc(facadeDesc);

        try {
            product.setProductDate(DateUtils.parseDate(productDate));
        } catch (Exception e) {
            product.setProductDate(DateUtils.getCurDate());
        }

        try {
            product.setEndDate(DateUtils.parseDate(endDate));
        } catch (Exception e) {
            product.setEndDate(DateUtils.getCurDate());
        }

        try {
            product.setWeight(BigDecimal.valueOf(Double.valueOf(weight)));
        } catch (Exception e) {
            product.setWeight(new BigDecimal(0));
        }

        product.setStandard(standard);
        product.setRemark(remark);

        try {
            product.setUid(super.getUser().getUserId());
            iProductService.insertProduct(product);
            model.put("msg", "操作成功");
            model.put("status", true);
        } catch (Exception e) {
            model.put("msg", "发生异常请重新操作");
            model.put("status", false);
        }

        model.put("status", true);
        model.put("msg", "产品收录完成！");

        return model;
    }

    @RequestMapping(value = "/product_add.html", method = RequestMethod.GET)
    public String jumpToAdd(HttpServletRequest request, ModelMap model) {
        return AD_HTML + "" + FlODER + "/product_add";
    }

    @RequestMapping(value = "/product_list.html", method = RequestMethod.GET)
    public String jumpToProductList(HttpServletRequest request, ModelMap model) {
        return AD_HTML + "" + FlODER + "/product_list";
    }

    @ResponseBody
    @RequestMapping(value = "/product_list.do", method = RequestMethod.POST)
    public ModelMap getProductList(HttpServletRequest request, ModelMap model) {

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

            QueryResult result = iProductService.getProducts(params);
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
