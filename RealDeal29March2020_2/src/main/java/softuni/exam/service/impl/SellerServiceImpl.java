package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Seller;
import softuni.exam.models.dtos.SellerWrapperDto;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_SELLER;
import static softuni.exam.constants.Messages.VALID_SELLER;
import static softuni.exam.constants.Paths.SELLERS_PATH;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count()>0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLERS_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(SELLERS_PATH, SellerWrapperDto.class)
                .getSellerImportDto()
                .stream()
                .filter(sellerImportDto -> {
                    boolean isValid = validationUtil.isValid(sellerImportDto);

                    Optional<Seller> seller = this.sellerRepository.findByEmail(sellerImportDto.getEmail());

                    if (seller.isPresent()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_SELLER, sellerImportDto.getLastName(),
                                    sellerImportDto.getEmail())
                                    : INVALID_SELLER)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(sellerImportDto -> {
                    Seller seller= modelMapper.map(sellerImportDto, Seller.class);


                    return seller;
                })
                .forEach(this.sellerRepository::save);

        return sb.toString();
    }
}
