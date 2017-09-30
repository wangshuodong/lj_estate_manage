package com.ym.iadpush.manage.mapper;

import java.util.Map;

import com.ym.iadpush.manage.entity.Upload;

public interface FileUploadMapper {
	
	/**
	 * 插入文件上传数据记录
	 * @param paramMap  Upload 
	 * @return int 
	 */
	Integer insertFileUpload(Map<String, Object> paramMap);

	Upload selectFileUpload(Map<String, Object> paramMap);

	Integer deleteFileUpload(Map<String, Object> paramMap);

	


	
	
	
}
