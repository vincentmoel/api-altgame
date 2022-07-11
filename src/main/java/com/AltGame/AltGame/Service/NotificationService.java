package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Entity.NotificationEntity;
import com.AltGame.AltGame.Entity.VwNotificationEntity;
import com.AltGame.AltGame.Repository.NotificationRepo;
import com.AltGame.AltGame.Repository.VwNotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepo notificationRepo;

    @Autowired
    VwNotificationRepo vwNotificationRepo;

    @Autowired
    UserService userService;

    public List<VwNotificationEntity> showUserNotifications(String username)
    {
        int userId = userService.getUserIdByUsername(username);
        return vwNotificationRepo.findAllByUserIdOrderByCreatedAtDesc(userId);

    }

    public void store(Integer invoiceId)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setInvoiceId(invoiceId);
        notificationEntity.setCreatedAt(timestamp);
        notificationEntity.setStatus("unread");
        notificationEntity.setUpdatedAt(timestamp);
        notificationRepo.save(notificationEntity);
    }

    public boolean updateStatus(String username, int notificationId, String status)
    {
        int userId = userService.getUserIdByUsername(username);
//        VwNotificationEntity vwNotificationEntity = vwNotificationRepo.findByNotificationIdAndUserId(notificationId,userId);


        NotificationEntity notificationEntity = notificationRepo.findByNotificationId(notificationId);
        notificationEntity.setStatus(status);
        notificationRepo.save(notificationEntity);

        return true;
    }

}
