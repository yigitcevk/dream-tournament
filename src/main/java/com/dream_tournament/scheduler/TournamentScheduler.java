package com.dream_tournament.scheduler;

import com.dream_tournament.model.Tournament;
import com.dream_tournament.repository.TournamentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TournamentScheduler {

    private final TournamentRepository tournamentRepository;

    public TournamentScheduler(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    /**
     * Create a new tournament at 00:00 UTC.
     */
    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    @Transactional
    public void createTournament() {
        Tournament latestTournament = tournamentRepository.findByLatestTrue();
        if (latestTournament != null) {
            latestTournament.setLatest(false);
            tournamentRepository.save(latestTournament);
        }

        Tournament newTournament = new Tournament();
        tournamentRepository.save(newTournament);
        System.out.println("New tournament created: " + newTournament);
    }

    /**
     * Deactivate the active tournament at 20:00 UTC.
     */
    @Scheduled(cron = "0 0 20 * * ?", zone = "UTC")
    @Transactional
    public void deactivateTournament() {
        Tournament activeTournament = tournamentRepository.findByIsActiveTrue();
        if (activeTournament != null) {
            activeTournament.setActive(false);
            tournamentRepository.save(activeTournament);
            System.out.println("Tournament deactivated: " + activeTournament);
        } else {
            System.out.println("No active tournament to deactivate.");
        }
    }
}
