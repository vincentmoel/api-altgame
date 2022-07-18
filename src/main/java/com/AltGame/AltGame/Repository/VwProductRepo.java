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
    List<VwProductEntity> findAllByStatusIn(List<String> statuses);

    List<VwProductEntity> findByStatusAndNameLike(String status, String search);
    List<VwProductEntity> findByStatusAndUsername(String status, String username);

    boolean existsByProductId(int productId);

    VwProductEntity findByProductId(int productId);

    List<VwProductEntity> findByUsernameAndStatusOrStatus(String username, String active, String waiting);

    List<VwProductEntity> findByUsernameAndStatusIn(String username, List<String> statuses);
}
