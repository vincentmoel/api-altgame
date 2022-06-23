package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    // Get All Data From Table
    public void index()
    {

    }

    // Save Data To Table
    public void store()
    {

    }

    // Update Data To Table
    public ResponseDto update()
    {
        return new ResponseDto("200","Succes Pay");
    }

    // Delete Data From Table
    public void destroy()
    {

    }
}
