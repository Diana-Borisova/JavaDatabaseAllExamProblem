package softuni.exam.service.impl;

import jdk.dynalink.linker.LinkerServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import softuni.exam.models.dto.TaskDtos.TaskWrapperDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.TASKS_PATH;
import static softuni.exam.models.entity.CarType.coupe;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    private final CarRepository carRepository;

    private final MechanicRepository mechanicRepository;

    private final PartRepository partRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils, CarRepository carRepository, MechanicRepository mechanicRepository, PartRepository partRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.carRepository = carRepository;
        this.mechanicRepository = mechanicRepository;
        this.partRepository = partRepository;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count()>0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(TASKS_PATH, TaskWrapperDto.class)
                .getTaskImportDto()
                .stream()
                .filter(taskImportDto -> {
                    boolean isValid = validationUtils.isValid(taskImportDto);
                    Optional<Car> car = this.carRepository.findById(taskImportDto.getCar().getId());
                    Optional<Part> part = this.partRepository.findById(taskImportDto.getPart().getId());
                    Optional<Mechanic> mechanic= this.mechanicRepository.findMechanicByFirstName(taskImportDto.getFirstName().getFirstName());
                  //  id = this.mechanicRepository.getIdByFirstName(taskImportDto.getMechanicXmlImportDto().getFirstName());
                    if (car.isEmpty() || part.isEmpty() || mechanic.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_MECHANIC,
                                    taskImportDto.getFirstName().getFirstName(), taskImportDto.getLastName())
                                    : INVALID_MECHANIC)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(taskImportDto -> {
                   Task task = modelMapper.map(taskImportDto, Task.class);
                    Car car = this.carRepository.findById(taskImportDto.getCar().getId()).orElseThrow();
                    Part part = this.partRepository.findById(taskImportDto.getPart().getId()).orElseThrow();
                    Mechanic mechanic = this.mechanicRepository.findMechanicByFirstName(taskImportDto.getFirstName().getFirstName()).orElseThrow();
                    task.setCar(car);
                    task.setPart(part);
                    task.setMechanic(mechanic);

                    return task;
                })
                .forEach(this.taskRepository::save);

        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {

        return this.taskRepository.findAllByPicturesCountDescThenByMake()
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }



}
