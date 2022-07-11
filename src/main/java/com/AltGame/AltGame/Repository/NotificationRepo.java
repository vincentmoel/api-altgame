package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotificationRepo extends JpaRepository<NotificationEntity, Integer> {
    NotificationEntity findByNotificationId(int notificationId);
}
