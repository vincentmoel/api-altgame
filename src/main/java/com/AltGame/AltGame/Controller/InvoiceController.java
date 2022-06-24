package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.InvoiceDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.InvoiceEntity;
import com.AltGame.AltGame.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    // Get All Data From Table
    public void index()
    {

    }

    // Update Data To Table
    @PostMapping("/pay")
    public ResponseDto update(InvoiceDto invoiceDto, @RequestParam("image") MultipartFile image) throws IOException {
        invoiceDto.setImage(image);
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
