package com.nagarro.notificationservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "ecommerceTopic", groupId = "productGroup")
    public void listenProductNotification(ConsumerRecord<String,String> record){
        System.out.println(" Received following notification: "+ record.value());
    }

}
