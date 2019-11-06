package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaCancellationReasonLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaMessage;
import ksa.so.domain.Item;
import ksa.so.domain.MetaCancellationReason;
import ksa.so.domain.UserApp;

public interface CancellationReasonRepository extends JpaRepository<MetaCancellationReason, Long>{
	MetaCancellationReason findByCode(String code);
}