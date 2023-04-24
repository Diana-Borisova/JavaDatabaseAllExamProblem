package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDto;
import softuni.exam.models.entity.BaseEntity;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.PARTS_PATH;

@Service
public class PartServiceImpl extends BaseEntity implements PartService  {
    private PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count()>0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readPartsFileContent(), PartImportDto[].class))
                .filter(partImportDto -> {
                    boolean isValid = this.validationUtils.isValid(partImportDto);
                    Optional<Part> parts = this.partRepository.findPartByPartName(partImportDto.getPartName());
                    if (parts.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_PART,
                                    partImportDto.getPartName(),
                                    partImportDto.getPrice())
                                    : INVALID_PART)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(partImportDto -> {
                    Part part = modelMapper.map(partImportDto, Part.class);

                    return part;
                })
                .forEach(this.partRepository::save);

        return sb.toString();
    }

    @Override
    public List<Part> findAll() {
        return this.partRepository.findAll();
    }

    @Override
    public Long findOne(Long id) {
        return id;
    }


}
