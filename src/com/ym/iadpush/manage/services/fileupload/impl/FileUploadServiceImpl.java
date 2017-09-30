package com.ym.iadpush.manage.services.fileupload.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.FileDeleteUtil;
import com.ym.iadpush.manage.entity.Upload;
import com.ym.iadpush.manage.mapper.FileUploadMapper;
import com.ym.iadpush.manage.services.fileupload.IFileUploadService;

@Service
public class FileUploadServiceImpl implements IFileUploadService {
    
    @Autowired
    private FileUploadMapper uploadMapper;
    
	@Override
	public Integer insertFileUpload(Map<String, Object> paramMap) {
		Upload upload=uploadMapper.selectFileUpload(paramMap);
		if(upload!=null){
			FileDeleteUtil.deleteFile(upload.getPath()+upload.getRealname());
			uploadMapper.deleteFileUpload(paramMap);
		}
		uploadMapper.insertFileUpload(paramMap);
		return uploadMapper.selectFileUpload(paramMap).getId();
	}

	@Override
	public Upload selectFileUpload(Map<String, Object> paramMap) {
		return uploadMapper.selectFileUpload(paramMap);
	}

	@Override
	public Integer deleteFileUpload(Map<String, Object> paramMap) {
		return uploadMapper.deleteFileUpload(paramMap);
	}


    

}
