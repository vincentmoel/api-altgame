package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {

    private Integer notificationId;
    private Integer invoiceId;
    private String status;
    private Date createdAt;
    private Date updatedAt;

}
