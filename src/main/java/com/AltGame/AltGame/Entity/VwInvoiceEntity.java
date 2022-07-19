package com.AltGame.AltGame.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="vw_invoices")
@Data
public class VwInvoiceEntity {
    @Id
    @Column(name="invoice_id")
    private Integer invoiceId;

    @Column(name="no_invoice")
    private String noInvoice;

    @Column(name = "address")
    private String address;

    @Column(name="status")
    private String status;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name ="bid_id")
    private int bidId;

    @Column(name ="bid_price")
    private int bidPrice;

    @Column(name = "buyer")
    private String buyer;

    @Column(name="phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "product_price")
    private int productPrice;

    @Lob
    @Column(name="image")
    private byte[] image;

    @Column(name = "seller")
    private String seller;
}
