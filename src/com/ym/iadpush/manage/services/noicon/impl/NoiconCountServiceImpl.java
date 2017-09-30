package com.ym.iadpush.manage.services.noicon.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.NoiconActive;
import com.ym.iadpush.manage.entity.NoiconActiveRate;
import com.ym.iadpush.manage.entity.NoiconAd;
import com.ym.iadpush.manage.entity.NoiconCount;
import com.ym.iadpush.manage.entity.SysConfigLog;
import com.ym.iadpush.manage.mapper.NoiconCountMapper;
import com.ym.iadpush.manage.mapper.SysConfigLogMapper;
import com.ym.iadpush.manage.services.noicon.INoiconCountService;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class NoiconCountServiceImpl implements INoiconCountService {
	
	@Autowired
    private NoiconCountMapper noiconMapper;
	
	@Autowired
	private SysConfigLogMapper logMapper;
	
	@Override
	public QueryResult queryDataStatement(Map<String, Object> params) {
		QueryResult result = new QueryResult();
        result.setData(noiconMapper.getAllNoicon(params));
        result.setCount(noiconMapper.getCountNoicon(params).size());
        return result;
	}

	@Override
	public NoiconCount getTotallCountNoicon(Map<String, Object> params) {
		return noiconMapper.getTotallCountNoicon(params);
	}


	@Override
	public NoiconAd select_noicon_config_data() {
		return noiconMapper.select_noicon_config_data();
	}

	@Override
	public Integer updateNoiconAd(Map<String, Object> paramMap) {
		
		NoiconAd ad = noiconMapper.selectByKey(Integer.valueOf(String.valueOf(paramMap.get("id"))));
		SysConfigLog log = new SysConfigLog();
		String content = "";
		if(ad.getTitle() != null && !ad.getTitle().trim().equalsIgnoreCase(String.valueOf(paramMap.get("title")))){
			content += "无图标标题 由【"+ad.getTitle()+" 】修改为了【  "+paramMap.get("title")+"】";
		}else if(ad.getTitle() == null && paramMap.get("title") != null){
			content += "新增了无图标标题 【"+paramMap.get("title")+"】";
		}
		
		if(ad.getPackagename() != null && !ad.getPackagename().trim().equalsIgnoreCase(String.valueOf(paramMap.get("packagename")))){
			content += "包名由   【"+ad.getPackagename()+"】 修改为了【  "+paramMap.get("packagename")+"】";
		}else if(ad.getPackagename() == null && paramMap.get("packagename") != null){
			content += "新增了包名  【"+ad.getPackagename()+"】";
		}
		
		if(ad.getPopCount() != null && ad.getPopCount().intValue() != Integer.valueOf(String.valueOf(paramMap.get("popCount")))){
			content += "弹出次数 由【"+ad.getPopCount()+"】 修改为了 【"+paramMap.get("popCount")+"】";
		}else if(ad.getPopCount() == null && paramMap.get("popCount") != null){
			content += "新增了弹出次数   【"+paramMap.get("popCount")+"】";
		}
		
		if(ad.getIntervalTime() != null && ad.getIntervalTime() != Integer.valueOf(String.valueOf(paramMap.get("intervalTime")))){
			content += "延时弹出时间  由   【"+paramMap.get("popCount")+"】修改为了【"+paramMap.get("intervalTime")+"】";
		}else if(ad.getIntervalTime() == null && paramMap.get("intervalTime") != null){
			content += "新增了延时时间【"+paramMap.get("intervalTime")+"】";
		}
		
		if(ad.getContext() != null && !ad.getContext().trim().equalsIgnoreCase(String.valueOf(paramMap.get("context")))){
			content += "内容由【"+ad.getContext()+"】 修改为了【"+paramMap.get("context")+"】";
		}else if(ad.getContext() == null && paramMap.get("context") != null){
			content += "新增了描述【"+paramMap.get("context")+"】";
		}
		
		if(null != paramMap.get("imgrealname") && null != paramMap.get("imgUrlpre")){
			if(ad.getImgrealname() != null && !ad.getImgrealname().trim().equalsIgnoreCase(String.valueOf(paramMap.get("imgrealname")))){
				content += "图片由【"+ad.getImgrealname()+"】修改为了【"+paramMap.get("imgrealname")+"】";
			}else if(ad.getImgrealname() == null && paramMap.get("imgrealname") != null){
				content += "新增了图片【"+paramMap.get("imgrealname")+"】";
			}
		}
		
		if(null != paramMap.get("apkrealname") && null != paramMap.get("apkUrlpre")){
			if(ad.getApkrealname() != null && !ad.getApkrealname().trim().equalsIgnoreCase(String.valueOf(paramMap.get("apkrealname")))){
				content += "apk由【"+ad.getApkrealname()+"】修改为了【"+paramMap.get("apkrealname")+"】";
			}else if(ad.getApkrealname() == null && paramMap.get("apkrealname") != null){
				content += "新增了apk【"+ad.getApkrealname()+"】";
			}
		}
		
		if(content != ""){
			log.setChangeContent(content);	
		    logMapper.insert(log);
		}
		return noiconMapper.updateNoiconAd(paramMap);
	}

	@Override
	public QueryResult queryNoticeActiveData(Map<String, Object> params) {
		QueryResult result = new QueryResult();
        result.setData(noiconMapper.getAllNoiconActiveReport(params));
        result.setCount(noiconMapper.getCountAllNoiconActiveReport(params));
        return result;
	}

	@Override
	public NoiconActive getTotalIcountAndAcount(Map<String, Object> params) {
		return noiconMapper.getTotalIcountAndAcount(params);
	}

	@Override
	public QueryResult queryNoiconActiveRate(Map<String, Object> params) {
		QueryResult result = new QueryResult();
        result.setData(noiconMapper.getAllNoiconActiveRate(params));
        result.setCount(noiconMapper.getCountAllNoiconActiveRate(params));
        return result;
	}

	@Override
	public NoiconActiveRate totalNoiconActiveRate(Map<String, Object> params) {
		return noiconMapper.totalNoiconActiveRate(params);
	}
	
}
