package com.jphilips.inventorymanagementapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jphilips.inventorymanagementapi.entity.MyUser;
import java.util.Optional;


public interface MyUserRepository extends JpaRepository<MyUser, Long> {
	
	@Query("Select u From MyUser u LEFT JOIN FETCH u.roles")
	Page<MyUser> findAllWithRoles(Pageable pageable);
	
	Page<MyUser> findAll(Pageable pageable);
	
	@Query("Select u From MyUser u LEFT JOIN FETCH u.roles where u.id = :id")
	Optional<MyUser> findById(@Param("id") Long id);
	
	@Query("Select u From MyUser u LEFT JOIN FETCH u.roles where u.username = :username")
	Optional<MyUser> findByUsername(@Param("username") String username);
	
}
