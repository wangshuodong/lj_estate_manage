package com.ym.iadpush.manage.action.datamanager;

import java.util.ArrayList;
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
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.WeekBean;
import com.ym.iadpush.manage.entity.WeekData;
import com.ym.iadpush.manage.services.datamanager.DataManagerService;
import com.ym.iadpush.manage.services.earn.IEarnService;
import com.ym.iadpush.manage.services.order.IOrderService;
import com.ym.iadpush.manage.services.tissue.IUserMgr;


@Controller
public class DataManagerAction extends BaseAction {

    /**
     * 数据管理页面目录
     */
    private static String FLODER = "managerindex";

    @Autowired
    private IEarnService earnService;

    @Autowired
    private DataManagerService dataManagerService;

    @Autowired
    private IOrderService iOrderService;

    private @Autowired
    IUserMgr userService;

    @ResponseBody
    @RequestMapping(value = "/data_listener.html", method = RequestMethod.POST)
    public ModelMap getDataListener(HttpServletRequest request, ModelMap model) {

        try {
            // 获取请求参数
            String startDate = request.getParameter("startdate");
            String endDate = request.getParameter("enddate");
            String currPage = request.getParameter("currPage");
            String hour = request.getParameter("hour");
            String greaterOrLess = request.getParameter("greaterOrLess");
            String isall = request.getParameter("isall");
            String colum = request.getParameter("colum");
            String sortType = request.getParameter("sortType");
            String appkey = request.getParameter("appkey");
            String uid = request.getParameter("uid");

            Map<String, Object> params = new HashMap<String, Object>();

            if (colum == null || colum.trim().equalsIgnoreCase("")) {
                params.put("colum", "irate");
            }
            if (sortType == null || sortType.trim().equalsIgnoreCase("")) {
                params.put("sortType", "desc");
            } else {
                params.put("sortType", sortType);
            }
            if (startDate == null || startDate.trim().equalsIgnoreCase("")) {
                params.put("startDate", DateUtils.get1DayBeforDate());
            } else {
                params.put("startDate", startDate);
            }
            if (endDate == null || endDate.trim().equalsIgnoreCase("")) {
                params.put("endDate", DateUtils.getDate());
            } else {
                params.put("endDate", endDate);
            }
            params.put("currPage", Integer.valueOf(currPage).intValue() - 1);
            params.put("pageSize", super.defaultPageSize);
            if (isall != null && !isall.trim().equalsIgnoreCase("")) {
                params.put("isall", isall);
            }
            if (greaterOrLess != null && !greaterOrLess.trim().equalsIgnoreCase("")) {
                params.put("greaterOrLess", greaterOrLess);
            }
            if (hour != null && !hour.trim().equalsIgnoreCase("")) {
                params.put("hour", hour);
            }

            if (uid != null && !uid.trim().equalsIgnoreCase("")) {
                params.put("uid", uid);
            }
            QueryResult result = new QueryResult();
            if (appkey != null && !appkey.trim().equalsIgnoreCase("")) {
                String[] keys = appkey.split("-");
                if (keys.length == 3) {
                    params.put("uid", keys[0]);
                    params.put("qid", keys[1]);
                    params.put("appid", keys[2]);
                }
                if (uid != null && !uid.trim().equalsIgnoreCase(keys[0].trim())) {
                    params.put("uid", -1);
                }
                result = dataManagerService.dataListener(params);
            } else {
                result = dataManagerService.dataListener(params);
            }

            model.put("list", result.getData());
            model.put("count", result.getCount());
            model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage"))) + 1);
            model.put("pageSize", params.get("pageSize"));
            model.put("startDate", startDate);
            model.put("endDate", endDate);
        } catch (Exception e) {
            LogFactory.getLog(DataManagerAction.class).error("数据监控报表查询时异常", e);
        }
        return model;
    }

    @RequestMapping(value = "/data_listener.html", method = RequestMethod.GET)
    public String jumpToDataListener(HttpServletRequest request, ModelMap model) {
        model.put("startDate", DateUtils.get1DayBeforDate());
        model.put("endDate", DateUtils.getDate());
        return this.AD_HTML + FLODER + "/" + "data_listener";
    }


