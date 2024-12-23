package com.green.sahwang.repository;

import com.green.sahwang.adminpage.dto.ReviewManageDto;
import com.green.sahwang.entity.Member;
import com.green.sahwang.entity.PurchaseProduct;
import com.green.sahwang.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPurchaseProductIn(List<PurchaseProduct> purchaseProducts);

    Page<Review> findAllByPurchaseProductIn(List<PurchaseProduct> purchaseProducts, Pageable pageable);

    @EntityGraph(attributePaths = {"member", "purchaseProduct"})
    Page<Review> findAllByMember(Member member, Pageable pageable);

    List<Review> findAllByMember(Member member);

    Page<Review> findAll(Pageable pageable);

}
