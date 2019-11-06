package ksa.so.repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.MetaRating;

public interface MetaRatingRepository extends JpaRepository<MetaRating, Long>{
	MetaRating findByCode(String code);
}
