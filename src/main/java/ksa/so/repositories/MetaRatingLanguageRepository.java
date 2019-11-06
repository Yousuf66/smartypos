package ksa.so.repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaRatingLanguage;
import ksa.so.domain.MetaRating;
import ksa.so.domain.MetaLanguage;

public interface MetaRatingLanguageRepository extends JpaRepository<MetaRatingLanguage, Long>{
	MetaRatingLanguage findByRatingAndLanguage(MetaRating rating, MetaLanguage language);
}
