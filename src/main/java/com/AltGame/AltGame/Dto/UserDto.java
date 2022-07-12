package com.AltGame.AltGame.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {
    private Integer userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String city;
    private MultipartFile image;
    private String bankAccount;
}
