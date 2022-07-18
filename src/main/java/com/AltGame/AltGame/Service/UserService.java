package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.*;
import com.AltGame.AltGame.Entity.RoleEntity;
import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Repository.RoleRepo;
import com.AltGame.AltGame.Repository.UserRepo;
import com.AltGame.AltGame.Repository.VwUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public Authentication authentication(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }
    public void store(UserDto userDto){
        UserEntity user = new UserEntity();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setCity(userDto.getCity());
        user.setRoleId(1);
        user.setCreatedAt(timestamp);
        user.setCreatedAt(timestamp);
        userRepo.save(user);
    }
    public void registerSeller(String bankAccount){
        UserEntity user = userRepo.findByUsername(authentication().getName());
        user.setBankAccount(bankAccount);
        user.setRoleId(2);
        userRepo.save(user);
    }
    public void update(UserDto userDto) throws IOException {
        UserEntity user = userRepo.findByUsername(userDto.getUsername());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setCity(userDto.getCity());
        user.setUpdatedAt(timestamp);
        if(userDto.getImage().getSize() > 0){
            user.setImage(userDto.getImage().getBytes());
        }
        if(userDto.getPassword().length() > 0){
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        if(userDto.getBankAccount().length() > 0){
            user.setBankAccount(userDto.getBankAccount());
            user.setRoleId(2);
        }
        userRepo.save(user);
    }

    public Optional<VwUserEntity> get_user(String username){
        Optional<VwUserEntity> vwUserEntity = vwUserRepo.findByUsername(username);
        return vwUserRepo.findByUsername(username);
    }

    public UserEntity getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public UserInformationDto getUserInfoByUsername(String username)
    {
        UserEntity userEntity = userRepo.findByUsername(username);
        UserInformationDto userInfo = new UserInformationDto();

        userInfo.setName(userEntity.getName());
        userInfo.setUsername(userEntity.getUsername());
        userInfo.setEmail(userEntity.getEmail());
        userInfo.setPhone(userEntity.getPhone());
        userInfo.setCity(userEntity.getCity());

        return userInfo;
    }

    public UserInformationDto getUserInfoByUserId(Integer userId)
    {
        String username = this.getUsernameByUserId(userId);
        return this.getUserInfoByUsername(username);
    }


    public Integer getUserIdByUsername(String username) {
        UserEntity userEntity = userRepo.findByUsername(username);
        return userEntity.getUserId();
    }

    public String getUsernameByUserId(Integer userId)
    {
        UserEntity userEntity = userRepo.findByUserId(userId);
        return userEntity.getUsername();
    }

    public boolean exitsByEmail(String email){
        return userRepo.existsByEmail(email);
    }
    public Optional<RoleEntity> role(Integer idRole){
        return roleRepo.findById(idRole);
    }

    public byte[] get_image(String username) {
        UserEntity userEntity = userRepo.findByUsername(username);
        return userEntity.getImage();
    }

}
