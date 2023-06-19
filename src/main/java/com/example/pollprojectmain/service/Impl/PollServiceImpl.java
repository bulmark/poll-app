package com.example.pollprojectmain.service.Impl;

import com.example.pollprojectmain.exception.BadArgumentException;
import com.example.pollprojectmain.exception.ResourceNotFoundException;
import com.example.pollprojectmain.model.Answer;
import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.model.Question;
import com.example.pollprojectmain.model.Spectator;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.QuestionDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.PollRepository;
import com.example.pollprojectmain.repository.UserRepository;
import com.example.pollprojectmain.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    private PollRepository pollRepository;
    private UserRepository userRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PollDto getById(Integer id) {
        var poll = pollRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("No such poll with " + id + " id")
        );

        return wrapPollToDto(poll, false);
    }
    @Override
    public List<PollDto> getByOwner(Integer ownerId) {
        var polls = pollRepository.findAll().stream()
                .filter(poll -> poll.getOwner().getId().equals(ownerId))
                .map(poll -> wrapPollToDto(poll, false))
                .collect(Collectors.toList());

        return polls;
    }
    @Override
    public List<PollDto> getAvailableFor(Integer userId) {
        var polls = pollRepository.findAll().stream()
                .filter(poll -> poll.getSpectators().stream()
                        .anyMatch(spectator -> spectator.getUser().getId().equals(userId))
                )
//                .filter(poll -> !isPollOver(poll))
                .map(poll -> wrapPollToDto(poll, false))
                .collect(Collectors.toList());

        return polls;
    }

    @Override
    public PollDto getResultById(Integer id) {
        var poll = pollRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No such poll with " + id + " id")
        );

        return wrapPollToDto(poll, true);
    }


    @Override
    public Response create(PollDto pollDto) {
        if (pollDto.getQuestions().stream().count() == 0) {
            throw new BadArgumentException("A poll cannot have zero questions");
        }

        for (var question : pollDto.getQuestions()) {
            if (question.getAnswers().stream().count() < 2) {
                throw new BadArgumentException("A question cannot have zero or one answer");
            }
        }

        var owner = userRepository.findById(pollDto.getOwnerId()).orElseThrow( () ->
                new ResourceNotFoundException("No such poll with " + pollDto.getOwnerId() + " id")
        );

        var poll = new Poll();
        poll.setText(pollDto.getText());
        poll.setOwner(owner);
        poll.setCreateAt(pollDto.getCreateAt());
        poll.setUpToDate(pollDto.getUpToDate());

        if (pollDto.getVotingTime() != null) {
            poll.setVotingTime(Duration.parse(pollDto.getVotingTime()));
        }

        if (pollDto.getPeriod() != null) {
            poll.setPeriod(Duration.parse(pollDto.getPeriod()));
        }


        for (var questionDto : pollDto.getQuestions()) {
            var question = new Question();
            question.setPoll(poll);
            question.setText(questionDto.getText());
            question.setMultiple(questionDto.getMultiple());

            for (var answerDto : questionDto.getAnswers()) {
                var answer = new Answer();
                answer.setQuestion(question);
                answer.setText(answerDto.getText());
                question.getAnswers().add(answer);
            }

            poll.getQuestions().add(question);
        }

        pollRepository.save(poll);

        return new Response(
                "New poll successfully created",
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response allowToVote(Integer pollId, List<UserDto> usersDto) {
        var poll = pollRepository.findById(pollId).orElseThrow(() ->
                new BadArgumentException("No such poll with " + pollId + " id")
        );

        var users = usersDto.stream()
                .map(userDto -> userRepository.findById(userDto.getId()).orElseThrow(() ->
                        new BadArgumentException("No such user with " + userDto.getId() + " id")
                ))
                .collect(Collectors.toSet());



//        users.stream()
//                .filter(user -> !poll.getSpectators().stream().
//                        anyMatch(spectator -> spectator.getUser().getId().equals(user.getId())))
//                .map(user -> poll.getSpectators().add(new Spectator(poll, user)))
//                .close();

        for (var user : users) {
            poll.getSpectators().add(new Spectator(poll, user));
        }

        pollRepository.save(poll);

        return new Response(
                "New users added to vote",
                LocalDateTime.now().toString()
        );
    }
    @Override
    public Response vote(VoteRequest voteRequest) {
        return null;
    }

    @Override
    public Response allowToGetResult(Integer pollId, List<UserDto> users) {


        return null;
    }


    private PollDto wrapPollToDto(Poll poll, Boolean withResult) {

        var pollDto = PollDto.builder()
                .id(poll.getId())
                .text(poll.getText())
                .ownerUsername(poll.getOwner().getUsername())
                .createAt(poll.getCreateAt())
                .upToDate(poll.getUpToDate())
                .questions(new ArrayList<>())
                .build();

        if (poll.getPeriod() != null) {
            pollDto.setPeriod(poll.getPeriod().toString());
        }

        if (poll.getVotingTime() != null) {
            pollDto.setVotingTime(poll.getVotingTime().toString());
        }


        for (var question : poll.getQuestions()) {
            var questionDto  = QuestionDto.builder()
                    .id(question.getId())
                    .text(question.getText())
                    .multiple(question.getMultiple())
                    .answers(new ArrayList<>())
                    .build();

            var totalVotes = question.getAnswers().stream()
                    .mapToLong(answer -> answer.getVotes().stream().count())
                    .sum();


            for (var answer : question.getAnswers()) {
                var answerDto = AnswerDto.builder().id(answer.getId()).text(answer.getText()).build();

                if (withResult == true) {
                    var votesForAnswer = answer.getVotes().stream().count();
                    var percent = votesForAnswer / (double) totalVotes * 100;
                    answerDto.setVotesCount(votesForAnswer);
                    answerDto.setPercent(percent);
                }

                questionDto.getAnswers().add(answerDto);
            }

            pollDto.getQuestions().add(questionDto);
        }

        return pollDto;
    }

    private Boolean isPollOver(Poll poll) {

        var votingTime = poll.getVotingTime();

        var currentDateTime = Timestamp.valueOf(LocalDateTime.now());
        var timeOfVoteEnding = Timestamp.from(poll.getCreateAt().toInstant().plus(votingTime));

        if ( votingTime == null) {
            return false;
        }

        if (currentDateTime.before(timeOfVoteEnding)) {
            return false;
        }

        return true;
    }

}
