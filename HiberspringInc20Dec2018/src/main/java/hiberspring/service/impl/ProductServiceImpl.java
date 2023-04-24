package hiberspring.service.impl;

import hiberspring.domain.dtos.productsDto.ProductWrapperDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static hiberspring.common.Messages.INVALID_DATA;
import static hiberspring.common.Messages.VALID_PRODUCT;
import static hiberspring.common.Paths.PRODUCTS_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public ProductServiceImpl(ProductRepository productRepository, BranchRepository branchRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count()>0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_PATH));
    }

    @Override
    public String importProducts() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(PRODUCTS_PATH, ProductWrapperDto.class)
                .getProductImportDto()
                .stream()
                .filter(productImportDto -> {
                    boolean isValid = validationUtil.isValid(productImportDto);

                    Optional<Product> product = this.productRepository.findProductByName(productImportDto.getName());
                    Optional<Branch> branch = this.branchRepository.findBranchByName(productImportDto.getBranch());
                    if (product.isPresent() || branch.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_PRODUCT,
                                    productImportDto.getName())
                                    : INVALID_DATA)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(productImportDto -> {
                    Product product = modelMapper.map(productImportDto, Product.class);

                   Branch branch = this.branchRepository.findBranchByName(productImportDto.getBranch()).orElseThrow();
//
                    product.setBranch(branch);

                    return product;
                })
                .forEach(this.productRepository::save);

        return sb.toString();
    }
}
