package ksa.so.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.SaleInvoice;

public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, Long>{
	
	@Modifying
    @Transactional
    @Query (value="CALL sp_sale_invoice(:siID,:item,:quantity,:price,:amount,:disPer,:disAmt,:netAmt,:remarks,:branch,:hq,'S','A','L')", nativeQuery = true)
    public void sp_sale_invoice(@Param("siID") Long siID,
					    		@Param("item") Long item,
					    		@Param("quantity") int quantity,
					    		@Param("price") double price,
					    		@Param("amount") double amount,
					    		@Param("disPer") double disPer,
					    		@Param("disAmt") double disAmt,
					    		@Param("netAmt") double netAmt,
					    		@Param("remarks") String remarks,
					    		@Param("branch") Long branch,
					    		@Param("hq") Long hq);
}

