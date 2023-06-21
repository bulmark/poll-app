package com.example.pollprojectmain.mapper;

import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.pojo.dto.PollDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper(componentModel = "Spring", uses = QuestionListMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PollMapper {

    @Mapping(source = "votingTime", target = "votingTime", qualifiedByName = "stringToDuration")
    @Mapping(source = "period", target = "period", qualifiedByName = "stringToDuration")
    Poll toModel(PollDto dto);

    @Mapping(source = "votingTime", target = "votingTime", qualifiedByName = "durationToString")
    @Mapping(source = "period", target = "period", qualifiedByName = "durationToString")
    PollDto toDto(Poll poll);

    @Named("stringToDuration")
    default Duration stringToDuration(String string) {
        if (string != null) {
            return Duration.parse(string);
        }
        return null;
    }

    @Named("durationToString")
    default String durationToString(Duration duration) {
        if (duration != null) {
            return duration.toString();
        }
        return null;
    }
}
