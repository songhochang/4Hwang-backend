package com.green.sahwang.mypage.dto.res;

import com.green.sahwang.entity.enumtype.PurchaseStatus;
import lombok.Data;

@Data
public class OrderProgressResDto {

    PurchaseStatus status;

}