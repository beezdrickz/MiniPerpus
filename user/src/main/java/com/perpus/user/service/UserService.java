package com.perpus.user.service;


import com.perpus.user.dto.in.UserRequest;
import com.perpus.user.dto.out.UserResult;
import com.perpus.user.model.User;
import com.perpus.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public List<UserResult> getListUser() {
        List<User> userModel = userRepository.findAll();
        return userModel.stream().map(this::convertToDTOList).collect(Collectors.toList());

    }

    public List<User> getListUserPagination() {
        return null;
    }

    public UserResult findUser(Long id) {
        UserResult userResult = new UserResult();
        User userModel = userRepository.findById(id).get();
        if (userModel != null) {
            userResult.setEmail(userModel.getEmail());
            userResult.setId(userModel.getId());
            userResult.setName(userModel.getName());
        }
        return userResult;

    }

    @Transactional
    public void insertUser(UserRequest request) throws Exception {
        User userModel = new User();
        userModel.setEmail(request.getEmail());
        userModel.setName(request.getName());

        try {
            userRepository.save(userModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    private UserResult convertToDTOList(User userModel) {
        UserResult userResult = new UserResult();
        userResult.setId(userModel.getId());
        userResult.setName(userModel.getName());
        userResult.setEmail(userModel.getEmail());
        return userResult;
    }

}
