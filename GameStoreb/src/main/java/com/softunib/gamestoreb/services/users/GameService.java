package com.softunib.gamestoreb.services.users;

public interface GameService {
    String addGame(String[] args);

    String editGame(String[] args);

    String deleteGame(Long id);
}
