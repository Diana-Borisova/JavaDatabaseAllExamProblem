package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.companiesDto.CompanyWrapperDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.COMPANIES_PATH;


@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;


    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;

    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count()>0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANIES_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(COMPANIES_PATH, CompanyWrapperDto.class)
                .getCompanyImportDto()
                .stream()
                .filter(companyImportDto -> {
                    boolean isValid = validationUtil.isValid(companyImportDto);

                    Optional<Company> company = this.companyRepository.findByName(companyImportDto.getName());
                    Optional<Country> country = this.countryRepository.findById(companyImportDto.getCountry());

                    if (company.isPresent() || country.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_COMPANY, companyImportDto.getName(),
                                    companyImportDto.getCountry())
                                    : INVALID_COMPANY)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(companyImportDto -> {
                    Company company= modelMapper.map(companyImportDto, Company.class);
                    Country country = this.countryRepository.findById(companyImportDto.getCountry()).orElse(null);

                    company.setCountry(country);

                    return company;
                })
                .forEach(this.companyRepository::saveAndFlush);

        return sb.toString();
    }
}
