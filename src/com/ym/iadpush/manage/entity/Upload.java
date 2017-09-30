package com.ym.iadpush.manage.entity;

import java.util.Date;


public class Upload {
	/**
	 * ID 
	 */
	private Integer id;
	/**
	 * 真实文件名
	 */
	private String realname;
	/**
	 * 文件名（上传时的文件名）
	 */
	private String filename;
	/**
	 *文件模块 
	 */
	private String filemodule;
	/**
	 * 模块ID
	 */
	private Integer moduleid;
	/**
	 * 文件路径
	 */
	private String path;
	/**
	 * 文件类型
	 */
	private String filetype;
	/**
	 * 文件大小
	 */
	private Long filesize;
	/**
	 * 上传时间
	 */
	private Date uploadtime;
	/**
	 * 修改时间
	 */
	private Date updatetime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilemodule() {
		return filemodule;
	}
	public void setFilemodule(String filemodule) {
		this.filemodule = filemodule;
	}
	public Integer getModuleid() {
		return moduleid;
	}
	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	public Date getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Upload(String realname, String filename, String filemodule,
			Integer moduleid, String path, String filetype, Long filesize,
			Date uploadtime, Date updatetime) {
		super();
		this.realname = realname;
		this.filename = filename;
		this.filemodule = filemodule;
		this.moduleid = moduleid;
		this.path = path;
		this.filetype = filetype;
		this.filesize = filesize;
		this.uploadtime = uploadtime;
		this.updatetime = updatetime;
	}
	public Upload() {
		super();
	}
	
	
}
