package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.InvoiceEntity;
import com.AltGame.AltGame.Entity.VwInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VwInvoiceRepo extends JpaRepository<VwInvoiceEntity, Integer> {

    List<VwInvoiceEntity> findByBuyer(String buyer);
    List<VwInvoiceEntity> findByNoInvoice(String id);
    VwInvoiceEntity findByBidId(int bidId);
    List<VwInvoiceEntity> findBySellerOrBuyer(String seller, String buyer);
}
