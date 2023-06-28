package com.example.pollprojectmain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SpectatorId implements Serializable {
    private User user;
    private Poll poll;
}
