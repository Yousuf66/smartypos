package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Combo;
import ksa.so.domain.ComboItem;
import ksa.so.domain.MetaStatus;

public interface ComboItemRepository extends JpaRepository<ComboItem, Long>{
	List<ComboItem> findByComboAndStatus(Combo combo, MetaStatus status);
}

