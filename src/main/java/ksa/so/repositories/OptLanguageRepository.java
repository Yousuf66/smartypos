package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Opt;
import ksa.so.domain.OptLanguage;
import ksa.so.domain.MetaLanguage;

public interface OptLanguageRepository extends JpaRepository<OptLanguage, Long>{
	OptLanguage findByOptAndLanguage(Opt opt, MetaLanguage language);
}
