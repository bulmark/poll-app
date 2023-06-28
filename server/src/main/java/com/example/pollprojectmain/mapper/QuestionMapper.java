package com.example.pollprojectmain.mapper;

import com.example.pollprojectmain.model.Question;
import com.example.pollprojectmain.pojo.dto.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring", uses = AnswerListMapper.class)
public interface QuestionMapper {
    Question toModel(QuestionDto dto);
    QuestionDto toDto(Question model);
}
