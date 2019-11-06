package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.AppVersion;
import ksa.so.domain.MetaAppType;
import ksa.so.domain.MetaStatus;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long>{
	List<AppVersion> findByAppTypeAndStatus(MetaAppType appType, MetaStatus status);
}
