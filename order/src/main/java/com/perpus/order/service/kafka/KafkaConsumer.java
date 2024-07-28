package com.perpus.order.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaConsumer {

    @Autowired
    private Map<String, CompletableFuture<String>> responseFutures;


    @KafkaListener(topics = "order_topic", groupId = "your-group-id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "book_response_topic", groupId = "your-group-id")
    public void listenToBookTopic(ConsumerRecord<String,String> message) {
        System.out.println("Received book message: " + message);
        String uuid=message.key();
        String resultMessage=message.value();
        CompletableFuture<String> future = responseFutures.remove(uuid);
        if (future != null) {
            future.complete(resultMessage);
        }
    }


    @KafkaListener(topics = "user_response_topic", groupId = "your-group-id")
    public void listenToUserTopic(ConsumerRecord<String,String> message) {
        System.out.println("Received user message: " + message);
        String uuid=message.key();
        String resultMessage=message.value();
        CompletableFuture<String> future = responseFutures.remove(uuid);
        if (future != null) {
            future.complete(resultMessage);
        }
    }
}
