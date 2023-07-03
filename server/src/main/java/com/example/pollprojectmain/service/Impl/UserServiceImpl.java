package com.example.pollprojectmain.service.Impl;

import com.example.pollprojectmain.exception.BadArgumentException;
import com.example.pollprojectmain.mapper.UserMapper;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.ChangePasswordRequest;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.UserRepository;
import com.example.pollprojectmain.service.UserService;
import com.example.pollprojectmain.util.MessageProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public Response create(UserDto userDto) {
        User user = userMapper.toModel(userDto);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadArgumentException(MessageProvider.userExistsWithUsername(user.getUsername()));
        }

        if (userRepository.existsByUsername(user.getEmail())) {
            throw new BadArgumentException(MessageProvider.userExistsWithEmail(user.getEmail()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new Response(
                MessageProvider.createUserSuccess(),
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response changePassword(Integer userId, ChangePasswordRequest passwordPojo) {
        User user  = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );
        user.setPassword(passwordEncoder.encode(passwordPojo.getPassword()));
        return new Response(
                MessageProvider.changePasswordSuccess(userId),
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response update(Integer userId, UserDto userDto) {

        User user  = userRepository.findById(userId).orElseThrow(() ->
                        new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return new Response(
                MessageProvider.updateUserSuccess(userId),
                LocalDateTime.now().toString()
        );
    }

    @Override
    public User findById(Integer userId) {
       return userRepository.findById(userId).orElseThrow( () ->
                new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Page<User> getAll(Integer page, Integer limit) {
        return userRepository.findAll(PageRequest.of(page, limit));
    }
}
