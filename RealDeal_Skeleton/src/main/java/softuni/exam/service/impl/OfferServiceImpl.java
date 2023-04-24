package softuni.exam.service.impl;

import org.springframework.stereotype.Service;

import softuni.exam.models.dto.OfferDTOs.OfferSeedDTO;
import softuni.exam.models.dto.OfferDTOs.OfferSeedRootDTO;
import softuni.exam.models.entities.Offer;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils.ValidationUtil;

import softuni.exam.util.XmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Enums.Functions.MODEL_MAPPER;
import static softuni.exam.util.Enums.Functions.STRING_BUILDER;
import static softuni.exam.util.Enums.Paths.OFFERS_FILE;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;

    public OfferServiceImpl(OfferRepository offerRepository, XmlParser xmlParser
            , ValidationUtil validationUtil, CarRepository carRepository, SellerRepository sellerRepository) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OfferSeedRootDTO offerSeedRootDTO = xmlParser.fromFile(OFFERS_FILE, OfferSeedRootDTO.class);

        for (OfferSeedDTO offerSeedDTO : offerSeedRootDTO.getOffers()) {

            Offer offer = MODEL_MAPPER.map(offerSeedDTO, Offer.class);

            if (!this.validationUtil.isValid(offer)) {
                STRING_BUILDER.append("Invalid offer").append(System.lineSeparator());
                continue;
            }

            offer.setCar(this.carRepository.findById(Long.valueOf(offerSeedDTO.getCarDto().getId())).orElse(null));
            offer.setSeller(this.sellerRepository.findById(Long.valueOf(offerSeedDTO.getSellerDto().getId())).orElse(null));

            this.offerRepository.saveAndFlush(offer);

            STRING_BUILDER.append("Successfully import offer ")
                    .append(offer.getAddedOn())
                    .append(" - ")
                    .append(offer.getHasGoldStatus())
                    .append(System.lineSeparator());
        }

        return STRING_BUILDER.toString().trim();
    }
}