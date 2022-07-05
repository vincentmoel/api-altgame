package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.*;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-user")
    public ResponseEntity<?> get_user(){
        Optional<VwUserEntity> vwUserEntity = userService.get_user(userService.authentication().getName());
        if(Objects.isNull(vwUserEntity)){
            return new ResponseEntity<>(new ResponseDto("204", "User Not Found"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("302", "Success Get Data", vwUserEntity), HttpStatus.FOUND);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(UserDto userDto, @RequestParam("image") MultipartFile image) throws IOException {
        userDto.setUsername(userService.authentication().getPrincipal().toString());
        userDto.setImage(image);
        if(Objects.nonNull(userService.getUserByUsername(userService.authentication().getPrincipal().toString()))){
            userService.update(userDto);
            return new ResponseEntity<>(new ResponseDto("202","Success Update"), HttpStatus.ACCEPTED) ;
        }
        return new ResponseEntity<>(new ResponseDto("400","Failed Update"), HttpStatus.BAD_REQUEST);
    }
}
