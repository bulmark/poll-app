package com.example.pollprojectmain.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllowToGetResultRequest {
    private Integer pollId;
    private List<Integer> usersId;
}
