package bg.softuni.shampoo_company_advanced_querying.services;

import bg.softuni.shampoo_company_advanced_querying.entities.Label;
import bg.softuni.shampoo_company_advanced_querying.entities.Shampoo;
import bg.softuni.shampoo_company_advanced_querying.entities.Size;

import java.util.List;

public interface ShampooService {

    List<Shampoo> findByBrand (String brand);

    List <Shampoo> findBySizeOrLabelIdOrderByIdAsc(String  size);

    List<Shampoo> findBySizeOrLabelId(Size size, Long labelId);


}
