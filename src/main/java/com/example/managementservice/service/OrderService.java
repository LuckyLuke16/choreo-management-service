package com.example.managementservice.service;

import com.example.managementservice.exception.AddressException;
import com.example.managementservice.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private RabbitTemplate rabbitTemplate;


    Logger logger = LoggerFactory.getLogger(OrderService.class);


    public OrderService( RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void startOrderProcess(String userId, OrderDTO orderDetails) {
        if(orderDetails.getAddress() == null)
            throw new AddressException();

        MessagePostProcessor messagePostProcessor = MessageProcessor.buildMessageProperties(userId);
        this.rabbitTemplate.convertAndSend("choreography-exchange", "order.received", orderDetails, messagePostProcessor);




    }
}
