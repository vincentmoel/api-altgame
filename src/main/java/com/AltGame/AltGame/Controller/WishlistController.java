package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Entity.WishlistEntity;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Dto.WishlistDto;
import com.AltGame.AltGame.Service.WishlistService;

import java.util.List;
import java.util.Map;

@RestController
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    UserService userService;

    @GetMapping("/api/is-product-in-wishlist/{productId}")
    public ResponseEntity<?> isProductInWishlist(@PathVariable Integer productId){
        Map<String, Boolean> isInWishlist = wishlistService.isProductInWishlist(productId, userService.getUserIdByUsername(userService.authentication().getName()));
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Show Wishlist", isInWishlist), HttpStatus.OK);

    }
    // Get All Data From Table
    @GetMapping("/api/wishlists/index")
    public ResponseEntity<?> index() {
        List<WishlistEntity> wishlists = wishlistService.index(userService.authentication().getName());
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Find Wishlists", wishlists), HttpStatus.OK);
    }

    // Save Data To Table
    @PostMapping("/api/wishlists/store")
    public ResponseEntity<?> store(@RequestBody WishlistDto wishlistDto) {
        WishlistEntity wishlist = wishlistService.store(wishlistDto, userService.authentication().getName());
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Store Wishlist", wishlist), HttpStatus.OK);

    }

    // Delete Data From Table
    @PostMapping("/api/wishlists/destroy")
    public ResponseEntity<?> destroy(@RequestBody WishlistDto wishlistDto) {
        boolean destroyStatus = wishlistService.destroy(wishlistDto, userService.authentication().getName());
        if(destroyStatus)
        {
            return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Destroy Wishlist"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Failed Destroy Wishlist"), HttpStatus.BAD_REQUEST);


    }
}
