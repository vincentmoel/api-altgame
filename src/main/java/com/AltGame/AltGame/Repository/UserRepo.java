package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    UserEntity findById(int id);
    UserEntity findByUsername(String username);

    UserEntity findByUserId(Integer userId);

    boolean existsByEmail(String email);
}
