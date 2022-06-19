package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class WishlistDto {
    private Integer wishlistId;
    private Integer userId;
    private Integer productId;
    private Date createdAt;
    private Date updatedAt;
}
