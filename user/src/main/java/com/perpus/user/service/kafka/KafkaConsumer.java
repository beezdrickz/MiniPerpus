package com.perpus.user.service.kafka;


import com.google.gson.Gson;
import com.perpus.user.dto.out.UserResult;
import com.perpus.user.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String,String>kafkaTemplate;

    @KafkaListener(topics = "user_topic", groupId = "your-group-id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }



    @KafkaListener(topics = "order_user_request_topic", groupId = "your-group-id")
    public void consumeUserRequest(ConsumerRecord<String, String> record) {
        String uuid = record.key();
        System.out.println("Order request for user with UUID : "+uuid+" and id user : "+record.value());
        Long id = Long.parseLong(record.value());
        UserResult result=userService.findUser(id);

        String responseMessage = (result != null) ? new Gson().toJson(result) : "User not found with ID: " + id;

        kafkaTemplate.send("book_response_topic", uuid, responseMessage);
    }
}
