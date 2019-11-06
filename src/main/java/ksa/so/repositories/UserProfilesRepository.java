package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Menu;
import ksa.so.domain.UserProfiles;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {

}
