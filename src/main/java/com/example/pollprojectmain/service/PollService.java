package com.example.pollprojectmain.service;

import com.example.pollprojectmain.pojo.AllowToGetResultRequest;
import com.example.pollprojectmain.pojo.Response;
import com.example.pollprojectmain.pojo.VoteRequest;
import com.example.pollprojectmain.pojo.dto.PollDto;

import java.util.List;

public interface PollService {
    public List<PollDto> getResultById(Integer id);
    public PollDto getById(Integer id);
    public List<PollDto> getByOwner(String username);
    public List<PollDto> getAvailableFor(String username);
    public PollDto create(PollDto poll);
    public Response allowToVote(List<String> users);
    public Response allowToGetResult(AllowToGetResultRequest request);
    public Response vote(VoteRequest voteRequest);

}
