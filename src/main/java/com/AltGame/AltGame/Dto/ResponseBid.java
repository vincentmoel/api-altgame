package com.AltGame.AltGame.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ResponseBid {


    private Integer bidId;
    private Integer productId;
    private Integer price;
    private String status;
    private Date createdAt;
    private Date acceptedAt;
    private UserInformationDto user;
}
