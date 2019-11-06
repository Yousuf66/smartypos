package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ksa.so.domain.MetaCard;

public interface MetaCardRepository extends JpaRepository<MetaCard, Long>{	
}
