package com.green.sahwang.mypage.mapper;

import com.green.sahwang.dto.response.ImageResDto;
import com.green.sahwang.mypage.dto.res.SaleListResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface SaleMapper {

    List<SaleListResDto> findVerifiedSaleList(@Param("memberId") Long memberId, Pageable pageable);

    List<SaleListResDto> findWaitingSaleList(@Param("memberId") Long memberId, Pageable pageable);

    List<SaleListResDto> findRejectedSaleList(@Param("memberId") Long memberId, Pageable pageable);

    // 해당 saleId에 대한 user 이미지 리스트 조회
    List<ImageResDto> findUserImages(Long saleId);

    // 해당 verifiedSaleId에 대한 검증된 이미지 리스트 조회
    List<ImageResDto> findVerifiedImages(Long verifiedSaleId);
}
