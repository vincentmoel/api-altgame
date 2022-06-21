package com.AltGame.AltGame.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Dto.WishlistDto;
import com.AltGame.AltGame.Entity.WishlistEntity;
import com.AltGame.AltGame.Service.WishlistService;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    @Autowired
    WishlistService wService;

    // Get All Data From Table
    @GetMapping("/{userId}")
    public ResponseDto index(@PathVariable("userId") int userId) {
        ResponseDto responseDto = new ResponseDto("200", "Success Find Wishlists", wService.index(userId));
        return responseDto;
    }

    // Save Data To Table
    @PostMapping("/store")
    public ResponseDto store(WishlistDto wDto) {
        ResponseDto responseDto = new ResponseDto("200", "Success Store Wishlist", wService.store(wDto));
        return responseDto;
    }

    // Update Data To Table
    public void update() {

    }

    // Delete Data From Table
    @DeleteMapping("/destroy")
    public ResponseDto destroy(int wId) {
        ResponseDto responseDto = new ResponseDto("200", "Success Destroy Wishlist", wService.destroy(wId));
        return responseDto;
    }
}
