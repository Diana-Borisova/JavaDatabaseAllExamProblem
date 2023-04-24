package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jobsDto.JobWrapperDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
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
import static softuni.exam.constants.Paths.JOBS_PATH;

@Service
public class JobServiceImpl implements JobService {

    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private List<Company> companies;


    @Autowired
    public JobServiceImpl(CompanyRepository companyRepository, JobRepository jobRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {

        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.companies = new ArrayList<>();
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count()>0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOBS_PATH));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(JOBS_PATH, JobWrapperDto.class)
                .getJobImportDto()
                .stream()
                .filter(jobImportDto -> {
                    boolean isValid = validationUtil.isValid(jobImportDto);

                    Optional<Company> company = this.companyRepository.findById(jobImportDto.getCompany());


                    if (company.isEmpty()) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_JOB, jobImportDto.getTitle())
                                    : INVALID_JOB)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(jobImportDto -> {
                    Job job= modelMapper.map(jobImportDto, Job.class);

                   Company company= this.companyRepository.findById(jobImportDto.getCompany()).orElse(null);
                   job.setCompany(company);
                  // job.getCompanies().add(company);

                    return job;
                })
                .forEach(this.jobRepository::saveAndFlush);

        return sb.toString();
    }

    @Override
    public String getBestJobs() {
        StringBuilder sb = new StringBuilder();
        List<Job> jobList = this.jobRepository.findBestJob().orElse(null);
        for (Job job :jobList) {
            sb.append(String.format("Job title %s", job.getTitle())).append(System.lineSeparator());
            sb.append(String.format("-Salary: %.2f$", job.getSalary())).append(System.lineSeparator());
            sb.append(String.format("--Hours a week: %.2fh.", job.getHoursAWeek())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
