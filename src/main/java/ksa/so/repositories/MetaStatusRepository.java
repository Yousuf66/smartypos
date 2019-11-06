package ksa.so.repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.idTitle;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;


public interface MetaStatusRepository extends JpaRepository<MetaStatus, Long>{
	@Query(value = "SELECT CURRENT_TIMESTAMP;", nativeQuery = true)
	Timestamp getCurrentTime();
	
	MetaStatus findByCode(String code);

	List<MetaStatus> findByCodeIn(ArrayList<String> metaStatusListInfo);
	
//	@Query(value="select ms.id,ms.code from meta_status ms, meta_status_language msl where msl.fk_status = ms.id And msl.text = ?1 ",nativeQuery = true)
	@Query(value="select * from meta_status where meta_status.id = ?1",nativeQuery=true)
   Optional<MetaStatus> findById(Long id);
	
	@Query(value = "select new ksa.so.beans.idTitle(ms.id, msl.title) from MetaStatus ms, MetaStatusLanguage msl WHERE ms = msl.status AND ms.code in (:codes) AND msl.language=:metaLanguage")
	List<idTitle> getIdTitle(@Param("metaLanguage") MetaLanguage metaLanguage,@Param("codes") List<String>  codes);
	
//	@Query(value = "select new ksa.so.beans.idTitle(ms.id, msl.title) from MetaStatus ms, MetaStatusLanguage msl WHERE ms = msl.status AND ms.code in (:codes) AND msl.language=:metaLanguage")
//	List<idTitle> getIdTitle(@Param("metaLanguage") MetaLanguage metaLanguage,@Param("codes") List<String>  codes);
}
