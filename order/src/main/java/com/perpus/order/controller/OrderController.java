package com.perpus.order.controller;


import com.perpus.order.dto.in.OrderRequest;
import com.perpus.order.dto.out.BookResult;
import com.perpus.order.dto.out.OrderResult;
import com.perpus.order.dto.out.UserResult;
import com.perpus.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list-all")
    public ResponseEntity<List<OrderResult>> getAll() {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<OrderResult> create(@RequestBody OrderRequest request) throws Exception {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/get-book/{id}")
    public ResponseEntity<BookResult> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findBook(id));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserResult> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findUser(id));
    }
}
