package com.ym.common.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.ym.common.base.BaseAction;
import com.ym.common.base.BeansManager;
import com.ym.common.base.Constants;
import com.ym.common.ehcache.ResCacheBean;

public class MVCFreeMarckerView extends FreeMarkerView {
    private ResCacheBean resCache = BeansManager.getBean(ResCacheBean.class);

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
        
        // 静态资源版本（缓存控制）
        model.put("v", "?v=" + resCache.resCacheVersion());
        String basePath = request.getScheme()+"://"+request.getServerName()+(request.getLocalPort() != 80?(":"+request.getLocalPort()):"")+request.getContextPath();
        model.put("base", basePath);
        model.put("url", request.getRequestURI().replaceAll(request.getContextPath(), ""));
        model.put("debug", Constants.DEBUG);
        model.put("isCompress", Constants.HTML_IS_COMPRESS);
        
        // 用户信息
        model.put("user", BaseAction.getUser());
        model.put("menus", BaseAction.getUser() == null ? null : BaseAction.getUser().getMenus());

        // JS，CSS文件自动引入
        model.put("jsPath", resCache.getResJsPath(this.getBeanName()));
        model.put("cssPath", resCache.getResCssPath(this.getBeanName()));
    }
}
