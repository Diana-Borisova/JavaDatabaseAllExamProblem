package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentWrapperDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.VALID_APARTMENT;
import static softuni.exam.constants.Paths.APARTMENTS_PATH;


@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count()>0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(APARTMENTS_PATH, ApartmentWrapperDto.class)
                .getApartmentImportDto()
                .stream()
                .filter(apartmentImportDto -> {
                    boolean isValid = validationUtils.isValid(apartmentImportDto);

                   Optional<Apartment> apartment = this.apartmentRepository.findByTown_TownNameAndArea(apartmentImportDto.getTown(), apartmentImportDto.getArea());
                    Optional<Town> town = this.townRepository.findTownByTownName(apartmentImportDto.getTown());
                    if (apartment.isPresent() || town.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_APARTMENT,
                                    apartmentImportDto.getApartmentType(), apartmentImportDto.getArea())
                                    : INVALID_APARTMENT)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(apartmentImportDto -> {
                    Apartment apartment = modelMapper.map(apartmentImportDto, Apartment.class);

                   Town town = this.townRepository.findTownByTownName(apartmentImportDto.getTown()).orElse(null);
//
                    apartment.setTown(town);

                    return apartment;
                })
                .forEach(this.apartmentRepository::save);

        return sb.toString();
    }
}
