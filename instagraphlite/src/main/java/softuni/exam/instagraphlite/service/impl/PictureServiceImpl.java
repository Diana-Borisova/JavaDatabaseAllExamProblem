package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureJsonImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_PICTURE;
import static softuni.exam.instagraphlite.constants.Messages.VALID_PICTURE;
import static softuni.exam.instagraphlite.constants.Paths.PICTURES_PATH;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readFromFileContent(), PictureJsonImportDto[].class ))
                .filter(pictureJsonImportDto -> {
                boolean isValid = validationUtils.isValid(pictureJsonImportDto);
                Optional<Picture> pictures = this.pictureRepository.findByPath(pictureJsonImportDto.getPath());
                if (pictures.isPresent()){
                    isValid = false;
                }
                sb.append(isValid ? String.format(VALID_PICTURE, pictureJsonImportDto.getSize())
                        : INVALID_PICTURE)
                        .append(System.lineSeparator());
                return isValid;
                })
                .map(pictureJsonImportDto -> {
                    Picture picture = modelMapper.map(pictureJsonImportDto, Picture.class);
                return picture;
                })
                .forEach(this.pictureRepository::save);

        return sb.toString();
    }

    @Override
    public String exportPictures() {
        return this.pictureRepository
                .findAllBySizeAfterOrderBySizeAsc(30000)
                .stream()
                .map(Picture::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }


    public Optional<Picture> findByPath(String path){
         return this.pictureRepository.findByPath(path);
    }

    public  Optional<Picture> findBySize(Double size){
        return this.pictureRepository.findBySize(size);
    }

    public Picture findPictureByPath(String path){
        return this.pictureRepository.findPictureByPath(path);
    }
}