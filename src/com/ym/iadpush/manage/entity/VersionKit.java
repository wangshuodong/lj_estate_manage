/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.util.Date;


public class VersionKit {
    private int id;
    private String kv;
    private String name;
    private String mid;
    private int type;
    private Date create_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKv() {
		return kv;
	}
	public void setKv(String kv) {
		this.kv = kv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
    

}
