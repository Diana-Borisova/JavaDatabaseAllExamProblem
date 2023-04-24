package exam.service.impl;

import exam.model.Town;
import exam.model.dtos.TownWrapperDto;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static exam.constants.Messages.INVALID_TOWN;
import static exam.constants.Messages.VALID_TOWN;
import static exam.constants.Paths.TOWNS_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
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
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(TOWNS_PATH, TownWrapperDto.class)
                .getTownImportDto()
                .stream()
                .filter(townImportDto -> {
                    boolean isValid = validationUtils.isValid(townImportDto);

                    Optional<Town> town = this.townRepository.findByName(townImportDto.getName());
                    if (town.isPresent()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_TOWN,
                                    townImportDto.getName())
                                    : INVALID_TOWN)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(townImportDto -> {
                    Town town= modelMapper.map(townImportDto, Town.class);

                    return town;
                })
                .forEach(this.townRepository::save);

        return sb.toString();
    }
}
