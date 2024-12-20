package com.green.sahwang.entity;

import com.green.sahwang.entity.enumtype.SaleStatus;
import com.green.sahwang.inspection.enumtype.InspectionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    private int tradePrice;

    private int quantity;

    private LocalDateTime tradeCompletedDate;

    private LocalDateTime createdDate;

//    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;
}
