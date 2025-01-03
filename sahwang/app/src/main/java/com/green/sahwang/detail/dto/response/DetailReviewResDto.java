package com.green.sahwang.detail.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DetailReviewResDto {

    String brandName;
    String productName;
    int productPrice;
    int productSize;
    double starAverage;
    int oneStarCount;
    int twoStarCount;
    int threeStarCount;
    int fourStarCount;
    int fiveStarCount;
    int favoriteCount;
    List<ReviewResDto> reviewResDtoList;

}
