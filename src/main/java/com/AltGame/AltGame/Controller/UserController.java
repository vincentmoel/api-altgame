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

    @PostMapping("/register-seller")
    public ResponseEntity<?> registerSeller(@RequestBody Map<String, Object> json){
        userService.registerSeller(json.get("bankAccount").toString());
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Succes Register Seller"),HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> get_user(){
        Optional<VwUserEntity> vwUserEntity = userService.get_user(userService.authentication().getName());
        if(Objects.isNull(vwUserEntity)){
            return new ResponseEntity<>(new ResponseDto("404", "User Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto("200", "Success Get Data", vwUserEntity), HttpStatus.OK);

    }
    @PostMapping("/update")
    public ResponseEntity<?> update(UserDto userDto, @RequestParam("image") MultipartFile image) throws IOException {
        userDto.setUsername(userService.authentication().getPrincipal().toString());
        userDto.setImage(image);
        if(Objects.nonNull(userService.getUserByUsername(userService.authentication().getPrincipal().toString()))){
            userService.update(userDto);
            return new ResponseEntity<>(new ResponseDto("200","Success Update"), HttpStatus.OK) ;
        }
        return new ResponseEntity<>(new ResponseDto("400","Failed Update"), HttpStatus.BAD_REQUEST);
    }
}
