package com.softunib.gamestoreb.repositories;

import com.softunib.gamestoreb.domains.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findFirstByTitle(String title);
}
