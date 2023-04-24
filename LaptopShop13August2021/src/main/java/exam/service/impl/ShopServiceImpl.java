package exam.service.impl;

import exam.model.Shop;
import exam.model.Town;
import exam.model.dtos.ShopWrapperDto;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static exam.constants.Messages.*;
import static exam.constants.Paths.SHOPS_PATH;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count()>0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOPS_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(SHOPS_PATH, ShopWrapperDto.class)
                .getShopImportDto()
                .stream()
                .filter(shopImportDto -> {
                    boolean isValid = validationUtils.isValid(shopImportDto);

                    Optional<Shop> shop = this.shopRepository.findByName(shopImportDto.getName());
                    Optional<Town> town = this.townRepository.findByName(shopImportDto.getTown().getName());
                    if (shop.isPresent()|| town.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_SHOP,
                                    shopImportDto.getName(),shopImportDto.getIncome())
                                    : INVALID_SHOP)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(shopImportDto -> {
                    Shop shop= modelMapper.map(shopImportDto, Shop.class);
                    Town town = this.townRepository.findByName(shopImportDto.getTown().getName()).orElse(null);
                    shop.setTown(town);
                    return shop;
                })
                .forEach(this.shopRepository::save);

        return sb.toString();
    }
}
