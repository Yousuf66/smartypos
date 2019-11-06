package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaAppType;

public interface MetaAppTypeRepository extends JpaRepository<MetaAppType, Long>{
	MetaAppType findByCode(String code);
}
