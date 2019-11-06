package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.beans.idTitle;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.UserApp;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
	Optional<UserApp> findByPhoneAndCountry(String phone, MetaCountry country);

	List<UserApp> findByInstallationId(String installationId);

	Optional<UserApp> findByUsername(String username);

	UserApp findByPhoneAndOtp(String phone, String otp);

	UserApp findByPhone(String phone);
//	@Query(value = "Select new ksa.so.beans.idTitle(id, name, phone) from UserApp")
//	List<idTitle> getAllUsersApp();

@Query(value=" Select new ksa.so.beans.idTitle(id, firstName, phone) from UserApp")
List<idTitle> getAllUsersApp();
}
