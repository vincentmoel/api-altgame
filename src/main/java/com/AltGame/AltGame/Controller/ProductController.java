package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseDto index() {
        return new ResponseDto("200", "Success Index Products", productService.index());
    }
    // Search Product
    @GetMapping(path = "")
    public ResponseDto serachProduct(@RequestParam(name = "search") String search){
        return new ResponseDto("200","Success Search",productService.searchByName(search));
    }
    // Show All User Products
    @GetMapping(path = "/products/{username}")
    public ResponseDto showUserProducts(@PathVariable String username) {
        Map<String, List<ProductEntity>> mapProducts = new HashMap<>();
        mapProducts.put("product", productService.showUserProducts(username));
        return new ResponseDto("200", "Success Show User Products", mapProducts);
    }

    // Get One Data From Table
    @GetMapping(path = "/show/{productId}")
    public ResponseDto show(@PathVariable int productId) {
        return new ResponseDto("200", "Success Show Product", productService.show(productId));
    }

    // Save Data To Table
    @PostMapping(path = "/store")
    public ResponseDto store(ProductDto pDto, @RequestParam("image") MultipartFile image) throws IOException {
        pDto.setImage(image);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        productService.store(pDto, (String) authUser.getPrincipal());
        return new ResponseDto("200", "Success Store Product");
    }

    // Update Data To Table
    @PostMapping(path = "/update/{productId}")
    public ResponseDto update(@PathVariable int productId, ProductDto pDto, @RequestParam("image") MultipartFile image) throws IOException {
        pDto.setImage(image);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseDto("200", "Success Update Product", productService.update((String) authUser.getPrincipal(), productId, pDto));
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
