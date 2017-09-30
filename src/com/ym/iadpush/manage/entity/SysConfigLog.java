package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SysConfigLog implements Serializable {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -5771183450292749584L;
	
	private String changeContent;
	
    private String oprationUsername;
    
    private Integer oprationUid;
    
    private Timestamp updateTime;
    
	public String getChangeContent() {
		return changeContent;
	}
	
	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}
	
	public String getOprationUsername() {
		if(oprationUsername != null){
			return oprationUsername;
		}else{
			return getUser().getUsername();
		}
	}
	
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setOprationUsername(String oprationUsername) {
		this.oprationUsername = oprationUsername;
	}
	
	public Integer getOprationUid() {
		oprationUid = getUser().getUserId();
		return oprationUid;
	}

	public void setOprationUid(Integer oprationUid) {
		this.oprationUid = oprationUid;
	}
	
	public static SysUsers getUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // PrincipalCollection collection = subject.getPrincipals();
            // return (SysUsers) collection.asList().get(2);
            return (SysUsers) subject.getPrincipal();
        }
        return null;
    }
}
