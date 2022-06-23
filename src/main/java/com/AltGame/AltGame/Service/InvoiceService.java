package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.InvoiceDto;
import com.AltGame.AltGame.Entity.InvoiceEntity;
import com.AltGame.AltGame.Repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepo invoiceRepo;

    public void update(InvoiceDto invoiceDto) throws IOException {
        InvoiceEntity invoiceEntity = invoiceRepo.findById(invoiceDto.getInvoiceId().intValue());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoiceEntity.setImage(invoiceDto.getImage().getBytes());
        invoiceEntity.setUpdatedAt(timestamp);
        invoiceRepo.save(invoiceEntity);
    }
    public boolean store(Integer bidId){
        if(invoiceRepo.existsById(bidId)){
            return false;
        }
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoiceEntity.setBidId(bidId);
        if(invoiceRepo.count() == 0){
            invoiceEntity.setNoInvoice("INV-"+dateNowFormatYMD()+"-00001");
        }else{
            invoiceEntity.setNoInvoice(genrate_noInvoice());
        }
        invoiceEntity.setCreatedAt(timestamp);
        invoiceEntity.setUpdatedAt(timestamp);
        invoiceEntity.setStatus("unpaid");
        invoiceRepo.save(invoiceEntity);
        return true;
    }
    public String genrate_noInvoice(){
        String lastNoIncoice = invoiceRepo.lastNoInvoice();
        String[] last =  lastNoIncoice.split("-");
        String[] noInvoce = new String[3];

        noInvoce[0] = "INV";
        noInvoce[1] = dateNowFormatYMD();
        if(noInvoce[1].equals(last[1])){
            int ai = Integer.parseInt(last[2]) + 1;
            noInvoce[2] = String.format("%05d",ai);
            return String.join("-", noInvoce);
        }
        noInvoce[2] = "00001";
        return String.join("-", noInvoce);
    }
    public String dateNowFormatYMD(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }
    public boolean exitsByInvoiceId(Integer invoiceId){
        return invoiceRepo.existsById(invoiceId);
    }
}
