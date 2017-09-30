/**
 * 
 */
package com.ym.iadpush.manage.action.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.ym.common.base.Pager;
import com.ym.common.utils.Arith;
import com.ym.common.utils.DateUtils;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.CurrentAccountCollect;
import com.ym.iadpush.manage.entity.CurrentAccountDetail;
import com.ym.iadpush.manage.entity.Order;
import com.ym.iadpush.manage.entity.OrderItem;
import com.ym.iadpush.manage.entity.OrderStatus;
import com.ym.iadpush.manage.entity.Order_old;
import com.ym.iadpush.manage.entity.OutBill;
import com.ym.iadpush.manage.entity.Product;
import com.ym.iadpush.manage.entity.Product_property;
import com.ym.iadpush.manage.entity.StockDetail;
import com.ym.iadpush.manage.entity.StockMonth;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.Warehouse;
import com.ym.iadpush.manage.services.balance.IBalanceService;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;
import com.ym.iadpush.manage.services.order.IOrderService;
import com.ym.iadpush.manage.services.product.IProductService;
import com.ym.iadpush.manage.services.stock.IStockService;
import com.ym.iadpush.manage.services.tissue.IUserMgr;
import com.ym.iadpush.manage.services.warehouse.IWarehouseService;

@Controller
public class OrderAction extends BaseAction {

    private @Autowired
    IOrderService orderServiceImpl;

    private @Autowired
    IProductService productService;

    private @Autowired
    IBalanceService balanceService;

    private @Autowired
    ICompanyService companyServiceImpl;

    private @Autowired
    IStockService stockServiceImpl;

    private @Autowired
    IWarehouseService warehouseServiceImpl;

    private @Autowired
    IUserMgr userService;

    private String packageName = "orders";

    @RequestMapping(value = "/initUpdateOrderPrice.html", method = RequestMethod.GET)
    public Object initUpdateOrderPrice(HttpServletRequest request, ModelMap model) {

        // 订单ID
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        //
        Order order = orderServiceImpl.getOrderById(orderId, null);
        model.put("order", order);

        return AD_HTML + packageName + "/updateOrderPrice";
    }

    @ResponseBody
    @RequestMapping(value = "/updateOrderPrice.html", method = RequestMethod.POST)
    public Object updateOrderPrice(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        // 订单ID
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        String price = request.getParameter("price");
        double parsePrice = Double.parseDouble(price);

        // 修改
        Order order = orderServiceImpl.getOrderById(orderId, null);

        if ((int) order.getStatus() != 0) {
            model.put("status", true);
            model.put("msg", "订单已经付款不能修改订单单价!");
            return model;
        }

        rebackReceMoney(order);

        double parseDouble = 0;
        if (parseDouble == 0) {
            parseDouble = Arith.mul(order.getNumber(), parsePrice, 2);
        }

        order.setMoney(BigDecimal.valueOf(parseDouble));
        order.setEndMoney(BigDecimal.valueOf(parseDouble));
        order.setPrice(new BigDecimal(parsePrice));
        orderServiceImpl.updateOrderBill(order, orderId);

        saveNewReceMoney(order);

        model.put("status", true);
        model.put("msg", "订单价格修改完成");

        return model;

    }

    private void saveNewReceMoney(Order order) {
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        Integer companyId = order.getCompanyId();
        String departmentCode = order.getDepartmentCode();
        Integer departmentId = order.getDepartmentId();

        BigDecimal receMoney = order.getMoney();

        // 更新汇总账
        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);
        if (collect != null) {
            if (collect.getMonth().equals(month)) {
                collect.setReceMoney(collect.getReceMoney().add(receMoney));
                balanceService.updateCurrentAccountCollect(collect);
            } else {
                BigDecimal startMoney = collect.getStartMoney();
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setStartMoney(startMoney);
                collect.setReceMoney(collect.getReceMoney().add(receMoney));
                balanceService.saveCurrentAccountCollect(collect);
            }
        } else {
            collect = new CurrentAccountCollect();
            collect.setDepartmentCode(departmentCode);
            collect.setDepartmentId(departmentId);
            collect.setMonth(month);
            collect.setCompanyId(companyId);
            collect.setReceMoney(collect.getReceMoney().add(receMoney));
            balanceService.saveCurrentAccountCollect(collect);
        }

        // 更新明细账
        CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
        CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

