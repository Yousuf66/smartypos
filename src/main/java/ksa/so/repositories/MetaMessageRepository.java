package ksa.so.repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaMessage;

public interface MetaMessageRepository extends JpaRepository<MetaMessage, Long>{	
	MetaMessage findByCode(String code);
}
