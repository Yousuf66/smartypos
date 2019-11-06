package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.ComboItem;
import ksa.so.domain.ComboItemSubOpt;
import ksa.so.domain.MetaStatus;

public interface ComboItemSubOptRepository extends JpaRepository<ComboItemSubOpt, Long>{
	List<ComboItemSubOpt> findByComboItem(ComboItem comboItem);
}

