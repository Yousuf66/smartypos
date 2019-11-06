package ksa.so.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.ItemBasic;
import ksa.so.domain.Item;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaLanguage;

public interface LibraryItemLanguageRepository extends JpaRepository<LibraryItemLanguage, Long> {
	LibraryItemLanguage findByItemAndLanguage(LibraryItem libraryItem, MetaLanguage language);

	List<LibraryItemLanguage> findByLanguageAndTitleContainingIgnoreCase(MetaLanguage language, String searchTerm);

	List<LibraryItemLanguage> findByLanguageAndTitleContainingIgnoreCase(MetaLanguage language, String searchTerm,
			Pageable pageable);
	/*
	 * @Query("SELECT new ksa.so.beans.LibrarySearchResults(lil.id,lil.title,i.libraryCategory.id, img.fileUrl) FROM LibraryItemLanguage lil "
	 * + " LEFT JOIN lil.item.itemImageList img " + " LEFT JOIN lil.item i " +
	 * "WHERE (lil.title like :searchPhrase or lower(lil.title) like :searchPhraseSpace or lower(lil.details) like :searchPhrase or lower(lil.details) like :searchPhraseSpace) and lil.language.id=:lang"
	 * ) ArrayList<LibrarySearchResults> customSearch(@Param("searchPhrase") String
	 * searchPhrase,
	 *
	 * @Param("searchPhraseSpace") String searchPhraseSpace, @Param("lang") Long
	 * lang, Pageable pageable);
	 */

	@Query("SELECT new ksa.so.beans.ItemBasic(lil.item.id,lil.title,i.libraryCategory.id, img.fileUrl)"
			+ " FROM LibraryItemLanguage lil " + " LEFT JOIN lil.item.itemImageList img " + " LEFT JOIN lil.item i "
			+ " LEFT JOIN i.itemList il " + "INNER JOIN lil.item.status s "
			+ " WHERE s.id=:statusID AND (lower(Replace(lil.title,'''','')) "
			+ "like Replace(:searchPhrase,'''','') or lower(Replace(lil.title,'''','')) "
			+ "like Replace(:searchPhraseSpace,'''','') or lower(Replace(lil.details,'''','')) "
			+ "like Replace(:searchPhrase,'''','') or lower(Replace(lil.details,'''','')) "
			+ "like Replace(:searchPhraseSpace,'''','')) and lil.language.id=:lang" + " AND "
			+ "(:categoryID is null or i.libraryCategory.id = :categoryID) " + " AND "
			+ "(:branchID is null or il.branch.id = :branchID) GROUP BY lil.item")
	ArrayList<ItemBasic> customSearch(@Param("searchPhrase") String searchPhrase,
			@Param("searchPhraseSpace") String searchPhraseSpace, @Param("lang") Long lang,
			@Param("categoryID") Long categoryID, @Param("branchID") Long branchID, @Param("statusID") Long statusID,
			Pageable pageable);

	/*
	 * @Query("SELECT new ksa.so.beans.ItemBasic(lil.item.id,lil.title,i.libraryCategory.id, img.fileUrl) FROM LibraryItemLanguage lil "
	 * + " LEFT JOIN lil.item.itemImageList img " + " LEFT JOIN lil.item i " +
	 * " LEFT JOIN i.itemList il " +
	 * " WHERE (lil.title like :searchPhrase or lower(lil.title) like :searchPhraseSpace or lower(lil.details) like :searchPhrase or lower(lil.details) like :searchPhraseSpace) and lil.language.id=:lang"
	 * + " AND " + "(:categoryID is null or i.libraryCategory.id = :categoryID) ")
	 * ArrayList<ItemBasic> customSearch(@Param("searchPhrase") String searchPhrase,
	 *
	 * @Param("searchPhraseSpace") String searchPhraseSpace, @Param("lang") Long
	 * lang, @Param("categoryID") Long categoryID, Pageable pageable);
	 */

	@Query("SELECT i.id,il.title FROM LibraryItemLanguage il " + "INNER JOIN il.item i "
			+ "WHERE il.language=:language")
	List<Object[]> findByLanguage(@Param("language") MetaLanguage language);

	List<LibraryItemLanguage> findByItemId(Long id);
	void deleteByItem(LibraryItem libraryItem);

	List<LibraryItemLanguage> findByItem(LibraryItem item);
	

}
