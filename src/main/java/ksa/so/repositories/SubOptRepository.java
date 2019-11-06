package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.SubOpt;
import ksa.so.domain.Opt;
import ksa.so.domain.MetaStatus;

public interface SubOptRepository extends JpaRepository<SubOpt, Long>{
	List<SubOpt> findByStatus(MetaStatus status);
	List<SubOpt> findByOptAndStatus(Opt Opt, MetaStatus status);
}
