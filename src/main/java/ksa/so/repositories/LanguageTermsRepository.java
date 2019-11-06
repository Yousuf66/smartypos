package ksa.so.repositories;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.LanguageTermDefinition;
import ksa.so.domain.LanguageTerms;



public interface LanguageTermsRepository extends JpaRepository<LanguageTerms, Long>{
		List<LanguageTerms> findAllByOrderByIdAsc();
		
	

		List<LanguageTerms> findByTermDefinitionIsNotNull();

		
		
}