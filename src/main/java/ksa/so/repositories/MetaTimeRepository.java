package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.AppVersion;
import ksa.so.domain.Branch;
import ksa.so.domain.MetaAppType;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.MetaTime;

public interface MetaTimeRepository extends JpaRepository<MetaTime, Long>{
	List<MetaTime> findByBranch(Branch branch);
}
