package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.EmailErrors;

public interface EmailErrorsRepository extends JpaRepository<EmailErrors, Long> {

}
