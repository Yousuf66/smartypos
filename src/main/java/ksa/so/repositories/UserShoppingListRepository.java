package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.UserApp;
import ksa.so.domain.UserShoppingList;

public interface UserShoppingListRepository extends JpaRepository<UserShoppingList, Long> {
	@Query("SELECT new ksa.so.domain.UserShoppingList(sl.id,sl.title,COUNT(s.id),sl.tsUpd) FROM ShoppingListItem s RIGHT JOIN s.shoppingList sl where sl.user=:user group by sl.id")
	List<UserShoppingList> findByUser(@Param("user") UserApp user);

	@Query("SELECT new ksa.so.domain.UserShoppingList(sl.id,sl.title,COUNT(s.id),sl.tsUpd) FROM ShoppingListItem s RIGHT JOIN s.shoppingList sl where sl.user=:user and lower(sl.title) like (:searchTerm) group by sl.id")
	List<UserShoppingList> findByUserAndTitleContainingIgnoreCase(@Param("user") UserApp user,
			@Param("searchTerm") String searchTerm);
}
