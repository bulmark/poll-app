package com.example.pollprojectmain.service.Impl;

import com.example.pollprojectmain.exception.AccessException;
import com.example.pollprojectmain.exception.BadArgumentException;
import com.example.pollprojectmain.mapper.AnswerListMapper;
import com.example.pollprojectmain.mapper.PollMapper;
import com.example.pollprojectmain.model.*;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.*;
import com.example.pollprojectmain.service.PollService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pollprojectmain.util.MessageProvider;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    private PollRepository pollRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private SpectatorRepository spectatorRepository;
    private PollMapper pollMapper;
    private AnswerListMapper answerListMapper;
    private VoteRepository voteRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           UserRepository userRepository,
                           SpectatorRepository spectatorRepository,
                           PollMapper pollMapper,
                           QuestionRepository questionRepository,
                           AnswerListMapper answerListMapper,
                           VoteRepository voteRepository,
                           AnswerRepository answerRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.spectatorRepository = spectatorRepository;
        this.pollMapper = pollMapper;
        this.questionRepository = questionRepository;
        this.answerListMapper = answerListMapper;
        this.voteRepository = voteRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Poll getById(Integer id) {
        return pollRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageProvider.userNotFound(id))
        );
    }
    @Override
    public List<Poll> getByOwner(Integer ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow( () ->
            new EntityNotFoundException(MessageProvider.userNotFound(ownerId))
        );

        return pollRepository.findPollsByOwner(user);
    }
    @Override
    public List<Poll> getAvailableFor(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow( () ->
                new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

        return spectatorRepository.findSpectatorsByUser(user).stream()
                .map(spectator -> spectator.getPoll())
                .filter(poll -> !poll.isOver())
                .toList();
    }

    @Override
    public Poll getWithResult(Integer id) {
        Poll poll = pollRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageProvider.pollNotFound(id))
        );

        for (Question question : poll.getQuestions()) {

            Long totalVotes = question.getAnswers().stream()
                    .mapToLong(answer -> answer.getVotes().stream().count())
                    .sum();

            if (totalVotes == 0) {
                for (Answer answer : question.getAnswers()) {
                    answer.setVotesCount(0);
                    answer.setPercent(0.0);
                }
            } else {
                for (Answer answer : question.getAnswers()) {

                    Integer votesForAnswer = answer.getVotes().size();
                    Double percent = votesForAnswer / (double) totalVotes * 100;
                    answer.setVotesCount(votesForAnswer);
                    answer.setPercent(percent);
                }
            }
        }

        return poll;
    }


    @Override
    public Response create(Integer userId, PollDto pollDto) {
        User user = userRepository.findById(userId).orElseThrow( () ->
                new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

        Poll poll = pollMapper.toModel(pollDto);
        poll.setOwner(user);
        poll.getQuestions().forEach(question -> {
            question.setPoll(poll);

            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        });
        Integer pollId = pollRepository.save(poll).getId();
        return new Response(
                MessageProvider.createPollSuccess(pollId),
                LocalDateTime.now().toString()
        );
    }

    @Override
    public Response allowToVote(Integer pollId, List<UserDto> usersDto) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() ->
                new EntityNotFoundException(MessageProvider.pollNotFound(pollId))
        );


        for (var userDto : usersDto) {
            User user = userRepository.findById(userDto.getId()).orElseThrow(() ->
                    new EntityNotFoundException(MessageProvider.userNotFound(userDto.getId()))
            );
            poll.getSpectators().add(new Spectator(poll, user));
        }

        pollRepository.save(poll);

        return new Response(
                MessageProvider.allowToVoteSuccess(),
                LocalDateTime.now().toString()
        );
    }
    @Override
    public Response vote(Integer userId, Integer pollId, VoteRequest voteRequest) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(
                () -> new EntityNotFoundException(MessageProvider.pollNotFound(pollId))
        );

        if (poll.isOver()) {
            throw new BadArgumentException(MessageProvider.pollIsOver(pollId));
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageProvider.userNotFound(userId))
        );

        if (spectatorRepository.findByUserAndPoll(user, poll).get() == null) {
            new AccessException(MessageProvider.noAccessFor(userId, pollId));
        }

        List<Answer> answers = answerRepository.findByIdIn(voteRequest.getAnswersId());

        validateVoteRequest(poll, answers, user);

        for (Answer answer : answers) {
            voteRepository.save(new Vote(answer, user));
        }

        return new Response(
                MessageProvider.userVoteSuccess(user.getId()),
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

        Set<Integer> questions = poll.getQuestions().stream()
                .map(question -> question.getId())
                .collect(Collectors.toSet());
        if (!questions.equals(questionIdToAnswerIdMap.keySet())) {
            throw new BadArgumentException(MessageProvider.incorrectListOfQuestionsAnswered());
        }


        for (Question question : poll.getQuestions()) {
            if (!questionIdToAnswerIdMap.containsKey(question.getId())) {
                throw new BadArgumentException(MessageProvider.questionSkipped(question.getId(), question.getText()));
            }

            Set<Integer> answersId = questionIdToAnswerIdMap.get(question.getId());

            if (answersId.size() == 0) {
                throw new BadArgumentException(MessageProvider.questionSkipped(question.getId(), question.getText()));
            }

            if (!question.getMultiple() && answersId.size() > 1) {
                throw new BadArgumentException(MessageProvider.toMuchVotes(question.getId(), question.getText()));
            }
        }

        if (voteRepository.existsByAnswerAndUser(answers.get(0), user)) {
            throw new BadArgumentException(MessageProvider.userHasAlreadyVoted(user.getId()));
        }

    }
    @Override
    public Response allowToGetResult(Integer pollId, List<UserDto> users) {
        return null;
    }

}
