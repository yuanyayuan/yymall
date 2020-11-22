package com.nexus.mall.api.component;

import com.nexus.mall.service.OrderService;
import com.nexus.mall.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**

* @Description:    定时任务扫描订单状态

* @Author:         Nexus

* @CreateDate:     2020/11/22 21:27

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/22 21:27

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Component
public class OrderJob {
    @Autowired
    private OrderService orderService;
    //    @Scheduled(cron = "0/3 * * * * ?")
    //    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为："
                + DateUtils.getCurrentDateString(DateUtils.DATETIME_PATTERN));
    }
}
