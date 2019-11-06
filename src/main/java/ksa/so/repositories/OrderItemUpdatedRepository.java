package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.CustomerOrder;
import ksa.so.domain.Item;
import ksa.so.domain.OrderItemUpdated;

public interface OrderItemUpdatedRepository extends JpaRepository<OrderItemUpdated, Long>{
	@Query("SELECT SUM(o.price * o.quantity) from OrderItem o where fk_order=:orderID")
	Object getOrderTotalPrice(@Param("orderID") long orderID);
	
	List<OrderItemUpdated> findByOrder(CustomerOrder order);

	OrderItemUpdated findByOrderAndItem(CustomerOrder customerOrder, Item item);
}
