package bg.softuni.shampoo_company_advanced_querying.services;

import bg.softuni.shampoo_company_advanced_querying.entities.Label;
import bg.softuni.shampoo_company_advanced_querying.entities.Shampoo;
import bg.softuni.shampoo_company_advanced_querying.entities.Size;
import bg.softuni.shampoo_company_advanced_querying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return this.shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelIdOrderByIdAsc(String size) {
        Size parsed = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findBySize(parsed);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelId(Size size, Long labelId) {
        return this.shampooRepository.findBySizeOrLabelIdOrderByIdAsc(size, labelId);
    }
}
