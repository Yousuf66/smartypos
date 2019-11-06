package ksa.so.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Branch;
import ksa.so.domain.UserApp;
import ksa.so.domain.UserBranch;

public interface UserBranchRepository extends JpaRepository<UserBranch, Long>{
	List<UserBranch> findByUser(UserApp user);
	Optional<UserBranch> findByUserAndBranch(UserApp user, Branch branch);
}

