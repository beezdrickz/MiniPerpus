package com.perpus.book.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.perpus.book.dto.in.BookOrderRequest;
import com.perpus.book.dto.out.BookResult;
import com.perpus.book.service.BookService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private BookService bookService;

    @Autowired
    private KafkaTemplate<String,String>kafkaTemplate;

    @KafkaListener(topics = "book_topic", groupId = "your-group-id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "order_book_request_topic", groupId = "your-group-id")
    public void consumeBookRequest(ConsumerRecord<String, String> record) {
        ObjectMapper objectMapper = new ObjectMapper();
        String uuid = record.key();
        System.out.println("Order request for book with UUID : "+uuid+" and id book : "+record.value());
        BookOrderRequest bookOrderRequest = null;
        try {
            bookOrderRequest = objectMapper.readValue(record.value(), BookOrderRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        BookResult result=bookService.findBookForOrder(bookOrderRequest);
        String responseMessage = (result != null) ? new Gson().toJson(result) : "Book not found with ID: " +bookOrderRequest.getId();

        kafkaTemplate.send("book_response_topic", uuid, responseMessage);
    }
}
