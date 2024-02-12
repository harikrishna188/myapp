package com.webapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT *FROM zco_user WHERE  email_id=?1  and is_active = '1'", nativeQuery = true)
	Optional<User> findByEmail(String email);

}
