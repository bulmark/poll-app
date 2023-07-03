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
import com.example.pollprojectmain.service.Impl.UserDetailsImpl;
import com.example.pollprojectmain.service.PollService;
import com.example.pollprojectmain.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Poll getPoll(@PathVariable Integer id) {
        return pollService.getById(id);
    }


    @GetMapping("/polls/available")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Page<Poll> getAvailablePolls(@RequestParam Integer page, @RequestParam Integer limit) {
        Integer userId = SecurityUtils.getCurrentUserDetails().getId();
        return pollService.getAvailableFor(userId, page, limit);
    }

    @GetMapping("/polls/created")
    @PreAuthorize("hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Page<Poll> getCreatedPolls(@RequestParam Integer page, @RequestParam Integer limit) {
        Integer userId = SecurityUtils.getCurrentUserDetails().getId();
        return pollService.getByOwner(userId, page, limit);
    }


    @GetMapping("/polls/{id}/result")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Poll getResultOfPoll(@PathVariable Integer id) {
        return pollService.getWithResult(id);
    }

    @PostMapping("/polls")
    @PreAuthorize("hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Response createPoll(@RequestBody PollDto poll) {
        Integer userId = SecurityUtils.getCurrentUserDetails().getId();
        return pollService.create(userId, poll);
    }

    @PutMapping("/polls/{id}/spectators")
    @PreAuthorize("hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Response allowToVote(@RequestBody List<UserDto> usersDto, @PathVariable Integer id) {
        return pollService.allowToVote(id, usersDto);
    }

    @PutMapping("/polls/{pollId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    @SecurityRequirement(name = "JWT")
    public Response vote(@RequestBody VoteRequest voteRequest,
                         @PathVariable("pollId") Integer pollId) {
        Integer userId = SecurityUtils.getCurrentUserDetails().getId();
        return pollService.vote(userId, pollId, voteRequest);
    }
}
