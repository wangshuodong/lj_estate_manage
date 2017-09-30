/**
 * 
 */
package com.ym.iadpush.manage.action.member;

import java.math.BigDecimal;
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
import com.ym.iadpush.manage.entity.MemberLevel;
import com.ym.iadpush.manage.services.tissue.MemberLevelService;


@Controller
public class MemberAction extends BaseAction {
    
    private static String FLODER = "member";
    
    @Autowired
    private MemberLevelService memberLevelService;
    
    

    @ResponseBody
    @RequestMapping(value="/member_delete.do",method=RequestMethod.POST)
    public ModelMap delete(HttpServletRequest request,ModelMap model){
        try {
            String id = request.getParameter("id");
            memberLevelService.deleteById(Integer.valueOf(id));
            model.put("status", true);
            model.put("msg", "删除成功");
        } catch (Exception e) {
            // TODO: handle exception
            model.put("status", false);
            model.put("msg", "删除失败");
            LogFactory.getLog(MemberAction.class).error("删除会员等级时异常", e);
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value="/member_update.do",method=RequestMethod.POST)
    public ModelMap update(HttpServletRequest request,ModelMap model){
        

        String level = request.getParameter("level");
        String desc = request.getParameter("desc");
        String rate = request.getParameter("rate");
        String id = request.getParameter("id");
        
        MemberLevel member = new MemberLevel();
        member.setDesc(desc);
        member.setLevel(Integer.valueOf(level));
        member.setRate(BigDecimal.valueOf(Double.valueOf(rate)));
        member.setId(Integer.valueOf(id));
        try {
            memberLevelService.update(member);
            model.put("status", true);
            model.put("msg", "修改成功");
        } catch (Exception e) {
            // TODO: handle exception
            model.put("status", false);
            model.put("msg", "修改失败");
        }
        return model;
    }
    

    @RequestMapping(value="/member_update.html",method=RequestMethod.GET)
    public String jumpToUpdate(HttpServletRequest request,ModelMap model){
        try {
            String id = request.getParameter("id");
            MemberLevel m = memberLevelService.findById(Integer.valueOf(id));
            model.put("result", m);
        } catch (Exception e) {
            LogFactory.getLog(MemberAction.class).error("跳转至会员等级修改一次", e);
        }
        return AD_HTML+FLODER+"/member_update";
    }
    

    @ResponseBody
    @RequestMapping(value="/member_add.do",method=RequestMethod.POST)
    public ModelMap addMember(HttpServletRequest request,ModelMap model){
        String level = request.getParameter("level");
        String desc = request.getParameter("desc");
        String rate = request.getParameter("rate");
        
        MemberLevel member = new MemberLevel();
        member.setDesc(desc);
        try {
            member.setLevel(Integer.valueOf(level));
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            member.setRate(BigDecimal.valueOf(Double.valueOf(rate)));
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            memberLevelService.insert(member);
            model.put("status", true);
            model.put("msg", "添加成功");
        } catch (Exception e) {
            // TODO: handle exception
            model.put("status", false);
            model.put("msg", "添加失败");
            LogFactory.getLog(MemberAction.class).error("添加会员等级异常", e);
        }
        return model;
    }
    

    @RequestMapping(value="/member_add.html",method=RequestMethod.GET)
    public String jumpToAdd(HttpServletRequest request,ModelMap model){
        return AD_HTML+FLODER+"/member_add";
    }
    

    @ResponseBody
    @RequestMapping(value="/member_list.do",method=RequestMethod.POST)
    public ModelMap list(HttpServletRequest request,ModelMap model){
        String currPage = request.getParameter("currPage");
        String desc = request.getParameter("desc");

        
        Map<String,Object> params = new HashMap<String, Object>();
        if (currPage == null || currPage.trim().equalsIgnoreCase("")) {
            params.put("currPage", Integer.valueOf(0));
        } else {
            params.put("currPage", Integer.valueOf(currPage.trim()) - 1);
        }
        if (desc != null && !desc.trim().equalsIgnoreCase("")) {
            params.put("desc", desc.trim());
        }
        params.put("pageSize", this.defaultPageSize);
        
        try {
            QueryResult result = memberLevelService.query(params);
            model.put("list", result.getData());
            model.put("count", result.getCount());
            model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage"))) + 1);
            model.put("pageSize", params.get("pageSize"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return model;
    }
    

    @RequestMapping(value="/member_list.html",method=RequestMethod.GET)
    public String jumpToList(HttpServletRequest request,ModelMap modelMap){
        return AD_HTML+FLODER+"/member_list";
    }

}
