package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.categories.CategoryProductsSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;


public interface CategoryService {

    List<CategoryProductsSummaryDto> getCategorySummary() throws IOException;
}
