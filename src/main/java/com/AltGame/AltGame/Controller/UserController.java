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
    UserService userService;

    // Get All Data From Table
    public void index()
    {

    }
    @GetMapping("/id/{id}")
    public ResponseDto get_user(@PathVariable("id") Integer id){
        return new ResponseDto("200", "Succes Get Data", userService.get_user(id));
    }
    @PutMapping("/update")
    public ResponseDto update(UserDto userDto, @RequestParam("img")MultipartFile img) throws IOException {
        Optional<VwUserEntity> user = userService.get_user(userDto.getUserId());
        userDto.setImg(img);
        if(Objects.isNull(user)){
            return new ResponseDto("400","Failed Update");
        }
        userService.update(userDto);
        return new ResponseDto("200","Succes Update");
    }
}
