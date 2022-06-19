package com.AltGame.AltGame.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    @Column(name="name")
    private String name;
}
