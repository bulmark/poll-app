package com.example.pollprojectmain.service;

import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.ChangePasswordRequest;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.SignUpRequest;
import com.example.pollprojectmain.pojo.UpdateUserRequest;
import com.example.pollprojectmain.pojo.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    public Response create(SignUpRequest signUpRequest);
    public Response changePassword(Integer userId, ChangePasswordRequest passwordPojo);
    public Response update(Integer userId, UpdateUserRequest user);
    public User findById(Integer id);
    public List<User> getAll();
    public Page<User> getAll(Integer page, Integer limit);
}
