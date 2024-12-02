package com.green.sahwang.mypage.dto.res;

import com.green.sahwang.entity.enumtype.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleListResDto {
    LocalDateTime createdDate;
    String saleId;
    SaleStatus saleStatus;
    List<SaleDetailResDto> saleDetailResDtoList;
}