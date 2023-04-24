package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDto;
import softuni.exam.models.dto.PartImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.MACHANICS_PATH;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.mechanicRepository = mechanicRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count()>0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MACHANICS_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readMechanicsFromFile(), MechanicImportDto[].class))
                .filter(mechanicImportDto -> {
                    boolean isValid = this.validationUtils.isValid(mechanicImportDto);
                    Optional<Mechanic> mechanic = this.mechanicRepository.findMechanicByFirstName(mechanicImportDto.getFirstName());
                    Optional<Mechanic> email = this.mechanicRepository.findMechanicByEmail(mechanicImportDto.getEmail());
                    if (mechanic.isPresent() ||email.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_MECHANIC,
                                    mechanicImportDto.getFirstName(),
                                    mechanicImportDto.getLastName())
                                    : INVALID_MECHANIC)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(mechanicImportDto -> {
                    Mechanic mechanic = modelMapper.map(mechanicImportDto, Mechanic.class);

                    return mechanic;
                })
                .forEach(this.mechanicRepository::save);

        return sb.toString();
    }

}
