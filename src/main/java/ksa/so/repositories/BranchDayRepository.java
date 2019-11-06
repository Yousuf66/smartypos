package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Branch;
import ksa.so.domain.BranchDay;
import ksa.so.domain.MetaDay;
import ksa.so.domain.MetaLanguage;

public interface BranchDayRepository extends JpaRepository<BranchDay, Long>{

	List<BranchDay> findByBranch(Branch branch);
	BranchDay findByBranchAndDay(Branch branch, MetaDay findByCode);
	BranchDay findByDay(MetaDay findByCode);
}
