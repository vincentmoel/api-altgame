package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.BuyerDto;
import com.AltGame.AltGame.Dto.RegisterDto;
import com.AltGame.AltGame.Dto.RegisterSellerDto;
import com.AltGame.AltGame.Dto.SellerDto;
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

    public RegisterDto store_buyer(RegisterDto registerDto){
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
    public void store_seller(RegisterSellerDto registerSellerDto){
        UserEntity user = userRepo.findById(registerSellerDto.getIdUser().intValue());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setRoleId(2);
        user.setBankAccount(registerSellerDto.getBankAccount());
        userRepo.save(user);
    }
    public void update_buyer(BuyerDto buyerDto) throws IOException {
        UserEntity user = userRepo.findById(buyerDto.getIdUser().intValue());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(buyerDto.getPassword().equals("")){
            user.setUserId(buyerDto.getIdUser());
            user.setUsername(buyerDto.getUsername());
            user.setName(buyerDto.getName());
            user.setImage(buyerDto.getImg().getBytes());
            user.setEmail(buyerDto.getEmail());
            user.setPhone(buyerDto.getPhone());
            user.setUpdatedAt(timestamp);
        }else{
            user.setUserId(buyerDto.getIdUser());
            user.setUsername(buyerDto.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(buyerDto.getPassword()));
            user.setName(buyerDto.getName());
            user.setImage(buyerDto.getImg().getBytes());
            user.setEmail(buyerDto.getEmail());
            user.setPhone(buyerDto.getPhone());
            user.setUpdatedAt(timestamp);
        }
        userRepo.save(user);
    }
    public void update_seller(SellerDto sellerDto) throws IOException {
        UserEntity user = userRepo.findById(sellerDto.getIdUser().intValue());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(sellerDto.getPassword().equals("")){
            user.setUserId(sellerDto.getIdUser());
            user.setUsername(sellerDto.getUsername());
            user.setName(sellerDto.getName());
            user.setImage(sellerDto.getImg().getBytes());
            user.setEmail(sellerDto.getEmail());
            user.setPhone(sellerDto.getPhone());
            user.setBankAccount(sellerDto.getBankAccount());
            user.setUpdatedAt(timestamp);
        }else{
            user.setUserId(sellerDto.getIdUser());
            user.setUsername(sellerDto.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(sellerDto.getPassword()));
            user.setName(sellerDto.getName());
            user.setImage(sellerDto.getImg().getBytes());
            user.setEmail(sellerDto.getEmail());
            user.setPhone(sellerDto.getPhone());
            user.setUpdatedAt(timestamp);
        }
        userRepo.save(user);
    }

    public Optional<VwUserEntity> get_user(Integer id){
        return vwUserRepo.findById(id);
    }

    public UserEntity username(String username){
        return userRepo.findByUsername(username);
    }
    public Optional<RoleEntity> role(Integer idRole){
        return roleRepo.findById(idRole);
    }
}
