package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.dtos.CarImportDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_CAR;
import static softuni.exam.constants.Messages.VALID_CAR;
import static softuni.exam.constants.Paths.CARS_PATH;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
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
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readCarsFileContent(), CarImportDto[].class ))
                .filter(carImportDto -> {
                    boolean isValid = validationUtil.isValid(carImportDto);
                    Optional<Car> car = this.carRepository.findByMakeAndModelAndKilometers(carImportDto.getMake(), carImportDto.getModel(), carImportDto.getKilometers());

                    if (car.isPresent() ){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_CAR, carImportDto.getMake(), carImportDto.getModel())
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
        List<Car> carList = this.carRepository.findCarsOrderByPicturesCount();
        for (Car car :carList) {
            sb.append(String.format("Car make - %s, model - %s", car.getMake(), car.getModel())).append(System.lineSeparator());
            sb.append(String.format("Kilometers - %d", car.getKilometers())).append(System.lineSeparator());
            sb.append(String.format("Registered on - " + car.getRegisteredOn())).append(System.lineSeparator());
            sb.append(String.format("Number of pictures - %d", car.getPictures().size())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}

