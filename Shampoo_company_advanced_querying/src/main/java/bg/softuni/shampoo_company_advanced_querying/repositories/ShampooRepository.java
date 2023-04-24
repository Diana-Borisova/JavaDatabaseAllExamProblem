package bg.softuni.shampoo_company_advanced_querying.repositories;

import bg.softuni.shampoo_company_advanced_querying.entities.Label;
import bg.softuni.shampoo_company_advanced_querying.entities.Shampoo;
import bg.softuni.shampoo_company_advanced_querying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findBySizeOrLabelIdOrderByIdAsc(Size size, Long labelId);

    List<Shampoo> findBySize(Size parsed);
}
