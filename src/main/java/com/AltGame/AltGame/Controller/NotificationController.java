package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.NotificationService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String username = userService.authentication().getName();

        if(notificationService.showUserNotifications(username).isEmpty()){
            return new ResponseEntity<>(new ResponseDto("404", "Notification Data Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto("200", "Success Get User Notifications", notificationService.showUserNotifications(username)), HttpStatus.OK);
    }

}
