package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferWrapperDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static softuni.exam.constants.Messages.*;

import static softuni.exam.constants.Paths.OFFERS_PATH;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    private final AgentRepository agentRepository;

    private final ApartmentRepository apartmentRepository;

    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;

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
                    boolean isValid = validationUtils.isValid(offerImportDto);

                    Optional<Agent> agent = this.agentRepository.findByFirstName(offerImportDto.getAgent().getName());

                    if (agent.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_OFFER,
                                    offerImportDto.getPrice())
                                    : INVALID_OFFER)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(offerImportDto -> {
                    Offer offer = modelMapper.map(offerImportDto, Offer.class);

                    Agent agent = this.agentRepository.findByFirstName(offerImportDto.getAgent().getName()).orElse(null);
                    Apartment apartment = this.apartmentRepository.findById(offerImportDto.getApartment().getId()).orElse(null);
                    offer.setAgent(agent);
                    offer.setApartment(apartment);

                    return offer;
                })
                .forEach(this.offerRepository::save);

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();
         List<Offer> offers = this.offerRepository.findOffersByApartmentTypeOrderByApartmentAreaThenPrice();

        for (Offer offer :offers) {
            sb.append(String.format("Agent %s %s with offer №%d:", offer.getAgent().getFirstName(), offer.getAgent().getLastName(), offer.getId()))
                    .append(System.lineSeparator());
            sb.append(String.format("      -Apartment area: %.2f", offer.getApartment().getArea())).append(System.lineSeparator());
            sb.append(String.format("      --Town: %s", offer.getApartment().getTown().getTownName())).append(System.lineSeparator());
            sb.append(String.format("      ---Price: %.2f$", offer.getPrice())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
