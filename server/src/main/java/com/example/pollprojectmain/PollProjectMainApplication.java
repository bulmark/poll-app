package com.example.pollprojectmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PollProjectMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollProjectMainApplication.class, args);
	}

}
