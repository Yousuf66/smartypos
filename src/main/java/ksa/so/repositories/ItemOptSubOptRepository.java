package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.ItemOptSubOpt;
import ksa.so.domain.Item;
import ksa.so.domain.Opt;

public interface ItemOptSubOptRepository extends JpaRepository<ItemOptSubOpt, Long>{
	List<ItemOptSubOpt> findByItemAndOpt(Item item, Opt opt);
}
