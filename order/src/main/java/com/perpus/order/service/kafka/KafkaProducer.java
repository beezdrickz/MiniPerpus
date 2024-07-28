package com.perpus.order.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    private static final String TOPIC = "order_topic";
    private static final String TOPIC_BOOK = "book_topic";
    private static final String TOPIC_USER = "user_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendMessageBook(String uuid,String message) {

        kafkaTemplate.send("order_book_request_topic", uuid,message);
    }

    public void sendMessageUser(String uuid,String message) {

        kafkaTemplate.send("order_user_request_topic", uuid,message);
    }
}