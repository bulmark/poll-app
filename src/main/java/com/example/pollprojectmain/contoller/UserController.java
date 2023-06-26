package com.example.pollprojectmain.contoller;

import com.example.pollprojectmain.pojo.PasswordPojo;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/admin/users/{userId}")
    public Response updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        return userService.update(userId, userDto);
    }

    @PutMapping("/users/{userId}/password")
    public Response changePassword(@PathVariable Integer userId, @RequestBody PasswordPojo passwordPojo) {
        return userService.changePassword(userId, passwordPojo);
    }

    @PostMapping("/users")
    public Response createUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

}
