package com.nexus.mall.service.component;


import com.nexus.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @className CancelOrderReceiver
 * @description 取消订单消息的处理者
 * @author LiYuan
 * @date 2020/10/19
**/
@Component
//@RabbitListener(queues = "mall.order.cancel")
@RabbitListener(queues = "mall.order.cancel.plugin")
@Slf4j
public class CancelOrderReceiver {
    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void handle(String orderId){
        log.info("receive delay message orderId:{}",orderId);
        orderService.closeOrderByOrderId(orderId);
    }
}
