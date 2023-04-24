package com.example.football.service.impl;

import com.example.football.models.dto.StatWrapperDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.example.football.constants.Messages.INVALID_STAT;
import static com.example.football.constants.Messages.VALID_STAT;
import static com.example.football.constants.Paths.STATS_PATH;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count()>0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_PATH));
    }

    @Override
    public String importStats() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(STATS_PATH, StatWrapperDto.class)
                .getStatImportDto()
                .stream()
                .filter(statImportDto -> {
                    boolean isValid = validationUtils.isValid(statImportDto);

                    Optional<Stat>stat= this.statRepository.findByShootingAndPassingAndEndurance(statImportDto.getShooting(), statImportDto.getPassing(),
                            statImportDto.getEndurance());

                    if (stat.isPresent()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_STAT,
                                    statImportDto.getPassing(),statImportDto.getShooting(), statImportDto.getEndurance())
                                    : INVALID_STAT)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(statImportDto -> {
                    Stat stat= modelMapper.map(statImportDto, Stat.class);


                    return stat;
                })
                .forEach(this.statRepository::save);

        return sb.toString();
    }
}
