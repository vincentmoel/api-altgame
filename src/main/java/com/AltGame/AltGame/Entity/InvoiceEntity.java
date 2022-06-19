package com.AltGame.AltGame.Entity;

import lombok.Data;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="invoices")
@Data
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="invoice_id")
    private Integer invoiceId;

    @Column(name="bid_id")
    private Integer bidId;

    @Column(name="no_invoice")
    private String noInvoice;

    @Column(name="status")
    private String status;

    @Lob
    @Column(name="image")
    private byte image;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;
}
