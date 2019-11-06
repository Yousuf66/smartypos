package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaCancellationReasonLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.Item;
import ksa.so.domain.MetaCancellationReason;
import ksa.so.domain.UserApp;

public interface CancellationReasonLanguageRepository extends JpaRepository<MetaCancellationReasonLanguage, Long>{
List<MetaCancellationReasonLanguage> findByLanguage(MetaLanguage language);
MetaCancellationReasonLanguage findByCancellationReasonAndLanguage(MetaCancellationReason metaCancellationReason,MetaLanguage language);
}