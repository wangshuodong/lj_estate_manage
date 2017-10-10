/**
 * 
 */
package com.ym.iadpush.common.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ym.iadpush.common.utils.PrintMessage;
import com.ym.iadpush.manage.entity.PrintInfo;
import com.ym.iadpush.manage.services.print.PrintInfoService;
import com.ym.iadpush.manage.services.stock.IStockService;

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
    private IStockService stockServiceImpl;
    @Autowired
    private PrintInfoService printInfoService;
    
    //每天晚上11点50执行
    @Scheduled(cron = "0 50 23 * * ?")
    public void printxiaoqu() {
        log.info("-----------wangshuodong:执行每个小区汇总打印------------");
        List<PrintInfo> list = printInfoService.selectBystatus(0);
        for (PrintInfo info : list) {
            PrintMessage obj = new PrintMessage(info.getMachineCode(), info.getMsign());
            StringBuffer sb = new StringBuffer("");
        }
    }
    
    //每天晚上12点执行
    @Scheduled(cron = "0 0 0 * * ?") 
    public void printwuye() {
        log.info("-----------wangshuodong:执行物业汇总打印------------");
    }
}
