package com.perpus.user.controller;


import com.perpus.user.dto.in.UserRequest;
import com.perpus.user.dto.out.UserResult;
import com.perpus.user.model.User;
import com.perpus.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/list-all")
    public ResponseEntity<List<UserResult>> getAll() {
        return ResponseEntity.ok(userService.getListUser());
    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody UserRequest request) throws Exception {
        userService.insertUser(request);
        return ResponseEntity.ok("Success Insert User");
    }
}
