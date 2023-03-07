package com.taskone.demo.repository;

import com.taskone.demo.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findUserAccountByUserId(Long userId);

    boolean existsUserAccountByUserId(Long userId);
}
