package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.VwNotificationEntity;
import com.AltGame.AltGame.Service.NotificationService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @GetMapping("/show-user-notifications")
    public ResponseEntity<?> showUserNotifications()
    {
        String username = userService.authentication().getPrincipal().toString();
        Map<String, Object> response = new HashMap<>();
        response.put("Notifications", notificationService.showUserNotifications(username));
        if(notificationService.showUserNotifications(username).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("202", "Success Get User Notifications", response), HttpStatus.ACCEPTED);
    }

}
