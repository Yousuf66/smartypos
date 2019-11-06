package ksa.so.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ksa.so.beans.InvoiceItemPojo;
import ksa.so.beans.InvoicePojo;
import ksa.so.domain.Invoice;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	
	@Query(value = " select iloii.invoice_id as inv_id, ii.item_id as item_id, il.title as item_title" + 
			" from invoice_list_of_invoice_items iloii, item_language il, invoice inv,invoice_item ii " + 
			" where  iloii.list_of_invoice_items_id = ii.id " + 
			" AND il.fk_item = ii.item_id " + 
			" AND iloii.invoice_id = inv.id " + 
			" AND il.fk_language =1 " + 
			" AND inv.branch_id = :branchId ",
			nativeQuery = true)
	List<Object> getInvoiceList(@Param(value="branchId") Long branchId);
	
	
	List<Invoice> findByBranchId(Long id,Pageable page);

	@Query(value="select count(*) from invoice where branch_id = ?1",nativeQuery=true)
	int findByBranchId(Long id);
	@Query(value="select count(*) from invoice where branch_id = ?1", nativeQuery = true)
	Long getBranchInvoiceCount(Long id);

//	@Query("select invoice0_.id as id1_37_, invoice0_.amount_returned as amount_r2_37_, " + 
//			" invoice0_.bill_date as bill_dat3_37_, invoice0_.bill_discount as bill_dis4_37_, invoice0_.bill_number as bill_num5_37_, " + 
//			" invoice0_.branch_id as branch_13_37_, invoice0_.net_amount as net_amou6_37_, invoice0_.paid_amount as paid_amo7_37_, " + 
//			" invoice0_.reference_number as referenc8_37_, invoice0_.remarks as remarks9_37_, invoice0_.total_amount as total_a10_37_, " + 
//			" invoice0_.total_discount as total_d11_37_, invoice0_.total_items as total_i12_37_ " + 
//			" from invoice invoice0_ left outer join branch branch1_ on invoice0_.branch_id=branch1_.id " + 
//			" where branch1_.id=?1 and invoice0_.bill_date LIKE ?2%")
	@Query("SELECT inv " + 
			" FROM Invoice inv , Branch br WHERE inv.branch.id=br.id " + 
			" AND br.id=?1 and DATEDIFF(inv.billDate,?2) = 0 ")
	List<Invoice> findByBranchIdAndBillDate(Long id,Date date,Pageable page);
	
	@Query("SELECT COUNT(inv) " + 
			" FROM Invoice inv , Branch br WHERE inv.branch.id=br.id " + 
			" AND br.id=?1 and DATEDIFF(inv.billDate,?2) = 0 ")
	int findByBranchIdAndBillDate(Long id,Date date);
}
