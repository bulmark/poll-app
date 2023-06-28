package com.example.pollprojectmain.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    @JsonIgnore
    private Integer id;

    @Size(
            min = 1,
            max = 255,
            message = "Maximum answer text length 255"
    )
    @NotNull
    private String text;

    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer votesCount;

    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double percent;
}

