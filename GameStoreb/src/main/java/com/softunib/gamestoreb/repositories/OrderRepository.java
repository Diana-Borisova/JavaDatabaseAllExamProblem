package com.softunib.gamestoreb.repositories;

import com.softunib.gamestoreb.domains.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
