package com.ym.iadpush.manage.action.news;

import java.util.Date;
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
import com.ym.common.base.Pager;
import com.ym.common.utils.DateUtils;
import com.ym.iadpush.manage.services.news.INewsService;


@Controller
public class NewsAction extends BaseAction {

    private @Autowired INewsService newsServiceImpl;
    private String packageName = "news";
    
    /**
     * 跳转至新闻列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/news_list.html", method = RequestMethod.GET)
    public String jumpToList(HttpServletRequest request, ModelMap model){
    	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	int pageSize=this.defaultPageSize;
    	String type = request.getParameter("type");
    	String currPage = request.getParameter("currPage");
    	String check = request.getParameter("check");
    	if(currPage == null || currPage.trim().equalsIgnoreCase("")){
    		currPage="1";
    		paramMap.put("currPage", 0);
    	}else{
    		try {
    			paramMap.put("currPage", (Integer.valueOf(currPage.trim())-1)*pageSize);
			} catch (Exception e) {
				paramMap.put("currPage", 0);
			}
    	}
    	
    	
        
        paramMap.put("pageSize",pageSize );
    	if(type != null && type.trim().equalsIgnoreCase("news")){
    		paramMap.put("type", 2);
    		model.put("type", "news");
    	}else{
    		paramMap.put("type", 1);
    		model.put("type", "gonggao");
    	}
    	//int currNo = Integer.valueOf(String.valueOf(currPage))+1;
    	int currNo = Integer.valueOf(String.valueOf(currPage));
    	int count = newsServiceImpl.getAllCountNews(paramMap);
    	
    	int countPage=count/pageSize;
    	if(count%pageSize!=0){
    		countPage++;
    	}
        model.put("data", newsServiceImpl.getAllNews(paramMap));
        model.put("count", count);
        model.put("pageSize", pageSize);
        model.put("check", check);
        
        if(currNo==1 && countPage>1){
        	model.put("nextPage", currNo+1);
        }else if(currNo>1 && currNo<countPage){
        	model.put("lastPage", currNo-1);
        	model.put("nextPage", currNo+1);
        }else if(currNo==countPage && countPage>1){
        	model.put("lastPage", currNo-1);
        }
        
    	return this.AD_HTML+packageName+"/news_list";
    }
    
    
    /**
     * 跳转至详情页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/news_detail.html", method = RequestMethod.GET)
    public String jumpToDetail(HttpServletRequest request, ModelMap model){
    	String id = request.getParameter("id");
    	try {
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("id", Integer.valueOf(id));
			model.put("news", newsServiceImpl.selectNewsById(paramMap));
		} catch (Exception e) {
			LogFactory.getLog(NewsAction.class).error("查询新闻详细时异常", e);
		}
    	return this.AD_HTML + packageName + "/news_detail";
    }
    
    /**
     * 首页公告
     * @param request
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/news_index_list.do", method = RequestMethod.POST)
    public Object ajaxfindList(HttpServletRequest request, ModelMap model){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("currPage", 0);
        paramMap.put("pageSize", 6);
        paramMap.put("type", 1);
        paramMap.put("index_news", "index_news");
        model.put("data", newsServiceImpl.getAllNews(paramMap));
    	return model;
    }

    /**
     * 加载所有数据
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/news_data.html", method = RequestMethod.POST)
    public Object ajaxListVersionKit(HttpServletRequest request, ModelMap model) {
    	int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request.getParameter("currPage"));
    	String title=request.getParameter("title");
    	String startdate=request.getParameter("startdate");
    	String enddate=request.getParameter("enddate");
    	String ser=request.getParameter("ser");
    	String type=request.getParameter("type");
    	if(ser!=null&&ser.trim().equalsIgnoreCase("search")){
        	currPage=1;
        }
    	
        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("currPage", (currPage-1)*pageSize);
        paramMap.put("pageSize", pageSize);
        
        paramMap.put("type", type==null||type.trim().equalsIgnoreCase("")||type.trim().equalsIgnoreCase("0")?null:type);
        paramMap.put("title", title==""?null:title);
        paramMap.put("title_val", "%"+title+"%");
        paramMap.put("startdate", startdate==""?null:startdate);
        paramMap.put("enddate", enddate==""?null:enddate);
        
        //设置总记录数
        pager.setTotalRecord(newsServiceImpl.getAllCountNews(paramMap));
       
        model.put("listNews",  newsServiceImpl.getAllNews(paramMap));
        model.put("count",pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        
    	return model;
    }
    /**
     * 删除公告
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/deletenews.html", method = RequestMethod.POST)
    public Object deletenews(HttpServletRequest request, ModelMap model) {
    	String id=request.getParameter("id");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("id", id);
    	model.put("result", newsServiceImpl.deleteNews(paramMap));
    	return model;
    }
    /**
     * 查询公告的单条记录
     * @param request 
     * @param model
     * @return
     */
    @SuppressWarnings("null")
	@RequestMapping(value = "/news_au.html", method = RequestMethod.GET)
    public Object selectNewsById(HttpServletRequest request, ModelMap model) {
    	String id=request.getParameter("id");
    	try {
			if(id!=null||!id.trim().equalsIgnoreCase("")){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("id", id);
				model.put("news", newsServiceImpl.selectNewsById(paramMap));
				mainOther(request, model);
			}
		} catch (Exception e) {
		}
        return AD_HTML + packageName + "/news_au";
    }
    /**
     * 增加公告
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/addNews.html", method = RequestMethod.POST)
    public Object addNews(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	String type = request.getParameter("type");
    	String title = request.getParameter("title");
    	String context = request.getParameter("context");
    	
    	if(type == null || type.trim().equalsIgnoreCase("")){
    		model.put("msg", "请选择类型");
    		model.put("status", false);
    	}else if(title == null || title.trim().equalsIgnoreCase("") || title.length() > 50){
    		model.put("msg", "请输入标题，且长度不能超过50字符");
    		model.put("status", false);
    	}else if(context == null || context.trim().equalsIgnoreCase("")){
    		model.put("msg", "请输入内容");
    		model.put("status", false);
    	}else{
    		paramMap.put("type", type);
        	paramMap.put("title",title );
        	paramMap.put("context", context);
        	paramMap.put("isShow", request.getParameter("isShow"));
        	paramMap.put("uid", this.getUser().getUserId());
        	String adddate=request.getParameter("enddate");
        	paramMap.put("nowtime", DateUtils.getDatetime());
        	paramMap.put("adddate", adddate==null||adddate.trim().equalsIgnoreCase("")?new Date():adddate);
        	model.put("result", newsServiceImpl.insertNews(paramMap));
        	model.put("status", true);
        	model.put("msg", "操作成功");
    	}
    	
    	return model;
    }
    /**
     * 修改公告
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/updateNews.html", method = RequestMethod.POST)
    public Object updateNews(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	String type = request.getParameter("type");
    	String title = request.getParameter("title");
    	String context = request.getParameter("context");
    	
    	if(type == null || type.trim().equalsIgnoreCase("")){
    		model.put("msg", "请选择类型");
    		model.put("status", false);
    	}else if(title == null || title.trim().equalsIgnoreCase("") || title.length() > 50){
    		model.put("msg", "请输入标题，且长度不能超过50字符");
    		model.put("status", false);
    	}else if(context == null || context.trim().equalsIgnoreCase("")){
    		model.put("msg", "请输入内容");
    		model.put("status", false);
    	}else{
    		paramMap.put("type", type);
        	paramMap.put("title",title );
        	paramMap.put("context", context);
        	paramMap.put("isShow", request.getParameter("isShow"));
        	paramMap.put("uid", this.getUser().getUserId());
        	String adddate=request.getParameter("enddate");
        	paramMap.put("nowtime", DateUtils.getDatetime());
        	paramMap.put("adddate", adddate==null||adddate.trim().equalsIgnoreCase("")?new Date():adddate);
        	paramMap.put("id", request.getParameter("id"));
        	model.put("result", newsServiceImpl.updateNews(paramMap));
        	model.put("status", true);
        	model.put("msg", "操作成功");
    	}
    	
    	return model;
    }
    /**
     * 修改查看状态
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/insertNotice.html", method = RequestMethod.POST)
    public Object insertNotice(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("noticeid", request.getParameter("noticeid"));
    	paramMap.put("title", request.getParameter("title"));
    	paramMap.put("userid", this.getUser().getUserId());
    	paramMap.put("createtime", DateUtils.getDatetime());
    	model.put("result", newsServiceImpl.insertNotice(paramMap));
    	this.getUser().setNews(null);
    	return model;
    }
    /**
     * 得到最新的公告数据--总记录
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/getCountNewsNotice.html", method = RequestMethod.POST)
    public Object getCountNewsNotice(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	paramMap.put("userid", this.getUser().getUserId());
    	paramMap.put("startdate",DateUtils.get30DayBeforDate());
    	paramMap.put("enddate", DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd"));
    	paramMap.put("checkCount","checkCount");
    	
    	//查询公告的最新记录总数
    	model.put("listnotice", newsServiceImpl.getAllNews(paramMap));
    	return model;
    }
    /**
     * 得到最新的公告数据
     * @param request 
     * @param model
     * @return
     */
    @ResponseBody 
    @RequestMapping(value = "/getNewsNotice.html", method = RequestMethod.POST)
    public Object getNewsNotice(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	paramMap.put("noticeid", request.getParameter("noticeid"));
    	model.put("news", newsServiceImpl.getNewestNotice(paramMap));
    	return model;
    }
    
    @RequestMapping(value = "/news.html", method = RequestMethod.GET)
    public Object jumpToPage(HttpServletRequest request, ModelMap model) {
        mainOther(request, model);
        return AD_HTML + packageName + "/news";
    }
    
}
