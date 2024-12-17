package com.green.sahwang.product.mapper;

import com.green.sahwang.dto.response.ProductWithSaleInfoDto;
import com.green.sahwang.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductWithSaleInfoDto> findProductsWithInUsedSaleInfo(Pageable pageable, @Param("dtype") String dtype);

    Product findProductById(@Param("productId") Long productId);
}
