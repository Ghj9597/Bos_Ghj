package cn.itcast.bos.quartz;

import cn.itcast.bos.service.PromotionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by 犹良 on 2017/8/6 0006.
 */
public class ActivityJob implements Job {
    @Autowired
    private PromotionService promotionService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("》》》》》》》》》》》》正则执行宣传活动的自动过期功能!!!");
        promotionService.updateStatus(new Date());
    }
}
