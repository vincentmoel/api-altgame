package com.AltGame.AltGame.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GetUserDto {
    private String name;
    private String username;
    private String email;
    private String phone;
    private MultipartFile image;
    private String bankAccount;
}
