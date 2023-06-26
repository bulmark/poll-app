package com.example.pollprojectmain.service;

import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.PasswordPojo;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.dto.UserDto;

import java.util.List;

public interface UserService {
    public Response create(UserDto user);
    public Response changePassword(Integer userId, PasswordPojo passwordPojo);
    public Response update(Integer userId, UserDto user);
    public List<User> getAll();
}
