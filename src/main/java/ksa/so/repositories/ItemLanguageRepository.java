package ksa.so.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.Item;
import ksa.so.domain.ItemLanguage;
import ksa.so.domain.MetaLanguage;

public interface ItemLanguageRepository extends JpaRepository<ItemLanguage, Long> {
	ItemLanguage findByItemAndLanguage(Item item, MetaLanguage language);

	List<ItemLanguage> findByLanguageAndTitleContainingIgnoreCase(MetaLanguage language, String searchTerm);

	@Query("SELECT i.id,il.title FROM ItemLanguage il " + "INNER JOIN il.item i "
			+ "WHERE i.branch=:branch AND il.language=:language")
	List<Object[]> findByBranchAndLanguage(@Param("branch") Branch branch, @Param("language") MetaLanguage language);

	List<ItemLanguage> findByLanguageAndTitleContainingIgnoreCase(MetaLanguage language, String searchTerm,
			Pageable pageable);

	void deleteByItem(Item item);

}
