package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Hq;
import ksa.so.domain.HqLanguage;
import ksa.so.domain.MetaLanguage;

public interface HqLanguageRepository extends JpaRepository<HqLanguage, Long>{
	HqLanguage findByHqAndLanguage(Hq hq, MetaLanguage language);
}
