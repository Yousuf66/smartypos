package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.Item;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.UserApp;
import ksa.so.domain.WalletOrder;

public interface WalletOrderRepository extends JpaRepository<WalletOrder, Long>{
	}
