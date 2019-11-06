package ksa.so.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaUserType;

public interface MetaUserTypeRepository extends JpaRepository<MetaUserType, Long>{	
	MetaUserType findByCode(String code);

	List<MetaUserType> findByCodeIn(ArrayList<String> codes);
}
