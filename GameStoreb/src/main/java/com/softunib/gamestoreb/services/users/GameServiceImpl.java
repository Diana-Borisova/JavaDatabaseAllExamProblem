package com.softunib.gamestoreb.services.users;

import com.softunib.gamestoreb.domains.dtos.GameDTO;
import com.softunib.gamestoreb.domains.entities.Game;
import com.softunib.gamestoreb.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameServiceImpl implements GameService {
    private final ModelMapper modelMapper = new ModelMapper();


    private final UserService userService;
    private final GameRepository gameRepository;

    @Override
    public String addGame(String[] args) {
        if (this.userService.getLoggedInUser() != null && this.userService.getLoggedInUser().getIsAdmin()) {

            final String title = args[1];

            final BigDecimal price = new BigDecimal(args[2]);

            final float size = Float.parseFloat(args[3]);

            final String trailer = args[4];

            final String imageUrl = args[5];

            final String description = args[6];

            final LocalDate releaseDate = LocalDate.now();

            final GameDTO gameDTO = new GameDTO(title, trailer, imageUrl, size, price, description, releaseDate);

            final Game gameToSave = gameDTO.toGame();

            this.gameRepository.save(gameToSave);

            return "Added " + title;
        }

        return "Impossible command";
    }
    @Autowired

    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String editGame(String[] args) {
        return null;
    }

    @Override
    public String deleteGame(Long id) {
        return null;
    }
}