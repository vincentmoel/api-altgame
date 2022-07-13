package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Dto.ResponseProduct;
import com.AltGame.AltGame.Entity.VwProductEntity;
import com.AltGame.AltGame.Repository.VwProductRepo;
import com.AltGame.AltGame.Service.CategoryService;
import com.AltGame.AltGame.Service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.AltGame.AltGame.Service.UserService;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Products Data Not Found "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Index Products", productService.index()), HttpStatus.OK);
    }

    // Get One Data From Table
    @GetMapping(path = "/show/{productId}")
    public ResponseEntity<?> show(@PathVariable int productId) {
//        if(productService.show(productId).isEmpty()){
//            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Products Data Not Found "), HttpStatus.NOT_FOUND);
//        }
        VwProductEntity product = productService.show(productId);
        userService.getUserInfoByUsername(product.getUsername());
//
//        LinkedHashMap<?,?> hm = new LinkedHashMap<>();
//        hm.put()

        ResponseProduct responseProduct = new ResponseProduct();
        responseProduct.setProductId(product.getProductId());
        responseProduct.setUsername(product.getUsername());
        responseProduct.setCategory(product.getCategory());
        responseProduct.setName(product.getName());
        responseProduct.setPrice(product.getPrice());
        responseProduct.setImage(product.getImage());
        responseProduct.setStatus(product.getStatus());
        responseProduct.setCreatedAt(product.getCreatedAt());
        responseProduct.setUpdatedAt(product.getUpdatedAt());
        responseProduct.setUser(userService.getUserInfoByUsername(product.getUsername()));

        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Show Product",responseProduct), HttpStatus.OK);
    }

    // Save Data To Table
    @PostMapping(path = "/store")
    public ResponseEntity<?> store(ProductDto pDto, @RequestParam("image") MultipartFile image) throws IOException {
        pDto.setImage(image);
        if(categoryService.exitsByCategoryId(pDto.getCategoryId())){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Category Not Found"), HttpStatus.NOT_FOUND);
        }else if(!productService.validationStoreProduct(userService.authentication().getName())){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Product Maximum 4"), HttpStatus.BAD_REQUEST);
        }
        productService.store(pDto, userService.authentication().getName());
        return new ResponseEntity<>(new ResponseDto().responseBuilder("201", "Success Store Product"), HttpStatus.CREATED);
    }

    // Update Data To Table
    @PostMapping(path = "/update/{productId}")
    public ResponseEntity<?> update(@PathVariable int productId, ProductDto pDto, @RequestParam("image") MultipartFile image)
            throws IOException {
        pDto.setImage(image);

        if(categoryService.exitsByCategoryId(pDto.getCategoryId())){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Category Not Found"), HttpStatus.NOT_FOUND);
        }
        productService.update(userService.authentication().getName(), productId, pDto);
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Update Product"), HttpStatus.OK);
    }

    // Delete Data From Table
    @PostMapping(path = "/destroy/{productId}")
    public ResponseEntity<?> destroy(@PathVariable int productId) {
        boolean status = productService.destroy(userService.authentication().getName(), productId);
        if (!status)
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Failed Destroy Product"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>( new ResponseDto().responseBuilder("200", "Success Destroy Product"), HttpStatus.OK);
    }

    // Search Product
    @GetMapping(path = "")
    public ResponseEntity<?> searchProducts(@RequestParam(name = "search") String search) {
        if(productService.searchByName(search).isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Products Data Not Found "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Search", productService.searchByName(search)),HttpStatus.OK);
    }

    // Show All User Products (Other Seller)
    @GetMapping(path = "/{username}")
    public ResponseEntity<?> showUserProducts(@PathVariable String username) {
        if(productService.showUserProducts(username).isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Products Data Not Found "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Show User Products", productService.showUserProducts(username)), HttpStatus.OK);
    }

    // Show All My Products (Seller)
    @GetMapping(path = "/my-products")
    public ResponseEntity<?> showMyProducts()
    {
        String username = userService.authentication().getName();
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Show My Products (Seller)", productService.showMyProducts(username)), HttpStatus.OK);
    }

}
