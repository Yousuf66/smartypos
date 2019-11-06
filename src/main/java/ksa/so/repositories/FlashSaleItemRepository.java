package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.FlashSale;
import ksa.so.domain.FlashSaleItem;
import ksa.so.domain.Item;
import ksa.so.domain.MetaLanguage;

public interface FlashSaleItemRepository extends JpaRepository<FlashSaleItem, Long> {

	List<FlashSaleItem> findByFlashSale(FlashSale flashSale);

	@Query("SELECT fsi FROM FlashSaleItem fsi INNER JOIN fsi.flashSale fs " + "INNER JOIN fsi.item i "
			+ "INNER JOIN i.itemLanguageList il " + "WHERE " + " fs.status=:statusID AND "
			+ "il.language=:language AND "
			+ "(replace(LOWER(il.title),'''','')) like LOWER(:searchTerm) AND fs.id=:flashSaleId "
			+ "ORDER BY (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + "    THEN 3"
			+ " ELSE 4 END) ")
	List<FlashSaleItem> findFlashSaleItemBySearch(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,
			@Param("statusID") Long statusID, @Param("flashSaleId") Long flashSaleId,
			@Param("language") MetaLanguage language);

	FlashSaleItem findByFlashSaleAndItem(FlashSale flashSale, Item item);

}
