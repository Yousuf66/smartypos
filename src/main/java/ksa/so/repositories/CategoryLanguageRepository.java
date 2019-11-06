package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Category;
import ksa.so.domain.CategoryLanguage;
import ksa.so.domain.MetaLanguage;

public interface CategoryLanguageRepository extends JpaRepository<CategoryLanguage, Long>{
	CategoryLanguage findByCategoryAndLanguage(Category category, MetaLanguage language);
}
