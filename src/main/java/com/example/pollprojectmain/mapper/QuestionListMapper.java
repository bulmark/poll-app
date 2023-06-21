package com.example.pollprojectmain.mapper;

import com.example.pollprojectmain.model.Question;
import com.example.pollprojectmain.pojo.dto.QuestionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface QuestionListMapper {
    List<Question> toModelList(List<QuestionDto> dloList);
    List<QuestionDto> toDTOList(List<Question> modelList);
}
