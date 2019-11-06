package ksa.so.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaStatusLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.beans.idTitle;
import ksa.so.domain.MetaLanguage;

public interface MetaStatusLanguageRepository extends JpaRepository<MetaStatusLanguage, Long>{
	MetaStatusLanguage findByStatusAndLanguage(MetaStatus status, MetaLanguage language);
//	@Query(value="select ms.id,ms.code from meta_status ms, meta_status_language msl where msl.fk_status = ms.id And msl.text = ?1 ",nativeQuery = true)
//	List<Object> findByText(String text );
	
	List<idTitle> findByText(String text);
}
