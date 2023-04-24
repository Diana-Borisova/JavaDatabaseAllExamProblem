package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchImportDto;
import hiberspring.domain.dtos.CardImportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.domain.entities.Town;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static hiberspring.common.Messages.*;
import static hiberspring.common.Paths.EMPLOYEE_CARDS_PATH;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final EmployeeCardRepository employeeCardRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.employeeCardRepository = employeeCardRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count()>0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readEmployeeCardsJsonFile(), CardImportDto[].class))
                .filter(cardImportDto -> {
                    boolean isValid = this.validationUtil.isValid(cardImportDto);
                    Optional<EmployeeCard> cardNumber = this.employeeCardRepository.findEmployeeCardByNumber(cardImportDto.getNumber());

                    if (cardNumber.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_CARD,
                                    cardImportDto.getNumber())
                                    : INVALID_DATA)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(cardImportDto -> {
                    EmployeeCard employeeCard = modelMapper.map(cardImportDto, EmployeeCard.class);

                    return employeeCard;
                })
                .forEach(this.employeeCardRepository::save);

        return sb.toString();
    }
}
