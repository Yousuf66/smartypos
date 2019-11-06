package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Opt;
import ksa.so.domain.MetaStatus;

public interface OptRepository extends JpaRepository<Opt, Long>{
	List<Opt> findByStatus(MetaStatus status);
}
