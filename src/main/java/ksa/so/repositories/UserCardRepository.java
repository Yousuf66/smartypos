package ksa.so.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.UserApp;
import ksa.so.domain.UserCard;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {

	Optional<UserCard> findByUser(UserApp user);
}