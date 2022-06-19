package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    // Get All Data From Table
    @GetMapping(path="/index")
    public ResponseDto index()
    {
        ResponseDto responseDto = new ResponseDto("200","Success Store User",productService.index());
        return responseDto;
    }

    // Get One Data From Table
    @GetMapping(path="/show/{idProduct}")
    public void show(@PathVariable int idProduct)
    {

    }

    // Save Data To Table
    @PostMapping(path="/store")
    public void store()
    {

    }

    // Update Data To Table
    @PostMapping(path="/update/{idProduct}")
    public void update(@PathVariable int idProduct)
    {

    }

    // Delete Data From Table
    @PostMapping(path="/destroy/{idProduct}")
    public void destroy(@PathVariable int idProduct)
    {

    }
}
