package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
}
