package com.green.sahwang.dto.request.cart;

import lombok.Data;

@Data
public class CartProductsRemoveReqDto {

    private Long productId;
    private int price;
    private int quantity;
}
