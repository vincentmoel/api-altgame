package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.VwNotificationEntity;
import com.AltGame.AltGame.Service.NotificationService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @GetMapping("/show-user-notifications")
    public ResponseDto showUserNotifications()
    {
        String username = userService.authentication().getPrincipal().toString();
        List<VwNotificationEntity> notifications = notificationService.showUserNotifications(username);
        return new ResponseDto("200", "Success Get User Notifications", notifications);
    }

}
