package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Combo;
import ksa.so.domain.ComboLanguage;
import ksa.so.domain.MetaLanguage;

public interface ComboLanguageRepository extends JpaRepository<ComboLanguage, Long>{
	ComboLanguage findByComboAndLanguage(Combo combo, MetaLanguage language);
}
