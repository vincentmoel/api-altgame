package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.BuyerDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    // Get All Data From Table
    public void index()
    {

    }
    @GetMapping("/buyer/{id}")
    public ResponseDto get_buyer(@PathVariable("id") Integer id){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("200");
        responseDto.setMessage("message");
        responseDto.setData(userService.get_buyer(id));
        return responseDto;
    }
    // Save Data To Table
    public void store()
    {

    }

    // Update Data To Table
    public void update()
    {

    }
    @PostMapping("/buyer")
    public ResponseDto update_buyer(BuyerDto buyerDto, @RequestParam("img")MultipartFile img) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        buyerDto.setImg(img);
        responseDto.setStatus("200");
        responseDto.setMessage("message");
        responseDto.setData(userService.update_buyer(buyerDto));
        return responseDto;
    }

    // Delete Data From Table
    public void destroy()
    {

    }
}
