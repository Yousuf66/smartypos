package ksa.so.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryCategoryLanguage;
import ksa.so.domain.MetaLanguage;

public interface LibraryCategoryLanguageRepository extends JpaRepository<LibraryCategoryLanguage, Long>,DataTablesRepository<LibraryCategoryLanguage,Long> {
	LibraryCategoryLanguage findByLibraryCategoryAndLanguage(LibraryCategory category, MetaLanguage language);

}
