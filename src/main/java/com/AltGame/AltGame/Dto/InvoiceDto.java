package com.AltGame.AltGame.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;

@Data
public class InvoiceDto {
    private Integer invoiceId;
    private int bidId;
    private String noInvoice;
    private String status;
    private String address;
    private MultipartFile image;
    private Date createdAt;
    private Date updatedAt;
}
