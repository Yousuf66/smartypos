package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.beans.CurrencyDto;
import ksa.so.domain.MetaCurrency;

public interface CurrencyRepository extends JpaRepository<MetaCurrency, Long>{

	@Query("SELECT new ksa.so.beans.CurrencyDto(id,code,symbol,title) FROM MetaCurrency")
	List<CurrencyDto> getAllCurrencies();
}
