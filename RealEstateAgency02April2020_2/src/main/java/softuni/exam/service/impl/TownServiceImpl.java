package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.VALID_TOWN;
import static softuni.exam.constants.Paths.TOWNS_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    private ModelMapper modelMapper;

    private Gson gson;

    private ValidationUtils validationUtils;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readTownsFileContent(), TownImportDto[].class ))
                .filter(townImportDto -> {
                    boolean isValid = validationUtils.isValid(townImportDto);
                    Optional<Town>town = this.townRepository.findByTownName(townImportDto.getTownName());

                    if (town.isPresent() ){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_TOWN, townImportDto.getTownName(), townImportDto.getPopulation()
                                    )
                                    : INVALID_TOWN)
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
