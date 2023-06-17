package com.example.pollprojectmain.service;

import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto create(UserDto user);
    public UserDto changePassword(String username, String password);
    public UserDto update(UserDto user);
    public List<UserDto> getAll();
}
