package com.example.demo.repository;


import com.example.demo.enums.Role;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Page<User> findByRole(Role role, Pageable pageable);
    Collection<Object> findAllByRole(Role role);
    List<User> findAllByCreationDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
    boolean existsByEmail(String mail);
}
