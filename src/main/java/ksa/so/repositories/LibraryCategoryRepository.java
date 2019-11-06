
package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.BranchResponse;
import ksa.so.beans.CategoryResponse;
import ksa.so.beans.LibraryCategoryResponse;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public interface LibraryCategoryRepository extends JpaRepository<LibraryCategory, Long> {
	List<LibraryCategory> findByStatus(MetaStatus status);

	@Query("SELECT new ksa.so.beans.CategoryResponse(c.id, cl.title, c.fileUrl, lc.id) FROM LibraryCategory c "
			+ "INNER JOIN c.libraryCategoryLanguageList cl " + "LEFT JOIN c.libraryCategory lc "
			+ "INNER JOIN c.status s " + "WHERE s.id=:statusID AND " + "cl.language=:language order by cl.title")
	List<CategoryResponse> findCategoryResponseByStatusOrderByTitle(@Param("statusID") Long statusID,
			@Param("language") MetaLanguage language);

	@Query("SELECT c FROM LibraryCategory c " + "INNER JOIN c.libraryCategoryLanguageList cl "
			+ "INNER JOIN c.status s " + "WHERE s.id=:statusID AND " + "cl.language=:language order by cl.title")
	List<LibraryCategory> findByStatusOrderByTitle(Long id, MetaLanguage language);

	@Query("SELECT distinct(c) FROM Item i " + "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory c "
			+ "INNER JOIN c.libraryCategoryLanguageList cl " + "INNER JOIN c.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "cl.language=:language order by cl.title")
	List<LibraryCategory> findSubCategoryByItemAndStatusOrderByTitle(@Param("statusID") Long statusID,
			@Param("branchID") Long branchID, @Param("language") MetaLanguage language);

	@Query("SELECT distinct(c) FROM Item i " + "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory cc "
			+ "INNER JOIN cc.libraryCategoryLanguageList ccl " + "INNER JOIN cc.libraryCategory c "
			+ "INNER JOIN c.libraryCategoryLanguageList cl " + "INNER JOIN c.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND " + "cl.language=:language order by cl.title")
	List<LibraryCategory> findCategoryByItemAndStatusOrderByTitle(@Param("statusID") Long statusID,
			@Param("branchID") Long branchID, @Param("language") MetaLanguage language);

	Optional<LibraryCategory> findById(Long id);

	@Query("SELECT new ksa.so.beans.CategoryResponse(c.id, cl.title, c.fileUrl, lc.id) " + "FROM LibraryCategory c "
			+ "INNER JOIN c.libraryCategoryLanguageList cl " + "LEFT JOIN c.libraryCategory lc "
			+ "LEFT JOIN lc.libraryItemList li " + "LEFT JOIN li.itemList i "
			+ "LEFT JOIN i.branch b with b.id=:branchID " + "INNER JOIN c.status s " + "WHERE s.id=:statusID AND "
			+ "cl.language=:language " + " group by c.id order by cl.title")
	List<CategoryResponse> findCategoryResponseByStatusOrderByTitle(@Param("statusID") Long statusID,
			@Param("language") MetaLanguage language, @Param("branchID") Long branchID);

	@Query("SELECT new ksa.so.beans.CategoryResponse(lc.id, lcl.title, lc.fileUrl, lc1.id,lcl1.title,lc1.fileUrl) FROM Item i "
			+ "INNER JOIN i.libraryItem li " + "INNER JOIN li.libraryCategory lc "
			+ "INNER JOIN lc.libraryCategoryLanguageList lcl " + "LEFT JOIN lc.libraryCategory lc1 "
			+ "INNER JOIN lc1.libraryCategoryLanguageList lcl1 " + "INNER JOIN i.status s " + "INNER JOIN i.branch b "
			+ "WHERE s.id=:statusID AND " + "b.id=:branchID AND "
			+ "lcl1.language=:language AND lcl.language=:language group by lc.id")
	List<CategoryResponse> findCategoryResponseByStatusAndBranchOrderByTitle(@Param("statusID") Long statusID,

			@Param("language") MetaLanguage language, @Param("branchID") Long branchID);
	
//	@Query("SELECT new ksa.so.beans.CategoryResponse(lc.id,lc.fileUrl,lc.fileName,lc.icon,lc.status,lcg.title,lcg.details) FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory ")
//	@Query(value = "select lc.id lc.file_name lc.file_url,lcg.details,lcg.title,lc.icon from library_category lc, library_category_language lcg where lc.id = lcg.fk_library_category",nativeQuery = true)
//	@Query (value = "select lc.id,lc.file_name,lc.file_url,lcg.title,lcg.details,lc.fk_status from library_category lc, library_category_language lcg where lc.id = lcg.fk_library_category", nativeQuery = true )
	@Query("SELECT new ksa.so.beans.LibraryCategoryResponse(lc.id,lcg.id, lcg.title,lcg.details,lc.fileName,lc.fileUrl,lc.status) FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory")
	List<LibraryCategoryResponse> findAllLibraryCategories(Pageable pageable);
	

	@Query("SELECT new ksa.so.beans.LibraryCategoryResponse(lc.id,lcg.id, lcg.title,lcg.details,lc.fileName,lc.fileUrl,lc.status) FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory")

	List<LibraryCategoryResponse> findAllLibraryCategories();
	
	@Query("SELECT COUNT(lc.id) FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory")
	Long getTotalLibraryCategories();

	@Query("SELECT new ksa.so.beans.LibraryCategoryResponse(lc.id,lcg.id, lcg.title,lcg.details,lc.fileName,lc.fileUrl,lc.status) FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory AND LOWER(lcg.title) LIKE %?1%")
	List<LibraryCategoryResponse> findByTitle(String title, Pageable listing);
	
	@Query("SELECT COUNT(lc.id) FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory AND LOWER(lcg.title) LIKE %?1%")
	Long getTotalRecordsByTitle(String title);

	@Query("SELECT new ksa.so.beans.LibraryCategoryResponse(lc.id,lcg.id, lcg.title,lcg.details,lc.fileName,lc.fileUrl,lc.status) "
			+ "FROM LibraryCategory lc, LibraryCategoryLanguage lcg WHERE lc = lcg.libraryCategory AND lc.libraryCategory IS NOT NULL AND lc.status.id = 4 ")
	List<LibraryCategoryResponse> findAllLibrarySubCategories();
	
//	@Query("SELECT new ksa.so.beans.BranchResponse(b.id,b.address,bl.details,bl.title,b.fileName,b.fileUrl,b.status) from Branch b, BranchLanguage bl WHERE bl.branch = b AND bl.language =1")
//	List<BranchResponse> getAllBranches(Pageable pageable);
}
