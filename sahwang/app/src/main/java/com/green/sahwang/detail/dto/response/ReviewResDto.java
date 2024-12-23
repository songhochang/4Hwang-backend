package com.green.sahwang.detail.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResDto {

    MemberDetailReviewResDto memberDetailReviewResDto;
    Long reviewId;
    double star;
    String content;
    LocalDateTime reviewCreationDate;
    LocalDateTime reviewModifiedDate;
    ReviewImageResDto reviewImageResDto;
    int favoriteCount;

}
