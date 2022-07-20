package com.AltGame.AltGame.Dto;

import com.AltGame.AltGame.Entity.ProductEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseWhishlist{
    private Integer wishlistId;
    private UserInformationDto user;
    private ProductEntity product;
    private Date createdAt;
    private Date updatedAt;
}

