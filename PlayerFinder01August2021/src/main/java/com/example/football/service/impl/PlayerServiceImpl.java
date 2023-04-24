package com.example.football.service.impl;

import com.example.football.models.dto.PlayerWrapperDto;
import com.example.football.models.dto.StatWrapperDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.football.constants.Messages.*;
import static com.example.football.constants.Paths.PLAYERS_PATH;
import static com.example.football.constants.Paths.STATS_PATH;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count()>0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(PLAYERS_PATH, PlayerWrapperDto.class)
                .getPlayerImportDto()
                .stream()
                .filter(playerImportDto -> {
                    boolean isValid = validationUtils.isValid(playerImportDto);

                    Optional<Player> player= this.playerRepository.findByEmail(playerImportDto.getEmail());

                    if (player.isPresent()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_PLAYER,
                                    playerImportDto.getFirstName(),playerImportDto.getLastName(), playerImportDto.getPosition())
                                    : INVALID_PLAYER)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerImportDto -> {
                    Player player= modelMapper.map(playerImportDto, Player.class);
                    Town town = this.townRepository.findByName(playerImportDto.getTown().getName()).orElse(null);
                    Stat stat = this.statRepository.findById(playerImportDto.getStat().getId()).orElse(null);
                    Team team = this.teamRepository.findByName(playerImportDto.getTeam().getName()).orElse(null);
                    player.setTown(town);
                    player.setStat(stat);
                    player.setTeam(team);
                    return player;
                })
                .forEach(this.playerRepository::save);

        return sb.toString();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();
        List<Player> players= this.playerRepository.findPlayersByBirthDateAfterAndBirthDateBefore(LocalDate.of(1995, 01, 01), LocalDate.of(2003,01,01));
        for (Player player :players) {
            sb.append(String.format("Player - %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator());
            sb.append(String.format("       Position - %s", player.getPosition())).append(System.lineSeparator());
            sb.append(String.format("       Team - %s", player.getTeam().getName())).append(System.lineSeparator());
            sb.append(String.format("       Stadium - %s", player.getTeam().getStadiumName())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
