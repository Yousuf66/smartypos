package ksa.so.repositories;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaLanguage;

public interface LanguagesRepository  extends JpaRepository<MetaLanguage, Long>{
	List<MetaLanguage> findAllByOrderByIdAsc();
	
}
