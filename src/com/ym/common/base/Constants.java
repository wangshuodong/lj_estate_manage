package com.ym.common.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

 
    public @Value("${shiro.cas.logoutUrl}") void initParam(String logoutUrl) {
        Constants.SHIRO_CAS_LOGOUTURL = logoutUrl;
    }

    /** 岗位：超级管理员 */
    public final static int POST_CG = 1;
    /** 岗位：普通管理员 */
    public final static int POST_PG = 2;

    /** Session保存登录用户信息key */
    public final static String SESSION_ADMIN_USER = "_SESSION_ADMIN_USER_";
    /** session保存验证码信息key */
    public final static String SESSION_KEY_AUTHCODE = "_SESSION_KEY_AUTHCODE_";
    /** 默认用户密码 */
    public final static String DEFAULT_PASSWORD = "111111";
    /** DEBUG模式 */
    public static boolean DEBUG = true;
    /** 是否压缩HTML代码 */
    public static boolean HTML_IS_COMPRESS = false;
    /** 文件上传路径 */
    public static String UPLOAD_PATH_PARENT;
    /** 系统登出地址 */
    public static String SHIRO_CAS_LOGOUTURL;
    /** 税率 */
    public final static float TAX_RATE = 0.95f;
}
