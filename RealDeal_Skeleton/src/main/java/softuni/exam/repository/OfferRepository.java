package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Picture;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}