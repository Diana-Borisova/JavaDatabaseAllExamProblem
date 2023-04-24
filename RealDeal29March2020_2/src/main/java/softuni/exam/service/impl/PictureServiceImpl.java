package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Picture;
import softuni.exam.models.dtos.CarImportDto;
import softuni.exam.models.dtos.PictureImportDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.PICTURES_PATH;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, CarRepository carRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURES_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readPicturesFromFile(), PictureImportDto[].class ))
                .filter(pictureImportDto -> {
                    boolean isValid = validationUtil.isValid(pictureImportDto);
                    Optional<Picture> picture = this.pictureRepository.findByName(pictureImportDto.getName());

                    if (picture.isPresent() ){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_PICTURE, pictureImportDto.getName())
                                    : INVALID_PICTURE)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(pictureImportDto -> {
                    Picture picture = modelMapper.map(pictureImportDto, Picture.class);
                    Car car = this.carRepository.findById(pictureImportDto.getCar()).orElse(null);
                    picture.setCar(car);
                    return picture;
                })
                .forEach(this.pictureRepository::save);

        return sb.toString();
    }
}