        if (detail != null) {
            detail.setReceMoney(detail.getReceMoney().add(receMoney));
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
            detail.setReceMoney(detail.getReceMoney().add(receMoney));
            balanceService.saveCurrentAccountDetail(detail);
        }
    }

    private void rebackReceMoney(Order order) {

        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        BigDecimal receMoney = order.getMoney();

        Integer companyId = order.getCompanyId();
        String departmentCode = order.getDepartmentCode();
        Integer departmentId = order.getDepartmentId();

        // 更新汇总账
        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);

        if (collect != null) {
            if (collect.getMonth().equals(month)) {
                collect.setReceMoney(collect.getReceMoney().subtract(receMoney));
                balanceService.updateCurrentAccountCollect(collect);
            } else {
                BigDecimal startMoney = collect.getEndMoney();
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setStartMoney(startMoney);
                collect.setReceMoney(collect.getReceMoney().subtract(receMoney));
                balanceService.saveCurrentAccountCollect(collect);
            }
        } else {
            collect = new CurrentAccountCollect();
            collect.setDepartmentCode(departmentCode);
            collect.setDepartmentId(departmentId);
            collect.setMonth(month);
            collect.setCompanyId(companyId);
            collect.setReceMoney(collect.getReceMoney().subtract(receMoney));
            balanceService.saveCurrentAccountCollect(collect);
        }

        // 更新明细账
        CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
        CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

        if (detail != null) {
            detail.setReceMoney(detail.getReceMoney().subtract(receMoney));
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
            detail.setReceMoney(detail.getReceMoney().subtract(receMoney));
            balanceService.saveCurrentAccountDetail(detail);
        }
    }

    @RequestMapping(value = "/orders.html", method = RequestMethod.GET)
    public Object orders(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        // 角色类型
        String assortment = user.getAssortment();

        model.put("departmentCode", departmentCode);

        List<Product> list = productService.getAllProducts();
        model.put("products", list);

        // 查询所有
        if (assortment.equals("warehouse")) {
            return AD_HTML + packageName + "/ordersFactory";
        } else {
            return AD_HTML + packageName + "/orders";
        }

    }

    @ResponseBody
    @RequestMapping(value = "/myOrders.html", method = RequestMethod.POST)
    public Object queryAllOrders(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();
        String ser = request.getParameter("ser");
        String money = request.getParameter("money");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String status = request.getParameter("status");
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String djsale = request.getParameter("djsale");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String dqsaleCode = request.getParameter("dqsale");
        String code = request.getParameter("code");
        String productId = request.getParameter("productId");

        if (dqsaleCode == null) {
            dqsaleCode = "";
        }

        CompanyInfo companyInfo = null;

        // 总统筹
        List<CompanyInfo> listSales = new ArrayList<CompanyInfo>();
        Map<String, Object> paramMap3 = new HashMap<String, Object>();
        paramMap3.put("assortment", "dqsale");
        paramMap3.put("currPage", 0);
        paramMap3.put("pageSize", Integer.MAX_VALUE);
        listSales = companyServiceImpl.getAllSales(paramMap3);

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        String statusTemp2 = (String) request.getSession().getAttribute("status");
        String currPageTemp = (String) request.getSession().getAttribute("currPage");

        if (currPageTemp != null && !currPageTemp.equals("")) {
            currPage = Integer.valueOf(currPageTemp);
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 经销商
        if (djsale != null && !djsale.equals("")) {
            Map<String, Object> paramMap2 = new HashMap<String, Object>();
            paramMap.put("djcompanyName", djsale.trim());
            // companyInfo = companyServiceImpl.getCompanyInfoByName(paramMap2);
        }

        paramMap.put("code", code);
        paramMap.put("dqsaleCode", dqsaleCode);
        paramMap.put("province", province);
        paramMap.put("city", city);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("money", money);
        paramMap.put("name", name);
        paramMap.put("number", number);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);

        if (productId != null && !productId.equals("")) {
            paramMap.put("productId", new Integer(productId));
        } else {
            paramMap.put("productId", "");
        }

        // 角色类型
        String assortment = user.getAssortment();
        Integer companyId = user.getCompanyId();

        if (assortment.equals("dqsale") || assortment.equals("manger") || assortment.equals("other")
                || assortment.equals("financial")) {

            if (companyInfo != null) {
                // paramMap.put("djsaleId", companyInfo.getId());
            }

            if (status != null && !status.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(status));
                paramMap.put("status", statusTemp);
            }
        }

        // 经销商
        if (assortment.equals("djsale")) {
            paramMap.put("parentCompany", String.valueOf(user.getCompanyId()));

            if (status != null && !status.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(status));
                paramMap.put("status", statusTemp);
            }
        }

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            paramMap.put("departmentCode", "");
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(1);
            statusList.add(2);
            statusList.add(3);
            statusList.add(4);
            statusList.add(5);
            paramMap.put("status", statusList);

            if (status != null && !status.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(status));
                paramMap.put("status", statusTemp);
            }

            if (statusTemp2 != null && !statusTemp2.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(statusTemp2));
                paramMap.put("status", statusTemp);
            }
        }

        // 物流中心管理员（接受订单,出库订单）
        if (assortment.equals("warehouse")) {
            paramMap.put("departmentCode", "");
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(2);
            statusList.add(3);
            statusList.add(4);
            statusList.add(5);
            paramMap.put("status", statusList);

            if (status != null && !status.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(status));
                paramMap.put("status", statusTemp);
            }

            if (statusTemp2 != null && !statusTemp2.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(statusTemp2));
                paramMap.put("status", statusTemp);
            }
        }

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            paramMap.put("companyId", String.valueOf(companyId));

            if (status != null && !status.equals("")) {
                List<Integer> statusTemp = new ArrayList<Integer>();
                statusTemp.add(new Integer(status));
                paramMap.put("status", statusTemp);
            }
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        List<Order> list = orderServiceImpl.getAllOrders(paramMap);

        pager.setTotalRecord(orderServiceImpl.getOrderCount(paramMap));

        QueryResult result = orderServiceImpl.getOrderCollect(paramMap);

        model.put("collect", result.getCollect());
        model.put("listOrdes", list);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("assortment", assortment);
        model.put("departmentCode", departmentCode);
        model.put("listSales", listSales);
        model.put("countSize", listSales.size());
        model.put("dqsaleCode", dqsaleCode);
        model.put("status", status);

        if (productId != null && !productId.equals("")) {
            model.put("productId", new Integer(productId));
        } else {
            model.put("productId", "");
        }

        List<Product> productList = productService.getAllProducts();
        model.put("products", productList);

        if (user.getCompanyId() != null) {
            model.put("userCompanyId", user.getCompanyId().intValue());
        } else {
            model.put("userCompanyId", 0);
        }

        try {
            request.getSession().removeAttribute("status");
            request.getSession().removeAttribute("currPage");
        } catch (Exception e) {

        }

        return model;
    }

    @RequestMapping(value = "/orderInputProxy.html", method = RequestMethod.GET)
    public Object orderInputProxy(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        Integer companyId = user.getCompanyId();

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(companyId);
        Product product = productService.getTopOneProduct();

        if (product == null) {
            product = new Product();
        }

        model.put("product", product);
        model.put("companyInfo", companyInfo);

        return AD_HTML + packageName + "/orderInput";
    }

    @RequestMapping(value = "/orderInput.html", method = RequestMethod.GET)
    public Object orderInput(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        // 角色类型
        String assortment = user.getAssortment();
        Integer companyId = user.getCompanyId();

        CompanyInfo companyInfo = new CompanyInfo();

        if (companyId != null) {
            companyInfo = companyServiceImpl.getCompanyInfoById(companyId);
        }

        Product product = productService.getTopOneProduct();
        // List<Product> listProducts = productService.getAllProducts();
        List<Product> listProducts = productService.getAllProductsUse();

        if (product == null) {
            product = new Product();
        }

        Integer productId = product.getId();

        model.put("listProducts", listProducts);
        model.put("product", product);
        model.put("companyInfo", companyInfo);

        if (assortment.equals("proxyFactory")) {
            List<Product_property> list = productService.getProduct_propertys(productId, null);
            model.put("propertys", list);
            return AD_HTML + packageName + "/orderInputProxy";
        } else {
            return AD_HTML + packageName + "/orderInput";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrderProxy.html", method = RequestMethod.POST)
    public Object saveOrderProxy(HttpServletRequest request, ModelMap model) {
        String productId = request.getParameter("productId");
        String number = request.getParameter("number");
        String money = request.getParameter("money");
        String propertyId = request.getParameter("property");
        double parseDouble = Double.parseDouble(money);
        String remark = request.getParameter("remark");
        String price = request.getParameter("price");
        double parsePrice = Double.parseDouble(price);

        double receMoney = parseDouble;
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        SysUsers user = getUser();
        Integer companyId = user.getCompanyId();
        String departmentCode = user.getDepartmentCode();
        Integer departmentId = user.getDepartmentId();

        String code = orderServiceImpl.getNextOrderCode("d");

        Order order = new Order();
        order.setCode(code);
        order.setEndMoney(BigDecimal.valueOf(parseDouble));
        order.setPrice(new BigDecimal(parsePrice));
        order.setRemark(remark);

        if (propertyId != null && !propertyId.equals("")) {
            order.setPropertyId(Integer.valueOf(propertyId));
        }

        order.setCompanyId(companyId);
        order.setAddate(new Date());
        order.setDepartmentId(departmentId);
        order.setDepartmentCode(departmentCode);
        order.setProductId(Integer.valueOf(productId));
        order.setCreateUid(user.getUserId());
        order.setNumber(Integer.valueOf(number));
        order.setEndNumber(Integer.valueOf(number));
        order.setEndMoney(BigDecimal.valueOf(parseDouble));
        order.setMoney(BigDecimal.valueOf(parseDouble));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        orderServiceImpl.inserOrder(order);

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(0));
        orderStatus.put("createUid", user.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "订单已生效，请及时付款，以便尽快给您发货。");
        orderStatus.put("orderId", order.getId());
        orderServiceImpl.inserOrderStatus(orderStatus);

        CurrentAccountCollect collect2 = balanceService.getCurrentAccountCollect(companyId, month);

        if (collect2 != null) {

            // 应收款+期初应收款
            BigDecimal temp1 = collect2.getStartMoney().add(collect2.getReceMoney());

            // 实收款+预收款
            BigDecimal temp2 = collect2.getPayMoney().add(collect2.getAdvancePayment());

            BigDecimal temp3 = temp2.subtract(temp1);

            // 直接审核通过，不增加实收款，直接预收款抵扣,修改审核状态
            if (temp3.doubleValue() >= parseDouble) {

                // 自动付款
                autoPayBill(order);

                // 自动审核
                autoAuditing(order);
            }
        }

        // 更新汇总账
        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);

        if (collect != null) {
            if (collect.getMonth().equals(month)) {
                collect.setReceMoney(collect.getReceMoney().add(new BigDecimal(receMoney)));
                balanceService.updateCurrentAccountCollect(collect);
            } else {
                BigDecimal startMoney = collect.getEndMoney();
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setStartMoney(startMoney);
                collect.setReceMoney(collect.getReceMoney().add(new BigDecimal(receMoney)));
                balanceService.saveCurrentAccountCollect(collect);
            }
        } else {
            collect = new CurrentAccountCollect();
            collect.setDepartmentCode(departmentCode);
            collect.setDepartmentId(departmentId);
            collect.setMonth(month);
            collect.setCompanyId(companyId);
            collect.setReceMoney(collect.getReceMoney().add(new BigDecimal(receMoney)));
            balanceService.saveCurrentAccountCollect(collect);
        }

        // 更新明细账
        CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
        CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

        if (detail != null) {
            detail.setReceMoney(detail.getReceMoney().add(new BigDecimal(receMoney)));
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
            detail.setReceMoney(detail.getReceMoney().add(new BigDecimal(receMoney)));
            balanceService.saveCurrentAccountDetail(detail);
        }

        model.put("status", true);
        model.put("msg", "订单申请已经生效,请及时付款，谢谢！");

        return model;

    }

    /**
     * 保存订单申请
     * 
     * @Author lixingbiao 2014-8-4 下午2:41:46
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrder.html", method = RequestMethod.POST)
    public Object saveOrderBill(HttpServletRequest request, ModelMap model) {

        String productId = request.getParameter("productId");
        String number = request.getParameter("number");
        String money = request.getParameter("money");
        double parseDouble = Double.parseDouble(money);
        String remark = request.getParameter("remark");
        String price = request.getParameter("price");
        String shippingAddress = request.getParameter("shippingAddress");
        Integer inNumber = Integer.valueOf(number);

        double receMoney = parseDouble;
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        if (price == null || price.equals("")) {
            price = "0";
        }

        double parsePrice = Double.parseDouble(price);

        if (parseDouble == 0) {
            parseDouble = Arith.mul(inNumber, parsePrice, 2);
        }

        if (productId == null || productId.equals("")) {
            model.put("status", false);
            model.put("msg", "请选择订单产品！");
            return model;
        }

        SysUsers user = getUser();
        Integer companyId = user.getCompanyId();
        String departmentCode = user.getDepartmentCode();
        Integer departmentId = user.getDepartmentId();

        // 修改门店收货地址
        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(companyId);

        if (shippingAddress != null && !shippingAddress.equals("")) {
            companyInfo.setShippingAddress(shippingAddress);
        }

        companyServiceImpl.updateCompanyInfo(companyInfo, companyId);

        String code = orderServiceImpl.getNextOrderCode("d");

        Order order = new Order();
        order.setEndMoney(BigDecimal.valueOf(parseDouble));
        order.setCode(code);

        if (shippingAddress != null && !shippingAddress.equals("")) {
            order.setShippingAddress(shippingAddress);
        }

        order.setPrice(new BigDecimal(parsePrice));
        order.setRemark(remark);
        order.setCompanyId(companyId);
        order.setAddate(new Date());
        order.setDepartmentId(departmentId);
        order.setDepartmentCode(departmentCode);
        order.setProductId(Integer.valueOf(productId));
        order.setCreateUid(user.getUserId());
        order.setNumber(inNumber);
        order.setEndNumber(inNumber);
        order.setMoney(BigDecimal.valueOf(parseDouble));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        orderServiceImpl.inserOrder(order);

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(0));
        orderStatus.put("createUid", user.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "订单已生效，请及时付款，以便尽快给您发货。");
        orderStatus.put("orderId", order.getId());
        orderServiceImpl.inserOrderStatus(orderStatus);

        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);

        if (collect != null) {

            // 应收款 + 期初应收款
            BigDecimal temp1 = collect.getStartMoney().add(collect.getReceMoney());

            // 实收款 + 预收款
            BigDecimal temp2 = collect.getPayMoney().add(collect.getAdvancePayment());

            BigDecimal temp3 = temp2.subtract(temp1);

            // 直接审核通过，付款，不增加实收款，直接预收款抵扣,修改审核状态
            if (temp3.doubleValue() >= parseDouble) {

                // 自动付款
                autoPayBill(order);

                // 自动审核
                autoAuditing(order);
            }
        }

        // 往来账
        saveCurrentAccount(departmentCode, departmentId, companyId, receMoney, month, addate);

        model.put("status", true);
        model.put("msg", "订单申请已经生效,请及时付款，谢谢！");

        return model;
    }

    private void autoPayBill(Order order) {

        SysUsers user = getUser();
        Integer userId = user.getUserId();

        order.setPayMoney(order.getMoney());
        order.setPaydate(new Date());
        order.setPayUid(userId);

        orderServiceImpl.updateOrder(1, order.getId());

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(1));
        orderStatus.put("createUid", userId);
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "订单已付款（预收款抵扣），等待财务审核中，会尽快为您安排发货。");
        orderStatus.put("orderId", order.getId());

        orderServiceImpl.inserOrderStatus(orderStatus);

        orderServiceImpl.updateOrderBill(order, order.getId());

    }

    private void autoAuditing(Order order) {

        SysUsers sysUser = userService.getByName("admin");
        order.setPayRemark("预收款抵扣，自动审核完毕:admin");

        orderServiceImpl.updateOrder(2, order.getId());
        orderServiceImpl.updateOrderBill(order, order.getId());

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(2));
        orderStatus.put("createUid", sysUser.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "财务审核完毕(预收款抵扣)，订单已传递到发货库房，请再次确认收货地址等相关信息。");
        orderStatus.put("orderId", order.getId());
        orderServiceImpl.inserOrderStatus(orderStatus);
    }

    private void saveCurrentAccount(String departmentCode, Integer departmentId, Integer companyId, double receMoney,
            String month, Date addate) {

        // 更新汇总账
        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);
        if (collect != null) {
            if (collect.getMonth().equals(month)) {
                collect.setReceMoney(collect.getReceMoney().add(new BigDecimal(receMoney)));
                balanceService.updateCurrentAccountCollect(collect);
            } else {
                BigDecimal startMoney = collect.getEndMoney();
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setStartMoney(startMoney);
                collect.setReceMoney(collect.getReceMoney().add(new BigDecimal(receMoney)));
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
            collect.setReceMoney(collect.getReceMoney().add(new BigDecimal(receMoney)));
            balanceService.saveCurrentAccountCollect(collect);
        }

        // 更新明细账
        CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
        CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

        if (detail != null) {
            detail.setReceMoney(detail.getReceMoney().add(new BigDecimal(receMoney)));
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
            detail.setReceMoney(detail.getReceMoney().add(new BigDecimal(receMoney)));
            balanceService.saveCurrentAccountDetail(detail);
        }
    }

    @RequestMapping(value = "/initOrderPay.html", method = RequestMethod.GET)
    public Object initOrderPay(HttpServletRequest request, ModelMap model) {

        // 订单ID
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        // 修改订单付款状态
        Order order = orderServiceImpl.getOrderById(orderId, null);
        model.put("order", order);

        return AD_HTML + packageName + "/orderPay";
    }

    @RequestMapping(value = "/deleteOrder.html", method = RequestMethod.GET)
    public Object deleteOrder(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        // 订单ID
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        // 查询订单
        Order order = orderServiceImpl.getOrderById(orderId, null);

        // 保存作废记录
        saveOrder_old(order);

        if (order != null) {
            Integer companyId = order.getCompanyId();
            String departmentCode = user.getDepartmentCode();
            Integer departmentId = user.getDepartmentId();

            if ((int) order.getStatus() != 0) {
                return AD_HTML + packageName + "/orders";
            }

            double receMoney = order.getMoney().doubleValue();
            String month = DateUtils.getYearMonth();
            Date addate = DateUtils.addDay(new Date(), 0);

            // 更新汇总账
            CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);

            if (collect != null) {
                if (collect.getMonth().equals(month)) {
                    collect.setReceMoney(collect.getReceMoney().subtract(new BigDecimal(receMoney)));
                    balanceService.updateCurrentAccountCollect(collect);
                } else {
                    BigDecimal startMoney = collect.getEndMoney();
                    collect = new CurrentAccountCollect();
                    collect.setDepartmentCode(departmentCode);
                    collect.setDepartmentId(departmentId);
                    collect.setMonth(month);
                    collect.setCompanyId(companyId);
                    collect.setStartMoney(startMoney);
                    collect.setReceMoney(collect.getReceMoney().subtract(new BigDecimal(receMoney)));
                    balanceService.saveCurrentAccountCollect(collect);
                }
            } else {
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setReceMoney(collect.getReceMoney().subtract(new BigDecimal(receMoney)));
                balanceService.saveCurrentAccountCollect(collect);
            }

            // 更新明细账
            CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
            CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

            if (detail != null) {
                detail.setReceMoney(detail.getReceMoney().subtract(new BigDecimal(receMoney)));
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
                detail.setReceMoney(detail.getReceMoney().subtract(new BigDecimal(receMoney)));
                balanceService.saveCurrentAccountDetail(detail);
            }
            Integer in = orderServiceImpl.deleteOrder(orderId);
        }

        return AD_HTML + packageName + "/orders";

    }

    private void saveOrder_old(Order order) {
        if (order != null) {
            Order_old order_old = new Order_old();
            order_old.setOld_orderId(order.getId());
            order_old.setCode(order.getCode());
            order_old.setAddate(order.getAddate());
            order_old.setCompanyId(order.getCompanyId());
            order_old.setCreateUid(order.getCreateUid());
            order_old.setColor(order.getColor());
            order_old.setProductId(order.getProductId());
            order_old.setPrice(order.getPrice());
            order_old.setMoney(order.getMoney());
            order_old.setPaydate(order.getPaydate());
            order_old.setPayMoney(order.getPayMoney());
            order_old.setRemark(order.getRemark());
            order_old.setEndMoney(order.getEndMoney());
            order_old.setEndNumber(order.getEndNumber());
            order_old.setNumber(order.getNumber());
            order_old.setNo(order.getNo());
            order_old.setStatus(order.getStatus());
            order_old.setPayRemark(order.getPayRemark());
            order_old.setPayUid(order.getPayUid());
            order_old.setUnit(order.getUnit());
            order_old.setDepartmentId(order.getDepartmentId());
            order_old.setDepartmentCode(order.getDepartmentCode());
            orderServiceImpl.inserOrder_old(order_old);
        }
    }

    /**
     * 订单付款
     * 
     * @Author lixingbiao 2014-8-4 下午2:21:26
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/payOrder.html", method = RequestMethod.POST)
    public Object payOrderBill(HttpServletRequest request, ModelMap model) {

        // 订单ID
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        // 付款凭据号
        String no = request.getParameter("no");

        Integer status = 1;

        // 修改订单付款状态
        Order order = orderServiceImpl.getOrderById(orderId, null);

        if ((int) order.getStatus() == 1) {
            model.put("status", true);
            model.put("msg", "订单已经付款，不能再次付款！");
            return model;
        }

        BigDecimal money = order.getMoney();

        SysUsers user = getUser();
        Integer userId = user.getUserId();

        order.setPayMoney(money);
        order.setPaydate(new Date());
        order.setPayUid(userId);
        order.setNo(no);

        orderServiceImpl.updateOrder(status, orderId);

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(status));
        orderStatus.put("createUid", userId);
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "订单已付款，等待财务审核中，会尽快为您安排发货。");
        orderStatus.put("orderId", order.getId());

        orderServiceImpl.inserOrderStatus(orderStatus);

        orderServiceImpl.updateOrderBill(order, orderId);

        model.put("status", true);
        model.put("msg", "订单付款完成");

        return model;
    }

    /**
     * 订单财务审核
     * 
     * @Author lixingbiao 2014-8-4 下午2:21:26
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/auditingOrder.html", method = RequestMethod.GET)
    public Object AuditingOrderBill(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Integer status = 2;
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        String status2 = request.getParameter("status");
        String currPage = request.getParameter("currPage");

        // 修改订单付款状态
        Order order = orderServiceImpl.getOrderById(orderId, null);

        if ((int) order.getStatus() == 2) {
            return AD_HTML + packageName + "/orders";
        }

        orderServiceImpl.updateOrder(status, orderId);

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(status));
        orderStatus.put("createUid", user.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "财务审核完毕，订单已传递到发货库房，请再次确认收货地址等相关信息。");
        orderStatus.put("orderId", order.getId());
        orderServiceImpl.inserOrderStatus(orderStatus);

        // 保存往来账
        saveCurrentAccount(month, addate, order);

        request.getSession().setAttribute("status", status2);
        request.getSession().setAttribute("currPage", currPage);

        return AD_HTML + packageName + "/orders";
    }

    private void saveCurrentAccount(String month, Date addate, Order order) {

        Integer companyId = order.getCompanyId();
        CurrentAccountCollect collect = balanceService.getCurrentAccountCollect(companyId, month);

        String departmentCode = order.getDepartmentCode();
        Integer departmentId = order.getDepartmentId();
        BigDecimal money = order.getMoney();

        if (collect != null) {
            if (collect.getMonth().equals(month)) {
                collect.setPayMoney(collect.getPayMoney().add(money));
                balanceService.updateCurrentAccountCollect(collect);
            } else {
                BigDecimal startMoney = collect.getEndMoney();
                collect = new CurrentAccountCollect();
                collect.setDepartmentCode(departmentCode);
                collect.setDepartmentId(departmentId);
                collect.setMonth(month);
                collect.setCompanyId(companyId);
                collect.setStartMoney(startMoney);
                collect.setPayMoney(money);
                balanceService.saveCurrentAccountCollect(collect);
            }
        } else {
            collect = new CurrentAccountCollect();
            collect.setDepartmentCode(departmentCode);
            collect.setDepartmentId(departmentId);
            collect.setMonth(month);
            collect.setCompanyId(companyId);
            collect.setPayMoney(money);
            balanceService.saveCurrentAccountCollect(collect);
        }

        // 更新明细账
        CurrentAccountDetail detail = balanceService.getCurrentAccountDetail(companyId, addate);
        CurrentAccountDetail beforeDetail = balanceService.getLatelyCurrentAccountDetail(companyId, addate);

        if (detail != null) {
            detail.setPayMoney(detail.getPayMoney().add(order.getPayMoney()));
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
            detail.setPayMoney(detail.getPayMoney().add(money));
            balanceService.saveCurrentAccountDetail(detail);
        }
    }

    /**
     * 工厂接受订单
     * 
     * @Author lixingbiao 2014-8-5 上午10:40:23
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/factoryReceiveOrder.html", method = RequestMethod.GET)
    public Object FactoryReceiveOrder(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Integer status = 3;

        String status2 = request.getParameter("status");
        String currPage = request.getParameter("currPage");

        // 修改订单付款状态
        Order order = orderServiceImpl.getOrderById(orderId, null);

        if ((int) order.getStatus() == 3) {
            return AD_HTML + packageName + "/orders";
        }

        orderServiceImpl.updateOrder(status, orderId);

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(status));
        orderStatus.put("createUid", user.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "工厂已接收订单，货物整理打包中。");
        orderStatus.put("orderId", order.getId());

        orderServiceImpl.inserOrderStatus(orderStatus);

        request.getSession().setAttribute("status", status2);
        request.getSession().setAttribute("currPage", currPage);

        return AD_HTML + packageName + "/orders";
    }

    @RequestMapping(value = "/initOrderExit.html", method = RequestMethod.GET)
    public Object initOrderExit(HttpServletRequest request, ModelMap model) {

        // 订单ID
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        String status2 = request.getParameter("status");
        String currPage = request.getParameter("currPage");

        request.getSession().setAttribute("status", status2);
        request.getSession().setAttribute("currPage", currPage);

        // 查询订单
        Order order = orderServiceImpl.getOrderById(orderId, null);
        model.put("order", order);

        CompanyInfo info = companyServiceImpl.getCompanyInfoById(order.getCompanyId());

        if (info != null) {
            model.put("customer", info);
            Integer parentId = info.getParentId();
            if (parentId != null && parentId != 0) {
                CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(parentId);
                model.put("company", companyInfo);
            } else {
                model.put("company", new CompanyInfo());
            }
        } else {
            model.put("company", new CompanyInfo());
            model.put("customer", new CompanyInfo());
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Warehouse> warehouses = warehouseServiceImpl.getAllWarehouses(paramMap);
        model.put("warehouses", warehouses);
        model.put("customer", info);

        return AD_HTML + packageName + "/orderExit";
    }

    /**
     * 订单出库
     * 
     * @Author lixingbiao 2014-8-5 上午10:37:49
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderExit.html", method = RequestMethod.POST)
    public Object OrderExit(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String month = DateUtils.getYearMonth();
        Date addate = DateUtils.addDay(new Date(), 0);

        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        String distribution = request.getParameter("distribution");
        String no = request.getParameter("no");
        String regetDate = request.getParameter("regetDate");
        String number = request.getParameter("number");
        String outsideCode = request.getParameter("outsideCode");
        String warehouse = request.getParameter("warehouseId");

        Order order = orderServiceImpl.getOrderById(orderId, null);
        double price = Double.parseDouble(order.getPrice().toString());

        Integer warehouseId = 0;
        if (warehouse != null && !warehouse.equals("")) {
            warehouseId = new Integer(warehouse);
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setAddate(new Date());
        orderItem.setUid(user.getUserId());
        orderItem.setNumber(Integer.valueOf(number));
        BigDecimal money = BigDecimal.valueOf(Integer.parseInt(number) * price);
        orderItem.setMoney(money);

        orderItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
        orderServiceImpl.inserOrderItem(orderItem);

        int endNumber = order.getEndNumber().intValue() - Integer.valueOf(number);

        if (endNumber < 0) {
            model.put("status", true);
            model.put("msg", "本次发货数量不能超过订单剩余数量！。");
            return model;
        }

        double endMoney = endNumber * price;

        order.setEndMoney(BigDecimal.valueOf(endMoney));
        order.setEndNumber(Integer.valueOf(endNumber));
        orderServiceImpl.updateOrderBill(order, orderId);

        Integer status = 4;
        // 修改订单出库状态
        if ((int) order.getStatus() == 4) {
            model.put("status", true);
            model.put("msg", "该笔订单已经出库，不能再次出库。");
            return model;
        }

        if (endNumber == 0) {
            orderServiceImpl.updateOrder(status, orderId);
        }

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(status));
        orderStatus.put("createUid", user.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "产品已出库，物流配送中，请保持电话畅通;物流公司:" + distribution + ";运单号：" + no + ";预计到货:" + regetDate
                + "本次发货:" + number + "" + order.getUnit());
        orderStatus.put("orderId", order.getId());
        orderServiceImpl.inserOrderStatus(orderStatus);

        Integer productId = order.getProductId();
        String remark = order.getRemark();
        int uid = user.getUserId();

        int companyId = order.getCompanyId();
        int departmentId = order.getDepartmentId();
        String departmentCode = order.getDepartmentCode();

        try {

            StockMonth stockMonth = saveStockMonth(month, warehouseId, productId, new Integer(number));

            StockDetail stockDetail = saveStockDetail(month, addate, warehouseId, productId, new Integer(number));

            saveOutBill(order.getId(), companyId, departmentId, departmentCode, uid, number, outsideCode, remark,
                    price, warehouseId, money, productId, stockMonth, stockDetail);

        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("status", true);

        if (endNumber == 0) {
            model.put("msg", "该笔订单物资全部出库完成。");
        } else {
            model.put("msg", "该笔物资出库完成。");
        }

        return model;
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

    private StockMonth saveStockMonth(String month, Integer warehouseId, Integer productId, Integer number) {

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

    private StockDetail saveStockDetail(String month, Date addate, Integer warehouseId, Integer productId,
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

    /**
     * 门店收货完毕
     * 
     * @Author lixingbiao 2014-8-5 上午10:38:31
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/receivePackage.html", method = RequestMethod.GET)
    public Object ReceivePackage(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Integer status = 5;

        // 修改订单付款状态
        Order order = orderServiceImpl.getOrderById(orderId, null);

        if ((int) order.getStatus() == 5) {
            return AD_HTML + packageName + "/orders";
        }

        orderServiceImpl.updateOrder(status, orderId);

        // 保存订单状态
        Map<String, Object> orderStatus = new HashMap<String, Object>();
        orderStatus.put("status", Integer.valueOf(status));
        orderStatus.put("createUid", user.getUserId());
        orderStatus.put("createTime", DateUtils.getDatetime());
        orderStatus.put("desc", "收货完毕。");
        orderStatus.put("orderId", order.getId());

        orderServiceImpl.inserOrderStatus(orderStatus);

        return AD_HTML + packageName + "/orders";
    }

    /**
     * 订单详情
     * 
     * @Author lixingbiao 2014-8-9 下午8:51:14
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/orderView.html", method = RequestMethod.GET)
    public Object orderView(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        Order order = orderServiceImpl.getOrderById(orderId, null);

        List<OrderStatus> orderStatus = orderServiceImpl.getAllOrderStatus(orderId);

        model.put("order", order);
        model.put("list", orderStatus);

        // 角色类型
        String assortment = user.getAssortment();

        // 工厂查询所有
        if (assortment.equals("factory")) {
            return AD_HTML + packageName + "/orderViewFactory";
        } else {
            return AD_HTML + packageName + "/orderView";
        }

    }
}
