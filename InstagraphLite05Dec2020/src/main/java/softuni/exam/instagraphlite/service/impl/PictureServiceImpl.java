package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.dtos.PictureImportDto;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readFromFileContent(), PictureImportDto[].class))
                .filter(pictureImportDto -> {
                    boolean isValid = this.validationUtils.isValid(pictureImportDto);
                    Optional<Picture> picture = this.pictureRepository.findByPath(pictureImportDto.getPath());

                    if (picture.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_PICTURE,
                                    pictureImportDto.getSize())
                                    : INVALID_PICTURE)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(pictureImportDto -> {
                    Picture picture = modelMapper.map(pictureImportDto, Picture.class);

                    return picture;
                })
                .forEach(this.pictureRepository::save);

        return sb.toString();
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();
        List<Picture>pictures = this.pictureRepository.findAllBySizeAfterOrderBySize(30000.00);
        for (Picture picture :pictures) {
            sb.append(String.format("%.2f - %s", picture.getSize(), picture.getPath())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
