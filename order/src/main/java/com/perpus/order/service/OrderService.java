package com.perpus.order.service;


import com.google.gson.Gson;
import com.perpus.order.dto.in.BookRequest;
import com.perpus.order.dto.in.OrderDetailsRequest;
import com.perpus.order.dto.in.OrderRequest;
import com.perpus.order.dto.out.BookResult;
import com.perpus.order.dto.out.OrderResult;
import com.perpus.order.dto.out.UserResult;
import com.perpus.order.model.OrderBook;
import com.perpus.order.model.OrderDetails;
import com.perpus.order.repository.OrderDetailsRepository;
import com.perpus.order.repository.OrderRepository;
import com.perpus.order.service.kafka.KafkaConsumer;
import com.perpus.order.service.kafka.KafkaProducer;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private Map<String, CompletableFuture<String>> responseFutures;


    @Transactional
    public OrderResult createOrder(OrderRequest order) throws Exception {
        UserResult userResult = findUser(order.getIdUser());
        if (userResult == null) {
            throw new Exception();
        }
        String uuid = UUID.randomUUID().toString();
        OrderBook orderModel = new OrderBook();
        orderModel.setOrderNumber(uuid);
        orderModel.setIdUser(order.getIdUser());
        orderModel.setEndDate(order.getEndDate());
        orderModel.setStartDate(new Date());

        List<OrderDetails> listDetailsModel = new ArrayList();
        for (OrderDetailsRequest detail : order.getOrderDetails()) {
            BookResult bookResult = findBookForOrder(new BookRequest(detail.getIdBook(), detail.getAmount()));
            if (bookResult == null) {
                throw new Exception();
            }
            OrderDetails detailsModel = new OrderDetails();
            detailsModel.setAmount(detail.getAmount());
            detailsModel.setIdbook(detail.getIdBook());
            detailsModel.setOrderBook(orderModel);
            listDetailsModel.add(detailsModel);
        }
        orderModel.setOrderDetails(listDetailsModel);
        orderRepository.save(orderModel);

        OrderResult result = new OrderResult(uuid, order.getEndDate());

        return result;
    }

    @Transactional
    public OrderResult returnOrder(OrderRequest order) {

        return null;
    }

    public OrderBook getOrderByid(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public BookResult findBook(Long id) {
        String uuid = UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        responseFutures.put(uuid, future);
        kafkaProducer.sendMessageBook(uuid, String.valueOf(id));

        BookResult bookResult = new BookResult();
        try {
            String result = future.get(10, TimeUnit.SECONDS);
            bookResult = new Gson().fromJson(result, BookResult.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return bookResult;

    }

    public BookResult findBookForOrder(BookRequest request) {
        String uuid = UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        responseFutures.put(uuid, future);
        kafkaProducer.sendMessageBook(uuid, request.toString());

        BookResult bookResult = new BookResult();
        try {
            String result = future.get(30, TimeUnit.SECONDS);
            bookResult = new Gson().fromJson(result, BookResult.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return bookResult;

    }

    public UserResult findUser(Long id) {
        String uuid = UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        responseFutures.put(uuid, future);
        kafkaProducer.sendMessageUser(uuid, String.valueOf(id));

        UserResult userResult = new UserResult();
        try {
            String result = future.get(30, TimeUnit.SECONDS);
            userResult = new Gson().fromJson(result, UserResult.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return userResult;

    }


}
