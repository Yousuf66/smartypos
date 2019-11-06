package ksa.so.repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaDay;
import ksa.so.domain.MetaDayLanguage;
import ksa.so.domain.MetaLanguage;

public interface MetaDayLanguageRepository extends JpaRepository<MetaDayLanguage, Long>{	
	MetaDayLanguage findByOpeningDayAndLanguage(MetaDay day, MetaLanguage language);
}
