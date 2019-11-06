package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.BundlesItem;
import ksa.so.domain.Item;

public interface BundlesItemRepository extends JpaRepository<BundlesItem, Long> {

	void deleteByItem(Item item);

}
