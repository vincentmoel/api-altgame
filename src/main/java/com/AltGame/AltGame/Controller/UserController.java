package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.*;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/get-user")
    public ResponseDto get_user(){
        Optional<VwUserEntity> vwUserEntity = userService.get_user(userService.authentication().getName());
        if(Objects.isNull(vwUserEntity)){
            return new ResponseDto("404", "User Not Found");
        }
        return new ResponseDto("200", "Success Get Data", vwUserEntity);
    }
    @PutMapping("/update")
    public ResponseDto update(UserDto userDto,@RequestParam("img") MultipartFile img) throws IOException {
        userDto.setUsername(userService.authentication().getPrincipal().toString());
        userDto.setImage(img);
        if(Objects.nonNull(userService.getUserByUsername(userService.authentication().getPrincipal().toString()))){
            userService.update(userDto);
            return new ResponseDto("200","Success Update");
        }
        return new ResponseDto("400","Failed Update");
    }
}
