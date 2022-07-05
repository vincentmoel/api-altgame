package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.VwProductEntity;
import com.AltGame.AltGame.Service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Get All Data From Table
    @GetMapping(path = "/index")
    public ResponseEntity<?> index() {
        Map<String, Object> response = new HashMap<>();
        response.put("Products",productService.index());
        if(productService.index().isEmpty()){
            return new ResponseEntity<>(new ResponseDto("204", "No Data Index Products"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("302", "Success Index Products", response), HttpStatus.FOUND);
    }

    // Search Product
    @GetMapping(path = "")
    public ResponseEntity<?> searchProducts(@RequestParam(name = "search") String search) {
        Map<String, Object> response = new HashMap<>();
        response.put("Products",productService.searchByName(search));
        if(productService.searchByName(search).isEmpty()){
            return new ResponseEntity<>(new ResponseDto("204", "No Data Search", response),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("302", "Success Search", response),HttpStatus.FOUND);
    }

    // Show All User Products
    @GetMapping(path = "/{username}")
    public ResponseDto showUserProducts(@PathVariable String username) {
        Map<String, List<VwProductEntity>> mapProducts = new HashMap<>();
        mapProducts.put("products", productService.showUserProducts(username));
        return new ResponseDto("200", "Success Show User Products", mapProducts);
    }

    // Get One Data From Table
    @GetMapping(path = "/show/{productId}")
    public ResponseEntity<?> show(@PathVariable int productId) {
        Map<String, Object> response = new HashMap<>();
        response.put("product", productService.show(productId));
        return new ResponseEntity<>(new ResponseDto("202", "Success Show Product", response), HttpStatus.ACCEPTED);
    }

    // Save Data To Table
    @PostMapping(path = "/store")
    public ResponseEntity<?> store(ProductDto pDto, @RequestParam("image") MultipartFile image) throws IOException {
        pDto.setImage(image);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        productService.store(pDto, (String) authUser.getPrincipal());
        return new ResponseEntity<>(new ResponseDto("201", "Success Store Product"), HttpStatus.CREATED);
    }

    // Update Data To Table
    @PostMapping(path = "/update/{productId}")
    public ResponseEntity<?> update(@PathVariable int productId, ProductDto pDto, @RequestParam("image") MultipartFile image)
            throws IOException {
        pDto.setImage(image);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        productService.update((String) authUser.getPrincipal(), productId, pDto);
        return new ResponseEntity<>(new ResponseDto("202", "Success Update Product"), HttpStatus.ACCEPTED);
    }

    // Delete Data From Table
    @PostMapping(path = "/destroy/{productId}")
    public ResponseDto destroy(@PathVariable int productId) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        boolean status = productService.destroy((String) authUser.getPrincipal(), productId);

        if (!status)
            return new ResponseDto("204", "Failed Destroy Product");

        return new ResponseDto("200", "Success Destroy Product");
    }
}
