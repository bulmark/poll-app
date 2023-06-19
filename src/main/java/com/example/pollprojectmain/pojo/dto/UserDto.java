package com.example.pollprojectmain.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.pollprojectmain.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private Role role;
}
