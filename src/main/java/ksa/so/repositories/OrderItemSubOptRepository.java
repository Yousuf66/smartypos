package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.OrderItem;
import ksa.so.domain.OrderItemSubOpt;

public interface OrderItemSubOptRepository extends JpaRepository<OrderItemSubOpt, Long>{
	List<OrderItemSubOpt> findByOrderItem(OrderItem orderItem);
}
