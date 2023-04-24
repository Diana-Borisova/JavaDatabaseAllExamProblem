package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostXmlImportWrapperDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_POST;
import static softuni.exam.instagraphlite.constants.Messages.VALID_POST;
import static softuni.exam.instagraphlite.constants.Paths.POSTS_PATH;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    private final PictureRepository pictureRepository;

    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils, PictureRepository pictureRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
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
                .fromFile(POSTS_PATH, PostXmlImportWrapperDto.class)
                .getPostXmlImportDto()
                .stream()
                .filter(postXmlImportDto -> {
                    boolean isValid = validationUtils.isValid(postXmlImportDto);

                    User user = this.userRepository.findUserByUsername(postXmlImportDto.getUser().getUsername());
                    Picture picture = this.pictureRepository.findPictureByPath(postXmlImportDto.getPicture().getPath());
                    if (user == null || picture == null) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_POST,
                                    postXmlImportDto.getUser().getUsername())
                                    : INVALID_POST)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(postXmlImportDto -> {
                    Post post = modelMapper.map(postXmlImportDto, Post.class);
                    Picture picture = this.pictureRepository.findPictureByPath(postXmlImportDto.getPicture().getPath());
                   User user= this.userRepository.findUserByUsername(postXmlImportDto.getUser().getUsername());
                    post.setCaption(postXmlImportDto.getCaption());
                    post.setPicture(picture);
                    post.setUser(user);
                    post.getPicture().setId(picture.getId());
post.getUser().setId(user.getId());
                    return post;
                })
                .forEach(postRepository::save);

        return sb.toString();
    }
}
