package hiberspring.service.impl;

import hiberspring.domain.dtos.employeesDto.EmployeeWrapperDto;
import hiberspring.domain.dtos.productsDto.ProductWrapperDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.domain.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static hiberspring.common.Messages.*;
import static hiberspring.common.Paths.EMPLOYEES_PATH;
import static hiberspring.common.Paths.PRODUCTS_PATH;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeCardRepository employeeCardRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardRepository employeeCardRepository, BranchRepository branchRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.employeeCardRepository = employeeCardRepository;
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count()>0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(EMPLOYEES_PATH, EmployeeWrapperDto.class)
                .getEmployeeImportDto()
                .stream()
                .filter(employeeImportDto -> {
                    boolean isValid = validationUtil.isValid(employeeImportDto);

                    Optional<Employee> employee = this.employeeRepository.findEmployeeByEmployeeCard_Number(employeeImportDto.getEmployeeCard());
                    Optional<Branch> branch = this.branchRepository.findBranchByName(employeeImportDto.getBranch());
                    Optional<EmployeeCard> employeeCard = this.employeeCardRepository.findEmployeeCardByNumber(employeeImportDto.getEmployeeCard());
                    if (employee.isPresent() || branch.isEmpty() || employeeCard.isEmpty() || !validationUtil.isValid(employeeCard)) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_EMPLOYEE,
                                    employeeImportDto.getFirstName(), employeeImportDto.getLastName())
                                    : INVALID_DATA)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(employeeImportDto -> {
                    Employee employee = modelMapper.map(employeeImportDto, Employee.class);

                    Branch branch = this.branchRepository.findBranchByName(employeeImportDto.getBranch()).orElseThrow();
                    EmployeeCard employeeCard = this.employeeCardRepository.findEmployeeCardByNumber(employeeImportDto.getEmployeeCard()).orElseThrow();
                    employee.setBranch(branch);
                    employee.setEmployeeCard(employeeCard);

                    return employee;
                })
                .forEach(this.employeeRepository::save);

        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder sb = new StringBuilder();
        List<Employee> employees = this.employeeRepository.findEmployeesByBranch().orElse(null);
        for (Employee employee :employees) {
            sb.append(String.format("Name: %s %s", employee.getFirstName(), employee.getLastName())).append(System.lineSeparator());
            sb.append(String.format("Position: %s", employee.getPosition())).append(System.lineSeparator());
            sb.append(String.format("Card Number: %s", employee.getEmployeeCard().getNumber())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
