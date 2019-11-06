package ksa.so.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.beans.CountryDto;
import ksa.so.domain.MetaCountry;

public interface MetaCountryRepository extends JpaRepository<MetaCountry, Long>{	
@Query("SELECT new ksa.so.beans.CountryDto(id,code,country,timezone) from MetaCountry")
List<CountryDto> findAllCountries();

}
