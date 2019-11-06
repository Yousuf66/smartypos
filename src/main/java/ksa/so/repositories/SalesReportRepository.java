package ksa.so.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.CustomerOrder;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.SalesReport;

public interface SalesReportRepository extends JpaRepository<CustomerOrder, Long> {

	@Query("SELECT new ksa.so.domain.SalesReport(o.id, i.barcode, il.title, bl.title, oi.quantity, coalesce(oi.price,0), coalesce(i.cost,0), coalesce(i.discountAmount,0)) "
			+ "FROM OrderItem oi " + "INNER JOIN oi.item i " + "INNER JOIN i.branch b " + "INNER JOIN oi.order o "
			+ "INNER JOIN i.itemLanguageList il " + "INNER JOIN b.branchLanguageList bl "
			+ "WHERE il.language=:language AND bl.language=:language AND date(o.tsClient) <=:date AND o.status=:status ")
	List<SalesReport> findDailySalesReport(@Param("language") MetaLanguage language, @Param("date") Date date,
			@Param("status") MetaStatus status);

	@Query("SELECT u.id,u.username,COUNT(co.subOperator) FROM CustomerOrder co " + "RIGHT JOIN co.subOperator u "
			+ "LEFT JOIN u.userType mut " + "where mut.code = 'UT000004' GROUP BY u.id,u.username")
	List<Object[]> findUser();

	@Query("select co.orderNumber,ua.firstName,co.feedback from CustomerOrder co inner join co.user ua")
	List<Object[]> getCustomerFeedback();

	@Query("select ua.firstName,dcl.title,cu.maxAmount,cu.amountUsed from CouponUsers cu inner join cu.user ua "
			+ "inner join cu.discountCoupon dc inner join dc.discountCouponLanguageList dcl "
			+ "where dcl.language=:language")
	List<Object[]> getDailyDiscountedAmount(@Param("language") MetaLanguage language);
	/*
	 *
	 * @Query("SELECT a.order,TIMEDIFF(a.tsClient, b.tsClient) , TIMEDIFF(a.tsServer, b.tsServer) "
	 * + "FROM OrderStatusLog a INNER JOIN OrderStatusLog b ON a.order =: b.order "
	 * + "where (a.status = 8 and b.status =5)") List<Object[]> ClientServerTime();
	 */

	@Query(value = "SELECT a.fk_order,CAST(TIMEDIFF(a.ts_client, b.ts_client) as char),CAST(TIMEDIFF(a.ts_server, b.ts_server) as char)"
			+ "from " + "order_status_log a inner join order_status_log b ON a.fk_order = b.fk_order "
			+ "where (a.fk_status = 8 and b.fk_status =5)", nativeQuery = true)
	List<Object[]> ClientServerTime();

	@Query("SELECT ua.firstName ,ua.phone ,COUNT(co.id) ,SUM(co.actualAmount) ,SUM(co.discountedAmount) ,SUM(co.totalAmount) from CustomerOrder co "
			+ "LEFT JOIN co.user ua " + "WHERE co.status!=9 GROUP BY ua.firstName,ua.phone ")
	List<Object[]> GenerateRegisteredUserReport();

	@Modifying
	@Transactional
	@Query(value = "CALL MOST_SOLD_ITEMS(:lang,:scode,:lim,:timeperiod,:sdate,:edate)", nativeQuery = true)
	public List<Object[]> topsolditems(@Param("lang") int lang, @Param("scode") String scode, @Param("lim") int lim,
			@Param("timeperiod") String timeperiod, @Param("sdate") Date sdate, @Param("edate") Date edate);

	@Modifying
	@Transactional
	@Query(value = "CALL topmost(:language,:category,:limit,:scode,:codecase,:sdate,:edate)", nativeQuery = true)
	public List<Object[]> topmostcategory(@Param("language") int language, @Param("category") int category,
			@Param("limit") int limit, @Param("scode") String scode, @Param("codecase") String codecase,
			@Param("sdate") Date sdate, @Param("edate") Date edate);

	@Modifying
	@Transactional
	@Query(value = "CALL HOURMIN(:time,:limit,:codecase,:sdate,:edate)", nativeQuery = true)
	public List<Object[]> stackedGraph(@Param("time") int time, @Param("limit") int limit,
			@Param("codecase") String codecase, @Param("sdate") Date sdate, @Param("edate") Date edate);

	@Modifying
	@Transactional
	@Query(value = "CALL MINGRAPH(:time, :limit, :spoint)", nativeQuery = true)
	public List<Object[]> stackedGraph(@Param("time") int time, @Param("limit") int limit, @Param("spoint") int spoint);

	@Modifying
	@Transactional
	@Query(value = "CALL orderDetailsReport()", nativeQuery = true)
	public List<Object[]> orderDetailsReport();
	
	@Query("SELECT COUNT(o) from CustomerOrder o where o.branch=:branch and o.status=:status")
	int getOrderNummer(@Param("branch") Branch branch, @Param("status") MetaStatus status);
	
	@Modifying
	@Transactional
	@Query(value = "CALL ReportConsolidated()", nativeQuery = true)
	public List<Object[]> consolidatedReport();
	
	@Modifying
	@Transactional
	@Query(value = "CALL GetItemSales()", nativeQuery = true)
	public List<Object[]> itemSalesReport();
	
//	hammad
	@Modifying
	@Transactional
	@Query(value = "CALL ReportB()", nativeQuery = true)
	public List<Object[]> reportB();
	
	@Modifying
	@Transactional
	@Query(value = "CALL ReportE()", nativeQuery = true)
	public List<Object[]> reportE();

}
