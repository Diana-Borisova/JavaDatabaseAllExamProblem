package hiberspring.repository;

import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmployeeCard_Number(String cardNumber);
    @Query(value = "select e from  Employee as e join fetch Branch as b on b.id = e.branch.id join fetch Product as p on p.branch.id = b.id " +
            " order by e.firstName, e.lastName, length(e.position) desc ")
    Optional<List<Employee>> findEmployeesByBranch();
}

