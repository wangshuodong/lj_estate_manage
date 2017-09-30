package com.ym.iadpush.manage.services.datamanager.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.DateUtils;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.dev.mapper.DevAppMapper;
import com.ym.iadpush.manage.entity.AddIcount;
import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.entity.DeveloperReport;
import com.ym.iadpush.manage.entity.IadpushCpacount;
import com.ym.iadpush.manage.entity.IadpushEarn;
import com.ym.iadpush.manage.mapper.AddIcountMapper;
import com.ym.iadpush.manage.mapper.DeveloperReportMapper;
import com.ym.iadpush.manage.mapper.IadpushCpacountMapper;
import com.ym.iadpush.manage.mapper.IadpushEarnMapper;
import com.ym.iadpush.manage.mapper.SysConfigMapper;
import com.ym.iadpush.manage.services.datamanager.AddIcountService;
@Service
public class AddIcountServiceImpl implements AddIcountService {
	
	@Autowired
	private AddIcountMapper addIcountMapper;
	
	@Autowired
	private DevAppMapper devAppMapper;
	
	@Autowired
	private SysConfigMapper   sysConfigMapper;
	
	@Autowired
	private IadpushEarnMapper earnMapper;
	
	@Autowired
	private IadpushCpacountMapper cpacountMapper;
	
	@Autowired
	private DeveloperReportMapper developerReportMapper;
	
	
	public QueryResult query(Map<String, Object> params) {
		
		QueryResult result = new QueryResult();
		List<AddIcount> list = addIcountMapper.queryByOrder(params);
		if(list != null && list.size() > 0){
			result.setData(list);
			int count = addIcountMapper.countByOrder(params);
			result.setCount(count);
		}
		
		return result;
	}
	
	public List<App> getApps(String type,String[] ids,Map<String,Object> params){
		
		List<App> list = new ArrayList<App>();
		
		if(type.trim().equalsIgnoreCase("user")){
			//这些用户下的所有APP
			if(ids == null || ids.length == 0){
				list = devAppMapper.queryByApps(null);
			}else{
				String uids = "";
				boolean flag = false;
				for(String str:ids){
					uids += flag?","+str:str;
					flag = true;
				}
				params.put("uids", uids);
				list = devAppMapper.queryByUsers(params);
			}
		}else if(type.trim().equalsIgnoreCase("app")){
			if(ids == null || ids.length == 0){
				list = devAppMapper.queryByApps(null);
			}else{
				String appids = "";
				boolean flag = false;
				for(String str:ids){
					appids += flag?","+str:str;
					flag = true;
				}
				params.put("appids", appids);
				list = devAppMapper.queryByApps(params);
			}
		}
		
		return list;
	}
	
	

	private boolean fixAddEarn(int amount_num,App app,Date d,String h,Double e){
		try {
			IadpushEarn earn = new IadpushEarn();
			earn.setAppid(app.getAid());
			earn.setEarnMoney(BigDecimal.valueOf(e));
			earn.setUid(app.getUid());
			earn.setQid(app.getQid());
			earn.setEdate(d);
			earn.setHour(Short.valueOf(h));
			earn.setType(2);//补量产生
			earn.setStatus(0);//未结算
			earn.setIcount(amount_num);
			earn.setAcount(amount_num);
			earn.setIcountkou(amount_num);
			earn.setAcountkou(amount_num);
			earn.setCreateTime(new Timestamp(System.currentTimeMillis()));
			earnMapper.insert(earn);
		} catch (Exception e2) {
			// TODO: handle exception
			LogFactory.getLog(AddIcountServiceImpl.class).error(e2);
			return false;
		}
		return true;
	}
	

	private boolean fixAddCpacount(int amount_num,String h,App app,Date d){
		try {
			IadpushCpacount cpacount = new IadpushCpacount();
			cpacount.setAcount(amount_num);
			cpacount.setAcountkou(amount_num);
			cpacount.setAdate(d);
			cpacount.setAppid(app.getAid());
			cpacount.setCompensationacount(amount_num);
			cpacount.setCompensationicount(amount_num);
			cpacount.setHour(Short.valueOf(h));
			cpacount.setIcount(amount_num);
			cpacount.setIcountkou(amount_num);
			cpacount.setUid(app.getUid());
			cpacount.setQid(app.getQid());
			cpacount.setAppkey(app.getUid()+"-"+app.getQid()+"-"+app.getAid());
			cpacountMapper.insert(cpacount);
		} catch (Exception e) {
			// TODO: handle exception
			LogFactory.getLog(AddIcountServiceImpl.class).error(e);
			return false;
		}
		return true;
	}
	

