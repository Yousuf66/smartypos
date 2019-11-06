package ksa.so.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.BranchResponse;
import ksa.so.beans.LibraryItemDto;
import ksa.so.beans.LibraryItemPojo;
import ksa.so.beans.LibraryItemResponse;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long> {

	List<LibraryItem> findByStatus(MetaStatus status, Pageable pageable);

	@Query("SELECT i FROM LibraryItem i " + "INNER JOIN i.itemLanguageList il " + "INNER JOIN i.status s "
			+ "WHERE s.id=:statusID AND " + "il.language=:language AND "
			+ "replace(LOWER(il.title),'''','') like LOWER(:searchTerm)")
	List<LibraryItem> getItemTitle(@Param("searchTerm") String searchTerm, @Param("language") MetaLanguage language,
			@Param("statusID") Long statusID);

	@Query("SELECT new ksa.so.beans.LibraryItemResponse(li.id,lil.title,lil.details,"
			+ "ii.fileUrl,li.libraryCategory.id) From "
			+ "LibraryItem li INNER JOIN li.itemLanguageList lil  INNER JOIN li.itemImageList ii "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN li.status s " + "WHERE s.id=:statusID AND "
			+ "lil.language=:language AND ii.sortOrder=0 AND " + "(:category is null or lc = :category) "
			+ "order by lil.title")
	ArrayList<LibraryItemResponse> getItemsList(@Param("category") LibraryCategory category,
			@Param("statusID") Long statusID, @Param("language") MetaLanguage language);

	@Query("SELECT new ksa.so.beans.LibraryItemResponse(li.id,lil.title,lil.details,"
			+ "			ii.fileUrl,li.libraryCategory.id) FROM " + "LibraryItem li INNER JOIN li.itemLanguageList lil "
			+ "INNER JOIN li.libraryCategory lc " + "INNER JOIN li.status s " + "INNER JOIN li.itemImageList ii "
			+ "WHERE " + "lil.language=:language AND ii.sortOrder=0 AND " + "s.id=:statusID AND lc.id=:categoryID")
	ArrayList<LibraryItemResponse> findProductsByCategory(@Param("categoryID") Long categoryID,
			@Param("statusID") Long statusID, @Param("language") MetaLanguage language, Pageable pageable);
	
//	@Query(value="select li.id,li.fk_status,li.fk_library_category,lcl.title as category_title,lil.title,lil.details "
//			+ "from library_item li,library_item_language lil,library_category_language lcl"
//			+ " where li.id = lil.fk_library_item AND li.fk_library_category = lcl.fk_library_category"
//			+ " group by lcl.title;",nativeQuery = true)
	@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details,"
			+ " lcl.title AS category,lii.id,lil.id,lcl.id AS categoryId,lii.fileUrl,lii.fileName,lil.title) "
			+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii "
			+ " WHERE li = lil.item "
			+ " AND lii.item = li "
			+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1")
	List<LibraryItemPojo> getAllLibraryItems();
	
	@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details,"
			+ " lcl.title AS category,lii.id,lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName, lil.title) "
			+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii "
			+ " WHERE li = lil.item "
			+ " AND lii.item = li "
			+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language.id = 1 AND lcl.language.id =1")
	List<LibraryItemPojo> getAllLibraryItems(Pageable pageable);

	


	@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details,"
			+ " lcl.title AS category,lii.id,lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName,lil.title) "
			+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii "
			+ " WHERE li = lil.item "
			+ " AND lii.item = li "
			+ " AND li.libraryCategory = lcl.libraryCategory"
			+ " AND lil.language = 1 AND LOWER(lil.title) LIKE %?1%")
	List<LibraryItemPojo> findByTitle(String title,Pageable pageable);



