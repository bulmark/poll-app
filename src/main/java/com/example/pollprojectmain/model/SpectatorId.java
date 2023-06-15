package com.example.pollprojectmain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpectatorId implements Serializable {
    private User user;
    private Poll poll;
}
