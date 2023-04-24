package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PictureDTOs.SellerSeedDTO;
import softuni.exam.models.dto.PictureDTOs.SellerSeedRootDTO;

import softuni.exam.models.entities.Seller;

import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtils.ValidationUtil;

import softuni.exam.util.XmlParser.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.SELLERS_FILE;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLERS_FILE));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        SellerSeedRootDTO sellerSeedRootDTO = xmlParser.fromFile(SELLERS_FILE, SellerSeedRootDTO.class);

        for (SellerSeedDTO sellerSeedDTO : sellerSeedRootDTO.getSellers()) {

            Seller seller = MODEL_MAPPER.map(sellerSeedDTO, Seller.class);

            if (!this.validationUtil.isValid(seller)) {
                STRING_BUILDER.append("Invalid seller").append(System.lineSeparator());
                continue;
            }

            if (seller.getRating() == null) {
                STRING_BUILDER.append("Invalid seller").append(System.lineSeparator());
                continue;
            }

            this.sellerRepository.saveAndFlush(seller);

            STRING_BUILDER.append("Successfully imported car - ").append(seller.getLastName())
                    .append(" - ").append(seller.getEmail()).append(System.lineSeparator());

        }
        return STRING_BUILDER.toString().trim();
    }
}