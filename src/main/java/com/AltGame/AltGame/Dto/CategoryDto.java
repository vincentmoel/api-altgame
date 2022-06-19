package com.AltGame.AltGame.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryDto {
    private Integer categoryId;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
