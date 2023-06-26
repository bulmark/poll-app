package com.example.pollprojectmain.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    @JsonIgnore
    private Integer id;

    @Size(
            min = 1,
            max = 255,
            message = "Maximum question text length 255"
    )
    @NotNull
    private String text;

    @NotNull
    private Boolean multiple;

    @Size(
            min = 2,
            message = "A question cannot have zero or one answer"
    )
    private List<AnswerDto> answers = new ArrayList<>();
}
