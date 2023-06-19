package com.example.pollprojectmain.contoller;

import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.UserDto;
import com.example.pollprojectmain.repository.UserRepository;
import com.example.pollprojectmain.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class PollController {

    private PollService pollService;
    private UserRepository userRepository;

    @Autowired
    public PollController(PollService pollService, UserRepository userRepository) {
        this.pollService = pollService;
        this.userRepository = userRepository;
    }
    @GetMapping("/polls/{id}")
    public PollDto getPoll(@PathVariable Integer id) {
        return pollService.getById(id);
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}/polls/available")
    public List<PollDto> getAvailablePolls(@PathVariable Integer userId) {
        return pollService.getAvailableFor(userId);
    }

    @GetMapping("/users/{userId}/polls/created")
    public List<PollDto> getCreatedPolls(@PathVariable Integer userId) {
        return pollService.getByOwner(userId);
    }


    @GetMapping("/polls/{id}/result")
    public PollDto getResultOfPoll(@PathVariable Integer id) {
        return pollService.getResultById(id);
    }

    @PostMapping("/users/{userId}/polls")
    public Response createPoll(@RequestBody PollDto poll, @PathVariable Integer userId) {
        poll.setOwnerId(userId);
        return pollService.create(poll);
    }

    @PutMapping("/polls/{id}/spectators")
    public Response allowToVote(@RequestBody List<UserDto> usersDto, @PathVariable Integer id) {
        return pollService.allowToVote(id, usersDto);
    }

    @PutMapping("/polls/{id}")
    public Response vote(@PathVariable("id") Integer id, VoteRequest voteRequest) {
        return null;
    }


}
