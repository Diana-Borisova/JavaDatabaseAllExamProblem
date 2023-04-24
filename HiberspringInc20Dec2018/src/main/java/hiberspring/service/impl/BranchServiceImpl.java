package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchImportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.service.BranchService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static hiberspring.common.Messages.*;
import static hiberspring.common.Paths.BRANCHES_PATH;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public BranchServiceImpl(BranchRepository branchRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count()>0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCHES_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readBranchesJsonFile(), BranchImportDto[].class))
                .filter(branchImportDto -> {
                    boolean isValid = this.validationUtil.isValid(branchImportDto);
                    Optional<Branch> branch = this.branchRepository.findBranchByName(branchImportDto.getName());
                    Optional<Town> town = this.townRepository.findTownByName(branchImportDto.getTown());
                    if (branch.isPresent() || town.isEmpty()) {
                        isValid = false;
                    }
                    sb.append(isValid
                                    ? String.format(VALID_BRANCH,
                                    branchImportDto.getName())
                                    : INVALID_DATA)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(branchImportDto -> {
                    Branch branch = modelMapper.map(branchImportDto, Branch.class);
                    Town town = this.townRepository.findTownByName(branchImportDto.getTown()).orElseThrow();
                    branch.setTown(town);
                    return branch;
                })
                .forEach(this.branchRepository::save);

        return sb.toString();
    }
}
