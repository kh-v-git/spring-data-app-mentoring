package com.taskone.demo.repository;


import com.taskone.demo.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    List<User> findUsersByName(String name);

    List<User> findUsersByName(String name, Pageable pageable);
}
