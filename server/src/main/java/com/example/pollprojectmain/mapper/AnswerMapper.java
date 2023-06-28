package com.example.pollprojectmain.mapper;

import com.example.pollprojectmain.model.Answer;
import com.example.pollprojectmain.model.Question;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer toModel(AnswerDto dto);
    AnswerDto toDto(Answer model);
}