    @ResponseBody
    @RequestMapping(value = "/bms_update_app_status.html", method = RequestMethod.POST)
    public ModelMap updateAppStatus(HttpServletRequest request, ModelMap model) {
        Map<String, Object> params = new HashMap<String, Object>();
        String appid = request.getParameter("appid");
        String appStatus = request.getParameter("appStatus");
        if (appStatus != null && appStatus.trim().equalsIgnoreCase("start")) {
            params.put("status", "1,1,1");
        } else if (appStatus != null && appStatus.trim().equalsIgnoreCase("stop")) {
            params.put("status", "0,0,0");
        } else {
            model.put("status", false);
            model.put("msg", "参数有误");
        }
        if (model.get("status") == null) {
            params.put("appid", appid);
            dataManagerService.updateAppStatus(params);
            model.put("status", true);
            model.put("msg", "操作成功");
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/data_app_statement.html", method = RequestMethod.POST)
    public ModelMap searchAppStatement(HttpServletRequest request, ModelMap model) {
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String currPage = request.getParameter("currPage");
        String appkey = request.getParameter("appkey");
        String userName = request.getParameter("username");
        String appName = request.getParameter("appname");
        String serviceId = request.getParameter("serviceId");
        String status = request.getParameter("status");

        Map<String, Object> params = new HashMap<String, Object>();
        if (startDate != null && !startDate.trim().equalsIgnoreCase("")) {
            params.put("startDate", startDate);
        }
        if (appkey != null && !appkey.trim().equalsIgnoreCase("")) {
            String[] keys = appkey.trim().split("-");
            if (keys.length == 3) {
                params.put("uid", keys[0]);
                params.put("appid", keys[2]);
                params.put("qid", keys[1]);
            } else {
                params.put("uid", "-1");
                params.put("appid", "-1");
                params.put("qid", "-1");
            }
        }

        if (appName != null && !appName.trim().equalsIgnoreCase("")) {
            params.put("appname", appName);
        }
        if (userName != null && !userName.trim().equalsIgnoreCase("")) {
            params.put("username", userName);
        }
        if (serviceId != null && !serviceId.trim().equalsIgnoreCase("") && serviceId.trim().equalsIgnoreCase("-2")) {
            // 查询全部
            if (getUser().getRoleType().trim().equalsIgnoreCase("outService")) {
                params.put("serviceId", getUser().getUserId());
            }
        } else if (serviceId != null && serviceId.trim().equalsIgnoreCase("-1")) {
            // 查询未分配
            params.put("noservice", "noservice");
        } else if (serviceId != null && !serviceId.trim().equalsIgnoreCase("")
                && !serviceId.trim().equalsIgnoreCase("-2") && !serviceId.trim().equalsIgnoreCase("-1")) {
            if (this.getUser().getRoleType().equalsIgnoreCase("outService")) {
                params.put("serviceId", this.getUser().getUserId());
            } else {
                params.put("serviceId", serviceId.trim());
            }
        }
        if (status != null && status.trim().equalsIgnoreCase("stop")) {
            params.put("status", "0,0,0");
        }
        if (status != null && status.trim().equalsIgnoreCase("start")) {
            params.put("status", "1,1,1");
        }

        if (endDate != null && !endDate.trim().equalsIgnoreCase("")) {
            params.put("endDate", endDate);
        }
        if (currPage != null && !currPage.trim().equalsIgnoreCase("")) {
            params.put("currPage", Integer.valueOf(currPage) - 1);
        } else {
            params.put("currPage", Integer.valueOf(0));
        }
        params.put("pageSize", super.defaultPageSize);

        QueryResult result = dataManagerService.queryAppStatement(params);

        model.put("list", result.getData());
        model.put("count", result.getCount());
        model.put("collect", result.getCollect());
        model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage"))) + 1);
        model.put("pageSize", params.get("pageSize"));
        model.put("startDate", startDate);
        model.put("endDate", endDate);

        return model;
    }

    /**
     * 跳转至应用报表
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/data_app_statement.html", method = RequestMethod.GET)
    public String jumpToAppStatement(HttpServletRequest request, ModelMap model) {
        // 查询出客服
        if (!this.getUser().getRoleType().equalsIgnoreCase("outService")) {
            List<Map<String, String>> services = userService.selectByType("outService");
            model.put("services", services);
        } else {
            model.put("services", new ArrayList<Map<String, Object>>());
        }
        return this.AD_HTML + FLODER + "/app-statement";
    }


    @ResponseBody
    @RequestMapping(value = "/search_dev_statement_detail.html", method = RequestMethod.POST)
    public ModelMap getDevStatementDetail(HttpServletRequest request, ModelMap model) {

        Map<String, Object> params = this.getDevStatementParams(request);
        QueryResult result = dataManagerService.queryDevStatementDetail(putServiceId(params));

        this.getDevStatementMap(model, params, result);

        return model;

    }


    @RequestMapping(value = "/dev_statement_detail.html", method = RequestMethod.GET)
    public String jumpToDevStatementDetail(HttpServletRequest request, ModelMap model) {

        model.put("uid", request.getParameter("uid"));
        // 查询菜单
        super.mainOther(request, model);
        return AD_HTML + FLODER + "/dev-statement-detail";
    }


    private Map<String, Object> getDevStatementParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 获取参数
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String username = request.getParameter("username");
        String serviceName = request.getParameter("serviceName");
        String uid = request.getParameter("uid");

        if (uid != null && !uid.trim().equalsIgnoreCase("")) {
            params.put("uid", Integer.valueOf(uid));
        }

        params.put("pageNo", currPage - 1);
        params.put("pageSize", super.defaultPageSize);
        if (startDate != null && !startDate.trim().equalsIgnoreCase("")) {
            params.put("startDate", startDate);
        }
        if (endDate != null && !endDate.trim().equalsIgnoreCase("")) {
            params.put("endDate", endDate);
        }

        if (username != null && !username.trim().equalsIgnoreCase("")) {
            params.put("username", username);
        }
        if (serviceName != null && !serviceName.trim().equalsIgnoreCase("")) {
            params.put("serviceName", serviceName);
        }
        return params;
    }


    private ModelMap getDevStatementMap(ModelMap model, Map<String, Object> params, QueryResult result) {

        model.put("list", result.getData());
        model.put("count", result.getCount());
        model.put("currPage", Integer.valueOf(String.valueOf(params.get("pageNo"))) + 1);
        model.put("pageSize", params.get("pageSize"));
        model.put("startDate", params.get("startDate") != null ? params.get("startDate") : "");
        model.put("endDate", params.get("endDate") != null ? params.get("endDate") : "");
        model.put("collect", result.getCollect());

        return model;
    }


    @ResponseBody
    @RequestMapping(value = "/search_dev_statement.html", method = RequestMethod.POST)
    public Object ajaxDevStatement(HttpServletRequest request, ModelMap model) {

        Map<String, Object> params = this.getDevStatementParams(request);

        QueryResult result = dataManagerService.queryDevStatement(putServiceId(params));

        return this.getDevStatementMap(model, params, result);
    }

    @RequestMapping(value = "/dev_statement.html", method = RequestMethod.GET)
    public String devStatement(HttpServletRequest request, ModelMap model) {

        // 查询菜单
        super.mainOther(request, model);
        return AD_HTML + FLODER + "/dev-statement";
    }


    @ResponseBody
    @RequestMapping(value = "/search_data_statement.html", method = RequestMethod.POST)
    public Object ajaxStatement(HttpServletRequest request, ModelMap model) {

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String dateStr = request.getParameter("dateType");

        Map<String, Object> params = new HashMap<String, Object>();

        // 日期类型
        if (dateStr != null && !dateStr.trim().equalsIgnoreCase("")) {
            if (dateStr.equalsIgnoreCase("today")) {
                startDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
                endDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
            } else if (dateStr.equalsIgnoreCase("yestoday")) {
                startDate = DateUtils.getNDayBeforDate(1);
                endDate = DateUtils.getNDayBeforDate(1);
            } else if (dateStr.equalsIgnoreCase("sevenday")) {
                startDate = DateUtils.getNDayBeforDate(6);
                endDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
            } else if (dateStr.equalsIgnoreCase("thismonth")) {
                startDate = DateUtils.getThisMothStart();
                endDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
            } else if (dateStr.equalsIgnoreCase("lastmonth")) {
                startDate = DateUtils.getLastMonFirtDay();
                endDate = DateUtils.getLastMothEnd();
            } else if (dateStr.equalsIgnoreCase("all")) {
                startDate = "";
                endDate = "";
            }
        }

        if (startDate != null && !startDate.trim().equalsIgnoreCase("")) {
            model.put("startDate", startDate);
            params.put("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
        } else {
            model.put("startDate", "");
        }
        if (endDate != null && !endDate.trim().equalsIgnoreCase("")) {
            model.put("endDate", endDate);
            params.put("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
        } else {
            model.put("endDate", "");
        }

        params.put("currPage", currPage - 1);
        params.put("pageSize", super.defaultPageSize);

        QueryResult result = dataManagerService.queryDataStatement(putServiceId(params));

        model.put("list", result.getData());
        model.put("count", result.getCount());
        model.put("currPage", currPage);
        model.put("pageSize", super.defaultPageSize);
        model.put("collect", result.getCollect());

        return model;

    }


    @RequestMapping(value = "/data_statement.html", method = RequestMethod.GET)
    public String jumpToStatement(HttpServletRequest request, ModelMap model) {
        super.mainOther(request, model);
        return AD_HTML + FLODER + "/statement";
    }


    private ModelMap getEarn(ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 角色类型
        String assortment = user.getAssortment();

        // 今日订单
        Map<String, Object> todayMap = new HashMap<String, Object>();
        todayMap.put("departmentCode", departmentCode);

        // 设置部门CODE参数
        setParm(user, assortment, todayMap, departmentCode);

        todayMap.put("startTime", DateUtils.getDate());
        todayMap.put("endTime", DateUtils.getDate());

        Integer today = iOrderService.sumOrderByDate(todayMap);

        // 昨日订单
        Map<String, Object> yestodayMap = new HashMap<String, Object>();
        setParm(user, assortment, yestodayMap, departmentCode);

        yestodayMap.put("startTime", DateUtils.getNDayBeforDate(1));
        yestodayMap.put("endTime", DateUtils.getNDayBeforDate(1));

        Integer yestoday = iOrderService.sumOrderByDate(yestodayMap);

        // 前七日订单
        Map<String, Object> sevenMap = new HashMap<String, Object>();
        setParm(user, assortment, sevenMap, departmentCode);
        sevenMap.put("startTime", DateUtils.getNDayBeforDate(7));
        sevenMap.put("endTime", DateUtils.getNDayBeforDate(1));

        Integer seven = iOrderService.sumOrderByDate(sevenMap);

        String firstDayOfThisMonth = DateUtils.getFirstDayOfMonth(Integer.valueOf(DateUtils.getYear()),
                Integer.valueOf(DateUtils.getMonth()) - 1);

        // 本月订单
        Map<String, Object> monthMap = new HashMap<String, Object>();
        setParm(user, assortment, monthMap, departmentCode);
        monthMap.put("startTime", firstDayOfThisMonth);
        monthMap.put("endTime", DateUtils.getDate());
        Integer month = iOrderService.sumOrderByDate(monthMap);

        int lmonth = Integer.valueOf(DateUtils.getMonth());
        int lyear = Integer.valueOf(DateUtils.getYear());

        if (lmonth == 0) {
            lyear = lyear - 1;
            lmonth = 11;
        }

        String firstDayOfLastMonth = DateUtils.getFirstDayOfMonth(lyear, lmonth - 2);

        String endDayOfLastMonth = DateUtils.getLastDayOfMonth(lyear, lmonth - 2);

        // 上月订单
        Map<String, Object> lastmonthMap = new HashMap<String, Object>();
        setParm(user, assortment, lastmonthMap, departmentCode);
        lastmonthMap.put("startTime", firstDayOfLastMonth);
        lastmonthMap.put("endTime", endDayOfLastMonth);

        Integer lastmonth = iOrderService.sumOrderByDate(lastmonthMap);

       
        // 总订单数
        Map<String, Object> allMap = new HashMap<String, Object>();
        setParm(user, assortment, allMap, departmentCode);
        Integer total = iOrderService.sumOrderByDate(allMap);
     
        model.put("today", today == null ? "0" : today);
        model.put("yestoday", yestoday == null ? "0" : yestoday);
        model.put("seven", seven == null ? "0" : seven);
        model.put("month", month == null ? "0" : month);
        model.put("lastmonth", lastmonth == null ? "0" : lastmonth);
        model.put("total", total == null ? "0" : total);
        return model;
    }

    private void setParm(SysUsers user, String assortment, Map<String, Object> map, String departmentCode) {

        map.put("departmentCode", departmentCode);

        if (assortment.equals("djsale")) {
            map.put("djsaleId", "");
            if (user.getCompanyId() != null) {
                map.put("djsaleId", user.getCompanyId());
            }
        }

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            map.put("departmentCode", "");
        }

        // 仓库管理员
        if (assortment.equals("warehouse")) {
            map.put("departmentCode", "");
        }

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            map.put("companyId", String.valueOf(user.getCompanyId()));
        }
    }


    private ModelMap getWeekData(ModelMap model) {

        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        // 角色类型
        String assortment = user.getAssortment();

        String companyId = "";

        Integer djsaleId = null;

        //经销商
        if (assortment.equals("djsale")) {
            djsaleId = user.getCompanyId();
        }

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            departmentCode = "";
        }

        // 工厂查询所有
        if (assortment.equals("factory")) {
            departmentCode = "";
        }

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            companyId = String.valueOf(user.getCompanyId());
        }

        /**
         * 查询最近四周的效果数据
         */
        WeekBean week1 = DateUtils.lastWeek(1);
        WeekBean week2 = DateUtils.lastWeek(2);
        WeekBean week3 = DateUtils.lastWeek(3);
        WeekBean week4 = DateUtils.lastWeek(4);

        WeekData wd1 = iOrderService.queryWeekData(week1.getStartDate(), week1.getEndDate(), departmentCode, companyId,
                djsaleId);
        WeekData wd2 = iOrderService.queryWeekData(week2.getStartDate(), week2.getEndDate(), departmentCode, companyId,
                djsaleId);
        WeekData wd3 = iOrderService.queryWeekData(week3.getStartDate(), week3.getEndDate(), departmentCode, companyId,
                djsaleId);
        WeekData wd4 = iOrderService.queryWeekData(week4.getStartDate(), week4.getEndDate(), departmentCode, companyId,
                djsaleId);

        wd1.setStartDate(week1.getStartDate());
        wd1.setEndDate(week1.getEndDate());

        wd2.setStartDate(week2.getStartDate());
        wd2.setEndDate(week2.getEndDate());

        wd3.setStartDate(week3.getStartDate());
        wd3.setEndDate(week3.getEndDate());

        wd4.setStartDate(week4.getStartDate());
        wd4.setEndDate(week4.getEndDate());

        model.put("w1", wd1);
        model.put("w2", wd2);
        model.put("w3", wd3);
        model.put("w4", wd4);

        return model;
    }

    @RequestMapping(value = "/manager_index.html", method = RequestMethod.GET)
    public String managerIndex(HttpServletRequest request, ModelMap model) {
        // 查询订单数量
        this.getEarn(model);

        // 查询第一周数据
        this.getWeekData(model);

        return AD_HTML + FLODER + "/manager-index";
    }

    public IEarnService getEarnService() {
        return earnService;
    }

    public void setEarnService(IEarnService earnService) {
        this.earnService = earnService;
    }

    public DataManagerService getDataManagerService() {
        return dataManagerService;
    }

    public void setDataManagerService(DataManagerService dataManagerService) {
        this.dataManagerService = dataManagerService;
    }

}
