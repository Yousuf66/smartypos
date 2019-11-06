package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.CustomerOrderHistory;

public interface CustomerOrderHistoryRepository extends JpaRepository<CustomerOrderHistory, Long>{
}