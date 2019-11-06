package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.CustomerOrder;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.OrderStatusLog;

public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, Long>{
	List<OrderStatusLog> findByOrderOrderByTsClientAsc(CustomerOrder order);
	OrderStatusLog findByOrderAndStatus(CustomerOrder order, MetaStatus status);
}
