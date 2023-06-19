package com.example.pollprojectmain.pojo;

import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
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
    private UserDto user;
    private List<AnswerDto> answers;
}
