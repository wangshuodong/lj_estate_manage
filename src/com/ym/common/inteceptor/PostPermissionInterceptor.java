package com.ym.common.inteceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.iadpush.manage.entity.SysUsers;


public class PostPermissionInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = null;
        Method method = invocation.getMethod();
        boolean bool = method.isAnnotationPresent(PostPermission.class);
        if (bool) {
            PostPermission limits = method.getAnnotation(PostPermission.class);
            List<Integer> arr = new ArrayList<Integer>();
            for (Integer post : limits.posts()) {
                arr.add(post);
            }
            SysUsers user = BaseAction.getUser();
            if (user != null) {
                if (arr.contains(user.getPostId())) {// 有权限
                    result = invocation.proceed();
                } else {// 无权限
                    if (method.isAnnotationPresent(ResponseBody.class)) {// 当存在ResponseBody注解，返回JSON数据
                        Map<String, Object> rMap = new HashMap<String, Object>();
                        rMap.put("r", false);
                        rMap.put("e", "没有访问权限！");
                        rMap.put("m", "没有访问权限！");
                        result = rMap;
                    } else { // 当不存在ResponseBody注解，返回页面
                        result = "errors/no_access";
                    }
                }
            } else {
                result = invocation.proceed();
            }
        } else {
            result = invocation.proceed();
        }
        return result;
    }
}
