package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.Post;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.models.dtos.PostWrapperDto;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.instagraphlite.constants.Messages.*;
import static softuni.exam.instagraphlite.constants.Paths.POSTS_PATH;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(POSTS_PATH, PostWrapperDto.class)
                .getPostImportDto()
                .stream()
                .filter(postImportDto -> {

                    boolean isValid = validationUtils.isValid(postImportDto);
                    Optional<Picture> picture = this.pictureRepository.findByPath(postImportDto.getPicture().getPath());

                    Optional<User> user = this.userRepository.findByUsername(postImportDto.getUser().getUsername());

                    if (picture.isEmpty() || postImportDto.getPicture().getPath() == null|| user.isEmpty()) {
                        isValid = false;
                    }


                    sb
                            .append(isValid
                                    ? String.format(VALID_POST,
                                    postImportDto.getUser().getUsername())
                                    : INVALID_POST)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(postImportDto -> {
                    Post post = modelMapper.map(postImportDto, Post.class);
Picture picture = this.pictureRepository.findByPath(postImportDto.getPicture().getPath()).orElse(null);
User user = this.userRepository.findByUsername(postImportDto.getUser().getUsername()).orElse(null);
post.setUser(user);
post.setPicture(picture);
                    return post;
                })
                .forEach(this.postRepository::save);

        return sb.toString();
    }

}
