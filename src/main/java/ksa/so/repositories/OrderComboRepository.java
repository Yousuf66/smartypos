package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.OrderCombo;

public interface OrderComboRepository extends JpaRepository<OrderCombo, Long>{
}
