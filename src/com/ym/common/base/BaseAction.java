package com.ym.common.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.ModelMap;

import com.ym.common.utils.BeanUtils;
import com.ym.common.utils.QueryResult;
import com.ym.common.utils.RandomUtil;
import com.ym.iadpush.manage.entity.SysUsers;

public class BaseAction {
    protected final String DATA = "d";
    protected final String ERROR = "e";
    protected final String RESULT = "r";
    protected final String MESSAGE = "m";
    protected final String AD_HTML = "biz-logic/";
    protected int defaultPageSize = 10;// 默认显示5条记录
    
    
    public String getToken(){
    	String token = "";
    	token = RandomUtil.getRandom(10);
    	SecurityUtils.getSubject().getSession().setAttribute("token", token);
    	return token;
    }

    protected String redirect(String url) {
        return "redirect:" + url;
    }

    protected String forward(String url) {
        return "forward:" + url;
    }
      

    public Map<String, Object> putServiceId(Map<String, Object> params){
    	if(getUser().getRoles().get(0).getType().trim().equalsIgnoreCase("outService")){
    		params.put("serviceId", getUser().getUserId());
    	}
    	return params;
    }


                
    /**
     * 创建分页对象
     * 
     * @param pageSize
     * @param pageNo
     * @return
     */
    public Pager createPager(int pageSize, int pageNo) {
        Pager pager = null;
        if (pageSize <= 0) {
            pageSize = defaultPageSize;
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
        pager = new Pager(pageSize, pageNo);
        return pager;
    }

    /**
     * 创建分页对象
     * 
     * @param request
     * @return
     */
    public Pager createPager(HttpServletRequest request) {
        Pager pager = null;
        String pageSize = request.getParameter("rows");
        String pageNo = request.getParameter("page");
        if (StringUtils.isBlank(pageSize)) {
            pageSize = String.valueOf(defaultPageSize);
        }
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        pager = new Pager(Integer.valueOf(pageSize), Integer.valueOf(pageNo));
        return pager;
    }

    /**
     * 获取result Map对象
     * 
     * @return
     */
    protected Map<String, Object> getResultMap() {
        return new HashMap<String, Object>();
    }

    /**
     * 获取系统登录用户
     * 
     * @param request
     * @return
     */
    public static SysUsers getUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // PrincipalCollection collection = subject.getPrincipals();
            // return (SysUsers) collection.asList().get(2);
            return (SysUsers) subject.getPrincipal();
        }
        return null;
    }

    /**
     * 获取Session对象
     * 
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    protected <T> T getRequestParams(Class<T> cls, HttpServletRequest request, String field) {
        String value = request.getParameter(field);
        value = StringUtils.isBlank(value) ? null : value;
        return BeanUtils.typeCast(cls, value);
    }

    /**
     * 获取日志输出对象
     * 
     * @param object
     * @return
     */
    public Log getLog(Object object) {
        return LogFactory.getLog(object.getClass());
    }

    /**
     * 生成表格数据格式
     * 
     * @param pager
     * @return
     */
    public Map<String, Object> getGridData(Pager pager) {
        Map<String, Object> result = getResultMap();
        result.put("total", pager.getTotalRecord());
        result.put("rows", pager.getResult());
        return result;
    }


    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求域名
     * 
     * @param request
     * @return
     */
    @Deprecated
    public static String getdDomain(HttpServletRequest request) {
        String path = request.getContextPath();
        String domain = request.getScheme() + "://";
        domain += request.getServerName();
        domain += request.getServerPort() == 80 ? "" : (":" + request.getServerPort());
        domain += path;
        return domain;
    }
    

    /**
     * 获取用户相关信息
     * 
     * @Author LiXingBiao 2014-4-10 下午8:58:51
     * @param request
     * @param model
     */
    public void mainOther(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        model.put("sysDate", format.format(Calendar.getInstance().getTime()));
        model.put("menus", user.getMenus());
        model.put("nick", user.getNick());
        model.put("logout", Constants.SHIRO_CAS_LOGOUTURL);
    }
    
  public ModelMap setModelMap(Map<String, Object> params, ModelMap model,QueryResult result){
        
        model.put("list", result.getData());
        model.put("count", result.getCount());
        model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage")))+1);
        model.put("pageSize", params.get("pageSize"));
        model.put("collect", result.getCollect());
        model.put("startDate", params.get("startDate")==null?"":params.get("startDate"));
        model.put("endDate", params.get("endDate")==null?"":params.get("endDate"));

        return model;
    }
}
