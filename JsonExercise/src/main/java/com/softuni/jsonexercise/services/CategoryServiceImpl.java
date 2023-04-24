package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.categories.CategoryProductsSummaryDto;
import com.softuni.jsonexercise.repositories.CategoryRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.jsonexercise.domain.constant.Paths.CATEGORY_PATH;
import static com.softuni.jsonexercise.domain.constant.Utils.writeJsonIntoFile;

@Getter
@Setter


@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryProductsSummaryDto> getCategorySummary() throws IOException {

        final List<CategoryProductsSummaryDto> categoryProductsSummaryDtos = this.categoryRepository
                .getCategorySummary()
                .orElseThrow(NoSuchElementException::new);
        writeJsonIntoFile(categoryProductsSummaryDtos,CATEGORY_PATH);
        return categoryProductsSummaryDtos;
    }
}

