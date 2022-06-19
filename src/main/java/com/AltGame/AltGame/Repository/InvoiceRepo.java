package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InvoiceRepo extends JpaRepository<InvoiceEntity, Integer> {
}
