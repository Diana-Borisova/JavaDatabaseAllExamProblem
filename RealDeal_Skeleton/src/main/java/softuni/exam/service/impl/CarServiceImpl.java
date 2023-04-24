package softuni.exam.service.impl;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarSeedDTO;

import softuni.exam.models.entities.Car;

import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtils.ValidationUtil;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.CARS_FILE;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CARS_FILE));
    }

    @Override
    public String importCars() throws IOException {
        Arrays.stream(GSON.fromJson(readCarsFileContent(), CarSeedDTO[].class))
                .filter(carSeedDTO -> {
                    boolean isValid = validationUtil.isValid(carSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported car - %s - %s",
                                    carSeedDTO.getMake(),
                                    carSeedDTO.getModel())
                                    : "Invalid car")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(carSeedDTO ->{
                    Car car = MODEL_MAPPER.map(carSeedDTO, Car.class);

                    return car;
                })
                .forEach(carRepository::save);

        return STRING_BUILDER.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        this.carRepository.findAllByPicturesCountDescThenByMake()
                .forEach(car -> {
                    STRING_BUILDER.append(String.format("Car make - %s, model - %s\n" +
                                            "\tKilometers - %d\n" +
                                            "\tRegistered on - %s\n" +
                                            "\tNumber of pictures - %d\n",
                                    car.getMake(), car.getModel(), car.getKilometers(),
                                    car.getRegisteredOn(), car.getPictures().size()))
                            .append(System.lineSeparator());
                });
        return STRING_BUILDER.toString();
    }
}