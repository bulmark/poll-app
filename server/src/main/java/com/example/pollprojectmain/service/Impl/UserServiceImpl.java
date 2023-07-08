package com.example.pollprojectmain.service.Impl;

import com.example.pollprojectmain.exception.BadArgumentException;
import com.example.pollprojectmain.mapper.UserMapper;
import com.example.pollprojectmain.model.Role;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.ChangePasswordRequest;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.SignUpRequest;
import com.example.pollprojectmain.pojo.UpdateUserRequest;
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
    public Response create(SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadArgumentException(MessageProvider.userExistsWithUsername(signUpRequest.getUsername()));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadArgumentException(MessageProvider.userExistsWithEmail(signUpRequest.getEmail()));
        }

//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User user = new User();
        user.setRole(Role.USER);
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());

        userRepository.save(user);
        User tmp = userRepository.findByUsername(user.getUsername()).get();

        return new Response(
                MessageProvider.createUserSuccess(tmp.getId()),
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response changePassword(Integer userId, ChangePasswordRequest passwordPojo) {
        User user  = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

        String newPassword = passwordPojo.getPassword();

        if (newPassword != null && newPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
        }

        user.setPassword(passwordEncoder.encode(passwordPojo.getPassword()));
        return new Response(
                MessageProvider.changePasswordSuccess(userId),
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response update(Integer userId, UpdateUserRequest updateUserRequest) {

        User user  = userRepository.findById(userId).orElseThrow(() ->
                        new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

        String newEmail =  updateUserRequest.getEmail();
        String newUsername = updateUserRequest.getUsername();
        String newPassword = updateUserRequest.getPassword();
        Role newRole = updateUserRequest.getRole();

        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            user.setEmail(newEmail);
        }

        if (newUsername != null && !newUsername.equals(user.getEmail())) {
            user.setUsername(newUsername);
        }

        if (newPassword != null && !newPassword.equals(user.getPassword())) {
            user.setPassword(newEmail);
        }

        if (newRole != null && !newRole.equals(user.getRole())) {
            user.setRole(newRole);
        }

        userRepository.save(user);

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
