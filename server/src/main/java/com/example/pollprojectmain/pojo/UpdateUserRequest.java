package com.example.pollprojectmain.pojo;

import com.example.pollprojectmain.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @Pattern(
            regexp = "^[a-zA-Z0-9_\\- ]{3,20}$",
            message = "The username must be between 3 and 20 characters long, including uppercase and lowercase letters," +
                    " spaces, underscores, and hyphens"

    )
    private String username;

    @Pattern(
            regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$",
            message = "Incorrect email format"
    )
    @Schema(
            example = "bebra@bebra.com"
    )
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must has at least one number, at least one letter an is at least 8 characters long"
    )
    private String password;

    @Pattern(regexp = "^USER$|^MODERATOR$|^ADMIN$")
    private Role role;
}
