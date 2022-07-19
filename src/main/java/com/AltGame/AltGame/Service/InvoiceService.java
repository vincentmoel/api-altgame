package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.InvoiceDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Entity.InvoiceEntity;
import com.AltGame.AltGame.Entity.VwInvoiceEntity;
import com.AltGame.AltGame.Entity.VwUserEntity;
import com.AltGame.AltGame.Repository.BidRepo;
import com.AltGame.AltGame.Repository.InvoiceRepo;
import com.AltGame.AltGame.Repository.VwInvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepo invoiceRepo;
    @Autowired
    VwInvoiceRepo vwInvoiceRepo;
    @Autowired
    ProductService productService;
    @Autowired
    BidRepo bidRepo;

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
            invoiceEntity.setNoInvoice(generate_noInvoice());
        }
        invoiceEntity.setCreatedAt(timestamp);
        invoiceEntity.setUpdatedAt(timestamp);
        invoiceEntity.setStatus("unpaid");
        invoiceRepo.save(invoiceEntity);
        return true;
    }
    public void update(InvoiceDto invoiceDto) throws IOException {
        InvoiceEntity invoiceEntity = invoiceRepo.findByNoInvoice(invoiceDto.getNoInvoice());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoiceEntity.setAddress(invoiceDto.getAddress());
        invoiceEntity.setStatus("paid");
        invoiceEntity.setImage(invoiceDto.getImage().getBytes());
        invoiceEntity.setUpdatedAt(timestamp);
        BidEntity bidEntity = bidRepo.findByBidId(invoiceEntity.getBidId());
        productService.setProductStatus(bidEntity.getProductId(), "sold");
        invoiceRepo.save(invoiceEntity);
    }

    public String generate_noInvoice(){
        String lastNoIncoice = invoiceRepo.lastNoInvoice();
        String[] last =  lastNoIncoice.split("-");
        String[] noInvoice = new String[3];

        noInvoice[0] = "INV";
        noInvoice[1] = dateNowFormatYMD();
        if(noInvoice[1].equals(last[1])){
            int ai = Integer.parseInt(last[2]) + 1;
            noInvoice[2] = String.format("%05d",ai);
            return String.join("-", noInvoice);
        }
        noInvoice[2] = "00001";
        return String.join("-", noInvoice);
    }
    public String dateNowFormatYMD(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }
    public boolean exitsByNoInvoice(String noInvoice){
        return invoiceRepo.existsByNoInvoice(noInvoice);
    }
//
//    public List<VwInvoiceEntity> index(Optional<VwUserEntity> vwUserEntity) {
//        if(Objects.equals(vwUserEntity.get().getRole(), "buyer")){
//            return vwInvoiceRepo.findByBuyer(vwUserEntity.get().getUsername());
//        }
//        return vwInvoiceRepo.findBySellerOrBuyer(vwUserEntity.get().getUsername(),vwUserEntity.get().getUsername());
//    }

    public List<VwInvoiceEntity> index(String usernameBuyer)
    {
        return vwInvoiceRepo.findByBuyer(usernameBuyer);
    }


    public VwInvoiceEntity showByBidId(int bidId) {
        return vwInvoiceRepo.findByBidId(bidId);
    }
}
