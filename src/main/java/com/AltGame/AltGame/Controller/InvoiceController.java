package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.InvoiceDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.InvoiceEntity;
import com.AltGame.AltGame.Service.InvoiceService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseDto index()
    {
        return new ResponseDto("200","Success Index Invoices", invoiceService.index(userService.get_user(userService.authentication().getName())));
    }

    @GetMapping("/show/{id}")
    public ResponseDto showById(@PathVariable Integer id){
        return new ResponseDto("200","Succes Show Inovice",invoiceService.show(id));
    }

    // Update Data To Table
    @PostMapping("/pay/{id}")
    public ResponseDto update(InvoiceDto invoiceDto, @RequestParam("image") MultipartFile image, @PathVariable Integer id) throws IOException {
        invoiceDto.setImage(image);
        invoiceDto.setInvoiceId(id);
        if(invoiceService.exitsByInvoiceId(invoiceDto.getInvoiceId())){
            invoiceService.update(invoiceDto);
            return new ResponseDto("200","Succes Pay");
        }
        return new ResponseDto("400","Failed Pay");
    }

    // Delete Data From Table
    public void destroy()
    {

    }
}
