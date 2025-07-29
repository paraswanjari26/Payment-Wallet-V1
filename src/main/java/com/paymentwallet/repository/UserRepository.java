package com.paymentwallet.repository;

import com.paymentwallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailId(String emailId);

    User findByUserIdAndDeleted(String userId, int nonDeleted);

    List<User> findAllByDeleted(int nonDeleted);
}
