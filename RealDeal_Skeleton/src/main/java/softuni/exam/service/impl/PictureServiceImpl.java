package softuni.exam.service.impl;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PictureSeedDTO;

import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;

import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtils.ValidationUtil;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Enums.Functions.*;
import static softuni.exam.util.Enums.Paths.PICTURES_FILE;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, CarRepository carRepository, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE));
    }

    @Override
    public String importPictures() throws IOException {

        Arrays.stream(GSON.fromJson(readPicturesFromFile(), PictureSeedDTO[].class))
                .filter(pictureSeedDTO -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDTO);

                    STRING_BUILDER.append(isValid ? String.format("Successfully imported picture - %s",
                                    pictureSeedDTO.getName())
                                    : "Invalid picture")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(pictureSeedDTO ->{
                    Picture picture = MODEL_MAPPER.map(pictureSeedDTO, Picture.class);
                    Car car = this.carRepository.findById(Long.valueOf(pictureSeedDTO.getCar())).get();
                    picture.setCar(car);

                    return picture;
                })
                .forEach(pictureRepository::save);

        return STRING_BUILDER.toString();
    }
}