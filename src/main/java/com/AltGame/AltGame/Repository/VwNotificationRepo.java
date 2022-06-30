package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.VwNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface VwNotificationRepo extends JpaRepository<VwNotificationEntity, Integer> {
    List<VwNotificationEntity> findAllByUserIdOrderByCreatedAtDesc(int userId);

}
