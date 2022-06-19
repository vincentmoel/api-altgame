package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private Integer productId;
    private Integer userId;
    private Integer categoryId;
    private String name;
    private String description;
    private int price;
    private byte image;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
