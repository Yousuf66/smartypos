
package ksa.so.repositories;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.Category;
import ksa.so.domain.Item;
import ksa.so.domain.ItemImages;
import ksa.so.domain.ItemsAvailableCount;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.ShoppingListItem;
import ksa.so.domain.User;

public interface ItemImagesRepository extends JpaRepository<ItemImages, Long> {
	Optional<ItemImages> findByfileName(String fileName);

}