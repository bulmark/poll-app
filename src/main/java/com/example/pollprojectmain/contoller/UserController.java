package com.example.pollprojectmain.contoller;

import com.example.pollprojectmain.config.jwt.JwtUtils;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.ChangePasswordRequest;
import com.example.pollprojectmain.pojo.JwtResponse;
import com.example.pollprojectmain.pojo.LoginRequest;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.service.Impl.UserDetailsImpl;
import com.example.pollprojectmain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UserController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PutMapping("/admin/users/{userId}")
    public Response updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        return userService.update(userId, userDto);
    }

    @PutMapping("/users/{userId}/password")
    public Response changePassword(@PathVariable Integer userId, @RequestBody ChangePasswordRequest passwordPojo) {
        return userService.changePassword(userId, passwordPojo);
    }

    @PostMapping("/signin")
    public JwtResponse authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .get().getAuthority();

        return new JwtResponse(jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
    }


    @PostMapping("/signup")
    public Response createUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User findAll(@PathVariable Integer id) {
        return userService.findById(id);
    }

}
