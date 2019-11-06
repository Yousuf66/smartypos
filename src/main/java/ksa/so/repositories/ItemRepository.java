package ksa.so.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.ItemResponse;
import ksa.so.domain.Branch;
import ksa.so.domain.Category;
import ksa.so.domain.Item;
import ksa.so.domain.ItemsAvailableCount;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByCategoryAndStatus(Category category, MetaStatus status);

	Optional<Item> findById(Long id);

	Item findByLibraryItem(LibraryItem item);

	List<Item> findByLibraryItemAndBranch(LibraryItem item, Branch branch);

	Item findByLibraryItemAndBranchAndStatus(LibraryItem item, Branch branch, MetaStatus status);

	List<Item> findByBranch(Branch branch);

	List<Item> findByLibraryItemInAndBranch(List<LibraryItem> libraryItems, Branch branch);

	@Modifying
	@Transactional
	@Query(value = "LOAD DATA LOCAL INFILE :filePath INTO TABLE Item_Category_Staging FIELDS TERMINATED BY ',' IGNORE 1 LINES", nativeQuery = true)
	public void bulkLoadData(@Param("filePath") String filePath);

	@Modifying
	@Transactional
	@Query(value = "CALL sp_AddCategoriesItems(:uniqueID)", nativeQuery = true)
	public void bulkImportData(@Param("uniqueID") String uniqueID);

	@Query("SELECT new ksa.so.domain.ItemsAvailableCount(i.branch, COUNT(i.branch)) FROM Item i "
			+ "INNER JOIN i.branch b " + "INNER JOIN b.status s "
			+ "WHERE i.libraryItem IN :libraryItems and s.id=:statusID " + "GROUP BY i.branch ORDER BY COUNT(i.branch)")
	List<ItemsAvailableCount> findBranchByItemsAvailableCountAndByStatus(
			@Param("libraryItems") List<LibraryItem> libraryItems, @Param("statusID") Long statusID);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE lc.id=:categoryID AND s.id=:statusID AND " + "b.id=:branchID AND "
			+ "il.language=:language and li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "ORDER BY (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + " THEN 3" + " ELSE 4 "
			+ "END)")
	List<Item> findByBranchAndCategoryAndStatusAndTitleContaining(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,
			@Param("branchID") Long branchID, @Param("categoryID") Long categoryID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s "
			+ "WHERE lc.id=:categoryID AND s.id=:statusID AND " + "il.language=:language and li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "ORDER BY (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + " THEN 3" + " ELSE 4 "
			+ "END)")
	List<Item> findByCategoryAndStatusAndTitleContaining(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,
			@Param("categoryID") Long categoryID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li " + "INNER JOIN i.itemLanguageList il "
			+ "INNER JOIN i.status s " + "INNER JOIN i.branch b " + "WHERE s.id=:statusID AND " + "b.id=:branchID AND "
			+ "il.language=:language AND li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "ORDER BY (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + "    THEN 3"
			+ " ELSE 4 END),i.price ASC")
	List<Item> findByBranchAndStatusAndTitleContaining(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,
			@Param("branchID") Long branchID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li "
	// + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "il.language=:language AND li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "ORDER BY (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + "    THEN 3"
			+ " ELSE 4 END),i.price ASC")
	List<Item> findByBranchAndStatusAndTitleContaining(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,
			@Param("branchID") Long branchID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, Pageable pageable);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li "
	// + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "il.language=:language AND li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "ORDER BY (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + "    THEN 3"
			+ " ELSE 4 END),i.price DESC")
	List<Item> findByBranchAndStatusAndTitleContainingDESC(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,
			@Param("branchID") Long branchID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li "
	// + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s "

			+ "WHERE s.id=:statusID AND " + "il.language=:language AND li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2) "
			+ "ORDER BY i.price ASC,(CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + "    THEN 3"
			+ " ELSE 4 END) ")
	List<Item> findByStatusAndTitleContaining(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,

			@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.libraryItem li "
	// + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s "

			+ "WHERE s.id=:statusID AND " + "il.language=:language AND li.status=:statusID and "
			+ "(replace(LOWER(il.title),'''','') like LOWER(:searchTerm) " + "OR "
			+ "soundex(substring_index(il.title,' ',1))" + "             = soundex(:searchOrder)) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2) "
			+ "ORDER BY i.price DESC, (CASE WHEN LOWER(il.title) = LOWER(:searchOrder)" + " THEN 1 "
			+ "WHEN LOWER(il.title) LIKE LOWER(:searchOrderw) THEN 2 "
			+ "When SOUNDEX(substring_index(il.title,' ',1)) LIKE SOUNDEX(:searchOrder) "
			+ "and substring(lower(il.title),1,2) like substring(lower(:searchOrderw),1,2)" + "    THEN 3"
			+ " ELSE 4 END) ")
	List<Item> findByStatusAndTitleContainingDESC(@Param("searchTerm") String searchTerm,
			@Param("searchOrder") String searchOrder, @Param("searchOrderw") String searchOrderw,

			@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE lc.id=:categoryID AND s.id=:statusID AND " + "b.id=:branchID AND " + "li.status=:statusID and "
			+ "il.language=:language order by i.price, i.quantity desc")
	List<Item> findByBranchAndCategoryAndStatus(@Param("branchID") Long branchID, @Param("categoryID") Long categoryID,
			@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE lc.id=:categoryID AND s.id=:statusID AND " + "b.id=:branchID AND " + "li.status=:statusID and "
			+ "il.language=:language order by i.price DESC, i.quantity desc")
	List<Item> findByBranchAndCategoryAndStatusDESC(@Param("branchID") Long branchID,
			@Param("categoryID") Long categoryID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s "

			+ "WHERE lc.id=:categoryID AND s.id=:statusID AND " + "li.status=:statusID and "

			+ "il.language=:language order by i.price, i.quantity desc")
	List<Item> findByCategoryAndStatus(@Param("categoryID") Long categoryID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s "

			+ "WHERE lc.id=:categoryID AND s.id=:statusID AND " + "li.status=:statusID and "

			+ "il.language=:language order by i.price DESC, i.quantity desc")
	List<Item> findByCategoryAndStatusDESC(@Param("categoryID") Long categoryID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "il.language=:language and "
			+ "li.status=:statusID " + "ORDER BY i.price, i.quantity desc")
	List<Item> findByBranchAndStatus(@Param("branchID") Long branchID,

			@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "il.language=:language and "
			+ "li.status=:statusID " + "ORDER BY i.price, i.quantity desc")
	List<Item> findByBranchAndStatus(@Param("branchID") Long branchID,

			@Param("statusID") Long statusID, @Param("language") MetaLanguage language, Pageable pageable);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "il.language=:language and "
			+ "li.status=:statusID  " + "ORDER BY i.price DESC, i.quantity desc")
	List<Item> findByBranchAndStatusDESC(@Param("branchID") Long branchID,

			@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s "

			+ "WHERE s.id=:statusID AND " + "il.language=:language " + "and li.status=:statusID "
			+ "ORDER BY i.price, i.quantity desc ")
	List<Item> findByStatus(@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s "

			+ "WHERE s.id=:statusID AND " + "il.language=:language and li.status=:statusID "
			+ "ORDER BY i.price DESC, i.quantity desc")
	List<Item> findByStatusDESC(@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT i FROM OrderItem oi " + "INNER JOIN oi.item i " + "INNER JOIN i.branch b "
			+ "INNER JOIN i.libraryItem li " + "INNER JOIN i.status s " + "WHERE "
			+ "s.id=:statusID AND b.id=:branchID and " + "li.status=:statusID "
			+ "GROUP BY oi.item ORDER BY COUNT(oi.quantity) DESC ")
	List<Item> findMostSoldProducts(@Param("branchID") Long branchID, @Param("statusID") Long statusID,
			Pageable pageable);

	@Query("SELECT i FROM OrderItem oi " + "INNER JOIN oi.item i " + "INNER JOIN i.branch b "
			+ "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc " + "WHERE "
			+ "b.id=:branchID AND lc.id=:categoryID and " + "li.status=:statusID "
			+ "GROUP BY oi.item ORDER BY SUM(oi.quantity) DESC ")
	List<Item> findMostSoldProductsbyCategory(@Param("branchID") Long branchID, @Param("categoryID") Long categoryID,
			Pageable pageable);

	@Query("SELECT i FROM Item i " + "INNER JOIN i.branch b " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s "

			+ "WHERE s.id=:statusID AND b.id=:branchID AND lc.id=:categoryID and" + " li.status=:statusID ")
	List<Item> findProductByCategory(@Param("branchID") Long branchID, @Param("categoryID") Long categoryID,
			@Param("statusID") Long statusID, Pageable pageable);

	@Query("SELECT i FROM OrderItem oi " + "INNER JOIN oi.item i " + "INNER JOIN i.branch b "
			+ "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc " + "WHERE " + "b.id=:branchID and "
			+ "li.status.id=:statusID  " + "GROUP BY lc.id ORDER BY COUNT(lc.id) DESC ")
	List<Item> findMostSoldCategory(@Param("branchID") Long branchID, @Param("statusID") Long statusID);


	@Query("SELECT i FROM Item i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s "
			+ "WHERE s.id=:statusID AND " + "il.language=:language AND " + "i.libraryItem.status=:statusID and "
			+ "replace(LOWER(il.title),'''','') like LOWER(:searchTerm)")
	List<Item> getItemTitle(@Param("searchTerm") String searchTerm, @Param("language") MetaLanguage language,
			@Param("statusID") Long statusID);

	List<Item> findByBranchAndStatusAndLibraryItemOrderByPriceAsc(Branch branch, MetaStatus status,
			LibraryItem libraryItem);

	List<Item> findByBranchAndStatusAndLibraryItemOrderByPriceDesc(Branch branch, MetaStatus status,
			LibraryItem libraryItem);

	List<Item> findByStatusAndLibraryItemOrderByPriceAsc(MetaStatus status, LibraryItem libraryItem);

	List<Item> findByStatusAndLibraryItemOrderByPriceDesc(MetaStatus status, LibraryItem libraryItem);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id) From " + " Item i " + "INNER JOIN i.itemLanguageList il "
			+ "INNER JOIN i.libraryItem li  INNER JOIN li.itemImageList ii " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.status s " + "INNER JOIN i.branch b " + "WHERE s.id=:statusID AND "
			+ "il.language=:language AND ii.sortOrder=0 AND " + "(:branch is null or b = :branch) AND "
			+ "(:libraryItem is null or li=:libraryItem) AND " + "li.status=:statusID and "
			+ "(:category is null or lc = :category) AND i.quantity > 0 "
			+ "order by i.price DESC, il.title, i.quantity DESC")
	ArrayList<ItemResponse> getItemsListDesc(@Param("branch") Branch branch,
			@Param("category") LibraryCategory category, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, @Param("libraryItem") LibraryItem libraryItem);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id) From " + " Item i " + "INNER JOIN i.itemLanguageList il "
			+ "INNER JOIN i.libraryItem li  INNER JOIN li.itemImageList ii " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.status s " + "INNER JOIN i.branch b " + "WHERE s.id=:statusID AND "
			+ "il.language=:language AND ii.sortOrder=0 AND " + "(:branch is null or b= :branch) AND "
			+ "(:libraryItem is null or li=:libraryItem) AND" + " li.status=:statusID and "
			+ "(:category is null or lc = :category) AND i.quantity > 0 " + "order by i.price, il.title, i.quantity")
	ArrayList<ItemResponse> getItemsList(@Param("branch") Branch branch, @Param("category") LibraryCategory category,
			@Param("statusID") Long statusID, @Param("language") MetaLanguage language,
			@Param("libraryItem") LibraryItem libraryItem);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id,bl.title) From " + " Item i "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li  INNER JOIN li.itemImageList ii "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "INNER JOIN b.branchLanguageList bl " + "WHERE s.id=:statusID AND "
			+ "il.language=:language AND bl.language=:language AND ii.sortOrder=0 AND "
			+ "(:branch is null or b= :branch) AND " + "(:libraryItem is null or li=:libraryItem) AND "
			+ "li.status=:statusID and " + "(:category is null or lc = :category) AND i.quantity > 0 "
			+ "order by i.price, il.title, i.quantity")
	ArrayList<ItemResponse> getItemsListWithBranch(@Param("branch") Branch branch,
			@Param("category") LibraryCategory category, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, @Param("libraryItem") LibraryItem libraryItem);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id,bl.title) From " + " Item i "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.libraryItem li  INNER JOIN li.itemImageList ii "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "INNER JOIN b.branchLanguageList bl " + "WHERE s.id=:statusID AND " + "li.status.id=:statusID and "
			+ "il.language=:language AND bl.language=:language AND ii.sortOrder=0 AND "
			+ "(:branch is null or b= :branch) AND " + "(:libraryItem is null or li=:libraryItem) AND"
			+ "(:category is null or lc = :category) AND i.quantity > 0 "
			+ "order by i.price DESC, il.title, i.quantity DESC")
	ArrayList<ItemResponse> getItemsListWithBranchDesc(@Param("branch") Branch branch,
			@Param("category") LibraryCategory category, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, @Param("libraryItem") LibraryItem libraryItem);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id) FROM OrderItem oi " + "INNER JOIN oi.item i "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.branch b " + "INNER JOIN i.libraryItem li "
			+ "INNER JOIN i.status s " + "INNER JOIN li.itemImageList ii " + "INNER JOIN li.libraryCategory lc "
			+ "WHERE " + "il.language=:language AND ii.sortOrder=0 AND " + "li.status.id=:statusID and "
			+ "s.id=:statusID AND b.id=:branchID AND i.quantity > 0 "
			+ "GROUP BY oi.item ORDER BY COUNT(oi.quantity) DESC ")
	ArrayList<ItemResponse> findMostSoldProduct(@Param("branchID") Long branchID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, Pageable pageable);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id) FROM Item i " + "INNER JOIN i.itemLanguageList il "
			+ "INNER JOIN i.branch b " + "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.status s " + "INNER JOIN li.itemImageList ii " + "WHERE "
			+ "il.language=:language AND ii.sortOrder=0 AND " + "li.status.id=:statusID and "
			+ "s.id=:statusID AND b.id=:branchID AND lc.id=:categoryID AND i.quantity > 0")
	ArrayList<ItemResponse> findProductsByCategory(@Param("branchID") Long branchID,
			@Param("categoryID") Long categoryID, @Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, Pageable pageable);

	@Query("SELECT new ksa.so.beans.ItemResponse(i.id,il.title,il.details,i.discountAmount,i.discountPercentage,i.price,i.brand,"
			+ "i.keyFeature,i.quantity,ii.fileUrl,b.id,lc.id) FROM Item i " + "INNER JOIN i.itemLanguageList il "
			+ "INNER JOIN i.branch b " + "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN i.status s " + "INNER JOIN li.itemImageList ii "
			+ " WHERE (lower(Replace(il.title,'''','')) like Replace(:searchPhrase,'''','') "
			+ "or lower(Replace(il.title,'''','')) like Replace(:searchPhraseSpace,'''','') "
			+ "or lower(Replace(il.details,'''','')) like Replace(:searchPhrase,'''','') "
			+ "or lower(Replace(il.details,'''','')) like Replace(:searchPhraseSpace,'''','')) AND "
			+ "il.language.id=:language AND ii.sortOrder=0 AND " + "li.status.id=:statusID and "
			+ "s.id=:statusID AND b.id=:branchID AND i.quantity > 0")
	ArrayList<ItemResponse> customSearchItems(@Param("searchPhrase") String searchPhrase,
			@Param("searchPhraseSpace") String searchPhraseSpace, @Param("language") Long language,
			@Param("branchID") Long branchID, @Param("statusID") Long statusID, Pageable pageable);
	@Query(value="select DISTINCT(item.fk_library_item) AS li from item where item.fk_branch = ?1 ORDER BY li ASC",nativeQuery=true)
	List<Object> findLibraryItemIdByBranchId(Long branchId);
}
