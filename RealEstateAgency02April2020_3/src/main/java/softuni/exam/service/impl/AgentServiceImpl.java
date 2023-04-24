package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.AGENTS_PATH;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count()>0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENTS_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentImportDto[].class ))
                .filter(agentImportDto -> {
                    boolean isValid = validationUtils.isValid(agentImportDto);
Optional<Agent> agent = this.agentRepository.findByFirstName(agentImportDto.getFirstName());
                    Optional<Town> town = this.townRepository.findTownByTownName(agentImportDto.getTown());
                    if (town.isEmpty()|| agent.isPresent()){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_AGENT, agentImportDto.getFirstName(), agentImportDto.getLastName()
                            )
                                    : INVALID_AGENT)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(agentImportDto -> {
                    Agent agent= modelMapper.map(agentImportDto,Agent.class);
Town town = this.townRepository.findTownByTownName(agentImportDto.getTown()).orElse(null);
agent.setTown(town);
                    return agent;
                })
                .forEach(this.agentRepository::save);

        return sb.toString();
    }
}
