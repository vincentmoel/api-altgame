package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Entity.VwUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface VwUserRepo extends JpaRepository<VwUserEntity, Integer> {

}
