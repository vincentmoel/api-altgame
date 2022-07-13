package com.AltGame.AltGame.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ResponseProduct {


    private Integer productId;
    private String username;
    private String category;
    private String name;
    private int price;
    private String image;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private UserInformationDto user;
}
