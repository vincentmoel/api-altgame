package com.AltGame.AltGame.Entity;

import com.AltGame.AltGame.Dto.UserInformationDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vw_products")
@Data
public class VwProductEntity {
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "username")
    private String username;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
