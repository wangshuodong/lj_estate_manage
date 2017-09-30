package com.ym.iadpush.manage.services.app.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.mapper.AppStatementMapper;
import com.ym.iadpush.manage.services.app.AppStatementService;
@Service
public class AppStatementServiceImpl implements AppStatementService {
    
    @Autowired
    private AppStatementMapper appStatementMapper;
    
    
    
    public AppStatementMapper getAppStatementMapper() {
        return appStatementMapper;
    }



    public void setAppStatementMapper(AppStatementMapper appStatementMapper) {
        this.appStatementMapper = appStatementMapper;
    }



    public QueryResult queryAppStatement(Map<String, Object> params) {
        QueryResult result = new QueryResult();
        String appkey = (String) params.get("appkey");
        if(appkey != null && !appkey.trim().equalsIgnoreCase("")){
            String[] key = appkey.split("-");
            try {
                if(key != null && key.length == 3){
                    params.put("appid", key[2].trim());
                    params.put("qid", key[1].trim());
                    params.put("uid", key[0].trim());
                }else{
                	 params.put("appid", -9999);
                     params.put("qid",  -9999);
                     params.put("uid",  -9999);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        result.setData(appStatementMapper.queryAppStatement(params));
        result.setCount(appStatementMapper.countAppStatement(params));
        result.setCollect(appStatementMapper.sumAppStatement(params));
        return result;
    }

}
