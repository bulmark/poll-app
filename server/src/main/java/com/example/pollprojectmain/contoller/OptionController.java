package com.example.pollprojectmain.contoller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Hidden
public class OptionController {
    @RequestMapping(value = {"/polls/**", "/users/**", "/admin/**"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsRequest() {
        return ResponseEntity.ok().build();
    }
}
