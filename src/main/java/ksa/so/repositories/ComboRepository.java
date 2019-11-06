package ksa.so.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Combo;
import ksa.so.domain.Branch;
import ksa.so.domain.MetaStatus;

public interface ComboRepository extends JpaRepository<Combo, Long>{
	List<Combo> findByStatus(MetaStatus status, Pageable pageable);
	List<Combo> findByBranchAndStatusAndIsSpecial(Branch branch, MetaStatus status, Boolean isSpecial, Pageable pageable);
}

