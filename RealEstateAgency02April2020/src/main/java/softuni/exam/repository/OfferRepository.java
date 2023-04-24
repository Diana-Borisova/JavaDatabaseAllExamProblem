package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Optional<Offer> findByAgent_FirstName(String agentFirstName);
   @Query(value = "SELECT o FROM Offer as o where o.apartment.apartmentType = 'three_rooms' order by o.apartment.area desc , o.price asc ")
   List<Offer> findOffersByApartmentTypeOrderByApartmentAreaThenPrice();

}

