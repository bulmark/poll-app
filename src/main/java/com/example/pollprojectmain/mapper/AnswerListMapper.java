package com.example.pollprojectmain.mapper;

import com.example.pollprojectmain.model.Answer;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerListMapper {
    List<Answer> toModelList(List<AnswerDto> dloList);
    List<AnswerDto> toDTOList(List<Answer> modelList);
}
