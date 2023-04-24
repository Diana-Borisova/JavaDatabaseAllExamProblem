package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Mechanic;

import java.util.Optional;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    Optional<Mechanic> findMechanicByFirstName(String firstName);

    Mechanic findMechanicByLastName(String lastName);
    Optional<Mechanic> findMechanicByEmail(String email);
}
