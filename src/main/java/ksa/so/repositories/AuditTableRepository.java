package ksa.so.repositories;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.AuditTable;


public interface AuditTableRepository extends JpaRepository<AuditTable, Long>{

	//Optional<AuditTable> findById(Long id);

}
