package com.example.pollprojectmain.service.Impl;

import com.example.pollprojectmain.exception.BadArgumentException;
import com.example.pollprojectmain.mapper.AnswerListMapper;
import com.example.pollprojectmain.mapper.PollMapper;
import com.example.pollprojectmain.model.*;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.*;
import com.example.pollprojectmain.service.PollService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PollServiceImpl implements PollService {

    private PollRepository pollRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private SpectatorRepository spectatorRepository;
    private PollMapper pollMapper;
    private AnswerListMapper answerListMapper;
    private VoteRepository voteRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           UserRepository userRepository,
                           SpectatorRepository spectatorRepository,
                           PollMapper pollMapper,
                           QuestionRepository questionRepository,
                           AnswerListMapper answerListMapper,
                           VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.spectatorRepository = spectatorRepository;
        this.pollMapper = pollMapper;
        this.questionRepository = questionRepository;
        this.answerListMapper = answerListMapper;
        this.voteRepository = voteRepository;
    }

    @Override
    public Poll getById(Integer id) {
        return pollRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such poll with " + id + " id")
        );
    }
    @Override
    public List<Poll> getByOwner(Integer ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow( () ->
            new EntityNotFoundException("No such user with " + ownerId + " id")
        );

        return pollRepository.findPollsByOwner(user);
    }
    @Override
    public List<Poll> getAvailableFor(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow( () ->
                new EntityNotFoundException("No such user with " + userId + " id")
        );

        return spectatorRepository.findSpectatorsByUser(user).stream()
                .map(spectator -> spectator.getPoll())
                .filter(poll -> poll.isOver())
                .toList();
    }

    @Override
    public Poll getWithResult(Integer id) {
        Poll poll = pollRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such poll with " + id + " id")
        );

        for (Question question : poll.getQuestions()) {

            Long totalVotes = question.getAnswers().stream()
                    .mapToLong(answer -> answer.getVotes().stream().count())
                    .sum();

            for (Answer answer : question.getAnswers()) {

                    Integer votesForAnswer = answer.getVotes().size();
                    Double percent = votesForAnswer / (double) totalVotes * 100;
                    answer.setVotesCount(votesForAnswer);
                    answer.setPercent(percent);
                }

            }

        return poll;
    }


    @Override
    public Response create(PollDto pollDto) {

        Poll poll = pollMapper.toModel(pollDto);
        poll.getQuestions().forEach(question -> {
            question.setPoll(poll);
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        });
        pollRepository.save(poll);
        return new Response(
                "New poll successfully created",
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response allowToVote(Integer pollId, List<UserDto> usersDto) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() ->
                new EntityNotFoundException("No such poll with " + pollId + " id")
        );

        for (var userDto : usersDto) {
            User user = userRepository.findById(userDto.getId()).orElseThrow(() ->
                    new EntityNotFoundException("No such user with " + userDto.getId() + " id")
            );
            poll.getSpectators().add(new Spectator(poll, user));
        }

        pollRepository.save(poll);

        return new Response(
                "New users added to vote",
                LocalDateTime.now().toString()
        );
    }
    @Override
    public Response vote(Integer userId, Integer pollId, List<AnswerDto> answersDto) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(
                () -> new EntityNotFoundException("No such poll with " + pollId + " id")
        );

        if (poll.isOver()) {
            throw new BadArgumentException("Poll with id " + pollId + " is over");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("No such user with " + userId + " id")
        );

        List<Answer> answers = answerListMapper.toModelList(answersDto);

        validateVoteRequest(poll, answers, user);

        for (Answer answer : answers) {
            voteRepository.save(new Vote(answer, user));
        }

        return new Response(
                "User with id " + user.getId() + " has successfully voted",
                LocalDateTime.now().toString()
        );
    }


    private void validateVoteRequest(Poll poll, List<Answer> answers, User user) throws BadArgumentException {

        Map<Integer, Set<Integer>> questionIdToAnswerIdMap = new HashMap<>();
        for (Answer answer : answers) {
            Integer questionId = answer.getQuestion().getId();

            if (questionIdToAnswerIdMap.containsKey(questionId)) {
                questionIdToAnswerIdMap.get(questionId).add(answer.getId());
            } else {
                Set<Integer> answersId = new HashSet<>();
                answersId.add(answer.getId());
                questionIdToAnswerIdMap.put(questionId, answersId);
            }
        }

        List<Question> questions = poll.getQuestions();
        if (!questions.equals(questionIdToAnswerIdMap.keySet())) {
            throw new BadArgumentException("Submitted a vote for a question from another poll, or not all questions were voted on");
        }


        for (Question question : poll.getQuestions()) {
            if (!questionIdToAnswerIdMap.containsKey(question.getId())) {
                throw new BadArgumentException("You didn't vote in the question with id " + question.getId() +  " and text:\n" +
                        question.getText());
            }

            Set<Integer> answersId = questionIdToAnswerIdMap.get(question.getId());

            if (answersId.size() == 0) {
                throw new BadArgumentException("You didnt vote in the question with id " + question.getId() +  " and text:\n" +
                        question.getText());
            }

            if (!question.getMultiple() && answersId.size() > 1) {
                throw new BadArgumentException("To much votes in the question with id " + question.getId() +  " and text:\n" +
                        question.getText());
            }
        }

        if (voteRepository.existsByAnswerAndUser(answers.get(0), user)) {
            throw new BadArgumentException("User with id " + user.getId() + " has already voted");
        }

    }
    @Override
    public Response allowToGetResult(Integer pollId, List<UserDto> users) {
        return null;
    }

}
