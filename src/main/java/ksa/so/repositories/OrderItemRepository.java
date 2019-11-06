package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.CustomerOrder;
import ksa.so.domain.Item;
import ksa.so.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	@Query("SELECT SUM(o.price * o.quantity) from OrderItem o where fk_order=:orderID")
	Object getOrderTotalPrice(@Param("orderID") long orderID);
	
	List<OrderItem> findByOrder(CustomerOrder order);

	OrderItem findByOrderAndItem(CustomerOrder order, Item item);
}
