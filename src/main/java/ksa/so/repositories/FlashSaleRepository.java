package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.FlashSale;

public interface FlashSaleRepository extends JpaRepository<FlashSale, Long> {

	@Query("SELECT f FROM FlashSale f " + "INNER JOIN f.status s " + "WHERE s.id=:statusID AND "
			+ "DATE(:date) >= Date(starting_date) AND  DATE(:date) <= Date(end_date) and start_time <= time(:time) and "
			+ "end_time >= time(:time)")
	FlashSale findSale(@Param("statusID") Long statusID, @Param("time") String time, @Param("date") String sdate);

}
