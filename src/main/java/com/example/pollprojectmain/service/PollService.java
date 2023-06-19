package com.example.pollprojectmain.service;

import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.PollDto;
import com.example.pollprojectmain.pojo.dto.UserDto;

import java.util.List;

public interface PollService {
    public PollDto getResultById(Integer id);
    public PollDto getById(Integer id);
    public List<PollDto> getByOwner(Integer ownerId);
    public List<PollDto> getAvailableFor(Integer userId);
    public Response create(PollDto poll);
    public Response allowToVote(Integer pollId, List<UserDto> users);
    public Response allowToGetResult(Integer pollId, List<UserDto> users);
    public Response vote(VoteRequest voteRequest);

}
