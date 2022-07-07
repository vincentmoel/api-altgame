package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.InvoiceDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.InvoiceEntity;
import com.AltGame.AltGame.Service.InvoiceService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    UserService userService;
    // Get All Data From Table
    @GetMapping("/index")
    public ResponseEntity<?> index()
    {
        if(invoiceService.index(userService.get_user(userService.authentication().getName())).isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Invoice Data Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Index Invoices", invoiceService.index(userService.get_user(userService.authentication().getName()))), HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showById(@PathVariable String id){
        if(invoiceService.show(id).isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Invoice Data Not Found"), HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Succes Show Inovice",invoiceService.show(id)), HttpStatus.OK) ;
    }

    // Update Data To Table
    @PostMapping("/pay/{id}")
    public ResponseEntity<?> update(InvoiceDto invoiceDto, @RequestParam("image") MultipartFile image, @PathVariable String id) throws IOException {
        invoiceDto.setImage(image);
        invoiceDto.setNoInvoice(id);
        if(invoiceService.exitsByNoInvoice(invoiceDto.getNoInvoice())){
            invoiceService.update(invoiceDto);
            return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Succes Pay"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("400","Failed Pay"), HttpStatus.BAD_REQUEST);
    }
}
