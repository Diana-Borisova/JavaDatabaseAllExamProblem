package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserJsonImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constants.Messages.*;
import static softuni.exam.instagraphlite.constants.Paths.USERS_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    private final PictureRepository pictureRepository;

    private final PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils, PictureRepository pictureRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.pictureRepository = pictureRepository;
        this.postRepository = postRepository;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readFromFileContent(), UserJsonImportDto[].class ))
                .filter(userJsonImportDto -> {
                    boolean isValid = validationUtils.isValid(userJsonImportDto);
                    Optional<User> user = this.userRepository.findByUsername(userJsonImportDto.getUsername());
                    if (user.isPresent() || userJsonImportDto.getUsername() == null){
                        isValid = false;
                    }
                    Optional<Picture> picture = this.pictureRepository.findByPath(userJsonImportDto.getProfilePicture());
                    if (picture.isEmpty()){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_USER, userJsonImportDto.getUsername())
                                    : INVALID_USER)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(userJsonImportDto -> {
                    User user = modelMapper.map(userJsonImportDto, User.class);
                    Picture picture = this.pictureRepository.findPictureByPath(userJsonImportDto.getProfilePicture());
                    user.setUsername(userJsonImportDto.getUsername());
                    user.setPassword(userJsonImportDto.getPassword());
                    user.setProfilePicture(picture);
                    return user;
                })
                .forEach(this.userRepository::save);

        return sb.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {


        return this.userRepository
                .findAllByOrderByPostsSizeDescIdAsc()
                .stream()
                .map(User::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    @Override
    public List<User> findAllByOrderByPostsSizeDescIdAsc() {
        return this.userRepository.findAllByOrderByPostsSizeDescIdAsc();
    }

    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User findUserByUsername(String username){
        return this.userRepository.findUserByUsername(username);
    }

    public List<Picture> findAllBySizeAfterOOrderBySizeAsc(double size){
        return this.pictureRepository.findAllBySizeAfterOrderBySizeAsc(size);
    }
}
