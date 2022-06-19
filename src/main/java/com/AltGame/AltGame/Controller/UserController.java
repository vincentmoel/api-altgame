package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.BuyerDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Dto.RegisterSellerDto;
import com.AltGame.AltGame.Dto.SellerDto;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/{id}")
    public ResponseDto get_user(@PathVariable("id") Integer id){
        return new ResponseDto("200", "Succes Get Data", userService.get_user(id));
    }
    // Save Data To Table
    public void store()
    {

    }
    @PostMapping("/seller")
    public ResponseDto register_seller(@RequestBody RegisterSellerDto registerSellerDto){
        userService.store_seller(registerSellerDto);
        return new ResponseDto("200","Succes Register Seller");

    }    // Update Data To Table
    public void update()
    {

    }
    @PutMapping("/buyer")
    public ResponseDto update_buyer(BuyerDto buyerDto, @RequestParam("img")MultipartFile img) throws IOException {
        buyerDto.setImg(img);
        userService.update_buyer(buyerDto);
        return new ResponseDto("200","Succes Update");
    }
    @PutMapping("/seller")
    public ResponseDto update_seller(SellerDto sellerDto, @RequestParam("img")MultipartFile img) throws IOException {
        sellerDto.setImg(img);
        userService.update_seller(sellerDto);
        return new ResponseDto("200","Succes Update");
    }

    // Delete Data From Table
    public void destroy()
    {

    }
}
