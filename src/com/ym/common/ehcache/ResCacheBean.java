package com.ym.common.ehcache;

import java.io.File;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * spring 缓存类（实现自定义内容缓存及清除）
 * 
 * @author Auser
 * 
 */
@Component
public class ResCacheBean {

    private static Log log = LogFactory.getLog(ResCacheBean.class);

    /**
     * 获取web应用的根路径
     * 
     * @param request
     * @return
     */
    @Cacheable(value = "springCache", key = "'web_root_path'")
    public String getRootPath() {
        String rootPath = null, url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
        int index = url.indexOf("WEB-INF");
        if (index == -1) {// 针对Maven本地调试
            index = url.indexOf("target");
            if (index != -1) {
                rootPath = url.substring(0, index) + "/src/main/webapp/";
            }
        } else {
            rootPath = url.substring(0, index);
        }
        return rootPath;
    }

    /**
     * 获取静态资源文件缓存版本
     * 
     * @return
     */
    @Cacheable(value = "springCache", key = "'res_cache_version'")
    public String resCacheVersion() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 根据view名称获取对应JS文件的路径
     * 
     * @param beanName view名称
     * @return
     */
    @Cacheable(value = "springCache", key = "'res_js_path_' + #beanName")
    public String getResJsPath(String beanName) {
        String resPath = getRootPath() + "assets/";
        int index = beanName.indexOf("/");
        String start = index != -1 ? beanName.substring(0, index) : "";
        String end = index != -1 ? beanName.substring(beanName.indexOf("/")) : "/" + beanName;
        String jsPath = start + "/js" + end + ".js";
        if (new File(resPath + jsPath).exists()) {
            return jsPath;
        }
        return null;
    }

    /**
     * 清除缓存的JS文件路径
     * 
     * @param beanName view名称
     */
    @CacheEvict(value = "springCache", key = "'res_js_path_' + #beanName")
    public void clearResJsPath(String beanName) {
        log.debug("==> 清除JS文件路径缓存信息[key:" + beanName + "]");
    }

    /**
     * 根据view名称获取对应CSS文件的路径
     * 
     * @param beanName view名称
     * @return
     */
    @Cacheable(value = "springCache", key = "'res_css_path_' + #beanName")
    public String getResCssPath(String beanName) {
        String resPath = getRootPath() + "assets/";
        int index = beanName.indexOf("/");
        String start = index != -1 ? beanName.substring(0, index) : "";
        String end = index != -1 ? beanName.substring(beanName.indexOf("/")) : "/" + beanName;
        String cssPath = start + "/css" + end + ".css";
        if (new File(resPath + cssPath.replace('/', File.separatorChar)).exists()) {
            return cssPath;
        }
        return null;
    }

    /**
     * 清除缓存的CSS文件路径
     * 
     * @param beanName view名称
     */
    @CacheEvict(value = "springCache", key = "'res_css_path_' + #beanName")
    public void clearResCssPath(String beanName) {
        log.debug("==> 清除CSS文件路径缓存信息[key:" + beanName + "]");
    }

    /**
     * 清楚所有的缓存信息
     */
    @CacheEvict(value = "springCache", allEntries = true)
    public void clearAll() {
        log.debug("==> 清楚信息全部缓存信息");
    }

    @PreDestroy
    public void destroy() {
        // 当对象销毁时，清楚所有的缓存
        clearAll();
    }
}
