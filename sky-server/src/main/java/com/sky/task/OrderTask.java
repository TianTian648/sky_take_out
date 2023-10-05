package com.sky.task;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j

public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 定时任务 1min触发一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void outOfTime() {
        log.info("定时处理超时订单:{}",LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        List<Orders> list = orderMapper.queryOutOfTime(time, Orders.PENDING_PAYMENT);
        for (Orders orders : list) {
            orders.setStatus(Orders.CANCELLED);
            orders.setCancelReason("订单超时未支付");
            orders.setCancelTime(LocalDateTime.now());
            orderMapper.update(orders);
            log.info("处理超时订单:{}", orders);

        }

    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void deliveryTime() {
        log.info("定时处理未完成订单:{}",LocalDateTime.now());
        List<Orders> list = orderMapper.queryOutOfTime(LocalDateTime.now().plusMinutes(-60), Orders.DELIVERY_IN_PROGRESS);
        for (Orders orders : list) {
            orders.setStatus(Orders.COMPLETED);
            orders.setDeliveryTime(LocalDateTime.now());
            log.info("处理未完成订单,{}", orders);
            orderMapper.update(orders);
        }
    }

}
