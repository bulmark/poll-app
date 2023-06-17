package com.example.pollprojectmain.pojo.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PollDto {
    private Integer id;
    private String ownerUsername;
    private String text;
    @Nullable
    private Duration period;
    @Nullable
    private Duration votingTime;
    private Timestamp createAt;
    @Nullable
    private Timestamp upToDate;
    private List<QuestionDto> answers;

}
