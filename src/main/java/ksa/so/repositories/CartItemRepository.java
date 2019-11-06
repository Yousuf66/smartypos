package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.CartItem;
import ksa.so.domain.Item;
import ksa.so.domain.UserApp;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	Optional<CartItem> findByUserAndItem(UserApp user, Item item);
	List<CartItem> findByUser(UserApp user);
}

