package com.green.sahwang.repository;

import com.green.sahwang.entity.Brand;
import com.green.sahwang.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByBrandAndDtype(Brand brand, Pageable pageable, String dtype);
}
