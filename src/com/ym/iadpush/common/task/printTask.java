/**
 * 
 */
package com.ym.iadpush.common.task;

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

import com.ym.iadpush.common.utils.PrintMessage;
import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.PrintInfo;
import com.ym.iadpush.manage.mapper.BillAccountMapper;
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
    
    //每天晚上11点50执行
    @Scheduled(cron = "0 50 23 * * ?")
    public void printxiaoqu() throws ParseException {
        log.info("-----------wangshuodong:执行每个小区汇总打印------------");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        List<PrintInfo> list = printInfoService.selectBystatus(0);
        for (PrintInfo info : list) {
            PrintMessage printMessage = new PrintMessage(info.getMachineCode(), info.getMsign());
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("departmentId", info.getDepartmentid());
            paramMap.put("payDate", sdf.parse(date));
            List<BillAccount> list1 = billAccountMapper.getPrintOne(paramMap);
            List<BillAccount> list2 = billAccountMapper.getPrintMore(paramMap);
            if (list1 != null && list1.size() > 0) {
                StringBuffer sb = new StringBuffer("");
                sb.append("<center>支付宝智慧小区</center>\r");
                sb.append("小区名称："+list1.get(0).getDepartmentName()+"\r");
                sb.append("结算时间："+date+"\r");
                double totalAmount = 0;
                for (BillAccount billAccount : list1) {
                    totalAmount += billAccount.getSumAmount();
                }
                sb.append("交易总额："+totalAmount+"\r");
                sb.append("缴费明细：\r");
                sb.append("<table><tr><td>户数</td><td>支付方式</td><td>支付金额</td></tr>");
                for (BillAccount billAccount : list1) {
                    sb.append("<tr><td>"+billAccount.getCountNum()+"</td><td>"+billAccount.getPayType()+"</td><td>"+billAccount.getSumAmount()+"</td></tr>");
                }
                sb.append("</table>\r");
                
                sb.append("<table><tr><td>费用类型</td><td>支付金额</td></tr>");
                for (BillAccount billAccount : list2) {
                    sb.append("<td>"+billAccount.getCost_type()+"</td><td>"+billAccount.getSumAmount()+"</td></tr>");
                }
                sb.append("</table>\r");
                sb.append("<center><FB><FS>浙江中都物业有限公司</FS></FB></center>\r");
                sb.append("<center>技术支持：杭州早早科技 400-720-8888</center>\r");
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
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("departmentId", info.getDepartmentid());
            paramMap.put("payDate", sdf.parse(date));
            List<BillAccount> list1 = billAccountMapper.getPrintOne(paramMap);
            List<BillAccount> list2 = billAccountMapper.getPrintMore(paramMap);
            if (list1 != null && list1.size() > 0) {
                StringBuffer sb = new StringBuffer("");
                sb.append("<center>支付宝智慧小区</center>\r");
                sb.append("小区名称："+list1.get(0).getDepartmentName()+"\r");
                sb.append("结算时间："+date+"\r");
                sb.append("缴费明细：\r");
                sb.append("<table><tr><td>户数</td><td>支付方式</td><td>支付金额</td></tr>");
                for (BillAccount billAccount : list1) {
                    sb.append("<tr><td>"+billAccount.getCountNum()+"</td><td>"+billAccount.getPayType()+"</td><td>"+billAccount.getSumAmount()+"</td></tr>");
                }
                sb.append("</table>\r");
                
                sb.append("<table><tr><td>费用类型</td><td>支付金额</td></tr>");
                for (BillAccount billAccount : list2) {
                    sb.append("<td>"+billAccount.getCost_type()+"</td><td>"+billAccount.getSumAmount()+"</td></tr>");
                }
                sb.append("</table>\r");
                sb.append("<center><FB><FS>浙江中都物业有限公司</FS></FB></center>\r");
                sb.append("<center>技术支持：杭州早早科技 400-720-8888</center>\r");
                sb.append("----------------------\r");
                sb.append("<center>交易小票</center>\r");
                printMessage.sendContent(sb.toString());
            }
        }
    }
}
