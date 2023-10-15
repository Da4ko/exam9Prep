package com.example.demo.service;

import com.example.demo.model.service.UserServiceModel;

public interface UserService {
    void register(UserServiceModel userServiceModel);

    UserServiceModel findUserByNameAndPassword(String username, String password);

    void clearUserPlaylist(UserServiceModel userServiceModel);
}
