package com.AltGame.AltGame.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BuyerDto {
    private Integer idUser;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private MultipartFile img;
}
