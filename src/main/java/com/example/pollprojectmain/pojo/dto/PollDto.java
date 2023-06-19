package com.example.pollprojectmain.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PollDto {
    private Integer id;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer ownerId;
    private String ownerUsername;

    @Size(
            min = 1,
            max = 255,
            message = "Maximum poll text length 255"
    )
    @NotNull
    private String text;

    @Nullable
    @Pattern(
            regexp = "^P(\\d+Y)?(\\d+M)?(\\d+D)?(T(\\d+H)?(\\d+M)?(\\d+S)?)?$",
            message = "The proper format of period is ISO-8610"
    )
    private String period;

    @Nullable
    @Pattern(
            regexp = "^P(\\d+Y)?(\\d+M)?(\\d+D)?(T(\\d+H)?(\\d+M)?(\\d+S)?)?$",
            message = "The proper format of voting time is ISO-8610"
    )
    private String votingTime;
    private Timestamp createAt;
    @Nullable
    private Timestamp upToDate;
    private List<QuestionDto> questions = new ArrayList<>();

}

