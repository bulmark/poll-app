package com.example.pollprojectmain.service.Impl;

import com.example.pollprojectmain.exception.BadArgumentException;
import com.example.pollprojectmain.mapper.UserMapper;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.UserRepository;
import com.example.pollprojectmain.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
            throw new BadArgumentException("User with username " + user.getUsername() + " already exists");
        }

        if (userRepository.existsByUsername(user.getEmail())) {
            throw new BadArgumentException("User with username " + user.getEmail() + " already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new Response(
                "New user successfully created",
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response changePassword(Integer userId, UserDto userDto) {
        User user  = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("No such user with " + userId + " id")
        );
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return new Response(
                "User with id " + userId + " successfully updated his password",
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response update(Integer userId, UserDto userDto) {

        User user  = userRepository.findById(userId).orElseThrow(() ->
                        new EntityNotFoundException("No such user with " + userId + " id")
        );

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return new Response(
                "User with id " + userId + " successfully updated",
                LocalDateTime.now().toString()
        );
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
