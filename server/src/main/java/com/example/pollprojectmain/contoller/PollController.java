package com.example.pollprojectmain.contoller;

import com.example.pollprojectmain.model.Answer;
import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.UserRepository;
import com.example.pollprojectmain.service.PollService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class PollController {

    private PollService pollService;
    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;

    }
    @GetMapping("/polls/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODER')")
    public Poll getPoll(@PathVariable Integer id) {
        return pollService.getById(id);
    }


    @GetMapping("/users/{userId}/polls/available")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODER')")
    public List<Poll> getAvailablePolls(@PathVariable Integer userId) {
        return pollService.getAvailableFor(userId);
    }

    @GetMapping("/users/{userId}/polls/created")
    @PreAuthorize("hasRole('MODER')")
    public List<Poll> getCreatedPolls(@PathVariable Integer userId) {
        return pollService.getByOwner(userId);
    }


    @GetMapping("/polls/{id}/result")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODER')")
    public Poll getResultOfPoll(@PathVariable Integer id) {
        return pollService.getWithResult(id);
    }

    @PostMapping("/users/{userId}/polls")
    @PreAuthorize("hasRole('MODER')")
    public Response createPoll(@RequestBody PollDto poll, @PathVariable Integer userId) {
        return pollService.create(userId, poll);
    }

    @PutMapping("/polls/{id}/spectators")
    @PreAuthorize("hasRole('MODER')")
    public Response allowToVote(@RequestBody List<UserDto> usersDto, @PathVariable Integer id) {
        return pollService.allowToVote(id, usersDto);
    }

    @PutMapping("users/{userId}/polls/{pollId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODER')")
    public Response vote(@RequestBody VoteRequest voteRequest,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("pollId") Integer pollId) {

        return pollService.vote(userId, pollId, voteRequest);
    }
}