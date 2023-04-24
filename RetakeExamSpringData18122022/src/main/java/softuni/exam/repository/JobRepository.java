package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Job;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
@Query(value = "select j from  Job j where j.salary>= 5000.00 and j.hoursAWeek <= 30.00 order by j.salary desc ")
    Optional<List<Job>> findBestJob();
}

