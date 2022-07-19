package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.InvoiceDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.VwInvoiceEntity;
import com.AltGame.AltGame.Service.InvoiceService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
        List<VwInvoiceEntity> invoices = invoiceService.index(userService.authentication().getName());
        if(invoices.isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Invoice Data Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Index Invoices", invoices), HttpStatus.OK);
    }

    @GetMapping("/show/{bidId}")
    public ResponseEntity<?> showByBidId(@PathVariable int bidId){
//        if(invoiceService.showByBidId(bidId).isEmpty()){
//            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Invoice Data Not Found"), HttpStatus.NOT_FOUND);
//
//        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Show Invoice",invoiceService.showByBidId(bidId)), HttpStatus.OK) ;
    }

//  Update Data To Table
    @PostMapping("/pay/{bidId}")
    public ResponseEntity<?> update(InvoiceDto invoiceDto, @RequestParam("image") MultipartFile image, @PathVariable int bidId) throws IOException {
        invoiceDto.setImage(image);
        VwInvoiceEntity invoice = invoiceService.showByBidId(bidId);
        invoiceDto.setNoInvoice(invoice.getNoInvoice());
        if(invoiceService.exitsByNoInvoice(invoiceDto.getNoInvoice())){
            invoiceService.update(invoiceDto);
            return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Pay"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("400","Failed Pay"), HttpStatus.BAD_REQUEST);
    }
}
