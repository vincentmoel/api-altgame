package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.BuyerDto;
import com.AltGame.AltGame.Dto.RegisterDto;
import com.AltGame.AltGame.Dto.UserDto;
import com.AltGame.AltGame.Entity.RoleEntity;
import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Repository.RoleRepo;
import com.AltGame.AltGame.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterDto store_buyer(RegisterDto registerDto){
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setRoleId(1);
        userRepo.save(user);
        return registerDto;
    }
    public BuyerDto update_buyer(BuyerDto buyerDto) throws IOException {
        UserEntity user = new UserEntity();
        if(buyerDto.getPassword().equals("")){
            user.setUserId(buyerDto.getIdUser());
            user.setUsername(buyerDto.getUsername());
            user.setName(buyerDto.getName());
            user.setImage(buyerDto.getImg().getBytes());
            user.setEmail(buyerDto.getEmail());
            user.setPhone(buyerDto.getPhone());
        }else{
            user.setUserId(buyerDto.getIdUser());
            user.setUsername(buyerDto.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(buyerDto.getPassword()));
            user.setName(buyerDto.getName());
            user.setImage(buyerDto.getImg().getBytes());
            user.setEmail(buyerDto.getEmail());
            user.setPhone(buyerDto.getPhone());
        }
        userRepo.save(user);
        return buyerDto;
    }

    public Optional<UserEntity> get_buyer(Integer id){
        return userRepo.findById(id);
    }

    public UserEntity username(String username){
        return userRepo.findByUsername(username);
    }
    public Optional<RoleEntity> role(Integer idRole){
        return roleRepo.findById(idRole);
    }
}
