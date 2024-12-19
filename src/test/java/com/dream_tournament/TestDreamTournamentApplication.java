package com.dream_tournament;

import org.springframework.boot.SpringApplication;

public class TestDreamTournamentApplication {

	public static void main(String[] args) {
		SpringApplication.from(DreamTournamentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
