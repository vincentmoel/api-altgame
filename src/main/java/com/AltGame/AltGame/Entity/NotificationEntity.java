package com.AltGame.AltGame.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="notifications")
@Data
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private Integer notificationId;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "status")
    private String status;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;



}
