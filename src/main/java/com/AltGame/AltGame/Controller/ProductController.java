package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.CategoryService;
import com.AltGame.AltGame.Service.ProductService;

import java.io.IOException;

import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    // Get All Data From Table
    @GetMapping(path = "/index")
    public ResponseEntity<?> index() {
        if(productService.index().isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "No Data Index Products"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Index Products", productService.index()), HttpStatus.OK);
    }

    // Search Product
    @GetMapping(path = "")
    public ResponseEntity<?> searchProducts(@RequestParam(name = "search") String search) {
        if(productService.searchByName(search).isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "No Data Search"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Search", productService.searchByName(search)),HttpStatus.OK);
    }

    // Show All User Products
    @GetMapping(path = "/{username}")
    public ResponseEntity<?> showUserProducts(@PathVariable String username) {
        if(productService.showUserProducts(username).isEmpty()){
            return new ResponseEntity<>( new ResponseDto().responseBuilder("404", "No Data Show User Products"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Show User Products", productService.showUserProducts(username)), HttpStatus.OK);
    }

    // Get One Data From Table
    @GetMapping(path = "/show/{productId}")
    public ResponseEntity<?> show(@PathVariable int productId) {
        if(productService.show(productId).isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("204", "No Data Search"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("202", "Success Show Product", productService.show(productId)), HttpStatus.ACCEPTED);
    }

    // Save Data To Table
    @PostMapping(path = "/store")
    public ResponseEntity<?> store(ProductDto pDto, @RequestParam("image") MultipartFile image) throws IOException {
        pDto.setImage(image);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if(categoryService.exitsByCategoryId(pDto.getCategoryId())){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Id Category Not Found"), HttpStatus.BAD_REQUEST);
        }else if(!productService.validationStoreProduct(userService.authentication().getName())){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Product Maximum 4"), HttpStatus.BAD_REQUEST);
        }
        productService.store(pDto, (String) authUser.getPrincipal());
        return new ResponseEntity<>(new ResponseDto().responseBuilder("201", "Success Store Product"), HttpStatus.CREATED);
    }

    // Update Data To Table
    @PostMapping(path = "/update/{productId}")
    public ResponseEntity<?> update(@PathVariable int productId, ProductDto pDto, @RequestParam("image") MultipartFile image)
            throws IOException {
        pDto.setImage(image);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if(categoryService.exitsByCategoryId(pDto.getCategoryId())){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Id Category Not Found"), HttpStatus.BAD_REQUEST);
        }
        productService.update((String) authUser.getPrincipal(), productId, pDto);
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Update Product"), HttpStatus.OK);
    }

    // Delete Data From Table
    @PostMapping(path = "/destroy/{productId}")
    public ResponseEntity<?> destroy(@PathVariable int productId) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        boolean status = productService.destroy((String) authUser.getPrincipal(), productId);
        if (!status)
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Failed Destroy Product"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>( new ResponseDto().responseBuilder("200", "Success Destroy Product"), HttpStatus.OK);
    }
}
