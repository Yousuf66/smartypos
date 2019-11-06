package ksa.so.repositories;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.LanguageTermDefinition;

public interface LanguageTermDefinitionRepository extends JpaRepository<LanguageTermDefinition, Long>{
	List<LanguageTermDefinition> findAllByOrderByIdAsc();
}
