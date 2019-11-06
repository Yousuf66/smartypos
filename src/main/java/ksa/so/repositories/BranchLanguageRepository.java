package ksa.so.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.BranchLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public interface BranchLanguageRepository extends JpaRepository<BranchLanguage, Long>{
	BranchLanguage findByBranchAndLanguage(Branch branch, MetaLanguage language);
	List<BranchLanguage> findByLanguageAndTitleContainingIgnoreCase(MetaLanguage language, String title);
	@Query("SELECT COUNT(s.id) FROM Item s INNER JOIN s.branch sl where sl.id=:branchID")
	double findTotalProducts(@Param("branchID") long branchID);

	
	 @Modifying
	    @Transactional
	    @Query (value="CALL Shipping_Rate(:branchID)", nativeQuery = true)
	    public Object[] shippingRate(@Param("branchID") long branchID);


	@Modifying
	@Transactional
	@Query("SELECT title FROM BranchLanguage b where b.branch=:branch")
	public List<Object[]> storeTitle(@Param("branch") Branch branch);
	
	List<BranchLanguage> findByBranchId(Long id);

}
