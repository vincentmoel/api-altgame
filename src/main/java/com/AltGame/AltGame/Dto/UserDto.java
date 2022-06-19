package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Integer userId;
    private Integer roleId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String bankAccount;
//    private byte image;
    private Date createdAt;
    private Date updatedAt;
}
