package com.ym.iadpush.manage.entity;

import java.sql.Date;


public class NoiconAd {
	/**
	 * ID 
	 */
	private Integer id;
	private String packagename;
	private String title;
	private Integer popCount;
	private Integer intervalTime;
	private String imgUrlpre;
	private String apkUrlpre;
	private String context;
	private String imgrealname;
	private String apkrealname;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPopCount() {
		return popCount;
	}
	public void setPopCount(Integer popCount) {
		this.popCount = popCount;
	}
	public Integer getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getImgUrlpre() {
		return imgUrlpre;
	}
	public void setImgUrlpre(String imgUrlpre) {
		this.imgUrlpre = imgUrlpre;
	}
	public String getApkUrlpre() {
		return apkUrlpre;
	}
	public void setApkUrlpre(String apkUrlpre) {
		this.apkUrlpre = apkUrlpre;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImgrealname() {
		return imgrealname;
	}
	public void setImgrealname(String imgrealname) {
		this.imgrealname = imgrealname;
	}
	public String getApkrealname() {
		return apkrealname;
	}
	public void setApkrealname(String apkrealname) {
		this.apkrealname = apkrealname;
	}
	
	
}
