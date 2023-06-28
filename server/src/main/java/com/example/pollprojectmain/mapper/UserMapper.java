package com.example.pollprojectmain.mapper;

import com.example.pollprojectmain.model.Answer;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserDto dto);
    UserDto toDto(User model);
}
