package com.ilianikonov.stockmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendObject(Object o, String topicName){
        kafkaTemplate.send(topicName, o);
    }
    @KafkaListener(topics = "kafka-test", groupId = "web-25d3acb2-e08a-40a7-b33c-718f1c938404")
    void listener(Object data){
        System.out.println(data);
    }
}
