package ksa.so.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.CustomerOrder;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.User;
import ksa.so.domain.UserAddress;
import ksa.so.domain.UserApp;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findByUserAndStatus(UserApp user, MetaStatus status);

	List<CustomerOrder> findByUserAndStatusInOrderByTsClientDesc(UserApp user, List<MetaStatus> statusList);

	List<CustomerOrder> findByUserAndStatusInOrderByTsClientDesc(UserApp user, List<MetaStatus> statusList,
			Pageable pageable);

	List<CustomerOrder> findByBranchAndStatusIn(Branch branch, List<MetaStatus> statusList);

	List<CustomerOrder> findByBranchAndStatusInOrderByTsServerDesc(Branch branch, List<MetaStatus> statusList,
			Pageable pageable);

	List<CustomerOrder> findByBranchAndSubOperatorAndStatusInOrderByTsServerDesc(Branch branch, User subOperator,
			List<MetaStatus> statusList, Pageable pageable);

	List<CustomerOrder> findByBranchAndStatus(Branch branch, MetaStatus status);

	List<CustomerOrder> findByBranchInAndSubOperatorAndStatusInOrderByTsServerDesc(List<Branch> branchList,
			User subOperator, List<MetaStatus> statusList, Pageable pageable);

	CustomerOrder findByBranchAndId(Branch branch, Long id);

	CustomerOrder findByUserAndId(UserApp user, Long id);

	@Query("SELECT AVG(r.score) FROM CustomerOrder o INNER JOIN o.rating r where fk_branch=:branchID group by fk_branch")
	double getAverageRating(@Param("branchID") long branchID);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b.id=:branchID AND s.id=:statusID AND " + "o.id like :searchTerm ")
	List<CustomerOrder> findByBranchAndStatusInAndIdContaining(@Param("searchTerm") Long searchTerm,
			@Param("branchID") Long branchID, @Param("statusID") Long statusID);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b.id=:branchID AND s.id=:statusID AND "
			+ "LOWER(u.firstName) like LOWER(:searchTerm)")
	List<CustomerOrder> findByBranchAndStatusInAndAndUserContaining(@Param("searchTerm") String searchTerm,
			@Param("branchID") Long branchID, @Param("statusID") Long statusID);

//	List<CustomerOrder>	findByBranchAndStatusInAndIdContainingAndUserContainingIgnoreCase(Branch branch,
//			List<MetaStatus> statusList, Long searchTerm);
	@Query("SELECT COUNT(s.id) FROM CustomerOrder s where fk_operator=:operatorID OR fk_sub_operator=:operatorID")
	double findbyOperator(@Param("operatorID") long operatorID);

	@Query("SELECT COUNT(s.id) FROM CustomerOrder s INNER JOIN s.branch sl where sl.id=:branchID")
	double findbyBranch(@Param("branchID") long branchID);

	// List<CustomerOrder> findByUserAddress(UserAddress userAddress);
	List<CustomerOrder> findByAddress(UserAddress userAddress);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b IN (:branchList) " + "AND  s IN (:metaStatusList) AND "
			+ "(o.orderNumber like :searchTerm OR u.firstName like :searchTerm) AND (o.subOperator IN (:subOperator)) ORDER BY o.tsServer DESC")
	List<CustomerOrder> findByBranchInAndStatusInAndOrderNumberOrderByTsServerDesc(
			@Param("branchList") List<Branch> branchList, @Param("metaStatusList") List<MetaStatus> metaStatusList,
			@Param("searchTerm") String searchTerm, @Param("subOperator") List<User> subOperator, Pageable listing);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b IN (:branchList) " + "AND  s IN (:metaStatusList) AND "
			+ "(o.subOperator IN (:subOperator)) ORDER BY o.tsServer DESC")
	List<CustomerOrder> findByBranchInAndStatusInAndSubOperatorInOrderByTsServerDesc(
			@Param("branchList") List<Branch> branchList, @Param("metaStatusList") List<MetaStatus> metaStatusList,
			@Param("subOperator") List<User> subOperator, Pageable listing);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b IN (:branchList) " + "AND  s IN (:metaStatusList) AND "
			+ "(o.orderNumber like :searchTerm OR u.firstName like :searchTerm)  ORDER BY o.tsServer DESC")
	List<CustomerOrder> findByBranchInAndStatusInAndOrderNumberOrderByTsServerDescs(
			@Param("branchList") List<Branch> branchList, @Param("metaStatusList") List<MetaStatus> metaStatusList,
			@Param("searchTerm") String searchTerm, Pageable listing);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b IN (:branchList) " + "AND  s IN (:metaStatusList) "
			+ " ORDER BY o.tsServer DESC")
	List<CustomerOrder> findByBranchInAndStatusInOrderByTsServerDesc(@Param("branchList") List<Branch> branchList,
			@Param("metaStatusList") List<MetaStatus> metaStatusList, Pageable listing);
