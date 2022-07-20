package com.AltGame.AltGame.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="vw_notifications")
@Data
public class VwNotificationEntity {
    @Id
    @Column(name="no_invoice")
    private String noInvoice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "bid_price")
    private int bidPrice;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="notification_id")
    private Integer notificationId;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="status")
    private String status;
}
