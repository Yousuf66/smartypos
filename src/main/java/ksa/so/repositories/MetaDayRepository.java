package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaDay;

public interface MetaDayRepository extends JpaRepository<MetaDay, Long>{
	MetaDay findByCode(String code);
}
