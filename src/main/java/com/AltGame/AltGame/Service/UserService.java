package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.*;
import com.AltGame.AltGame.Entity.RoleEntity;
import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Repository.RoleRepo;
import com.AltGame.AltGame.Repository.UserRepo;
import com.AltGame.AltGame.Repository.VwUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private VwUserRepo vwUserRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterDto store(RegisterDto registerDto){
        UserEntity user = new UserEntity();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setUsername(registerDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setRoleId(1);
        user.setCreatedAt(timestamp);
        user.setCreatedAt(timestamp);
        userRepo.save(user);
        return registerDto;
    }
    public void update(UserDto userDto) throws IOException {
        UserEntity user = userRepo.findById(userDto.getUserId().intValue());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setImage(userDto.getImg().getBytes());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setUpdatedAt(timestamp);
        if(userDto.getPassword().length() > 0){
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        if(userDto.getBankAccount().length() > 0){
            user.setBankAccount(userDto.getBankAccount());
        }
        userRepo.save(user);
    }

    public Optional<VwUserEntity> get_user(String username){
        return vwUserRepo.findByUsername(username);
    }

    public UserEntity username(String username){
        return userRepo.findByUsername(username);
    }
    public Optional<RoleEntity> role(Integer idRole){
        return roleRepo.findById(idRole);
    }
}
