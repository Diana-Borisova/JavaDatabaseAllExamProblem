package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static com.example.football.constants.Messages.INVALID_TEAM;
import static com.example.football.constants.Messages.VALID_TEAM;
import static com.example.football.constants.Paths.TEAMS_PATH;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count()>0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamImportDto[].class ))
                .filter(teamImportDto -> {
                    boolean isValid = validationUtils.isValid(teamImportDto);
                    Optional<Team> team = this.teamRepository.findByName(teamImportDto.getName());
                    Optional<Town> town = this.townRepository.findByName(teamImportDto.getTownName());
                    if (team.isPresent() || town.isEmpty() ){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_TEAM, teamImportDto.getName(), teamImportDto.getFanBase())
                                    : INVALID_TEAM)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(teamImportDto -> {
                    Team team = modelMapper.map(teamImportDto, Team.class);
                    Town town = this.townRepository.findByName(teamImportDto.getTownName()).orElse(null);
                    team.setTown(town);
                    return team;
                })
                .forEach(this.teamRepository::save);

        return sb.toString();
    }

}
