package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.beans.idTitle;
import ksa.so.domain.Branch;
import ksa.so.domain.ItemsAvailableCount;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaUserType;
import ksa.so.domain.User;
import ksa.so.domain.CustomerOrder;
import ksa.so.domain.Hq;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByPhoneAndCountry(String phone, MetaCountry country);
	Optional<User> findByUsernameAndUserType(String username, MetaUserType userType);
	Optional<User> findByUsernameAndUserTypeIn(String username, List<MetaUserType> userType);
	User findByUserTypeAndBranch(MetaUserType userType, Branch branch); // only send user type of main operator
	Optional<User> findById(Long id);
	List<User> findByUserTypeInAndBranch(List<MetaUserType> userType, Branch branch);
	Optional<User> findByUsername(String username);
	User findBySessionid(String sessionid);
	Optional<User> findBySessionidAndUsername(String sessionid,String username);
	User findByHq(Hq hq);
	/*@Query("SELECT u.id,u.username FROM User u "
			+ "LEFT JOIN u.userType mut "
			+ "LEFT JOIN CustomerOrder co "
			+ "ON co.subOperator = u.Id where mut.code = 'UT000004' GROUP BY u.id,u.username")
	List<User> findUser();
	
	*/
	List<User> findAll();



	@Query("SELECT new ksa.so.beans.idTitle(u.branch.id,bl.title) FROM User u, BranchLanguage bl "
			+ "WHERE u.branch.id = bl.branch.id AND u.id = ?1 AND bl.language =1")
	List<idTitle> findBranchByUser(Long id);
	
	@Query("SELECT new ksa.so.beans.idTitle(id, name, phone) from User")
	List<idTitle> getAllUsers();
	List<User> findByusername(String username);
	
}
