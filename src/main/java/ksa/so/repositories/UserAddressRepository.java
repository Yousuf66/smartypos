package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.MetaStatus;
import ksa.so.domain.UserAddress;
import ksa.so.domain.UserApp;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

	List<UserAddress> findByUserAndStatus(UserApp user,MetaStatus status);
}

