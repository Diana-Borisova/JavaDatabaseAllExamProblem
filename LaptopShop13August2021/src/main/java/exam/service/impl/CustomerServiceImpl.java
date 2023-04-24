package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.Town;
import exam.model.dtos.CustomerImportDto;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static exam.constants.Messages.INVALID_CUSTOMER;
import static exam.constants.Messages.VALID_CUSTOMER;
import static exam.constants.Paths.CUSTOMERS_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count()>0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readCustomersFileContent(), CustomerImportDto[].class ))
                .filter(customerImportDto -> {
                    boolean isValid = validationUtils.isValid(customerImportDto);
                    Optional<Customer> customer = this.customerRepository.findByEmail(customerImportDto.getEmail());
                    Optional<Town> town = this.townRepository.findByName(customerImportDto.getTown().getName());
                    if (customer.isPresent() || town.isEmpty()){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_CUSTOMER, customerImportDto.getFirstName(), customerImportDto.getLastName(),
                                    customerImportDto.getEmail())
                                    : INVALID_CUSTOMER)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(customerImportDto -> {
                    Customer customer = modelMapper.map(customerImportDto, Customer.class);
                    Town town = this.townRepository.findByName(customerImportDto.getTown().getName()).orElse(null);
                    customer.setTown(town);
                    return customer;
                })
                .forEach(this.customerRepository::save);

        return sb.toString();
    }

}
