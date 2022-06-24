package com.AltGame.AltGame.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/index")
    public ResponseDto index() {

        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        Map<String, List<WishlistEntity>> mapWishlists = new HashMap<>();
        mapWishlists.put("wishlists", wService.index((String) authUser.getPrincipal()));
        return new ResponseDto("200", "Success Find Wishlists", mapWishlists);
    }

    // Save Data To Table
    @PostMapping("/store")
    public ResponseDto store(@RequestBody WishlistDto wDto) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseDto("200", "Success Store Wishlist",
                wService.store(wDto, (String) authUser.getPrincipal()));
    }

    // Delete Data From Table
    @PostMapping("/destroy")
    public ResponseDto destroy(@RequestBody WishlistDto wDto) {
        ResponseDto responseDto;
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        boolean destroyStatus = wService.destroy(wDto, (String) authUser.getPrincipal());

        if(destroyStatus)
        {
            responseDto = new ResponseDto("200", "Success Destroy Wishlist");
        }
        else
        {
            responseDto = new ResponseDto("204", "Failed Destroy Wishlist");
        }

        return responseDto;
    }
}
