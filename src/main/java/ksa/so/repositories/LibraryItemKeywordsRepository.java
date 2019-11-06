package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.LibraryItemKeywords;
import ksa.so.domain.MetaDay;

public interface LibraryItemKeywordsRepository extends JpaRepository<LibraryItemKeywords, Long> {
	
}
