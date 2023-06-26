package com.example.pollprojectmain.service;

import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.AnswerDto;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.UserDto;

import java.util.List;

public interface PollService {
    public Poll getWithResult(Integer id);
    public Poll getById(Integer id);
    public List<Poll> getByOwner(Integer ownerId);
    public List<Poll> getAvailableFor(Integer userId);
    public Response create(Integer userId, PollDto poll);
    public Response allowToVote(Integer pollId, List<UserDto> users);
    public Response allowToGetResult(Integer pollId, List<UserDto> users);
    public Response vote(Integer userId, Integer pollId, VoteRequest voteRequest);
    public void repeatPolls();

}
