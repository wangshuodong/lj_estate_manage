/**
 * 
 */
package com.ym.common.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ym.common.utils.DateUtils;
import com.ym.iadpush.common.utils.PrintMessage;
import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.Department;
import com.ym.iadpush.manage.entity.PrintInfo;
import com.ym.iadpush.manage.mapper.BillAccountMapper;
import com.ym.iadpush.manage.mapper.DepartmentMapper;
import com.ym.iadpush.manage.services.print.PrintInfoService;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年10月10日 下午3:25:30 
 * @Version: $Id$
 * @Desc: <p></p>
 */
@Component
public class printTask {
    private static Log log = LogFactory.getLog(printTask.class);
    
    @Autowired
    private BillAccountMapper billAccountMapper;
    @Autowired
    private PrintInfoService printInfoService;
    @Autowired
    private DepartmentMapper departmentMapper;
    
    //每天晚上11点50执行
    @Scheduled(cron = "0 50 23 * * ?") 
    public void printxiaoqu() throws ParseException {
        log.info("-----------wangshuodong:执行每个小区汇总打印------------");
        String date = DateUtils.getDate();
        List<PrintInfo> list = printInfoService.selectBystatus(0);
        for (PrintInfo info : list) {
            PrintMessage printMessage = new PrintMessage(info.getMachineCode(), info.getMsign());
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("departmentId", info.getDepartmentid());
            paramMap.put("payDate", date);
            List<BillAccount> list1 = billAccountMapper.getPrintOne(paramMap);
            List<BillAccount> list2 = billAccountMapper.getPrintMore(paramMap);
            int userCount = billAccountMapper.getPrintOneCount(paramMap);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("departmentId", Integer.valueOf(info.getDepartmentid()));
            Department department = departmentMapper.getDepartmentById(param);
            param.put("departmentId", Integer.valueOf(department.getId()));
            department = departmentMapper.getDepartmentById(param);
            if (list1 != null && list1.size() > 0) {
                StringBuffer sb = new StringBuffer("");
                sb.append("<center>支付宝智慧小区</center>\r");
                sb.append("小区名称："+list1.get(0).getDepartmentName()+"\r");
                sb.append("汇总时间："+date+"\r");
                double totalAmount = 0;
                for (BillAccount billAccount : list1) {
                    totalAmount += billAccount.getSumAmount();
                }
                sb.append("交易总额："+totalAmount+"\r");
                sb.append("户数："+userCount+"\r");
                sb.append("缴费明细：\r");
                sb.append("<table><tr><td>交易笔数</td><td>支付方式</td><td>金额</td></tr>");
                for (BillAccount billAccount : list1) {
                    sb.append("<tr><td>"+billAccount.getCountNum()+"</td><td>"+billAccount.getPayType()+"</td><td>"+billAccount.getSumAmount()+"</td></tr>");
                }
                sb.append("</table>");
                sb.append("----------------------\r");
                sb.append("<table><tr><td>费用类型</td><td>金额</td></tr>");
                for (BillAccount billAccount : list2) {
                    sb.append("<td>"+billAccount.getCost_type()+"</td><td>"+billAccount.getSumAmount()+"</td></tr>");
                }
                sb.append("</table>");
                sb.append("收款单位："+department.getName()+"\r");
                sb.append("<center>技术支持：早早科技/0571-88683117/www.早早.com\r");
                sb.append("----------------------\r");
                sb.append("<center>交易小票</center>\r");
                printMessage.sendContent(sb.toString());
            }
        }
    }
    
    //每天晚上12点执行
    @Scheduled(cron = "0 55 23 * * ?") 
    public void printwuye() throws ParseException{
        log.info("-----------wangshuodong:执行物业汇总打印------------");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        List<PrintInfo> list = printInfoService.selectBystatus(1);
        for (PrintInfo info : list) {
            PrintMessage printMessage = new PrintMessage(info.getMachineCode(), info.getMsign());
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("departmentId", Integer.valueOf(info.getDepartmentid()));
            Department departmentInfo = departmentMapper.getDepartmentById(param);
            param.put("departmentId", Integer.valueOf(departmentInfo.getId()));
            departmentInfo = departmentMapper.getDepartmentById(param);
            List<Department> list1 = departmentMapper.selectCounthouse(info.getDepartmentid());
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("parentId", info.getDepartmentid());
            paramMap.put("payDate", sdf.parse(date));
            BillAccount bill1 = billAccountMapper.getPrintTotal1(paramMap);
            List<BillAccount> list2 = billAccountMapper.getPrintTotal2(paramMap);
            if (bill1 != null && bill1.getCountNum() > 0) {
                StringBuffer sb = new StringBuffer("");
                sb.append("<center>支付宝智慧小区</center>\r");
                sb.append("汇总时间："+date+"\r");
                sb.append("----------------------\r");
                sb.append("小区数量："+list1.size()+"\r");
                sb.append("交易户数："+bill1.getCountNum()+"\r");
                sb.append("总额："+bill1.getSumAmount()+"\r");
                for (BillAccount bill : list2) {
                    sb.append(bill.getPayType() + "："+bill.getSumAmount()+"\r");
                }
                sb.append("----------------------\r");
                for (Department department : list1) {
                    int temp1 = 0;
                    double temp2 = 0.0;
                    Map<String, Object> paramMap1 = new HashMap<String, Object>();
                    paramMap1.put("departmentId", department.getId());
                    paramMap1.put("payDate", sdf.parse(date));
                    List<BillAccount> list3 = billAccountMapper.getPrintOne(paramMap1);
                    sb.append("小区名称："+department.getName()+"\r");
                    for (BillAccount bill : list3) {
                        temp1 += bill.getCountNum();
                        temp2 += bill.getSumAmount();
                        sb.append(bill.getPayType() + "："+bill.getSumAmount()+"\r");
                    }
                    sb.append("交易户数："+temp1+"\r");
                    sb.append("总额："+temp2+"\r");
                    sb.append("----------------------\r");
                }
                
                sb.append("收款单位："+departmentInfo.getName()+"\r");
                sb.append("<center>技术支持：早早科技/0571-88683117/www.早早.com</center>\r");
                sb.append("----------------------\r");
                sb.append("<center>交易小票</center>\r");
                printMessage.sendContent(sb.toString());
            }
        }
    }
}
