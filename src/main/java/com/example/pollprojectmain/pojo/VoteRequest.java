package com.example.pollprojectmain.pojo;

import com.example.pollprojectmain.pojo.dto.AnswerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRequest{
    private String username;
    private List<AnswerDto> answers;
}
