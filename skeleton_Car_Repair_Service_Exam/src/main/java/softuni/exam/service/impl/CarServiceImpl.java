package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarWrapperDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.crypto.spec.OAEPParameterSpec;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_CAR;
import static softuni.exam.constants.Messages.VALID_CAR;
import static softuni.exam.constants.Paths.CARS_PATH;


@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(CARS_PATH, CarWrapperDto.class)
                .getCarImportDto()
                .stream()
                .filter(carImportDto -> {
                    boolean isValid = validationUtils.isValid(carImportDto);

                    Optional<Car> car = this.carRepository.findByPlateNumber(carImportDto.getPlateNumber());

                    if (car.isPresent() || !validationUtils.isValid(carImportDto.getCarType())) {
                        isValid = false;
                    }


                    sb
                            .append(isValid
                                    ? String.format(VALID_CAR,
                                    carImportDto.getCarMake(), carImportDto.getCarModel())
                                    : INVALID_CAR)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(carImportDto -> {
                    Car car = modelMapper.map(carImportDto, Car.class);
                   /* car.setCarMake(carImportDto.getCarMake());
                    car.setCarModel(carImportDto.getCarModel());
                    car.setYear(carImportDto.getYear());*/
                    car.setPlateNumber(carImportDto.getPlateNumber());
                  /*  car.setEngine(carImportDto.getEngine());
                    car.setCarType(carImportDto.getCarType());*/

                    return car;
                })
                .forEach(this.carRepository::save);

        return sb.toString();
    }
}
