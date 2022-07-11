package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.VwNotificationEntity;
import com.AltGame.AltGame.Service.NotificationService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Get User Notifications", notificationService.showUserNotifications(username)), HttpStatus.OK);
    }

    @PostMapping("/update-status-to-read/{notificationId}")
    public ResponseEntity<?> updateStatusToRead(@PathVariable int notificationId)
    {
        String username = userService.authentication().getName();

        boolean status = notificationService.updateStatus(username,notificationId, "read");
        if(!status)
        {
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Success Update Notification Status"), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Update Notification Status"), HttpStatus.OK);

    }

}
