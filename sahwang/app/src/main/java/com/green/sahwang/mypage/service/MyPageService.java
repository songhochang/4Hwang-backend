package com.green.sahwang.mypage.service;

import com.green.sahwang.mypage.dto.req.MemberInfoReqDto;
import com.green.sahwang.mypage.dto.req.ReviewCreateReqDto;
import com.green.sahwang.mypage.dto.req.ReviewUpdateReqDto;
import com.green.sahwang.mypage.dto.res.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MyPageService {
    OrderProgressResDto getOrderProgress(UserDetails userDetails);

    SaleProgressResDto getSaleProgress(UserDetails userDetails);

    List<OrderListResDto> getOrderList(UserDetails userDetails, int pageNum, int size);

//    String getSaleList(UserDetails userDetails, int pageNum, int size);
    List<SaleListResDto> getSaleList(UserDetails userDetails, int pageNum, int size);

    List<WishListCategoryResDto> getWishCategoryList(UserDetails userDetails, int pageNum, int size);

    List<WishListProductResDto> getWishProductList(UserDetails userDetails, int pageNum, int size);

    List<MyReviewResDto> getReviewList(UserDetails userDetails, int pageNum, int size);

    void reviewCreate(UserDetails userDetails, MultipartFile file, ReviewCreateReqDto reviewCreateReqDto);

    void reviewUpdate(UserDetails userDetails, ReviewUpdateReqDto reviewUpdateReqDto);

    void reviewDelete(UserDetails userDetails, Long reviewId);

    MemberInfoResDto updateMemberInfo(UserDetails userDetails, MemberInfoReqDto memberInfoReqDto);

    void approveVerifiedSale(UserDetails userDetails, Long pendingSaleId);

    void rejectVerifiedSale(UserDetails userDetails, Long pendingSaleId);
}
