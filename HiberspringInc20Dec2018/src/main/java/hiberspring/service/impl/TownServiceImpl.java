package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.TownImportDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static hiberspring.common.Messages.INVALID_DATA;
import static hiberspring.common.Messages.VALID_TOWN;
import static hiberspring.common.Paths.TOWNS_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWNS_PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readTownsJsonFile(), TownImportDto[].class))
                .filter(townImportDto -> {
                    boolean isValid = this.validationUtil.isValid(townImportDto);
                    Optional<Town> town = this.townRepository.findTownByName(townImportDto.getName());
                    if (town.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_TOWN,
                                    townImportDto.getName())
                                    : INVALID_DATA)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(townImportDto -> {
                    Town town = modelMapper.map(townImportDto, Town.class);
                    return town;
                })
                .forEach(this.townRepository::save);

        return sb.toString();
    }
}
