package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.Laptop;
import exam.model.Shop;
import exam.model.Town;
import exam.model.dtos.LaptopImportDto;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static exam.constants.Messages.*;
import static exam.constants.Paths.LAPTOPS_PATH;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count()>0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOPS_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readLaptopsFileContent(), LaptopImportDto[].class ))
                .filter(laptopImportDto -> {
                    boolean isValid = validationUtils.isValid(laptopImportDto);
                    Optional<Shop> shop = this.shopRepository.findByName(laptopImportDto.getShop().getName());
                    Optional<Laptop> laptop = this.laptopRepository.findByMacAddress(laptopImportDto.getMacAddress());
                    if (laptop.isPresent() || shop.isEmpty()){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_LAPTOP, laptopImportDto.getMacAddress(), laptopImportDto.getCpuSpeed(),
                                    laptopImportDto.getRam(), laptopImportDto.getStorage())
                                    : INVALID_LAPTOP)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(laptopImportDto -> {
                    Laptop laptop = modelMapper.map(laptopImportDto, Laptop.class);
                    Shop shop = this.shopRepository.findByName(laptopImportDto.getShop().getName()).orElse(null);
                    laptop.setShop(shop);
                    return laptop;
                })
                .forEach(this.laptopRepository::save);

        return sb.toString();
    }


    @Override
    public String exportBestLaptops() {
StringBuilder sb = new StringBuilder();
List<Laptop> laptops = this.laptopRepository.findTheBestLaptop();

        for (Laptop laptop :laptops) {
            sb.append(String.format("Laptop - %s", laptop.getMacAddress())).append(System.lineSeparator());
            sb.append(String.format("*Cpu speed - %.2f", laptop.getCpuSpeed())).append(System.lineSeparator());
            sb.append(String.format("**Ram - %d", laptop.getRam())).append(System.lineSeparator());
            sb.append(String.format("***Storage - %d", laptop.getStorage())).append(System.lineSeparator());
            sb.append(String.format("****Price - %.2f", laptop.getPrice())).append(System.lineSeparator());
            sb.append(String.format("#Shop name - %s", laptop.getShop().getName())).append(System.lineSeparator());
            sb.append(String.format("##Town - %s", laptop.getShop().getTown().getName())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
