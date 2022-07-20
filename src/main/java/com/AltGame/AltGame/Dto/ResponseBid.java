package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseBid {


    private Integer bidId;
    private Integer productId;
    private Integer price;
    private String status;
    private Date createdAt;
    private Date acceptedAt;
    private UserInformationDto user;
}
