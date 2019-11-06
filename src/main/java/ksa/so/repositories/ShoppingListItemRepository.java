package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.ItemBasic;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.ShoppingListItem;
import ksa.so.domain.UserShoppingList;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
	@Query("SELECT new ksa.so.beans.ItemBasic(li.id,lil.title,sli.quantity) FROM LibraryItem li "
			+ " JOIN li.itemLanguageList lil " + " JOIN li.shoppingListItemList sli "
			+ " WHERE sli.shoppingList=:shoppingList and lil.language=:language")
	List<ItemBasic> findDetailsByShoppingList(@Param("shoppingList") UserShoppingList shoppingList,
			@Param("language") MetaLanguage language);

	List<ShoppingListItem> findByShoppingList(UserShoppingList shoppingList);

	ShoppingListItem findByItem(LibraryItem item);

	Optional<ShoppingListItem> findByItemAndShoppingList(LibraryItem item, UserShoppingList shoppingList);

	@Query("SELECT COUNT(s.id) FROM ShoppingListItem s INNER JOIN s.shoppingList sl where sl.id=:shoppingListId")
	double getTotalItemsInShoppingList(@Param("shoppingListId") long shoppingListId);

//	@Query("SELECT i.branch, COUNT(i.branch) FROM ShoppingListItem sli "
//			+ "INNER JOIN sli.item li "
//			+ "INNER JOIN li.item i "
//			+ "INNER JOIN sli.shoppingList usl "
//			+ "INNER JOIN i.branch b "
//			+ "INNER JOIN b.status s "
//			+ "WHERE usl.id=:userShoppingListID and s.id=:statusID "
//			+ "GROUP BY i.branch ORDER by COUNT(i.branch)")
//		List<Branch> findBranchByItemsAvailableCountAndByStatus(@Param("userShoppingListID") Long userShoppingListID, @Param("statusID") Long statusID);
//	select fk_branch,count(fk_branch) as itemsCount from item i join shopping_list_item sli on i.fk_library_item = sli.fk_library_item where sli.fk_shopping_list = 35
//			group by fk_branch order by itemsCount;
}
