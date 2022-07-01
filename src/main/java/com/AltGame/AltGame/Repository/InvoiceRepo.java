package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InvoiceRepo extends JpaRepository<InvoiceEntity, Integer> {
    @Query(value = "SELECT no_invoice FROM invoices ORDER BY invoice_id DESC LIMIT 1", nativeQuery = true)
    String lastNoInvoice();

    @Query(value = "SELECT invoice_id FROM invoices ORDER BY invoice_id DESC LIMIT 1", nativeQuery = true)
    Integer lastInvoiceId();
    InvoiceEntity findByNoInvoice(String noInvoice);

    boolean existsByNoInvoice(String noInvoice);
}
