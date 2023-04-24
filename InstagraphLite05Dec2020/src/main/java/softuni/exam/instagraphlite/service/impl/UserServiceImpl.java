package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.Post;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.models.dtos.UserImportDto;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constants.Messages.*;
import static softuni.exam.instagraphlite.constants.Paths.USERS_PATH;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository, PostRepository postRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
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
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readFromFileContent(), UserImportDto[].class))
                .filter(userImportDto -> {
                    boolean isValid = this.validationUtils.isValid(userImportDto);

                    Optional<User> user = this.userRepository.findByUsername(userImportDto.getUsername());
                    Optional<Picture> picture = this.pictureRepository.findByPath(userImportDto.getProfilePicture());

                    if (picture.isEmpty() || user.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_USER,
                                    userImportDto.getUsername())
                                    : INVALID_PICTURE)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(userImportDto -> {
                    User user = modelMapper.map(userImportDto,User.class);
                    Picture picture = this.pictureRepository.findByPath(userImportDto.getProfilePicture()).orElse(null);
                    user.setProfilePicture(picture);
                    return user;
                })
                .forEach(this.userRepository::save);

        return sb.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder sb = new StringBuilder();
        List<User> users = this.userRepository.findAllUsersAndTheirPosts();
        for (User user :users) {
            sb.append(String.format("User: %s", user.getUsername())).append(System.lineSeparator());
            sb.append(String.format("Post count: %d", user.getPosts().size())).append(System.lineSeparator());
            sb.append("==Post Details:").append(System.lineSeparator());
            user.getPosts().forEach(post -> {
                sb.append(String.format("----Caption: %s",post.getCaption())).append(System.lineSeparator());
                sb.append(String.format("----Picture Size: %.2f",post.getPicture().getSize())).append(System.lineSeparator());
            });

        }
        return sb.toString().trim();
    }
}
