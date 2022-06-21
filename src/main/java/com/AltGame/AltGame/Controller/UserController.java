package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.*;
import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    public Authentication authentication(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }
    // Get All Data From Table
    public void index()
    {

    }
    @GetMapping("/get")
    public ResponseDto get_user(){
        Optional<VwUserEntity> vwUserEntity = userService.get_user(authentication().getName());
        if(Objects.isNull(vwUserEntity)){
            return new ResponseDto("404", "User Not Found");
        }
        return new ResponseDto("200", "Succes Get Data", vwUserEntity);
    }
    @PutMapping("/update")
    public ResponseDto update(UserDto userDto,@RequestParam("img") MultipartFile img) throws IOException {
        userDto.setUsername(authentication().getPrincipal().toString());
        userDto.setImg(img);
        if(userService.check_username(authentication().getPrincipal().toString())){
            userService.update(userDto);
            return new ResponseDto("200","Succes Update");
        }
        return new ResponseDto("400","Failed Update");
    }
}
