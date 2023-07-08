package com.example.pollprojectmain.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PollDto {
    @JsonIgnore
    private Integer id;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private Integer ownerId;
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerUsername;
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto owner;

    @Size(
            min = 1,
            max = 255,
            message = "Maximum poll text length 255"
    )
    @NotNull
    private String text;

    @Nullable
    @Pattern(
            regexp = "^P(?:(\\d{1,3})D)?T?(?:(\\d{1,3})H)?(?:(\\d{1,3})M)?$",
            message = "The proper format of period is ISO-8610"
    )
    private String period;

    @Nullable
    @Pattern(
            regexp = "^P(?:\\d{1,3}D)?(?:T(?:\\d{1,3}H)?(?:\\d{1,3}M)?(?:\\d{1,3}S)?)?$",
            message = "The proper format of voting time is ISO-8610"
    )
    private String votingTime;
    private Timestamp createAt;
    @Nullable
    private Timestamp upToDate;
    @Size(
            min = 1,
            message = "A poll cannot have zero questions"
    )
    private List<QuestionDto> questions = new ArrayList<>();

}

