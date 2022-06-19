package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponDto;
import com.AltGame.AltGame.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping(path="")
    public ResponDto index()    // Get All Data From Table
    {
        ResponDto responDto = new ResponDto();
        responDto.setStatus("200");
        responDto.setMessage("Success Store User");
        responDto.setData(productService.index());
        return  responDto;
    }

    // Save Data To Table
    public void store()
    {

    }

    // Update Data To Table
    public void update()
    {

    }

    // Delete Data From Table
    public void destroy()
    {

    }
}
