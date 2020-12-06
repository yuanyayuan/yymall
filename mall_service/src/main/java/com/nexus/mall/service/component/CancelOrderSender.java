package com.nexus.mall.service.component;

import com.nexus.mall.common.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @className CancelOrderSender
 * @description 取消订单消息的发出者
 * @author LiYuan
 * @date 2020/10/19
**/
@Component
@Slf4j
public class CancelOrderSender {
    @Autowired
    private AmqpTemplate amqpTemplate;
      /**
       * 死信队列
       * @Author : Nexus
       * @Description : 死信队列
       * @Date : 2020/12/3 22:34
       * @Param : orderId
       * @Param : delayTimes
       * @return : void
       **/
//    public void sendMessage(Long orderId,final long delayTimes){
//        //给延迟队列发送消息
//        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, message -> {
//            //给消息设置延迟毫秒值
//            message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
//            return message;
//        });
//        log.info("send delay message orderId:{}",orderId);
//    }

    public void sendMessage(String orderId,final long delayTimes){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getExchange(), QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setHeader("x-delay",delayTimes);
                return message;
            }
        });
        log.info("send delay message orderId:{}",orderId);
    }
}
