package ksa.so.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaLanguage;


public interface MetaLanguageRepository extends JpaRepository<MetaLanguage, Long>{	
	MetaLanguage findByCode(String code);
	List<MetaLanguage> findAllByOrderByIdAsc();
}
