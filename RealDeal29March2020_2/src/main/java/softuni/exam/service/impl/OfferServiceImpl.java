package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Offer;
import softuni.exam.models.Seller;
import softuni.exam.models.dtos.OfferWrapperDto;
import softuni.exam.models.dtos.SellerWrapperDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.OFFERS_PATH;
import static softuni.exam.constants.Paths.SELLERS_PATH;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public OfferServiceImpl(OfferRepository offerRepository, CarRepository carRepository, SellerRepository sellerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(OFFERS_PATH, OfferWrapperDto.class)
                .getOfferImportDto()
                .stream()
                .filter(offerImportDto -> {
                    boolean isValid = validationUtil.isValid(offerImportDto);

                    Optional<Car> car = this.carRepository.findById(offerImportDto.getCar().getId());
                    Optional<Seller> seller = this.sellerRepository.findById(offerImportDto.getSeller().getId());

                    if (seller.isEmpty()|| car.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_OFFER,offerImportDto.getAddedOn(), offerImportDto.getDescription())

                                    : INVALID_OFFER)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(offerImportDto -> {
                    Offer offer= modelMapper.map(offerImportDto, Offer.class);
                    Car car = this.carRepository.findById(offerImportDto.getCar().getId()).orElse(null);
                    Seller seller = this.sellerRepository.findById(offerImportDto.getSeller().getId()).orElse(null);
                    offer.setSeller(seller);
                    offer.setCar(car);
                    return offer;
                })
                .forEach(this.offerRepository::save);

        return sb.toString();
    }
}
