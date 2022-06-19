package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.BuyerDto;
import com.AltGame.AltGame.Dto.ResponDto;
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
    public ResponDto get_buyer(@PathVariable("id") Integer id){
        ResponDto responDto = new ResponDto();
        responDto.setStatus("200");
        responDto.setMessage("message");
        responDto.setData(userService.get_buyer(id));
        return responDto;
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
    public ResponDto update_buyer(BuyerDto buyerDto, @RequestParam("img")MultipartFile img) throws IOException {
        ResponDto responDto = new ResponDto();
        buyerDto.setImg(img);
        responDto.setStatus("200");
        responDto.setMessage("message");
        responDto.setData(userService.update_buyer(buyerDto));
        return responDto;
    }

    // Delete Data From Table
    public void destroy()
    {

    }
}
