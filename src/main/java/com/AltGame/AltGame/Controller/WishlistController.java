package com.AltGame.AltGame.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Dto.WishlistDto;
import com.AltGame.AltGame.Entity.WishlistEntity;
import com.AltGame.AltGame.Service.WishlistService;

@RestController
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    UserService userService;

    @GetMapping("/api/is-product-in-wishlist/{productId}")
    public ResponseDto isProductInWishlist(@PathVariable Integer productId){
        return new ResponseDto("200","Succes Show Wishlist", wishlistService.isProductInWishlist(productId, userService.getUserIdByUsername(userService.authentication().getName())));
    }
    // Get All Data From Table
    @GetMapping("/api/wishlists/index")
    public ResponseDto index() {

        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        Map<String, List<WishlistEntity>> mapWishlists = new HashMap<>();
        mapWishlists.put("wishlists", wishlistService.index((String) authUser.getPrincipal()));
        return new ResponseDto("200", "Success Find Wishlists", mapWishlists);
    }

    // Save Data To Table
    @PostMapping("/api/wishlists/store")
    public ResponseDto store(@RequestBody WishlistDto wishlistDto) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseDto("200", "Success Store Wishlist",
                wishlistService.store(wishlistDto, (String) authUser.getPrincipal()));
    }

    // Delete Data From Table
    @PostMapping("/api/wishlists/destroy")
    public ResponseDto destroy(@RequestBody WishlistDto wishlistDto) {
        ResponseDto responseDto;
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        boolean destroyStatus = wishlistService.destroy(wishlistDto, (String) authUser.getPrincipal());
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
