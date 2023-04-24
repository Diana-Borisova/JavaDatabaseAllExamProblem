package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.dto.PersonImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.PEOPLE_PATH;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count()>0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PEOPLE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readPeopleFromFile(), PersonImportDto[].class ))
                .filter(personImportDto -> {
                    boolean isValid = validationUtil.isValid(personImportDto);
                    Optional<Person> person = this.personRepository.findByFirstNameOrEmailOrPhone(
                            personImportDto.getFirstName(), personImportDto.getEmail(), personImportDto.getPhone());
                    Optional<Country> country = this.countryRepository.findById(personImportDto.getCountry());

                    if (person.isPresent() || country.isEmpty() || personImportDto.getStatusType() == null ){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_PEOPLE, personImportDto.getFirstName(), personImportDto.getLastName())
                                    : INVALID_PEOPLE)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(personImportDto -> {
                    Person person= modelMapper.map(personImportDto, Person.class);
                    Country country = this.countryRepository.findById(personImportDto.getCountry()).orElse(null);
                    person.setCountry(country);
                    return person;
                })
                .forEach(this.personRepository::saveAndFlush);

        return sb.toString();
    }
}
