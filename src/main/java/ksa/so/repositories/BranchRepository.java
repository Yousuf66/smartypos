package ksa.so.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.BranchItemPojo;
import ksa.so.beans.BranchResponse;
import ksa.so.domain.Branch;
import ksa.so.domain.Hq;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public interface BranchRepository extends JpaRepository<Branch, Long>{
	List<Branch> findByStatus(MetaStatus status);

	List<Branch> findByStatusIn(List<MetaStatus> status);

	List<Branch> findByStatusAndHq(MetaStatus status, Hq hq);

	@Query("SELECT COUNT(s.id) FROM Item s INNER JOIN s.branch sl where sl.id=:branchID")
	int findTotalProducts(@Param("branchID") long branchID);

	@Query("Select b from Branch b " + "inner join b.branchLanguageList bl " + "inner join b.status s "
			+ "where s.id=:statusID and " + "bl.language=:language and "
			+ "replace(LOWER(bl.title),'''','') like LOWER(:searchTerm)")
	List<Branch> findBranchByTitle(@Param("statusID") Long statusID, @Param("language") MetaLanguage language,
			@Param("searchTerm") String searchTerm);

	List<Branch> findByHq(Hq hq);

	
	List<Branch> findByStatusOrderByOrderNumberDesc(MetaStatus status);




@Query("SELECT new ksa.so.beans.BranchResponse(b.id,b.address,bl.details,bl.title,b.fileName,b.fileUrl,bl.id,"
		+ "b.phone1,b.is24hours,b.lat,b.lon,b.orderCompletionTime,"
		+ "b.timeOpen,b.timeClose,b.phone1,b.status,b.rating,b.hasDelivery,b.hasTakeaway,b.country.id,b.currency.id, "
		+ "b.shippingFees,b.maxOrderTime,b.maxKm,b.phone2) from Branch b, BranchLanguage bl"
		+ " WHERE bl.branch = b AND bl.language =1")
	List<BranchResponse> getAllBranches(Pageable pageable);



@Query("SELECT new ksa.so.beans.BranchResponse(b.id,b.address,bl.details,bl.title,b.fileName,b.fileUrl,bl.id, " 
				+ "b.phone1,b.is24hours,b.lat,b.lon,b.orderCompletionTime, " 
				+ "b.timeOpen,b.timeClose,b.phone1,b.status,b.rating,b.hasDelivery,b.hasTakeaway,b.country.id,b.currency.id, "
				+ "b.shippingFees,b.maxOrderTime,b.maxKm,b.phone2) from Branch b, BranchLanguage bl"
				+ " WHERE bl.branch = b AND bl.language = 1 AND LOWER(bl.title) LIKE %?1%")
	List<BranchResponse> findByTitle(String title,Pageable pageable);

@Query(value = "select count(*) from branch, branch_language where branch.id = branch_language.fk_branch"
		+ " AND branch_language.fk_language=1",nativeQuery=true)
	Long getTotalRecords();

@Query("SELECT COUNT(b.id) from Branch b, BranchLanguage bl WHERE bl.branch = b"
		+ " AND bl.language =1 AND LOWER(bl.title) LIKE %?1%")
	Long getTotalRecordsByTitle(String title);

@Query("SELECT new ksa.so.beans.BranchItemPojo(i.id,i.price,i.cost,i.quantity,i.status,i.discountAmount,i.branch.id,"
		+ "i.netSalePrice) FROM Item i WHERE i.libraryItem.id = ?1")
	List<BranchItemPojo> getBranchItems(Long id);

@Query("SELECT new ksa.so.beans.BranchItemPojo(i.id,i.price,i.cost,i.quantity,i.status,i.discountAmount,i.branch.id,"
		+ "i.netSalePrice,il.title,il.details,i.barcode,lii.fileName,lii.fileUrl,il.title)"
		+ " FROM Item i, ItemLanguage il,  LibraryItemImages lii"
		+ " WHERE i.branch.id = ?1 "
		+ " AND i.id = il.item.id "
		+ " AND i.libraryItem.id = lii.item.id "
		
		+ " AND il.language =1")
	List<BranchItemPojo> getBranchInventory(Long id,Pageable pageable);

@Query("SELECT count(*)FROM Item i, ItemLanguage il WHERE i.branch.id = ?1 " + 
		 " AND i.id = il.item.id AND il.language =1")
	Long getTotalBranchInventoryRecords(Long id);

@Query("SELECT new ksa.so.beans.BranchItemPojo(i.id,i.price,i.cost,i.quantity,i.status,i.discountAmount,i.branch.id, " + 
	 "i.netSalePrice,il.title,il.details,i.barcode,lil.fileName,lil.fileUrl,il.title)"
	 + " FROM Item i, ItemLanguage il,LibraryItemImages lil "
	 + "WHERE i.branch.id = ?1"
		+ " AND i.id = il.item.id "

		+ " AND lil.item.id = i.libraryItem.id "
		+ " AND il.language =1  "
		+ "AND LOWER(il.title) LIKE %?2%")
	List<BranchItemPojo> findBranchInventoryByTitle(Long id,String lowerCase, Pageable listing);

@Query("SELECT count(*) FROM Item i, ItemLanguage il WHERE i.branch.id = ?1"
		+ " AND i.id = il.item.id AND il.language =1  AND LOWER(il.title) LIKE %?2%")
	Long getBranchInventoryTotalRecordsByTitle(Long id,String lowerCase);



@Query("SELECT new ksa.so.beans.BranchItemPojo(i.id,i.price,i.cost,i.quantity,i.status,i.discountAmount,i.branch.id,"
		+ "i.netSalePrice,il.title,il.details,i.barcode,lii.fileName,lii.fileUrl,il.title)"
		+ " FROM Item i, ItemLanguage il,  LibraryItemImages lii"
		+ " WHERE i.branch.id = ?1 "
		+ " AND i.id = il.item.id "
		+ " AND i.libraryItem.id = lii.item.id "
		+ "AND i.barcode = ?2"
		
		+ " AND il.language =1")
BranchItemPojo getBranchInventoryByBarcode(Long id,String barcode);

}
