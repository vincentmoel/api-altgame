package com.AltGame.AltGame.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="vw_users")
@Data
public class VwUserEntity {
    @Id
    @Column(name="user_id")
    private Integer userId;

    @Column(name="name")
    private String name;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name="bank_account")
    private String bankAccount;

    @Lob
    @Column(name="image")
    private byte[] image;

    @Column(name = "role")
    private String role;
}