//	List<CustomerOrder> findByBranchInAndStatusInAndOrderNumberOrderByTsServerDesc(List<Branch> branchList,
//			List<MetaStatus> metaStatusList, String OrderNumber, Pageable listing);

	@Query("SELECT o FROM CustomerOrder o " + "INNER JOIN o.user u " + "INNER JOIN o.status s "
			+ "INNER JOIN o.branch b " + "WHERE b IN (:branchList) "
			+ "AND  s IN (:metaStatusList) AND fk_sub_operator=:operatorID And "
			+ "(o.orderNumber like :searchTerm OR u.firstName like :searchTerm) ORDER BY o.tsServer DESC")
	List<CustomerOrder> findByBranchInAndSubOperatorAndStatusInAndOrderNumberOrderByTsServerDesc(
			@Param("branchList") List<Branch> branchList, @Param("operatorID") long operatorID,
			@Param("metaStatusList") List<MetaStatus> metaStatusList, @Param("searchTerm") String searchTerm,
			Pageable listing);
	
		
//CustomerOrder findByUserAndId(UserApp user, Long id);
//	
//	@Query("SELECT AVG(r.score) FROM CustomerOrder o INNER JOIN o.rating r where fk_branch=:branchID group by fk_branch")
//	double getAverageRating(@Param("branchID") long branchID);
//	
//	@Query("SELECT o FROM CustomerOrder o "
//		+ "INNER JOIN o.user u "
//		+ "INNER JOIN o.status s "
//		+ "INNER JOIN o.branch b "
//		+ "WHERE b.id=:branchID AND s.id=:statusID AND "
//		+ "o.id like :searchTerm ")
//	List<CustomerOrder> findByBranchAndStatusInAndIdContaining(@Param("searchTerm")Long searchTerm,
//    		@Param("branchID")Long branchID,
//    		@Param("statusID")Long statusID);
//	
//	@Query("SELECT o FROM CustomerOrder o "
//			+ "INNER JOIN o.user u "
//			+ "INNER JOIN o.status s "
//			+ "INNER JOIN o.branch b "
//			+ "WHERE b.id=:branchID AND s.id=:statusID AND "
//			+ "LOWER(u.firstName) like LOWER(:searchTerm)")
//	List<CustomerOrder> findByBranchAndStatusInAndAndUserContaining(@Param("searchTerm")String searchTerm,
//	    		@Param("branchID")Long branchID,
//	    		@Param("statusID")Long statusID);
////	List<CustomerOrder>	findByBranchAndStatusInAndIdContainingAndUserContainingIgnoreCase(Branch branch, 
////			List<MetaStatus> statusList, Long searchTerm);
//	@Query("SELECT COUNT(s.id) FROM CustomerOrder s where fk_operator=:operatorID OR fk_sub_operator=:operatorID")
//	double findbyOperator(@Param("operatorID") long operatorID);
//	@Query("SELECT COUNT(s.id) FROM CustomerOrder s INNER JOIN s.branch sl where sl.id=:branchID")
//	double findbyBranch(@Param("branchID") long branchID);
//	//List<CustomerOrder> findByUserAddress(UserAddress userAddress);
//	List<CustomerOrder> findByAddress(UserAddress userAddress);
//	
	
	//Creating dashboard queries.
	// Query # 1 : Getting total sales.
	@Query("SELECT ROUND(SUM(o.totalAmount)) FROM CustomerOrder o where fk_status = 8")
	Double getSalesAlltime();
	
	@Query("SELECT ROUND(SUM(o.totalAmount)) FROM CustomerOrder o where fk_status = 8 AND fk_branch = :branchId")
	Double getSalesAlltime(@Param("branchId") long branchId);
	
	
	
	// Query # 2 : Getting this month sales
	@Query(" SELECT SUM(o.totalAmount) " + 
			" FROM CustomerOrder o " + 
			" WHERE MONTH(o.tsServer) = MONTH(CURRENT_DATE()) " + 
		    " AND YEAR(o.tsServer) = YEAR(CURRENT_DATE()) " + 
			" AND fk_status = 8")
	Double getSalesThisMonth();
	
	@Query(" SELECT SUM(o.totalAmount) " + 
			" FROM CustomerOrder o " + 
			" WHERE MONTH(o.tsServer) = MONTH(CURRENT_DATE()) " + 
		    " AND YEAR(o.tsServer) = YEAR(CURRENT_DATE()) " + 
			" AND fk_status = 8 AND fk_branch = :branchId")
	Double getSalesThisMonth(@Param("branchId") long branchId);
	
	
	
	
	//Query # 3. Getting today's Sales.		
	@Query("SELECT SUM(o.totalAmount) " + 
			" FROM CustomerOrder o " + 
			" WHERE MONTH(o.tsServer) = MONTH(CURRENT_DATE()) " + 
			" AND YEAR(o.tsServer) = YEAR(CURRENT_DATE()) " +
			" AND DAY(o.tsServer) = DAY(CURRENT_DATE()) " +
			" AND fk_status = 8")
	Double getSalesToday();
	
	@Query("SELECT SUM(o.totalAmount) " + 
			" FROM CustomerOrder o " + 
			" WHERE MONTH(o.tsServer) = MONTH(CURRENT_DATE()) " + 
			" AND YEAR(o.tsServer) = YEAR(CURRENT_DATE()) " +
			" AND DAY(o.tsServer) = DAY(CURRENT_DATE()) " +
			" AND fk_status = 8  AND fk_branch = :branchId")
	Double getSalesToday(@Param("branchId") long branchId);
	
	
	
	
	//Query # 4 Getting products sold today.
			
	@Query("SELECT SUM(i.quantity) " + 
			" FROM CustomerOrder o, OrderItem i " + 
			" WHERE o = i.order AND fk_status = 8 " + 
			" AND YEAR(o.tsServer) = YEAR(CURRENT_DATE()) " +
			" AND DAY(o.tsServer) = DAY(CURRENT_DATE()) " +
			" AND MONTH(o.tsServer) = MONTH(CURRENT_DATE()) ")
	Double getProductsSoldToday();
	
	@Query("SELECT SUM(i.quantity) " + 
			" FROM CustomerOrder o, OrderItem i " + 
			" WHERE o = i.order " + 
			" AND YEAR(o.tsServer) = YEAR(CURRENT_DATE()) " +
			" AND DAY(o.tsServer) = DAY(CURRENT_DATE()) " +
			" AND MONTH(o.tsServer) = MONTH(CURRENT_DATE())"
			+ " AND fk_status = 8 AND fk_branch = :branchId ")
	Double getProductsSoldToday(@Param("branchId") long branchId);
	
	
	
	//Query # 5 Getting Sales per month.
			
	@Query(value = "SELECT CONCAT(month(ts_server), '-', year(ts_server)) order_month, round(sum(total_amount))/1000 \"Sales in K\"\r\n" + 
			" FROM customer_order\r\n" + 
			" WHERE fk_status = 8  \r\n" + 
			" GROUP BY order_month\r\n" + 
			" ORDER BY order_month DESC\r\n" + 
			" LIMIT 12;", nativeQuery = true)
	List<Object> getSalesPerMonth();
	
	@Query(value = "SELECT CONCAT(month(ts_server), '-', year(ts_server)) order_month, round(sum(total_amount))/1000 \"Sales in K\"\r\n" + 
			" FROM customer_order\r\n" + 
			" WHERE fk_status = 8  AND fk_branch = :branchId \r\n" + 
			" GROUP BY order_month\r\n" + 
			" ORDER BY order_month DESC\r\n" + 
			" LIMIT 12;", nativeQuery = true)
	List<Object> getSalesPerMonth(@Param("branchId") long branchId);
			
	
	
	//Query # 6 Getting Sales per area.
			
	@Query(value = "SELECT ua.lat, ua.lon, sum(co.total_amount) per_user_sale\r\n" + 
			" FROM customer_order co, user_address ua\r\n" + 
			" WHERE co.fk_user_address is not null\r\n" + 
			" AND co.fk_status = 8\r\n" + 
			" AND co.fk_user_address = ua.id\r\n" + 
			" GROUP BY ua.lat, ua.lon;", nativeQuery = true)
	List<Object> getSalesPerArea();
	
	@Query(value = "SELECT ua.lat, ua.lon, sum(co.total_amount) per_user_sale\r\n" + 
			" FROM customer_order co, user_address ua\r\n" + 
			" WHERE co.fk_user_address is not null\r\n" + 
			" AND co.fk_status = 8  AND fk_branch = :branchId \r\n" + 
			" AND co.fk_user_address = ua.id\r\n" + 
			" GROUP BY ua.lat, ua.lon;", nativeQuery = true)
	List<Object> getSalesPerArea(@Param("branchId") long branchId);
	
	
	//Query # 7 Getting Sales per Store.
			
	@Query(value = "SELECT bl.title, ROUND(SUM(co.total_amount)) branch_total\r\n" + 
			" FROM customer_order co, branch_language bl\r\n" + 
			" WHERE co.fk_branch = bl.fk_branch\r\n" + 
			" AND bl.fk_language = 1\r\n" +
			" AND co.fk_status = 8  " + 
			" GROUP BY co.fk_branch\r\n" + 
			" ORDER BY branch_total DESC;", nativeQuery = true)
	List<Object> getSalesPerStore();
	
	@Query(value = "SELECT bl.title, ROUND(SUM(co.total_amount)) branch_total\r\n" + 
			" FROM customer_order co, branch_language bl\r\n" + 
			" WHERE co.fk_branch = bl.fk_branch\r\n" + 
			" AND bl.fk_language = 1\r\n" +
			" AND co.fk_status = 8  AND fk_branch = :branchId " + 
			" GROUP BY co.fk_branch\r\n" + 
			" ORDER BY branch_total DESC;", nativeQuery = true)
	List<Object> getSalesPerStore(@Param("branchId") long branchId);
			
	//Query # 8 Getting orders per month.
			
	@Query(value = "SELECT CONCAT(month(ts_server), '-', year(ts_server)) order_month, count orders \r\n" + 
			" FROM customer_order\r\n" + 
			" WHERE fk_status = 8\r\n" + 
			" GROUP BY order_month\r\n" + 
			" ORDER BY order_month DESC\r\n" + 
			" LIMIT 12;", nativeQuery = true)
	List<Object> getOrdersPerMonth();
	
	@Query(value = "SELECT CONCAT(month(ts_server), '-', year(ts_server)) order_month, count orders \r\n" + 
			" FROM customer_order\r\n" + 
			" WHERE fk_status = 8  AND fk_branch = :branchId \r\n" + 
			" GROUP BY order_month\r\n" + 
			" ORDER BY order_month DESC\r\n" + 
			" LIMIT 12;", nativeQuery = true)
	List<Object> getOrdersPerMonth(@Param("branchId") long branchId);
			
	//Query # 6 Getting Sales and Orders Per month.
			
	@Query(value = "SELECT CONCAT(month(ts_server), '-', year(ts_server)) order_month, count orders, round(sum(total_amount))/1000 \"Sales in K\" " + 
			" FROM customer_order\r\n" + 
			" WHERE fk_status = 8\r\n" + 
			" GROUP BY order_month\r\n" + 
			" ORDER BY order_month DESC\r\n" + 
			" LIMIT 12;", nativeQuery = true)
	List<Object> getSalesAndOrdersPerMonth();
	
	@Query(value = "SELECT CONCAT(month(ts_server), '-', year(ts_server)) order_month, count orders, round(sum(total_amount))/1000 \"Sales in K\" " + 
			" FROM customer_order\r\n" + 
			" WHERE fk_status = 8 AND fk_branch = :branchId \r\n" + 
			" GROUP BY order_month\r\n" + 
			" ORDER BY order_month DESC\r\n" + 
			" LIMIT 12;", nativeQuery = true)
	List<Object> getSalesAndOrdersPerMonth(@Param("branchId") long branchId);
			
	
	
	//Query # 7 Getting total number of Orders all time.
			
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8", nativeQuery = true)
	Double getItdOrders();
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8 AND fk_branch = :branchId ", nativeQuery = true)
	Double getItdOrders(@Param("branchId") long branchId);
	

			
	
	
	//Query # 8 Getting total number of orders this quarter
			
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8 " + 
			"AND QUARTER(ts_server) = QUARTER(CURRENT_DATE()) " + 
			"AND YEAR(ts_server) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getQtdOrders();
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8  AND fk_branch = :branchId " + 
			"AND QUARTER(ts_server) = QUARTER(CURRENT_DATE()) " + 
			"AND YEAR(ts_server) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getQtdOrders(@Param("branchId") long branchId);
	
	
	
	//Query # 9 Getting total number of orders this month
			
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8 " + 
			"AND MONTH(ts_server) = MONTH(CURRENT_DATE()) " + 
			"AND YEAR(ts_server) = YEAR(CURRENT_DATE())",nativeQuery = true)
	Double getMtdOrders();
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8 AND fk_branch = :branchId " + 
			"AND MONTH(ts_server) = MONTH(CURRENT_DATE()) " + 
			"AND YEAR(ts_server) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getMtdOrders(@Param("branchId") long branchId);
	
	
	
	//Query # 10 Getting total number of orders this day
			
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order  " + 
			"WHERE fk_status = 8 " + 
			"AND DAY(ts_server) = DAY(CURRENT_DATE()) " + 
			"AND MONTH(ts_server) = MONTH(CURRENT_DATE()) " + 
			"AND YEAR(ts_server) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getTodaysOrders();
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 8 AND fk_branch = :branchId " + 
			"AND DAY(ts_server) = DAY(CURRENT_DATE()) " + 
			"AND MONTH(ts_server) = MONTH(CURRENT_DATE()) " + 
			"AND YEAR(ts_server) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getTodaysOrders(@Param("branchId") long branchId);
	
	
	
	//Query # 11 Getting avg daily orders.
			
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT CAST(ts_server as DATE))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8;", nativeQuery = true)
	Double getAvgDailyOrders();
	
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT CAST(ts_server as DATE))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8  AND fk_branch = :branchId;", nativeQuery = true)
	Double getAvgDailyOrders(@Param("branchId") long branchId);
	
	
	
	//Query # 12 Getting Average monthly orders.
	
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT YEAR(ts_server),MONTH(ts_server))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8;", nativeQuery = true)
	Double getAvgMonthlyOrders();
	
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT YEAR(ts_server),MONTH(ts_server))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8 AND fk_branch = :branchId;", nativeQuery = true)
	Double getAvgMonthlyOrders(@Param("branchId") long branchId);
	
	//Query # 13 Getting Average quarterly orders.
	
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT YEAR(ts_server),QUARTER(ts_server))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8;", nativeQuery = true)
	Double getAvgQuarterlyOrders();
	
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT YEAR(ts_server),QUARTER(ts_server))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8 AND fk_branch = :branchId;", nativeQuery = true)
	Double getAvgQuarterlyOrders(@Param("branchId") long branchId);
	
	
	//Query # 14 Getting Average yearly orders.
			
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT YEAR(ts_server))) " + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8;", nativeQuery = true)
	Double getAvgYearlyOrders();
	
	@Query(value = "SELECT ROUND(COUNT(*)/COUNT(DISTINCT YEAR(ts_server))) " + 
			"FROM customer_order" + 
			"WHERE fk_status = 8 AND fk_branch = :branchId;", nativeQuery = true)
	Double getAvgYearlyOrders(@Param("branchId") long branchId);
	
	
	
	//Query # 15 Getting products sold all time.
			
	@Query( "SELECT SUM(i.quantity) " + 
			"FROM CustomerOrder o, OrderItem i " + 
			"WHERE o = i.order " + 
			"AND o.status = 8 ")
	Double getProductsSoldAlltime();
	
	@Query(value = "SELECT SUM(i.quantity) " + 
			"FROM CustomerOrder o, OrderItem i " + 
			"WHERE o = i.order " + 
			"AND fk_status = 8  AND fk_branch = :branchId", nativeQuery = true)
	Double getProductsSoldAlltime(@Param("branchId") long branchId);
	
	
	
	//Query # 16 Getting products sold this quarter.
			
	@Query(value = "SELECT sum(i.quantity) " + 
			"FROM CustomerOrder o, OrderItem i " + 
			"WHERE o = i.order " + 
			"AND fk_status = 8 " + 
			"AND QUARTER(o.tsServer) = QUARTER(CURRENT_DATE())\r\n" + 
			"AND YEAR(o.tsServer) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getProductsSoldThisQuarter();
	
	@Query(value = "SELECT sum(i.quantity) " + 
			"FROM CustomerOrder o, OrderItem i " + 
			"WHERE o = i.order " + 
			"AND fk_status = 8  AND fk_branch = :branchId " + 
			"AND QUARTER(o.tsServer) = QUARTER(CURRENT_DATE())\r\n" + 
			"AND YEAR(o.tsServer) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getProductsSoldThisQuarter(@Param("branchId") long branchId);
	
	
	
	//Query # 17 Getting products sold this month.
			
	@Query(value = "SELECT sum(i.quantity) " + 
			"FROM CustomerOrder o, OrderItem i " + 
			"WHERE o = i.order " + 
			"AND fk_status = 8 " + 
			"AND MONTH(o.tsServer) = MONTH(CURRENT_DATE())\r\n" + 
			"AND YEAR(o.tsServer) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getProductsSoldThisMonth();
	
	@Query(value = "SELECT sum(i.quantity) " + 
			"FROM CustomerOrder o, OrderItem i " + 
			"WHERE o = i.order " + 
			"AND fk_status = 8 AND fk_branch = :branchId " + 
			"AND MONTH(o.tsServer) = MONTH(CURRENT_DATE())\r\n" + 
			"AND YEAR(o.tsServer) = YEAR(CURRENT_DATE())", nativeQuery = true)
	Double getProductsSoldThisMonth(@Param("branchId") long branchId);
	
	
	
	@Query(value = "SELECT count(*)\r\n" + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status IN (5,6,7);", nativeQuery = true)
	Double getNumberOfPendingOrders();
	
	@Query(value = "SELECT count(*)\r\n" + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status IN (5,6,7) AND fk_branch = :branchId "
			, nativeQuery = true)
	Double getNumberOfPendingOrders(@Param("branchId") long branchId);
	
	//Getting Orders Last Day.
	
	@Query(value = "SELECT count(*)\r\n" + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8\r\n" + 
			"AND ts_server > (DATE(NOW()) + INTERVAL -1 DAY);", nativeQuery = true)
	Double getLastDayOrders();
	
	@Query(value = "SELECT count(*)\r\n" + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8  AND fk_branch = :branchId \r\n" + 
			"AND ts_server > (DATE(NOW()) + INTERVAL -1 DAY);", nativeQuery = true)
	Double getLastDayOrders(@Param("branchId") long branchId);
	
	//Getting Orders Last 7 Days.
	
	@Query(value = "SELECT count(*)\r\n" + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8\r\n" + 
			"AND ts_server > (DATE(NOW()) + INTERVAL -7 DAY);", nativeQuery = true)
	Double getLastSevenDaysOrders();
	
	@Query(value = "SELECT count(*)\r\n" + 
			"FROM customer_order\r\n" + 
			"WHERE fk_status = 8  AND fk_branch = :branchId \r\n" + 
			"AND ts_server > (DATE(NOW()) + INTERVAL -7 DAY);", nativeQuery = true)
	Double getLastSevenDaysOrders(@Param("branchId") long branchId);
	
	@Query(value = "SELECT COUNT " + 
			"FROM customer_order " + 
			";", nativeQuery = true)
	Double getTotalOrders();
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_branch = :branchId ", nativeQuery = true)
	Double getTotalOrders(@Param("branchId") long branchId);
	
	
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 9;", nativeQuery = true)
	Double getCancelledOrders();
	
	@Query(value = "SELECT COUNT(*) " + 
			"FROM customer_order " + 
			"WHERE fk_status = 9 AND fk_branch = :branchId ", nativeQuery = true)
	Double getCancelledOrders(@Param("branchId") long branchId);
	
	@Query(value = "SELECT rating " + 
			"FROM branch " + 
			"WHERE id = :branchId ", nativeQuery = true)
	Double getBranchRating(@Param("branchId") long branchId);
}