@Query("SELECT COUNT(li.id) "
		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii "
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1")
	Long getTotalRecords();

@Query("SELECT COUNT(li.id) "
		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii "
		+ "WHERE li = lil.item "
		+ "AND lii.item = li "
		+ "AND li.libraryCategory = lcl.libraryCategory"
		+ " AND lil.language = 1 AND LOWER(lil.title) LIKE %?1%")
	Long getTotalRecordsByTitle(String title);


@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details, lcl.title AS category,lii.id,"
		+ " lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName,"
		+ " li.CreatedBy.id,b.id,bl.title,u.username,li.created,lil.title) "
		+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii,Branch b, "
		+ " BranchLanguage bl, User u"
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND  b.id = bl.branch.id "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND b.id = u.branch.id "
		+ " AND bl.language.id = 1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language.id = 1 AND li.status = 14 ")
List<LibraryItemPojo> getRequestedLibraryItems(Pageable pageable);

@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details, lcl.title AS category,lii.id,"
		+ " lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName,"
		+ " li.CreatedBy.id,b.id,bl.title,u.username,li.created,lil.title) "
		+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii,Branch b, "
		+ " BranchLanguage bl, User u"
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND  b.id = bl.branch.id "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND b.id = u.branch.id "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language.id = 1 "
		+ " AND bl.language.id = 1 "
		+ " AND li.status = 14 AND LOWER(lil.title) LIKE %?2%")
List<LibraryItemPojo> getRequestedLibraryItemsByTitle(Pageable pageable,String title);

@Query("SELECT COUNT(*)"
		+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii,Branch b, "
		+ " BranchLanguage bl, User u"
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND  b.id = bl.branch.id "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND b.id = u.branch.id "
		+ " AND bl.language.id = 1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language.id = 1 "
		+ " AND li.status = 14 AND LOWER(lil.title) LIKE %?1%")
Long getRequestedLibraryItemsByTitleCount(String title);

@Query("SELECT COUNT(*) "
		+ " FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii,Branch b, "
		+ " BranchLanguage bl, User u"
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND  b.id = bl.branch.id "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND b.id = u.branch.id "
		+ " AND bl.language.id = 1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language.id = 1  "
		+ " AND li.status = 14 ")
Long getRequestedLibraryItemsCount();

//@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details, lcl.title AS category,lii.id,lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName) "
//		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii, Branch b, User u " 
//		+ "WHERE li.id = lil.item "
//		+ " AND lii.item = li.id "
//		+" AND li.CreatedBy.id = ?1 "
////		+ " AND u.branch.id = ?2 "
//		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1")
//List<LibraryItemPojo> getRequestedLibraryItemsOfBranch( Long id, Pageable pageable);
//



@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details,"
		+ " lcl.title AS category,lii.id,lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName,lil.title) "
		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii, User u "
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND u.branch.id = ?1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1")
List<LibraryItemPojo> getRequestedLibraryItemsOfBranch( Long id, Pageable pageable);

@Query("SELECT COUNT(*) "
		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii, User u "
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND u.branch.id = ?1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1")
	Long getRequestedLibraryItemsOfBranchCount(Long id);

@Query("SELECT new ksa.so.beans.LibraryItemPojo(li.id,li.status,lil.title,lil.details,"
		+ " lcl.title AS category,lii.id,lil.id,lcl.libraryCategory.id AS categoryId,lii.fileUrl,lii.fileName,lil.title) "
		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii, User u "
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND u.branch.id = ?1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1 "
		+ " AND LOWER(lil.title) LIKE %?2%")
List<LibraryItemPojo> getRequestedLibraryItemsOfBranchByTitle(Pageable pageable,String title);

@Query("SELECT COUNT(*) "
		+ "FROM LibraryItem li,LibraryItemLanguage lil, LibraryCategoryLanguage lcl ,LibraryItemImages lii, User u "
		+ " WHERE li = lil.item "
		+ " AND lii.item = li "
		+ " AND li.CreatedBy.id = u.id "
		+ " AND u.branch.id = ?1 "
		+ " AND li.libraryCategory = lcl.libraryCategory AND lil.language = 1 "
		+ " AND LOWER(lil.title) LIKE %?1%")
Long getRequestedLibraryItemsOfBranchByTitle(String title);



}
