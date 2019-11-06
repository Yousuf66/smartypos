package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.SubOpt;
import ksa.so.domain.SubOptLanguage;
import ksa.so.domain.MetaLanguage;

public interface SubOptLanguageRepository extends JpaRepository<SubOptLanguage, Long>{
	SubOptLanguage findBySubOptAndLanguage(SubOpt subOpt, MetaLanguage language);
}
