package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Entity.VwProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface VwProductRepo extends JpaRepository<VwProductEntity, Integer> {

}
