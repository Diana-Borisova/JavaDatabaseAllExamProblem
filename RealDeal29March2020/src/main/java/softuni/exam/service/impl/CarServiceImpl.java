package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.dto.CarImportDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.CARS_PATH;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CARS_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readCarsFileContent(), CarImportDto[].class))
                .filter(carImportDto -> {
                    boolean isValid = this.validationUtils.isValid(carImportDto);
                    Optional<Car> car = this.carRepository.findByModelAndMakeAndKilometers(carImportDto.getModel(),
                            carImportDto.getMake(), carImportDto.getKilometers());
                    if (car.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_CAR,
                                    carImportDto.getMake(),
                                    carImportDto.getModel())
                                    : INVALID_CAR)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(carImportDto -> {
                    Car car = modelMapper.map(carImportDto, Car.class);

                    return car;
                })
                .forEach(this.carRepository::save);

        return sb.toString();
    }


    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();

        this.carRepository.findCarsOrderByPicturesCountThenByMake()
                .forEach(car -> {
                    sb.append(String.format("Car make - %s, model - %s\n" +
                                            "\tKilometers - %d\n" +
                                            "\tRegistered on - %s\n" +
                                            "\tNumber of pictures - %d\n",
                                    car.getMake(), car.getModel(), car.getKilometers(),
                                    car.getRegisteredOn(), car.getPictures().size()))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }
}
