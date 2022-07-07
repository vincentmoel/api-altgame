package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")  
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    // Get All Data From Table
    @GetMapping(path="/index")
    public ResponseEntity<?> index()
    {
        if(categoryService.index().isEmpty()){
            return  new ResponseEntity<>(new ResponseDto().responseBuilder("404","Category Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Get All Category",categoryService.index()), HttpStatus.OK) ;
    }
}
