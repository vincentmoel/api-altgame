package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.*;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    // Get All Data From Table
    public void index()
    {

    }
    @GetMapping("/get")
    public ResponseDto get_user(Authentication authentication){
        Optional<VwUserEntity> vwUserEntity = userService.get_user(authentication.getName());
        if(Objects.isNull(vwUserEntity)){
            return new ResponseDto("404", "User Not Found", authentication.getName());
        }
        return new ResponseDto("200", "Succes Get Data", vwUserEntity);
    }
    @PutMapping("/update")
    public ResponseDto update(UserDto userDto, @RequestParam("img") MultipartFile img, Authentication authentication) throws IOException {
        Optional<VwUserEntity> user = userService.get_user(authentication.getName());
        userDto.setImg(img);
        if(Objects.isNull(user)){
            return new ResponseDto("400","Failed Update");
        }
        userService.update(userDto);
        return new ResponseDto("200","Succes Update", user);
    }
}