	private boolean fixAddIcount(int amount,Date d,String h,App app,int cuid,String addType){
		try {
			AddIcount addIcount = new AddIcount();
			addIcount.setAcount(Integer.valueOf(amount));
			addIcount.setIcount(Integer.valueOf(amount));
			addIcount.setAdate(d);
			addIcount.setHour(Short.valueOf(h));
			addIcount.setAppid(app.getAid());
			addIcount.setCreatetime(new Date(System.currentTimeMillis()));
			addIcount.setDeveloperId(app.getUid());
			addIcount.setCuid(cuid);
			addIcount.setAddType(addType);
			addIcountMapper.insert(addIcount);
		} catch (Exception e) {
			// TODO: handle exception
			LogFactory.getLog(AddIcountServiceImpl.class).error(e);
			return false;
		}
		return true;
	}
	

	private boolean aboutCpacount(String h,App app,String amount,Date d){
		try {
			Map<String,Object> cpaParams = new HashMap<String, Object>();
			cpaParams.put("hour", h);
			cpaParams.put("date", DateUtils.formartRandomDate(d, "yyyy-MM-dd"));
			cpaParams.put("appid", app.getAid());
			cpaParams.put("uid", app.getUid());
			App cpaApp = devAppMapper.queryFormCpacount(cpaParams);
			if(cpaApp != null){
				//更新
				Map<String,Object> cpa = new HashMap<String, Object>();
				cpa.put("hour", h);
				cpa.put("date",  DateUtils.formartRandomDate(d, "yyyy-MM-dd"));
				cpa.put("appid", app.getAid());
				cpa.put("uid", app.getUid());
				cpa.put("amount", amount);
				devAppMapper.updateCpacountByParams(cpa);
			}else{
				//新增
				fixAddCpacount(Integer.valueOf(amount), h, app, d);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogFactory.getLog(AddIcountServiceImpl.class).error(e);
			return false;
		}
		return true;
	}
/*	
	private boolean aboutVcount(Date d,String hour,App app){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("adate", DateUtils.formartRandomDate(d, "yyyy-MM-dd"));
		params.put("hour", hour);
		params.put("appid", app.getAid());
		params.put("uid", app.getUid());
		App isexist = devAppMapper.queryFormVcount(params);
		if(isexist != null){
			//更新
		}else{
			//新建
		}
		return true;
	}*/

	private boolean aboutEarn(String amount,Date d,App app,String price,String h){
		try {
			Map<String,Object> earnParams = new HashMap<String, Object>();
			String date = DateUtils.getDate(d, "yyyy-MM-dd");
			//维护earn表
			//更新earn
			earnParams.put("date", date);
			earnParams.put("amount", amount);
			earnParams.put("hour", h);
			earnParams.put("appid", app.getAid());
			earnParams.put("uid", app.getUid());
			earnParams.put("price", Double.valueOf(price)*Double.valueOf(amount));
			App earnApp = devAppMapper.queryFormEarnByParams(earnParams);
			if(earnApp != null){
				//更新
				earnMapper.updateEarnByParams(earnParams);
			}else{
				//创建
				fixAddEarn(Integer.valueOf(amount), app, d, h, Double.valueOf(price)*Double.valueOf(amount));
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogFactory.getLog(AddIcountServiceImpl.class).error(e);
			return false;
		}
		return true;
	}
	
	private boolean aboutDevReport(App app,String amount,String[] hours,Date d,String price,int cuid){
		Map<String,Object> reportParams = new HashMap<String, Object>();
		reportParams.put("date", DateUtils.formartRandomDate(d, "yyyy-MM-dd"));
		reportParams.put("appid", app.getAid());
		reportParams.put("uid", app.getUid());
		App reportApp = devAppMapper.queryFormDeveloper(reportParams);
		if(reportApp != null){
			//更新
			reportParams.put("amount", Double.valueOf(amount)*hours.length);
			reportParams.put("price", Double.valueOf(amount)*Double.valueOf(price)*hours.length);
			devAppMapper.updateDevReportByParams(reportParams);
		}else{
			
			Double icount = Double.valueOf(amount)*hours.length;
			Double earn = Double.valueOf(amount)*Double.valueOf(price)*hours.length;
			//创建
			//维护dev_report表
			DeveloperReport report = new DeveloperReport();
			report.setAcount(icount.intValue());
			report.setAcountkou(icount.intValue());
			report.setAddate(d);
			report.setAppid(app.getAid());
			report.setEarn(BigDecimal.valueOf(earn));
			report.setIcount(icount.intValue());
			report.setIcountkou(icount.intValue());
			report.setIcountkouAct(BigDecimal.valueOf(Double.valueOf(String.valueOf(icount.intValue()))));
			report.setAcountkou(icount.intValue());
			report.setLastUpdate(new Timestamp(System.currentTimeMillis()));
			report.setOperUid(cuid);
			report.setUid(app.getUid());
			developerReportMapper.insert(report);
		}
		return true;
	}
	
	

	/**
	 * 根据数量补量
	 * @param ids
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param hours
	 * @param amount
	 * @param cuid
	 * @param addType
	 */
	private void addByCount(String[] ids, String type, String startDate,
			String endDate, String[] hours, String amount,int cuid,String addType){
		
		List<App> list = null;
		
		//获取到日期
		List<Date> dates = DateUtils.getDatesBetweenTwoDate(DateUtils.parseDate(startDate), DateUtils.parseDate(endDate));
		Map<String,Object> params = new HashMap<String, Object>();
		
		/**
		 * 获取待补量的APP
		 */
		list = getApps(type, ids, params);
		
		if(list != null && list.size() > 0){
	
			//避免日期重复
			if(dates.size() == 2 && dates.get(0).equals(dates.get(1))){
				dates.remove(1);
			}
			
			//遍历App
			//查询出单价
			String price = sysConfigMapper.getValuesByName("imeiPrice");
			for(App app:list){
				//是否存在历史收入记录,如果无历史收入记录则不补量
				App isexist = devAppMapper.queryFormEarnByApp(app.getAid());
				if(isexist != null){
					//遍历日期
					for(Date d:dates){
						// 遍历小时
						for(String h:hours){
							//当日当时该app是否存在cpacount数据,维护cpacount
							aboutCpacount(h, app, amount, d);
							
							//维护收入表
							aboutEarn(amount, d, app, price, h);
							
							//维护日志
							fixAddIcount(Integer.valueOf(amount), d, h, app, cuid, addType);
						}
						
						//维护developer
						aboutDevReport(app, amount, hours, d, price, cuid);
					}
				}
				
			}
			
		}
	}


	public void addAmount(String[] ids, String type, String startDate,
			String endDate, String[] hours, String amount,int cuid,String addType) {
		
		List<String> types = new ArrayList<String>();
		types.add("rate");
		types.add("count");
		
		if(addType != null && addType.trim().equalsIgnoreCase("count")){
			//根据数量统计
			addByCount(ids, type, startDate, endDate, hours, amount, cuid, addType);
		}else if(addType != null && addType.trim().equalsIgnoreCase("rate")){
			//根据比例更新
			addByRate(ids, type, startDate, endDate, hours, amount, cuid, addType);
		}
		
		
	}
	
	/**
	 * 根据比例补量
	 * @param ids
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param hours
	 * @param amount
	 * @param cuid
	 * @param addType
	 */
	public void addByRate(String[] ids, String type, String startDate,
			String endDate, String[] hours, String amount,int cuid,String addType){
		

		List<App> list = null;
		
		//获取到日期
		List<Date> dates = DateUtils.getDatesBetweenTwoDate(DateUtils.parseDate(startDate), DateUtils.parseDate(endDate));
		Map<String,Object> params = new HashMap<String, Object>();
		
		if(dates.size() == 2 && dates.get(0).equals(dates.get(1))){
			dates.remove(1);
		}
		
		/**
		 * 获取待补量的APP
		 */
		list = getApps(type, ids, params);
		
		if(list != null && list.size() > 0){

			//遍历所有日期
			for(Date d:dates){
				//遍历所有时间段
				for(String h:hours){
					//遍历所有APP
					for(App app:list){
						//查询当天，当时，该APP的新增及活跃
						//如果无历史收入记录则不补量
						App isexits = devAppMapper.queryFormEarnByApp(app.getAid());
						if(isexits != null){
							int addCount = 0;
							try {
								Map<String,Object> pmap = new HashMap<String, Object>();
								pmap.put("appid", app.getAid());
								pmap.put("hour", h);
								pmap.put("date", DateUtils.getDate(d, "yyyy-MM-dd"));
								
								IadpushEarn earn = earnMapper.selectByParams(pmap);
								if(earn != null){
									int icount = earn.getIcount();
									try {
										Double di = (icount * Double.valueOf(amount));
										addCount = di.intValue();
									} catch (Exception e) {
										// TODO: handle exception
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
								LogFactory.getLog(AddIcountServiceImpl.class).error(e);
							}
							//如果不为0 则补量
							if(addCount != 0){
								String date = DateUtils.getDate(d, "yyyy-MM-dd");
								//维护earn表
								//查询出单价
								String price = sysConfigMapper.getValuesByName("imeiPrice");
								//更新earn
								params.put("date", date);
								params.put("amount", addCount);
								params.put("hour", h);
								params.put("appid", app.getAid());
								params.put("uid", app.getUid());
								params.put("price", Double.valueOf(price)*Double.valueOf(addCount));
								earnMapper.updateEarnByParams(params);
								
								//更新cpacount
								devAppMapper.updateCpacountByParams(params);
								
								//更新developer_report
								devAppMapper.updateDevReportByParams(params);
								
								AddIcount addIcount = new AddIcount();
								addIcount.setAcount(addCount);
								addIcount.setIcount(addCount);
								addIcount.setAdate(d);
								addIcount.setHour(Short.valueOf(h));
								addIcount.setAppid(app.getAid());
								addIcount.setCreatetime(new Date(System.currentTimeMillis()));
								addIcount.setDeveloperId(app.getUid());
								addIcount.setCuid(cuid);
								addIcount.setAddType(addType);
								addIcountMapper.insert(addIcount);
								
							}
						}
					}
					
				}
			}

		}
		
		
	}

}
