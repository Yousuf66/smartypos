package ksa.so.repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaMessage;
import ksa.so.domain.MetaMessageLanguage;

public interface MetaMessageLanguageRepository extends JpaRepository<MetaMessageLanguage, Long>{	
	MetaMessageLanguage findByMessageAndLanguage(MetaMessage msg, MetaLanguage language);
}
