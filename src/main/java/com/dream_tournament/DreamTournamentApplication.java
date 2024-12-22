package com.dream_tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DreamTournamentApplication {

	private static final Logger log = LoggerFactory.getLogger(DreamTournamentApplication.class);

	public static void main(String[] args) {
		log.info("Application started successfully");
		SpringApplication.run(DreamTournamentApplication.class, args);
	}

}
