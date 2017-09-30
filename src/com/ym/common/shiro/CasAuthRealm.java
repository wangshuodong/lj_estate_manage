package com.ym.common.shiro;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.ym.iadpush.manage.entity.SysMenus;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

public class CasAuthRealm extends CasRealm {

    private @Autowired IUserMgr userSv;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo result = (SimpleAuthorizationInfo) super.doGetAuthorizationInfo(principals);
        // 获取当前登录的用户
        SysUsers user = (SysUsers) principals.asList().get(2);
        List<SysMenus> menus = user.getMenus();
        for (SysMenus menu : menus) {
            if (menu.getIsParent() && menu.getStatus() == 1) {
                for (SysMenus sysMenus : menu.getChildrens()) {
                    result.addStringPermission("/" + sysMenus.getUrl());
                }
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) super.doGetAuthenticationInfo(token);
        if (info != null) {
            SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) info.getPrincipals();
            SysUsers user = userSv.getByName(String.valueOf(principalCollection.asList().get(0)));
            principalCollection.add(user, getName());
            info.setPrincipals(principalCollection);
            Map<String, Object> arrs = (Map<String, Object>) principalCollection.asList().get(1);
            System.out.println(arrs);
        }
        return info;
    }

}
