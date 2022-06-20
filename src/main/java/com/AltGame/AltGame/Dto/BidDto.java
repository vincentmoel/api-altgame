package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class BidDto {
    private Integer bidId;
    private Integer userId;
    private Integer productId;
    private Integer price;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
