package com.AltGame.AltGame.Entity;



import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Integer productId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="category_id")
    private Integer categoryId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private int price;

    @Lob
    @Column(name="image")
    private byte image;

    @Column(name="status")
    private String status;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;
}
